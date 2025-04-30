package com.basic.myspringboot.runner;

import com.basic.myspringboot.config.CustomerVO;
import com.basic.myspringboot.property.MyBootProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;

    @Value("${myboot.age}")
    private int age;

    @Autowired
    private Environment environment;

    @Autowired
    private MyBootProperties properties;

    @Autowired
    private CustomerVO customerVO;

    private Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception{

        System.out.println("Logger 구현체: "+logger.getClass().getName());
        logger.debug("${myboot.name} = {}",name);
        logger.debug("${myboot.age} = {}",age);
        logger.debug("${myboot.fullName} = {}",environment.getProperty("myboot.fullName"));

        System.out.println("현재 활성화 된 CustomerVO: "+customerVO);


        logger.info("MyBootProperties getName(): {}",properties.getName());
        logger.info("MyBootProperties getAge(): {}",properties.getAge());
        logger.info("MyBootProperties getFullName(): {}",properties.getFullName());

        System.out.println("설정된 포트 번호: "+environment.getProperty("local.server.port"));

        System.out.println("VM argument foo: "+args.containsOption("foo"));
        System.out.println("Program Argument bar: "+args.containsOption("bar"));

        args.getOptionNames()
                .forEach(name -> System.out.println(name));
    }
}
