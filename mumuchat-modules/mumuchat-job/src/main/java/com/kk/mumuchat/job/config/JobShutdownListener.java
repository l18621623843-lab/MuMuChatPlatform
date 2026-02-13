package com.kk.mumuchat.job.config;

import com.kk.mumuchat.job.util.JobInvokeUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * 定时任务关闭监听器
 * 监听Spring应用上下文关闭事件，在应用关闭时清理定时任务相关资源
 *
 * @author mumuchat
 */
@Component
public class JobShutdownListener implements ApplicationListener<ContextClosedEvent> {

    /**
     * 处理应用上下文关闭事件
     * 当Spring应用上下文开始关闭时，通知JobInvokeUtil清理相关静态资源
     *
     * @param event 应用上下文关闭事件
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        JobInvokeUtil.onContextClosed();
    }
}
