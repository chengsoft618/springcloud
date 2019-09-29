package com.iyysoft.msdp.common.core.util;

import com.iyysoft.msdp.common.core.constant.enums.REnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 消息内容管理
 * Created by Administrator on 2017/7/25 0025.
 */
@Slf4j
@Service
@Lazy(false)
public class MessageManager {

    private static final String PREFIX_KEY = "lang.";
    private static MessageSource messageSource;

    public static String getMsg(REnum key) {
        return getMsg(key, LocaleContextHolder.getLocale(), new Object[0]);
    }

    public static String getMsg(REnum key, String... arg) {
        return getMsg(key, LocaleContextHolder.getLocale(), arg);
    }

    public static String getMsg(REnum key, Locale locale, String... args) {
        Object[] oArgs = new Object[args.length];
        System.arraycopy(args, 0, oArgs, 0, args.length);
        return getMsg(key, locale, oArgs);
    }

    public static String getMsg(REnum key, Locale locale, Object[] args) {
        try {
            return messageSource.getMessage(PREFIX_KEY + key.getCode(), args, locale);
        } catch (NoSuchMessageException e) {
            log.warn("R enum {} no message exist", key);
            return null;
        }
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageManager.messageSource = messageSource;
    }
}
