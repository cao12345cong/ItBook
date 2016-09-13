package com.xtx.itbook.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xtx.itbook.entity.Terminology;
import com.xtx.itbook.util.ConstantInterface;
import com.xtx.itbook.util.TimeMemoryUtil;

/**
 * 产品术语数据库访问对象
 * @author caocong
 *2013.10.25
 */
public class TerminologyDao implements ConstantInterface
{
    private FinalDb finalDb = null;
    
    public TerminologyDao(Context context)
    {
        finalDb = FinalDb.create(context,DB_NAME,true);
    }

    /**
     * 批量查询
     */
    public List<Terminology> findAll()
    {
        return finalDb.findAll(Terminology.class);
    }
    
    /**
     * 批量存
     * @param terminologys
     */
    public void saveAll(List<Terminology> terminologys)
    {
        TimeMemoryUtil.getStartTimeMemory();
        if (terminologys == null || terminologys.isEmpty())
        {
            return;
        }
        finalDb.beginTranscation();
        for (Terminology terminology : terminologys)
        {
            finalDb.save(terminology);
        }
        finalDb.sucessTranscation();
        finalDb.endTranscation();
        TimeMemoryUtil.showTimeMemory("save all");
    }

    /**
     * 修改一条数据
     */
    public void update(Terminology terminology){
        finalDb.update(terminology);
    }
    
    /**
     * 删除所有数据
     */
    public void deleteAll()
    {
        finalDb.deleteByWhere(Terminology.class, null);
    }
    /**
     * 删除一条数据
     * @param terminology
     */
    public void delete(Terminology terminology){
        finalDb.delete(terminology);
    }
    
    /**
     * 删除一条数据
     * @param id
     */
    public void deleteById(long id){
        finalDb.deleteByWhere(Terminology.class, "id="+id);
    }

}
