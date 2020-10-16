package com.sdpm.smart.farming.mqtt;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * EMQ管理API
 *
 * @author shirukai
 */
@Component
public class MQTTManagementAPI {
    @Value("${mqtt.api-prefix}")
    private String mqttAPIPrefix;
    @Value("${mqtt.api-auth}")
    private String mqttAPIAuth;

    public static void main(String[] args) throws IOException {
        Response response = Request.Get("http://154.8.222.152:8081/api/v4/clients/sfs-device-6cdf08f8-1")
                .addHeader("Authorization", "Basic YWRtaW46cHVibGlj")
                .execute();
        APIResponse apiResponse1 = response.handleResponse(httpResponse -> {
            APIResponse apiResponse = new APIResponse();
            int code = httpResponse.getStatusLine().getStatusCode();
            String contentType = httpResponse.getEntity().getContentType().getValue();
            InputStream content = httpResponse.getEntity().getContent();
            apiResponse.setCode(code);
            apiResponse.setContentType(contentType);
            if (200 != code) {
                apiResponse.setSuccess(false);
                apiResponse.setMsg(IOUtils.toString(content, StandardCharsets.UTF_8));
            } else {
                apiResponse.setSuccess(true);
                apiResponse.setMsg("");
            }
            if ("application/json".equals(contentType)) {
                apiResponse.setData(JSON.parse(IOUtils.toString(content, StandardCharsets.UTF_8)));
            } else {
                apiResponse.setData(content);
            }

            return apiResponse;
        });
        System.out.println(apiResponse1);
    }


}
