package com.iyysoft.msdp.common.core.util.text;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.iyysoft.msdp.common.core.util.lang.StringUtil;

/**
 * 工具类
 *
 * @author chimao
 * @date 16:33 2018-07-18
 */
public class JpinyinUtil {

    /**
     * 判断一个字是否是多音字
     *
     * @param chinese
     * @return
     */
    public static boolean hasMultiPinyin(char chinese) {
        return PinyinHelper.hasMultiPinyin(chinese);
    }

    /**
     * 将一段话转成拼音，“，”是每个字的分隔符，最后一个参数和上面的一样
     *
     * @param chinese
     * @return
     */
    public static String convertToPinyinString(String chinese, String separator) {
        try {
            String separ = ",";
            if (StringUtil.isNotBlank(separator)) {
                separ = separator;
            }
            return PinyinHelper.convertToPinyinString(chinese, separ, PinyinFormat.WITH_TONE_MARK);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输出首字母
     *
     * @param chinese
     * @return
     */
    public static String getShortPinyin(String chinese) {
        try {
            return PinyinHelper.getShortPinyin(chinese);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回简体字。繁体字转为简体字
     *
     * @param chinese
     * @return
     */
    public static String convertToSimplifiedChinese(String chinese) {
        return ChineseHelper.convertToSimplifiedChinese(chinese);
    }

    /**
     * 返回繁体字。简体字转为繁体字
     *
     * @param chinese
     * @return
     */
    public static String convertToTraditionalChinese(String chinese) {
        return ChineseHelper.convertToTraditionalChinese(chinese);
    }

    /**
     * 返回是否有汉字
     *
     * @param chinese
     * @return
     */
    public static boolean containsChinese(String chinese) {
        return ChineseHelper.containsChinese(chinese);
    }

    /**
     * 返回汉字的拼音
     * PinyinFormat.WITH_TONE_MARK为[zhòng, chóng]的形式
     * PinyinFormat.WITH_TONE_NUMBER为[zhong4, chong2]的形式
     * PinyinFormat.WITHOUT_TONE为[zhong, chong]
     *
     * @param chinese
     * @return
     */
    public static String[] convertToPinyinArray(char chinese, PinyinFormat pformat) {
        if (pformat instanceof PinyinFormat) {
            if (pformat.equals(PinyinFormat.WITH_TONE_MARK))
                return PinyinHelper.convertToPinyinArray(chinese, PinyinFormat.WITH_TONE_MARK);
            else if (pformat.equals(PinyinFormat.WITH_TONE_NUMBER))
                return PinyinHelper.convertToPinyinArray(chinese, PinyinFormat.WITH_TONE_NUMBER);
            else if (pformat.equals(PinyinFormat.WITHOUT_TONE))
                return PinyinHelper.convertToPinyinArray(chinese, PinyinFormat.WITHOUT_TONE);
        }
        return null;
    }


    //psvm
    /*
    public static void main(String[] args) throws PinyinException {
        //
         // 把字的读音转出来：
         // PinyinFormat.WITH_TONE_MARK为[zhòng, chóng]的形式
         // PinyinFormat.WITH_TONE_NUMBER为[zhong4, chong2]的形式
         // PinyinFormat.WITHOUT_TONE为[zhong, chong]
//        System.out.println(Arrays.toString(PinyinHelper.convertToPinyinArray('重', PinyinFormat.WITH_TONE_MARK)));
//        System.out.println(Arrays.toString(PinyinHelper.convertToPinyinArray('重', PinyinFormat.WITH_TONE_NUMBER)));
//        System.out.println(Arrays.toString(PinyinHelper.convertToPinyinArray('重', PinyinFormat.WITHOUT_TONE)));

        System.out.println(JpinyinUtil.convertToPinyinArray('重', PinyinFormat.WITH_TONE_NUMBER));


        //将一段话转成拼音，“，”是每个字的分隔符，最后一个参数和上面的一样
        //System.out.println(PinyinHelper.convertToPinyinString("我是你大爷", ",", PinyinFormat.WITH_TONE_MARK));
        //
        System.out.println(JpinyinUtil.convertToPinyinString("我是你大爷", ","));

        // 判断一个字是否是多音字
        //System.out.println(PinyinHelper.hasMultiPinyin('重'));
        System.out.println(JpinyinUtil.hasMultiPinyin('重'));

        // 输出首字母，例如这里的结果是：zl
        //System.out.println(PinyinHelper.getShortPinyin("重量"));
        System.out.println(JpinyinUtil.getShortPinyin("重量"));

        // 将一段话中的繁体字转为简体字，这里的结果是：义义
        //System.out.println(ChineseHelper.convertToSimplifiedChinese("義义"));
        System.out.println(JpinyinUtil.convertToSimplifiedChinese("義义"));

        // 将一段话中的简体字转为繁体字，这里的结果是：義義
        //System.out.println(ChineseHelper.convertToTraditionalChinese("義义"));
        System.out.println(JpinyinUtil.convertToTraditionalChinese("義义"));

        //判断依据话里是否有汉字
        //System.out.println(ChineseHelper.containsChinese("123我234asfs12"));
        System.out.println(JpinyinUtil.containsChinese("123我234asfs12"))
    }
    */

}
