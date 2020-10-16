package com.sdpm.smart.farming.common.util;


import com.sdpm.smart.farming.common.rest.PageData;
import com.sdpm.smart.farming.common.rest.RestMessage;
import org.springframework.data.domain.Page;

/**
 * REST消息工具类
 * 用来返回统一格式
 *
 * @author rukey
 */
public class RestMessageUtil {
    public static RestMessage pageToRestMessage(Page page) {

        RestMessage restMessage = new RestMessage();

        PageData pd = new PageData();
        pd.setPageNum(page.getNumber() + 1);
        pd.setPages(page.getTotalPages());
        pd.setPageSize(page.getSize());
        pd.setTotal(page.getTotalElements());
        pd.setList(page.getContent());

        restMessage.setData(pd);

        return restMessage;

    }

    public static RestMessage objectToRestMessage(Object object) {

        RestMessage restMessage = new RestMessage();
        restMessage.setData(object);

        return restMessage;

    }
}
