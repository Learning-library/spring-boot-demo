package com.mq.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
//public class SimpleApplication implements CommandLineRunner {
public class SimpleApplication {
    static Logger logger = LogManager.getLogger();

    //	@Autowired
//	JmsTemplate jmsTemplate;
    public static void main(String[] args) {
        ConfigurableApplicationContext content = SpringApplication.run(SimpleApplication.class, args);

        //Test Bean
        String[] beanNames = content.getBeanDefinitionNames();
        for (String name : beanNames) {
            logger.info("beanName: {}", name);
        }
    }

//	@Override
//	public void run(String... strings) throws Exception {
//		jmsTemplate.send("my-destination", new Msg()); //3

//	}
}
