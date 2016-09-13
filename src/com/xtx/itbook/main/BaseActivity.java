/*
 * 文件名：BaseActivity.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午3:19:48
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.main;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xtx.itbook.ui.R;
import com.xtx.itbook.util.ConstantInterface;

/**
 * 所有activity 的父类.
 * @author Abelart.
 */
public abstract class BaseActivity extends FinalActivity implements
        OnClickListener, ConstantInterface
{
    private ImageView ivTitlebarLeft,ivTitlebarRight;//标题栏按钮
    private ImageView ivtitleArrow;//标题右边的小三角
    private TextView tvTitle;//标题
    private PopupWindow popup;//下拉菜单
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    /** 初始化 相关信息.*/
    public abstract void init();

    /**初始化视图控件.*/
    public abstract void initView();
    
    
    
    /**
     * 标题栏统一设置，样式，和事件监听, 有下拉菜单
     * @param resLeftId    标题栏左按钮的图片id
     * @param listenerLeft 标题栏左按钮监听
     * @param resRightId   标题栏右按钮的图片id
     * @param listenerRight标题栏右按钮监听
     * @param title        标题内容
     * @param listenerTitle标题监听
     * @param showArrow    是否显示标题右边的小三角
     */
    public void initTitlebar(String title,int resLeftId,int resRightId,OnClickListener listenerLeft,
            OnClickListener listenerRight,OnClickListener listenerTitle,boolean showArrow){
        this.ivTitlebarLeft=(ImageView) findViewById(R.id.iv_titlebar_goback);
        ivTitlebarLeft.setImageResource(resLeftId);
        ivTitlebarLeft.setOnClickListener(listenerLeft);
        
        this.ivTitlebarRight=(ImageView) findViewById(R.id.iv_titlebar_right);
        ivTitlebarRight.setImageResource(resRightId);
        ivTitlebarRight.setOnClickListener(listenerRight);
        
        this.tvTitle=(TextView) findViewById(R.id.tv_titlebar_title);
        tvTitle.setText(title);
        tvTitle.setOnClickListener(listenerTitle);
        
        ivtitleArrow=(ImageView) findViewById(R.id.iv_titlebar_title_arrow);
        if(showArrow){
            ivtitleArrow.setVisibility(View.VISIBLE);
        }
        
    }
    /**
     * 常用的标题栏统一设置,没有下拉菜单
     * 看initTitlebar方法
     * @param listenerLeft
     * @param resRightId
     * @param listenerRight
     * @param title
     */
    public void initTitleBarDefault(String title,int resRightId,OnClickListener listenerLeft,OnClickListener listenerRight){
        initTitlebar(title, R.drawable.ic_goback, resRightId, listenerLeft, listenerRight, null, false);
    }
    
    /**
     * 设置标题文字
     */
    public void setTitleBarTitle(String title){
        if(title!=null){
            tvTitle.setText(title);
        }
        
    }
    /**
     * 标题下拉菜单
     * @param width    宽
     * @param height   高
     * @param data     listview数据集
     * @param listener listview的onItemClickListener
     * @return
     */
    public PopupWindow creatPopupWindow(int width,int height,List data,OnItemClickListener listener){
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.popup_top_menu, null);
        ListView listview=(ListView) view;
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.popup_top_menu_item , R.id.tv_popup_text, data);
        
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listener);
        PopupWindow popupWindow = new PopupWindow(listview,
                width, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        return popupWindow;
    }
    
    public void skip(Class<?> activityClass)
    {
    	Intent intent = new Intent();
    	intent.setClass(this, activityClass);
    	startActivity(intent);
    }
}
