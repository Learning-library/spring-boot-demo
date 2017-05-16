package com.mq.consumer.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by changwenhu on 2017/5/16.
 */
@Component(value = "stringListener")
public class StringListener {
    Logger log = LogManager.getLogger(getClass());


    @JmsListener(destination = "123") //1
    public void handleMessage(String message) {
        log.info("TextMessage: {}", message);
    }
}
