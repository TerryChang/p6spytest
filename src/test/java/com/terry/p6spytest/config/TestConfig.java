package com.terry.p6spytest.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.terry.p6spytest.config.p6spy.P6SpySqlFormatConfiguration;
import javax.annotation.PostConstruct;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {

    @PostConstruct
    public void initTestConfig() {
        // P6Spy 로그 메시지 포맷 설정
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpySqlFormatConfiguration.class.getName());
    }

}
