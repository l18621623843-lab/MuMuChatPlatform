package com.kk.mumuchat.common.core.utils.core;

import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.DateUtil;
import com.kk.mumuchat.common.core.web.entity.base.BaseEntity;
import com.kk.mumuchat.common.core.web.entity.query.TimeRangeQuery;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 *
 * @author xueyi
 */
@Slf4j
public class CollUtil extends cn.hutool.core.collection.CollUtil {

    /**
     * 将单个对象封装成列表
     *
     * @param singleInfo 单个对象
     * @param <T>        对象类型
     * @return 包含单个对象的列表
     */
    public static <T> List<T> singleList(T singleInfo) {
        return new ArrayList<>() {{
            add(singleInfo);
        }};
    }

    /**
     * 判断集合是否非空
     *
     * @param collection 集合对象
     * @return 如果集合不为 null 且包含元素，返回 true；否则返回 false
     */
    public static boolean isNotNull(Collection<?> collection) {
        return !isNull(collection);
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合对象
     * @return 如果集合为 null，返回 true；否则返回 false
     */
    public static boolean isNull(Collection<?> collection) {
        return collection == null;
    }

    /**
     * 判断集合中是否不包含指定值
     *
     * @param collection 集合对象
     * @param value      要检查的值
     * @return 如果集合中不包含该值，返回 true；否则返回 false
     */
    public static boolean notContains(Collection<?> collection, Object value) {
        return !contains(collection, value);
    }

    /**
     * 根据分页参数截取列表子集
     *
     * <p>如果 pageNum 或 pageSize 为 null 或小于等于 0，则返回原始列表的完整副本；
     * 否则根据 pageNum 和 pageSize 计算起始索引并截取对应区间的数据。
     *
     * <p>该方法避免越界访问，并在请求页超出数据范围时返回空列表，
     * 确保调用方不会因无效页码而抛出异常。
     *
     * @param fullList 原始数据列表（不能为 null）
     * @param pageNum  当前页码（从 1 开始计数）
     * @param pageSize 每页条目数量
     * @return 分页后的子列表（不会为 null）
     */
    public static <T> List<T> getPageData(List<T> fullList, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
            // 分页参数无效时，直接返回全部数据
            return new ArrayList<>(fullList);
        }

        int totalSize = fullList.size();
        int startIndex = (pageNum - 1) * pageSize; // 计算当前页起始索引
        int endIndex = Math.min(startIndex + pageSize, totalSize); // 控制结束索引不越界

        if (startIndex >= totalSize) {
            // 请求页超出数据范围，返回空列表
            return Collections.emptyList();
        }

        // 截取当前页对应的数据范围
        return fullList.subList(startIndex, endIndex);
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 查询转多次批量
     *
     * @param queryCodes 查询编码集合
     * @param function   处理方法
     * @return 查询结果集合
     */
    public static <C, T> List<T> selectListToBatch(Collection<C> queryCodes, Function<List<C>, List<T>> function) {
        return selectListToBatch(queryCodes, function, NumberUtil.TwoHundred);
    }

    /**
     * 查询转多次批量
     *
     * @param queryCodes 查询编码集合
     * @param function   处理方法
     * @param batchSize  批处理数量
     * @return 查询结果集合
     */
    public static <C, T> List<T> selectListToBatch(Collection<C> queryCodes, Function<List<C>, List<T>> function, Integer batchSize) {
        return Optional.ofNullable(queryCodes)
                .filter(CollUtil::isNotEmpty)
                .map(ArrayList::new)
                .map(codes -> ListUtil.partition(codes, ObjectUtil.getObjOrNullElse(batchSize, NumberUtil.TwoHundred)))
                .map(codeList -> codeList.stream()
                        .map(function)
                        .filter(CollUtil::isNotEmpty)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList())
                )
                .orElseGet(ArrayList::new);
    }


    /**
     * 多次批量处理
     *
     * @param handleList 处理信息集合
     * @param consumer   处理方法
     */
    public static <C> Integer handleListToBatch(List<C> handleList, Function<List<C>, Integer> consumer) {
        return handleListToBatch(handleList, consumer, NumberUtil.TwoHundred);
    }

    /**
     * 多次批量处理
     *
     * @param handleList 处理信息集合
     * @param consumer   处理方法
     * @param batchSize  批处理数量
     */
    public static <C> Integer handleListToBatch(List<C> handleList, Function<List<C>, Integer> consumer, Integer batchSize) {
        AtomicInteger result = new AtomicInteger(NumberUtil.Zero);
        Optional.ofNullable(handleList)
                .filter(CollUtil::isNotEmpty)
                .map(list -> ListUtil.partition(list, ObjectUtil.getObjOrNullElse(batchSize, NumberUtil.TwoHundred)))
                .ifPresent(batchList -> batchList.forEach(list -> result.addAndGet(consumer.apply(list))));
        return result.get();
    }

