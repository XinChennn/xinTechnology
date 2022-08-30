package cn.ixinjiu.springTask.test;

import cn.hutool.core.date.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by XinChen on 2022-08-30
 *
 * @Description:TODO SpringTask_Test
 */
@Component
public class SpringTaskTest {
    @Scheduled(fixedRate = 1000)  // 上一次执行开始时间点之后1秒再执行,等价于@Scheduled(fixedRateString = “1000”)
    public void task1() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ":task1--->" + DateUtil.date());
        Thread.sleep(2000);
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 2000) // 第一次执行延后1秒，后面等上一次执行结束之后2秒再执行
    @Scheduled(fixedDelay = 2000) // 上一次执行结束之后2秒再执行,等价于@Scheduled(fixedDelayString = “2000”)
//    @Scheduled(cron = "*/1 * * * * *") // 支持cron表达式，当前表示一秒执行一次
//    0 0 * * * * 整点执行一次.
//    */10 * * * * * 每十秒执行一次
//    0 0 8-10 * * * 每天8点、9点和10点执行
//    0 0 6,19 * * * 每天上午6：00和下午7:00执行。
//    0 0/30 8-10 * * * 8-10点之间30分钟执行一次
//    0 0 9-17 * * MON-FRI 每个工作日9-17点之间整点执行
//    0 0 0 25 12 ? 圣诞节执行
//    秒 分 时 天 月 星期几
    public void test2() {
        System.err.println(Thread.currentThread().getName() + ":task2--->" + DateUtil.date());
    }
}
