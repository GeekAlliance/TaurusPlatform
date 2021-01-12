package com.geekalliance.taurus.base.listener;

import com.geekalliance.taurus.base.init.InitBaseApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author maxuqiang
 * @description
 * @date 2019/12/30
 **/
@Slf4j
@Component
public class InitBaseApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private InitBaseApplicationService initBaseApplicationService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initSystem();
    }

    private void initSystem() {
        initBaseApplicationService.initApplication();
        initBaseApplicationService.initUser();
        initBaseApplicationService.initClient();
    }
}