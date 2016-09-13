package com.xtx.itbook.ui.terminology;

import java.util.Comparator;

import com.xtx.itbook.entity.Terminology;

/**
 * 按照拼音比较器
 * @author caocong
 *
 */
public class PinyinComparator implements Comparator<Terminology>
{

    public int compare(Terminology o1, Terminology o2)
    {
        if (o1.getFirstLetter().equals("@") || o2.getFirstLetter().equals("#"))
        {
            return -1;
        }
        else if (o1.getFirstLetter().equals("#")
                || o2.getFirstLetter().equals("@"))
        {
            return 1;
        }
        else
        {
            return o1.getFirstLetter().compareTo(o2.getFirstLetter());
        }
    }

}
