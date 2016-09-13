package com.xtx.itbook.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.Faq;
import com.xtx.itbook.ui.R;

public class FaqListAdapter extends BaseAdapter
{
    Context context;

    List<Faq> dataList;

    public FaqListAdapter(Context context, List<Faq> faqs)
    {
        this.context = context;
        setData(faqs);
    }

    private void setData(List<Faq> faqs)
    {
        if (faqs != null)
        {
            this.dataList = faqs;
        }
        else
        {
            this.dataList = new ArrayList<Faq>();
        }

    }

    // 更新界面
    public void updateView(List<Faq> faqs)
    {
        setData(faqs);
        this.notifyDataSetChanged();
    }

    public int getCount()
    {
        return dataList.size();
    }

    public Faq getItem(int position)
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
            convertView = inflater.inflate(R.layout.faq_index_item, null);
            holder = new Holder();
            holder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tv_faq_item_title);
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_faq_item_content);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Faq faq = getItem(position);
        holder.tvTitle.setText(faq.getQuestion() == null ? "" : faq
                .getQuestion());
        holder.tvContent
                .setText(faq.getAnswer() == null ? "" : faq.getAnswer());
        return convertView;
    }

    class Holder
    {
        private TextView tvTitle, tvContent;

    }
}
