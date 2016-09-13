/*
 * 文件名：ProductListAdapter.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午5:14:49
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.Product;
import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.ui.R;

/**
 * 解决方案适配器
 * @author Administrator
 *
 */
public class SolutionListAdapter extends BaseAdapter
{
    private Context context;

    private List<SglSolutions> solutionsListData;

    LayoutInflater mLayoutInflater;

    /**构造函数.*/
    public SolutionListAdapter(Context context, List<SglSolutions> listSolutions)
    {
        this.context = context;
        setData(listSolutions);
        mLayoutInflater = LayoutInflater.from(context);
    }

    private void setData(List<SglSolutions> solutionListData)
    {
        if (null != solutionListData)
        {
            this.solutionsListData = solutionListData;
        }
        else
        {
            this.solutionsListData = new ArrayList<SglSolutions>();
        }
    }

    // 更新界面
    public void changeDataUdapteView(List<SglSolutions> listData)
    {
        setData(listData);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return solutionsListData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return solutionsListData.get(position);
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public long getItemId(int position)
    {
        return solutionsListData.get(position).hashCode();
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        SolutionItem solutionItem = null;
        if (convertView == null)
        {
            solutionItem = new SolutionItem();
            convertView = mLayoutInflater
                    .inflate(R.layout.solutions_item, null);

            solutionItem.sltnImg = (ImageView) convertView
                    .findViewById(R.id.sltn_item_image);
            solutionItem.sltnTitleTv = (TextView) convertView
                    .findViewById(R.id.sltn_item_title);
            solutionItem.sltnContentTv = (TextView) convertView
                    .findViewById(R.id.sltn_item_text_content);

            convertView.setTag(solutionItem);
        }
        else
        {
            solutionItem = (SolutionItem) convertView.getTag();
        }
        if (solutionsListData != null)
        {
            FinalBitmap finalBitmap = FinalBitmap.create(context);
            SglSolutions sglSolutions = solutionsListData.get(position);
            finalBitmap.display(solutionItem.sltnImg,
                    sglSolutions.getSltnImageUrl());
            solutionItem.sltnTitleTv.setText(sglSolutions.getSltnTitle());
            solutionItem.sltnContentTv.setText(sglSolutions.getSltnContent());
        }

        return convertView;
    }

   private class SolutionItem
    {

        ImageView sltnImg;

        TextView sltnTitleTv;

        TextView sltnContentTv;
    }
    

}
