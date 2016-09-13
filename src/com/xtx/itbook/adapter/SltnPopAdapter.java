package com.xtx.itbook.adapter;

import java.util.List;

import com.xtx.itbook.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 *  解决方案下拉菜单适配器
 */
public class SltnPopAdapter extends BaseAdapter
{
    Context context;
    List<String> popList;
    LayoutInflater layoutInflater;
    
    public SltnPopAdapter(Context context, List<String> popList)
    {
        super();
        this.context = context;
        this.popList = popList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return popList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return popList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return popList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if(null == convertView)
        {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.sltn_pop_list_item, null);
            viewHolder.itemTv = (TextView) convertView.findViewById(R.id.sltn_pop_item_tv);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        if(null != popList)
        {
        String itemText = popList.get(position);
        viewHolder.itemTv.setText(itemText);
        }
        
        return convertView;
    }

    private class ViewHolder{
        
        TextView itemTv;
        
    }
}
