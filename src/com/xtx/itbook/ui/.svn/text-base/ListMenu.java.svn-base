/*
 * 文件名：ListMenu.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午2:46:30
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 下拉列表菜单.
 * @author Abelart.
 */
public class ListMenu extends PopupWindow
{
    
    private Activity activity;
    
    public DataItemPostion dataPostion;
    
    /**
     * 构造方法 .
     */
    public ListMenu()
    {
        super();
        
    }
    
    /**
     * 构造方法 @param context.
     */
    public ListMenu(Context context)
    {
        super(context);
        
    }

    /**
     * 构造方法 @param context
     * 构造方法 @param attrs
     * 构造方法 @param defStyle.
     */
    public ListMenu(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        
    }

    /**
     * 构造方法 @param context
     * 构造方法 @param attrs.
     */
    public ListMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
    }

    

    /**
     * 构造方法 @param width
     * 构造方法 @param height.
     */
    public ListMenu(Activity context,View parentView,int width,int height,String data[])
    {   
        this.activity =  context;
        View view = initView();
        this.setContentView(view);
        this.setHeight(width);
        this.setWidth(height);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setClippingEnabled(true);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.navbar));
        this.showAtLocation(parentView, Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        this.dismiss();
        initData(view,data);
    }

    /**
     * 构造方法 @param contentView
     * 构造方法 @param width
     * 构造方法 @param height
     * 构造方法 @param focusable.
     */
    public ListMenu(View contentView, int width, int height, boolean focusable)
    {
        super(contentView, width, height, focusable);
        
    }

    /**
     * 构造方法 @param contentView
     * 构造方法 @param width
     * 构造方法 @param height.
     */
    public ListMenu(View contentView, int width, int height)
    {
        super(contentView, width, height);
        
        
    }

    /**
     * 构造方法 @param contentView.
     */
    public ListMenu(View contentView)
    {
        super(contentView);
        
    }

    @Override
    public void setAnimationStyle(int animationStyle)
    {
        super.setAnimationStyle(animationStyle);
    }
    
    public View initView()
    {
        return LayoutInflater.from(activity).inflate(R.layout.list_menu, null);
    }
    
    public void initData(View view,String []datas)
    {
        ListView listView = (ListView) view.findViewById(R.id.menu_list);
        listView.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, datas));
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3)
            {
                dataPostion.getPostion(arg2);
            }
        });
    }
    
    public interface DataItemPostion
    {
        public int getPostion(int position);
    }

    public void setDataPostion(DataItemPostion dataPostion)
    {
        this.dataPostion = dataPostion;
    }
    
    
    
}
