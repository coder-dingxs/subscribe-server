package xyz.dingxs.subscribe.sniff.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.dingxs.subscribe.sniff.service.SniffService;

/**
 * 嗅探任务
 *
 * @author dingxs
 */
@EnableScheduling
@Component
public class SniffTask {

    @Autowired
    private SniffService sniffService;

    private final Logger logger = LoggerFactory.getLogger(SniffTask.class);

    @Scheduled(cron = "${sniff-task.cron}")
    public void sniff() {

        logger.debug("start sniff");
        Boolean res = sniffService.sniff();
        sniffService.setSniffRes(res);
        logger.debug("end sniff");

    }
}
