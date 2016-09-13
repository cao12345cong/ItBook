package com.xtx.itbook.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.Faultcase;
import com.xtx.itbook.ui.R;

public class FaultcaseAdatper extends BaseAdapter
{
    Context context;

    List<Faultcase> dataList;

    public FaultcaseAdatper(Context context, List<Faultcase> faultcases)
    {
        this.context = context;
        setData(faultcases);
    }

    private void setData(List<Faultcase> faultcases)
    {
        if (faultcases != null)
        {
            this.dataList = faultcases;
        }
        else
        {
            this.dataList = new ArrayList<Faultcase>();
        }

    }

    // 更新界面
    public void updateView(List<Faultcase> faultcases)
    {
        setData(faultcases);
        this.notifyDataSetChanged();
    }

    public int getCount()
    {
        return dataList.size();
    }

    public Faultcase getItem(int position)
    {
        return dataList.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder = null;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.faultcase_index_item, null);
            holder = new Holder();
            holder.ivImage = (ImageView) convertView
                    .findViewById(R.id.iv_faultcase_item_img);
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.tv_faultcase_item_title);
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_faultcase_item_content);
            holder.ivNewTag = (ImageView) convertView
                    .findViewById(R.id.iv_faultcase_item_newtag);
            convertView.setTag(holder);
            
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Faultcase faultcase = getItem(position);
        holder.tvName.setText(faultcase.getName() == null ? "" :faultcase
                .getName());
        holder.tvContent.setText(faultcase.getResolve()==null?"":faultcase.getResolve());
        return convertView;
    }

    class Holder
    {
        private ImageView ivNewTag;//new标签
        
        private ImageView ivImage;

        private TextView tvName;
        
        private TextView tvContent;

    }
}
