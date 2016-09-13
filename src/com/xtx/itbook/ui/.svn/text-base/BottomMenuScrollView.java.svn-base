package com.xtx.itbook.ui;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xtx.itbook.entity.ListMenuEntry;
import com.xtx.itbook.main.ItBookApp;
import com.xtx.itbook.util.LogUtil;

/**
 * 底部滚动菜单
 * @author caocong
 * 2013.11.1
 */
public class BottomMenuScrollView extends LinearLayout
{
    private LinearLayout radioGroup;

    private HorizontalScrollView scrollView;

    private OnSelectedItemListener listener;

    private static int ID_SCROLL = 3;//

    private static int ID_RAGIOGROUP = 4;

    public BottomMenuScrollView(Context context)
    {
        super(context);
        init();
    }

    public BottomMenuScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BottomMenuScrollView(Context context, AttributeSet attrs,
            int defStyle)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setOrientation(LinearLayout.HORIZONTAL);

        // 滚动条
        scrollView = new HorizontalScrollView(getContext());
        scrollView.setId(ID_SCROLL);
        scrollView.setHorizontalScrollBarEnabled(false);
        radioGroup = new LinearLayout(getContext());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);
        radioGroup.setId(ID_RAGIOGROUP);

        scrollView.addView(radioGroup);
        this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        this.addView(scrollView);

    }

    /**
     * 从服务器获取数据，并转换成视图
     * @param data
     */
    public void addView(List<ListMenuEntry> data)
    {

        FinalBitmap finalBitmap = FinalBitmap.create(getContext());
        for (ListMenuEntry obj : data)
        {
            final View radioButton = LayoutInflater.from(ItBookApp.instance())
                    .inflate(R.layout.bottom_menu_item, null);
            LayoutParams params1 = new LayoutParams(160,
                    LayoutParams.FILL_PARENT);
            params1.gravity = Gravity.CENTER_VERTICAL;
            params1.leftMargin = 10;
            radioButton.setLayoutParams(params1);
            finalBitmap.display(
                    (ImageView) radioButton.findViewById(R.id.bottom_item_img),
                    obj.getImageurl());
            ((TextView) radioButton.findViewById(R.id.bottom_item_txt))
                    .setText(obj.getName());

            radioButton.setTag(obj);
            radioButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.doSelected(radioButton.getTag());
                }
            });
            radioGroup.addView(radioButton);
            invalidate();
            LogUtil.i("item data", obj.getImageurl() + "\n" + obj.getName());
        }

    }

    /**
     * 设置监听器
     * @param listener
     */
    public void setOnSelectedItemListener(OnSelectedItemListener listener)
    {
        this.listener = listener;
    }

    /**
     * 监听器
     * @author caocong
     *
     */
    public interface OnSelectedItemListener
    {
        void doSelected(Object obj);
    }

}
