package com.xtx.itbook.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.TradeCase;
import com.xtx.itbook.ui.R;

/**
 * 行业案例 适配器.
 * @author ChenHuigang
 *
 */
public class TradeCaseListAdapter extends BaseAdapter
{   
    private Context context;
    
    private List<TradeCase> tradeCaseListData = null;
    LayoutInflater layoutInflater;
    /**构造函数.*/
    public TradeCaseListAdapter(Context context,List<TradeCase> tradeCaseListData)
    {
        this.context = context;
        this.tradeCaseListData = tradeCaseListData;
        layoutInflater = LayoutInflater.from(context);
    }
    public void changeDataUpdateView(List<TradeCase> tradeCaseListData)
    {
        this.tradeCaseListData = tradeCaseListData;
        this.notifyDataSetChanged();
    }
    
    @Override
    public int getCount()
    {
        return tradeCaseListData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return tradeCaseListData.get(position);
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public long getItemId(int position)
    {
        return tradeCaseListData.get(position).hashCode();
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   
        TradeCaseItem TradeCaseItem = null;
        if (convertView == null)
        {
            TradeCaseItem = new TradeCaseItem();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.tradecase_item, null);
            
            TradeCaseItem.itemImg = (ImageView)convertView.findViewById(R.id.tradecase_item_img);
            TradeCaseItem.titleTradeCaseextView = (TextView) convertView.findViewById(R.id.tradecase_item_title_name);
            TradeCaseItem.msgTradeCaseextView = (TextView) convertView.findViewById(R.id.tradecase_item_title_text);
            
            if(tradeCaseListData != null)
            {
                FinalBitmap finalBitmap = FinalBitmap.create(context);
                finalBitmap.display(TradeCaseItem.itemImg, tradeCaseListData.get(position).getImageUrl());
                TradeCaseItem.titleTradeCaseextView.setText(tradeCaseListData.get(position).getTitle());
                TradeCaseItem.msgTradeCaseextView.setText(tradeCaseListData.get(position).getContent());
            }
            
            convertView.setTag(TradeCaseItem);
        }
        else
        {
            TradeCaseItem = (TradeCaseItem) convertView.getTag();
        }
      
        return convertView;
    }
    
    static class TradeCaseItem
    {
        ImageView itemImg;
        
        TextView titleTradeCaseextView;
        
        TextView msgTradeCaseextView;
    }

}
