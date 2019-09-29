package com.iyysoft.msdp.dp.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 第三方登录
 *
 * @author waylen.chi
 * @date 2019-04-17 15:52:27
 */
@Data
@TableName("sys_user_third")
@EqualsAndHashCode(callSuper = true)
public class SysUserThird extends Model<SysUserThird> {
private static final long serialVersionUID = 1L;

    /**
   * 第三方类型 1-微信登录 2-支付宝登录 101-公众服务号 102-订阅号
   */
    private String thirdId;
    /**
   * 第三方ID
   */
    private String openId;
    /**
   * 用户ID
   */
    private String userId;
    /**
   * apikey
   */
    private String apikey;
    /**
   * 设备号
   */
    private String device;
    /**
   * 设备操作系统
   */
    private String deviceos;
    /**
   * 来源:Android,IOS,WEB,WEP,WECHAT,ALIPAY
   */
    private String froms;
    /**
   * 版本号
   */
    private String version;
    /**
   * token
   */
    private String token;
    /**
   * ip地址
   */
    private String ipaddr;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 修改时间
   */
    private LocalDateTime updateTime;
  
}
