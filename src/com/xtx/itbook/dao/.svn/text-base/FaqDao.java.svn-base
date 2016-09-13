package com.xtx.itbook.dao;

import java.util.List;

import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.util.ConstantInterface;

import android.content.Context;
import net.tsz.afinal.FinalDb;

public class FaqDao implements ConstantInterface
{
    private FinalDb finalDb = null;

    public FaqDao(Context context)
    {
        finalDb = FinalDb.create(context,DB_NAME,true);
    }

    /**
     * 查找所有
     * @return
     */
    public List<Faq> findAll()
    {
        return finalDb.findAll(Faq.class);
    }

    /**
     * 分页查找
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<Faq> findByPageAndSize(long upid, int currentPage, int pageSize)
    {
        
        return finalDb.findAll(Faq.class, "upid=" + upid + " limit "
                + (currentPage - 1) + "," + pageSize);
    }

    /**
     * 批量存
     * @param faqs
     */
    public void saveAll(List<Faq> faqs)
    {
        if (faqs == null || faqs.isEmpty())
        {
            return;
        }
        for (Faq faq : faqs)
        {
            finalDb.save(faq);
        }
    }

    /**
     * 删除所有
     */
    public void deleteAll()
    {
        finalDb.deleteByWhere(Faq.class, null);
    }

    /**
     * 获取数据的最新更新时间
     * @return
     */
    public long getLastUpdateTime()
    {
        return findAll().get(0).getTime();
    }
}
