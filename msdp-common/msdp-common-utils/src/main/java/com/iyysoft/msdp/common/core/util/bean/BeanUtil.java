//package com.iyysoft.msdp.common.utils.bean;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cglib.beans.BeanCopier;
//import org.springframework.cglib.beans.BeanMap;
//
//import java.lang.reflect.Field;
//import java.util.Map;
//
///**
// * 基于CGlib，扩展BeanUtils
// * @author chimao
// * @date 9:01 2018-05-28
// */
//public final class BeanUtil extends org.springframework.beans.BeanUtils {
//
//  protected BeanUtil() {
//    /* 保护 */
//  }
//
//  /**
//   * 实例化对象
//   * @param clazz 类
//   * @return 对象
//   */
//  @SuppressWarnings("unchecked")
//  public static <T> T newInstance(Class<?> clazz) {
//    try {
//      return (T) clazz.newInstance();
//    } catch (InstantiationException e) {
//      throw new RuntimeException(e);
//    } catch (IllegalAccessException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//
//  /**
//   * 实例化对象
//   *
//   * @param clazzStr 类名
//   * @return {T}
//   */
//  public static <T> T newInstance(String clazzStr) {
//    try {
//      Class<?> clazz = Class.forName(clazzStr);
//      return newInstance(clazz);
//    } catch (ClassNotFoundException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//
//  /**
//   * copy 对象属性到另一个对象，默认不使用Convert
//   *
//   * @param clazz 类名
//   * @return {T}
//   */
//  public static <T> T copy(Object src, Class<T> clazz) {
//    BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
//    T to = newInstance(clazz);
//    copier.copy(src, to, null);
//    return to;
//  }
//
//
//  /**
//   * 拷贝对象
//   *
//   * @param src 源对象
//   * @param dist 需要赋值的对象
//   */
//  public static void copy(Object src, Object dist) {
//    BeanCopier copier = BeanCopier.create(src.getClass(), dist.getClass(), false);
//    copier.copy(src, dist, null);
//  }
//
//
//  /**
//   * 将对象装成map形式 注意：生成的是unmodifiableMap
//   *
//   * @param src 源对象
//   * @return {Map<K, V>}
//   */
//  @SuppressWarnings("unchecked")
//  public static <K, V> Map<K, V> toMap(Object src) {
//    return BeanMap.create(src);
//  }
//
//  /**
//   * 复制bean属性,忽略null属性
//   */
//  public static void copyPropertiesIgnoreNull(Object from,Object to){
//    copyProperties(from,to,new NullAwareBeanUtilsBean());
//  }
//
//  /**
//   * 复制bean，不过滤null属性
//   * @param from
//   * @param to
//   */
//  public static void copyPropertiesAllowNull(Object from,Object to){
//    copyProperties(from,to,new BeanUtilsBean());
//  }
//
//  private static void copyProperties(Object from,Object to,BeanUtilsBean bub){
//    try {
//      bub.copyProperties(to,from);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public static boolean ContainElements(Object obj) {
//    final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
//    boolean flag = false;
//    int plus = 0;
//    for (Field f : obj.getClass().getDeclaredFields()) {
//      f.setAccessible(true);
//      //logger.info(f.getName());
//      try {
//        if (f.get(obj) != null) {
//          plus++;
//        }
//      } catch (IllegalAccessException e) {
//        logger.error("反射失败", e);
//      }
//    }
//    if(plus >=2) {
//      flag = true;
//    }
//    return flag;
//  }
//}
