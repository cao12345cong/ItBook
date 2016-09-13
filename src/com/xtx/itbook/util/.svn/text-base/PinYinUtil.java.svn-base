package com.xtx.itbook.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * 拼音工具类，汉字转换成拼音，用了pinying4j.jar包
 * 产品术语模块中做A-Z 排序使用
 * @author caocong
 * 2013.10.25
 */
public class PinYinUtil
{
    /** 
     * 获取汉字串拼音首字母，英文字符不变 
     * @param chinese 汉字串 
     * @return 汉语拼音首字母 
     */
    public static String getFirstSpell(String chinese)
    {
        StringBuffer pybf = new StringBuffer();
        char [] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] > 128)
            {

                try
                {
                    String [] temp;
                    temp = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if (temp != null)
                    {
                        pybf.append(temp[0].charAt(0));
                    }
                }
                catch (BadHanyuPinyinOutputFormatCombination e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else
            {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /** 
     * 获取汉字串拼音，英文字符不变 
     * @param chinese 汉字串 
     * @return 汉语拼音 
     * @throws BadHanyuPinyinOutputFormatCombination 
     */
    public static String getFullSpell(String chinese)

    {
        StringBuffer pybf = new StringBuffer();
        char [] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] > 128)
            {

                try
                {
                    String [] s = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if(s!=null&&s.length>0){
                        pybf.append(s[0]);
                    }else{
                        pybf.append("#");
                    }
                   
                }
                catch (BadHanyuPinyinOutputFormatCombination e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else
            {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    /** 
     * 获取汉字串拼音的集合
     * @param chinese 汉字串 
     * @return 汉语拼音 
     * @throws BadHanyuPinyinOutputFormatCombination 
     */
    public static ArrayList<String> getFullSpellList(String chinese)
    {
        ArrayList<String> spells = null;
        if (chinese != null && !"".equals(chinese))
        {
            spells = new ArrayList<String>();
            for (int i = 0; i < chinese.length(); i++)
            {

                String spell = getFullSpell(String.valueOf(chinese.charAt(i)));
                spells.add(spell);
            }
        }
        return spells;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     * 
     * @param str
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination 
     */
    public static String getFirstLetter(String str)
    {
        return getFirstSpell(str).substring(0, 1).toUpperCase();
    }

    /**
     * 判断字符串中是否含有中文
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str)
    {
        boolean flg = false;
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        if (matcher.find())
        {
            flg = true;
        }
        return flg;
    }

    /**
     * 使字符串中一部分字符改变颜色
     * 使用：textView.setText(changeColorOfString());
     * @param str
     * @param startoff 字符串的开始位置
     * @param endoff字符串的结束位置
     * @return 
     */
    public static CharSequence changeColorOfString(String str, int startoff,
            int endoff)
    {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        // style.setSpan(new BackgroundColorSpan(Color.RED), 2, 5,
        // Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的背景颜色
        style.setSpan(new ForegroundColorSpan(Color.BLUE), startoff, endoff,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 设置指定位置文字的颜色
        return style;
    }
}
