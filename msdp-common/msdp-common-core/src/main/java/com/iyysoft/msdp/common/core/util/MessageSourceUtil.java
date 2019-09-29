package com.iyysoft.msdp.common.core.util;

import com.iyysoft.msdp.common.core.constant.MessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 消息内容工具
 * Created by Administrator on 2017/7/25 0025.
 */
public class MessageSourceUtil {

    private MessageSource messageSource;

    @Autowired
    public MessageSourceUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, Object[] args, String language) {
        Locale locale;
        if (language == null || language.equals(MessageConstants.MESSAGES_LANGUAGE_ZH_CN)) {
            locale = MessageConstants.LOCALE_ZH_CN;
        } else {
            locale = MessageConstants.LOCALE_EN_US;
        }
        return this.getMessage(code, args, locale);
    }

    public String getMessage(String code, String language) {
        return this.getMessage(code, null, language);
    }

    public String getMessage(String code) {
        return this.getMessage(code, new Object[]{});
    }

    public String getMessage(String code, String defaultMessage, Locale locale) {
        return this.getMessage(code, null, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, MessageConstants.MESSAGES_LANGUAGE_ZH_CN);
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return this.getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
