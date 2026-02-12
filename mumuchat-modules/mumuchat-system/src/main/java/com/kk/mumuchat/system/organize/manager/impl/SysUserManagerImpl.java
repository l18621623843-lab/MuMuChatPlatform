package com.kk.mumuchat.system.organize.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.constant.basic.SqlConstants;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.security.utils.SecurityUserUtils;
import com.kk.mumuchat.common.web.entity.manager.impl.BaseManagerImpl;
import com.kk.mumuchat.system.api.organize.domain.dto.SysDeptDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysPostDto;
import com.kk.mumuchat.system.api.organize.domain.dto.SysUserDto;
import com.kk.mumuchat.system.api.organize.domain.po.SysUserPo;
import com.kk.mumuchat.system.api.organize.domain.query.SysUserQuery;
import com.kk.mumuchat.system.authority.domain.model.SysRoleConverter;
import com.kk.mumuchat.system.authority.mapper.SysRoleMapper;
import com.kk.mumuchat.system.organize.domain.merge.SysOrganizeRoleMerge;
import com.kk.mumuchat.system.organize.domain.merge.SysUserPostMerge;
import com.kk.mumuchat.system.organize.domain.model.SysDeptConverter;
import com.kk.mumuchat.system.organize.domain.model.SysPostConverter;
import com.kk.mumuchat.system.organize.domain.model.SysUserConverter;
import com.kk.mumuchat.system.organize.manager.ISysUserManager;
import com.kk.mumuchat.system.organize.mapper.SysDeptMapper;
import com.kk.mumuchat.system.organize.mapper.SysPostMapper;
import com.kk.mumuchat.system.organize.mapper.SysUserMapper;
import com.kk.mumuchat.system.organize.mapper.merge.SysOrganizeRoleMergeMapper;
import com.kk.mumuchat.system.organize.mapper.merge.SysUserPostMergeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kk.mumuchat.common.core.constant.basic.SqlConstants.LIMIT_ONE;

/**
 * 系统服务|组织模块|用户管理 数据封装层处理
 *
 * @author xueyi
 */
@Component
public class SysUserManagerImpl extends BaseManagerImpl<SysUserQuery, SysUserDto, SysUserPo, SysUserMapper, SysUserConverter> implements ISysUserManager {

    @Resource
    private SysUserPostMergeMapper userPostMergeMapper;

    @Resource
    private SysOrganizeRoleMergeMapper organizeRoleMergeMapper;

    @Resource
    private SysPostMapper postMapper;

    @Resource
    private SysPostConverter postConverter;

    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private SysDeptConverter deptConverter;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleConverter roleConverter;