    /**
     * 多次批量处理
     *
     * @param handleList 处理信息集合
     * @param consumer   处理方法
     */
    public static <C> void handleListToBatch(List<C> handleList, java.util.function.Consumer<List<C>> consumer) {
        handleListToBatch(handleList, consumer, NumberUtil.TwoHundred);
    }

    /**
     * 多次批量处理
     *
     * @param handleList 处理信息集合
     * @param consumer   处理方法
     * @param batchSize  批处理数量
     */
    public static <C> void handleListToBatch(List<C> handleList, java.util.function.Consumer<List<C>> consumer, Integer batchSize) {
        Optional.ofNullable(handleList)
                .filter(CollUtil::isNotEmpty)
                .map(list -> ListUtil.partition(list, ObjectUtil.getObjOrNullElse(batchSize, NumberUtil.TwoHundred)))
                .ifPresent(batchList -> batchList.forEach(consumer));
    }

    /**
     * 按批次处理数据的通用方法（基于时间范围分批处理）
     *
     * @param query       查询条件对象，用于设置分页和时间范围等参数
     * @param dataFetcher 数据获取函数，根据查询条件获取数据列表
     * @param processor   数据处理函数，处理每批次的数据
     * @param processType 处理类型名称，用于日志记录和标识
     */
    public static <Q extends BaseEntity, D extends BaseEntity> void processInBatchesWithCreateTime(Q query
            , Function<Q, List<D>> dataFetcher, java.util.function.Consumer<List<D>> processor, String processType) {
        processInBatchesWithCreateTime(LocalDateTime.now(), query, dataFetcher, processor, processType);
    }

    /**
     * 按批次处理数据的通用方法（基于时间范围分批处理）
     *
     * @param startTime   开始时间，用于设置初始查询的时间范围
     * @param query       查询条件对象，用于设置分页和时间范围等参数
     * @param dataFetcher 数据获取函数，根据查询条件获取数据列表
     * @param processor   数据处理函数，处理每批次的数据
     * @param processType 处理类型名称，用于日志记录和标识
     */
    public static <Q extends BaseEntity, D extends BaseEntity> void processInBatchesWithCreateTime(LocalDateTime startTime, Q query
            , Function<Q, List<D>> dataFetcher, java.util.function.Consumer<List<D>> processor, String processType) {
        if (ObjectUtil.isNull(startTime)) {
            throw new UtilException("开始时间不能为空");
        }
        log.info("【{}】开始处理数据", processType);
        // 第一次查询，只查一条数据判断是否存在需要处理的数据
        query.setLimitNum(NumberUtil.One);
        TimeRangeQuery timeRangeQuery = new TimeRangeQuery();
        timeRangeQuery.setEndTime(startTime);
        timeRangeQuery.setEndTimeEqual(Boolean.FALSE);
        timeRangeQuery.setDesc(Boolean.TRUE);
        query.setCreateTimeQuery(timeRangeQuery);

        List<D> firstBatchList = dataFetcher.apply(query);
        if (firstBatchList.isEmpty()) {
            log.info("【{}】数据查询结果为空，无需处理", processType);
            return; // 没有需要处理的数据，直接返回
        }

        log.info("【{}】发现需要处理的数据，开始处理", processType);

        // 处理第一批数据
        processor.accept(firstBatchList);
        AtomicInteger totalProcessed = new AtomicInteger(firstBatchList.size());

        // 后续批量处理，每次处理100条
        query.setLimitNum(NumberUtil.BATCH_SIZE);
        LocalDateTime batchEndTime = DateUtil.getEarliestTime(firstBatchList, D::getCreateTime, LocalDateTime::now);

        AtomicInteger batchCount = new AtomicInteger(1);
        while (true) {
            timeRangeQuery.setEndTime(batchEndTime);
            List<D> batchList = dataFetcher.apply(query);

            // 处理当前批次数据
            processor.accept(batchList);
            totalProcessed.addAndGet(batchList.size());
            batchCount.incrementAndGet();

            log.info("【{}】已处理第{}批次数据，当前批次数量：{}，累计处理数量：{}",
                    processType, batchCount.get(), batchList.size(), totalProcessed.get());

            // 当查询数小于批次数时，则认为不存在数据了
            if (batchList.isEmpty() || batchList.size() < NumberUtil.BATCH_SIZE) {
                break; // 没有更多数据或不足一批数据，终止循环
            }

            // 取到的batchEndTime应为批次数据中创建时间最早的数据
            batchEndTime = DateUtil.getEarliestTime(batchList, D::getCreateTime, LocalDateTime::now);
        }
        log.info("【{}】完成数据处理，总处理数量：{}", processType, totalProcessed.get());
    }
}