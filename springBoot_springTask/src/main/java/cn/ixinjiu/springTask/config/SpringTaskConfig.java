package cn.ixinjiu.springTask.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by XinChen on 2022-08-30
 *
 * @Description:TODO  多定时任务的并行调度与异步调度
 */
@Configuration
@Slf4j
public class SpringTaskConfig implements SchedulingConfigurer, AsyncConfigurer {

    /**
     * TODO 并行调度
     * @param taskRegistrar
     * @return void
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        // 配置线程池大小
        threadPoolTaskScheduler.setPoolSize(10);
        // 线程名称前缀
        threadPoolTaskScheduler.setThreadNamePrefix("spring-task-scheduler-");
        // 线程池关闭时最大等待时间，确保最后一定关闭
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        // 线程池关闭时等待所有任务执行结束
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 丢弃任务策略
        threadPoolTaskScheduler.setRejectedExecutionHandler( new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskScheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
        // 配置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(10);
        // 配置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(20);
        // 线程超时时间
        threadPoolTaskExecutor.setKeepAliveSeconds(100);
        // 队列容量
        threadPoolTaskExecutor.setQueueCapacity(50);
        // 线程名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix("spring-task--executor-");
        // 线程池关闭时最大等待时间，确保最后一定关闭
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        // 线程池关闭时等待所有任务执行结束
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 丢弃任务策略
        threadPoolTaskExecutor.setRejectedExecutionHandler( new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringTaskExceptionHandler();
    }

    class SpringTaskExceptionHandler implements AsyncUncaughtExceptionHandler{
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            log.error("SpringTaskExceptionHandler execute error",params);
        }
    }
}
