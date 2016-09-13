package com.xtx.itbook.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xtx.itbook.entity.Terminology;
import com.xtx.itbook.ui.R;

/**
 * 产品术语适配器，按照首字母A-Z排序
 * @author caocong
 *
 */
public class TerminologyAdapter extends BaseAdapter implements
        SectionIndexer
{
    private List<Terminology> list = null;

    private Context mContext;

    public TerminologyAdapter(Context mContext, List<Terminology> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 更新界面
     * @param list
     */
    public void updateView(List<Terminology> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount()
    {
        return this.list.size();
    }

    public Terminology getItem(int position)
    {
        return list.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2)
    {
        ViewHolder viewHolder = null;
        final Terminology mContent = list.get(position);
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.terminology_index_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_catalog);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        int section = getSectionForPosition(position);

        if (position == getPositionForSection(section))
        {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getFirstLetter());
        }
        else
        {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position)
                .getWord());

        return view;

    }

    final static class ViewHolder
    {
        TextView tvLetter;

        TextView tvTitle;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position)
    {
        return list.get(position).getFirstLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section)
    {
        for (int i = 0; i < getCount(); i++)
        {
            String sortStr = list.get(i).getFirstLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section)
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object [] getSections()
    {
        return null;
    }
}