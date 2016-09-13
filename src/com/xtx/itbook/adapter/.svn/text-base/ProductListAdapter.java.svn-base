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
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtx.itbook.entity.Product;
import com.xtx.itbook.ui.R;

/**
 * 产品列表 适配器.
 * @author Abelart.
 */
public class ProductListAdapter extends BaseAdapter
{   
    private Context context;
    
    private List<Product> listProducts;
    
    /**构造函数.*/
    public ProductListAdapter(Context context,List<Product> listPro)
    {
        this.context = context;
        data(listPro);
    }
    
    /**刷新数据请求.*/
    public void refreshView(List<Product> products)
    {
    	data(products);
    	this.notifyDataSetChanged();
    }
    
    public void data(List<Product> listProduct)
    {
    	if(listProduct != null)
    	{
    		this.listProducts = listProduct;
    	}else{
    		this.listProducts = new ArrayList<Product>();
    	}
    	
    }
    
    @Override
    public int getCount()
    {
        return listProducts.size();
    }

    @Override
    public Product getItem(int position)
    {
        return listProducts.get(position);
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /** 
     * TODO.
     * 见父类.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   
        ProductItem productItem = null;
        if (convertView == null)
        {
            productItem = new ProductItem();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.product_item, null);
            
            productItem.itemImg = (ImageView)convertView.findViewById(R.id.product_item_img);
            productItem.titleProducTextView = (TextView) convertView.findViewById(R.id.product_item_title_name);
            productItem.msgProducTextView = (TextView) convertView.findViewById(R.id.product_item_title_text);
            
            convertView.setTag(productItem);
        }
        else
        {
            productItem = (ProductItem) convertView.getTag();
        }
        
        Product product = getItem(position);
        FinalBitmap finalBitmap = FinalBitmap.create(context);
        finalBitmap.display(productItem.itemImg, product.getImageurl() == null ? "" : product.getImageurl());
        productItem.titleProducTextView.setText(product.getName() == null ? "" : product.getName());
        productItem.msgProducTextView.setText(product.getItro() == null ? "" : product.getItro());
          
        Log.i("item",listProducts.get(position).getItro()+"\n" );
        return convertView;
    }
    
    static class ProductItem
    {
        ImageView itemImg;
        
        TextView titleProducTextView;
        
        TextView msgProducTextView;
    }

}
