package com.sdpm.smart.farming.config;

import com.sdpm.smart.farming.devicemgr.initializer.BridgerInitializer;
import com.sdpm.smart.farming.devicemgr.initializer.DeviceInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 应用初始化
 *
 * @author rukey
 */
@Component
public class ApplicationInitialization implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationInitialization.class);
    @Value("${auth.username}")
    private String authUsername;
    @Value("${auth.password}")
    private String authPassword;
    @Autowired
    private DeviceInitializer deviceInitializer;

    @Autowired
    private BridgerInitializer bridgerInitializer;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 初始化设备模板
        deviceInitializer.init();

        // 初始化桥接器
        bridgerInitializer.init();

    }
}
