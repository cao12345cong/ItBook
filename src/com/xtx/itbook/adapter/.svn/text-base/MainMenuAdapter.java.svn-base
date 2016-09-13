package com.xtx.itbook.adapter;

import com.xtx.itbook.ui.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 首界面主 菜单
 * @author Abelart.
 */
public class MainMenuAdapter extends BaseAdapter
{
    /** 日志标签. */
    private static final String TAG = MainMenuAdapter.class.getName();
    
    private int clickPositon = -1;         //点击的position

    /** 菜单图标. */
    private static final int menus_img[] =
        {
                R.drawable.news, R.drawable.product, R.drawable.solution,
                R.drawable.sucess_case, R.drawable.it, R.drawable.media_center,
        };

    private Context mContext;

    private ImageView imageViewMenu;

    /**
     * 构造函数.
     * 
     * @param context
     *            .
     */
    public MainMenuAdapter(Context context)
    {
        mContext = context;
        Log.i(TAG, "MainMenuAdapter Constructor");
    }

    public int getCount()
    {
        return menus_img.length;
    }

    /**
     * 得到当前的项目
     * 
     * @param position
     *            当前位置.
     * @return position 返回Item所在的位置.
     */
    public Object getItem(int posit)
    {
        return posit;
    }

    public long getItemId(int positio)
    {
        return positio;
    }
    
    public void setClickPositon(int clickPositon) 
    {
		this.clickPositon = clickPositon;
	}

	public View getView(final int postion, View convertView, ViewGroup parent)
    {

        final View view = View.inflate(mContext, R.layout.grid_menu_item, null);
        imageViewMenu = (ImageView) view.findViewById(R.id.main_menu_img);
        imageViewMenu.setBackgroundResource(menus_img[postion]);
        if(clickPositon == postion)
        {
        	view.setBackgroundColor(Color.RED);
        }else{
        	view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }

}