    @Override
    public SysUserDto loginByAdminUser() {
        return mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>lambdaQuery()
                        .eq(SysUserPo::getUserType, SecurityConstants.UserType.ADMIN.getCode())
                        .last(SqlConstants.LIMIT_ONE)));
    }

    @Override
    public SysUserDto userLogin(String userName, String password) {
        SysUserDto userDto = mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>query().lambda()
                        .eq(SysUserPo::getUserName, userName)));
        // check password is true
        if (ObjectUtil.isNull(userDto) || !SecurityUserUtils.matchesPassword(password, userDto.getPassword())) {
            return null;
        }

        // select posts in user && select depts in post
        List<SysUserPostMerge> userPostMerges = userPostMergeMapper.selectList(
                Wrappers.<SysUserPostMerge>query().lambda()
                        .eq(SysUserPostMerge::getUserId, userDto.getId()));

        List<Long> postIds = userPostMerges.stream().map(SysUserPostMerge::getPostId).collect(Collectors.toList());
        Set<Long> roleDeptIds = new HashSet<>();
        // if exist posts, must exist depts
        if (CollUtil.isNotEmpty(userPostMerges)) {
            userDto.setPosts(postConverter.mapperDto(postMapper.selectByIds(postIds)));
            if (CollUtil.isNotEmpty(userDto.getPosts())) {
                Set<Long> deptIds = userDto.getPosts().stream().map(SysPostDto::getDeptId).collect(Collectors.toSet());
                List<SysDeptDto> depts = deptConverter.mapperDto(deptMapper.selectByIds(deptIds));
                depts.forEach(item -> {
                    if (StrUtil.isNotBlank(item.getAncestors())) {
                        roleDeptIds.add(item.getId());
                        List<String> deptIdStrList = StrUtil.splitTrim(item.getAncestors(), StrUtil.COMMA);
                        roleDeptIds.addAll(deptIdStrList.stream().map(ConvertUtil::toLong).collect(Collectors.toSet()));
                    }
                });
                Map<Long, SysDeptDto> deptMap = MapUtil.buildListToMap(depts, SysDeptDto::getId, Function.identity());
                userDto.getPosts().forEach(post -> post.setDept(deptMap.get(post.getDeptId())));
            }
        }
        // 是否为超管用户 ? 无角色集合 : 获取角色集合
        if (userDto.isAdmin()) {
            userDto.setRoles(new ArrayList<>());
        } else {
            // select roles in user
            List<SysOrganizeRoleMerge> organizeRoleMerges = organizeRoleMergeMapper.selectList(
                    Wrappers.<SysOrganizeRoleMerge>query().lambda()
                            .eq(SysOrganizeRoleMerge::getUserId, userDto.getId())
                            .func(i -> {
                                if (CollUtil.isNotEmpty(postIds)) {
                                    i.or().in(SysOrganizeRoleMerge::getPostId, postIds);
                                }
                            })
                            .func(i -> {
                                if (CollUtil.isNotEmpty(roleDeptIds)) {
                                    i.or().in(SysOrganizeRoleMerge::getDeptId, roleDeptIds);
                                }
                            }));
            userDto.setRoles(CollUtil.isNotEmpty(organizeRoleMerges)
                    ? roleConverter.mapperDto(roleMapper.selectByIds(organizeRoleMerges.stream().map(SysOrganizeRoleMerge::getRoleId).collect(Collectors.toList())))
                    : new ArrayList<>());
        }
        return userDto;
    }

    @Override
    public int updateUserProfile(Long id, String nickName, String sex, String profile) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getNickName, nickName)
                        .set(SysUserPo::getSex, sex)
                        .set(SysUserPo::getProfile, profile)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public int updateUserName(Long id, String userName) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getUserName, userName)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public int updateEmail(Long id, String email) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getEmail, email)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public int updatePhone(Long id, String phone) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getPhone, phone)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public int updateUserAvatar(Long id, String avatar) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getAvatar, avatar)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public int resetUserPassword(Long id, String password) {
        return baseMapper.update(null,
                Wrappers.<SysUserPo>update().lambda()
                        .set(SysUserPo::getPassword, password)
                        .eq(SysUserPo::getId, id));
    }

    @Override
    public SysUserDto checkUserNameUnique(Serializable id, String userName) {
        return mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>query().lambda()
                        .ne(SysUserPo::getId, id)
                        .eq(SysUserPo::getUserName, userName)
                        .last(LIMIT_ONE)));
    }

    @Override
    public SysUserDto checkNameUnique(Serializable id, String userName) {
        return mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>query().lambda()
                        .ne(SysUserPo::getId, id)
                        .eq(SysUserPo::getUserName, userName)
                        .last(LIMIT_ONE)));
    }

    @Override
    public SysUserDto checkPhoneUnique(Long id, String phone) {
        return mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>query().lambda()
                        .ne(SysUserPo::getId, id)
                        .eq(SysUserPo::getPhone, phone)
                        .last(LIMIT_ONE)));
    }

    @Override
    public SysUserDto checkEmailUnique(Long id, String email) {
        return mapperDto(baseMapper.selectOne(
                Wrappers.<SysUserPo>query().lambda()
                        .ne(SysUserPo::getId, id)
                        .eq(SysUserPo::getEmail, email)
                        .last(LIMIT_ONE)));
    }

    /**
     * 查询条件构造|列表查询
     *
     * @param query 数据查询对象
     * @return 条件构造器
     */
    @Override
    protected LambdaQueryWrapper<SysUserPo> selectListQuery(SysUserQuery query) {
        return super.selectListQuery(query)
                .func(i -> {
                    if (ObjectUtil.isNotNull(query.getPostId()))
                        i.apply(" id in ( select user_id from sys_user_post_merge where post_id = {0} ) ", query.getPostId());
                })
                .func(i -> {
                    if (ObjectUtil.isNotNull(query.getDeptId())) {
                        i.apply(" id in ( select upm.user_id from sys_user_post_merge upm " +
                                " left join sys_post p on upm.post_id = p.id where p.dept_id = {0} ) ", query.getDeptId());
                    }
                });
    }
}
