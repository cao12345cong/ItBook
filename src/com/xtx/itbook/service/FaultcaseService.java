package com.xtx.itbook.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;

import com.xtx.itbook.entity.Faultcase;
import com.xtx.itbook.entity.Terminology;

/**
 * 产品术语服务类
 * @author caocong
 *
 */
public class FaultcaseService
{
    private Context context;

    public FaultcaseService(Context context)
    {
        this.context = context;
    }

    public List<Faultcase> getFaultcases(Integer totle, String language)
            throws JSONException
    {
        List<Faultcase> faultcases = new ArrayList<Faultcase>();
        return faultcases;
    }

}
