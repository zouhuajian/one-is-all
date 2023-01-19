package org.coastline.one.common.tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <a href="https://github.com/belerweb/pinyin4j">Github</a>
 * <a href="https://juejin.cn/post/7024488901246976031">使用文档</a>
 * @author Jay.H.Zou
 * @date 2023/1/19
 */
public class PinyinTool {

    private static final String CHINESE_REGEX = "[\\u4E00-\\u9FA5]+";
    private static HanyuPinyinOutputFormat DEFAULT_OUTPUT_FORMAT;

    static {
        DEFAULT_OUTPUT_FORMAT = new HanyuPinyinOutputFormat();
        DEFAULT_OUTPUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        DEFAULT_OUTPUT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        DEFAULT_OUTPUT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    private PinyinTool(){
    }

    /**
     * chinese to pinyin
     *
     * @param chinese
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinyin(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        if(chinese == null) {
            return null;
        }
        char[] chars = chinese.trim().toCharArray();
        StringBuilder pinyin = new StringBuilder();
        for (char c : chars) {
            if (String.valueOf(c).matches(CHINESE_REGEX)) {
                pinyin.append(PinyinHelper
                        .toHanyuPinyinStringArray(c, DEFAULT_OUTPUT_FORMAT)[0]);
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }

}
