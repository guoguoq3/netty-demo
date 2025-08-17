package org.guoguo.nettydemo.nettyTest.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SampleXxlJob {
    private static final Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    /**
     * 1. 简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        logger.info("XXL-Job 示例任务开始执行...");
        
        // 任务执行逻辑
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        XxlJobHelper.log("当前时间: {}", currentTime);
        XxlJobHelper.log("执行简单的示例任务");
        
        // 模拟任务执行
        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("执行步骤 {}...", i+1);
            Thread.sleep(1000);
        }
        
        logger.info("XXL-Job 示例任务执行完成");
    }

    /**
     * 2. 分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        // 实际任务逻辑 - 根据分片参数处理不同数据
        for (int i = 0; i < 3; i++) {
            XxlJobHelper.log("分片 {} 处理第 {} 条数据", shardIndex, i);
            Thread.sleep(1000);
        }
    }
}
