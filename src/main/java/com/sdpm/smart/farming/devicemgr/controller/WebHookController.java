package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.service.WebHookService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Receive request from emqx_web_hook
 * Doc: https://docs.emqx.io/broker/latest/cn/advanced/webhook.html
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/webhook")
@Api(tags = {"WebHook"})
public class WebHookController {
    @Autowired
    private WebHookService webHookService;

    @PostMapping
    public RestMessage onWebHook(@RequestBody Map<String, String> param) {
        webHookService.handleWebHook(param);
        return RestMessageUtil.objectToRestMessage("");
    }
}
