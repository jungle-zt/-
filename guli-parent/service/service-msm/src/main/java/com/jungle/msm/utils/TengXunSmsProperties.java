package com.jungle.msm.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tengxun.sms")
@PropertySource(value="classpath:application.properties",  encoding="UTF-8")
public class TengXunSmsProperties  implements InitializingBean {
    private String secretId;
    private String secretKey;
    private String appId;
    private String sign;
    private String templateID;
    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String APP_ID;
    public static String SIGN;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {

        SECRET_ID=secretId;
        SECRET_KEY=secretKey;
        APP_ID=appId;
        SIGN=sign;
        TEMPLATE_ID=templateID;
    }
}
