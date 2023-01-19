package org.coastline.one.common.test;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.coastline.one.common.tool.PinyinTool;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jay.H.Zou
 * @date 2023/1/19
 */
public class TestPinyinTool {

    @Test
    public void testChineseToPinyin() throws BadHanyuPinyinOutputFormatCombination {
        String pinyin = PinyinTool.toPinyin("邹华健clever");
        Assert.assertEquals("zouhuajianclever", pinyin);
    }

}
