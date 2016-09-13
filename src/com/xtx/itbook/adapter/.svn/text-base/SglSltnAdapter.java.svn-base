package com.xtx.itbook.adapter;
/**
 * 解决方案详情相关内容listView适配器
 */
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.xtx.itbook.entity.SglSolutions;
import com.xtx.itbook.entity.SltnRltd;
import com.xtx.itbook.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SglSltnAdapter extends BaseAdapter
{
   Context context;
   List<SltnRltd> sglSltnListData;
  LayoutInflater mLayoutInflater;

    public SglSltnAdapter(Context context, List<SltnRltd> sglSltnListData)
{
    super();
    this.context = context;
    this.sglSltnListData = sglSltnListData;
    mLayoutInflater = LayoutInflater.from(context);
}

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return sglSltnListData.size();
    }

    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return sglSltnListData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       SglSltnItem sglSltnItem = null;
        if(null == convertView)
        {
            convertView = mLayoutInflater.inflate(R.layout.sltn_dial_item, null);
            sglSltnItem = new SglSltnItem();
            sglSltnItem.titleTv = (TextView) convertView.findViewById(R.id.sgl_sltn_showbtn_title);
            sglSltnItem.dailTv = (TextView) convertView.findViewById(R.id.sgl_sltn_detail_content);
            convertView.setTag(sglSltnItem);
        }
        else
        {
            sglSltnItem = (SglSltnItem) convertView.getTag();
        }
        if(null != sglSltnListData)
        {
           SltnRltd sltnRltd = sglSltnListData.get(position);
           sglSltnItem.titleTv.setText(sltnRltd.getSltnDtlTitle());
           FinalBitmap finalBitmap = FinalBitmap.create(context);
           sglSltnItem.dailTv.setText(sltnRltd.getSltnDtlContentUrl());
        }
        return convertView;
    }
    class SglSltnItem{
        TextView titleTv;
       TextView dailTv;
       
        
        
    }
}
