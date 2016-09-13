package com.xtx.itbook.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 视频展示自定义view
 * @author caocong
 * 2013.11.05
 */
public class MediaShowView extends RelativeLayout
{
    private ImageView imageView;

    private TextView textView;

    private OnClickedListener listener;

    public MediaShowView(Context context)
    {
        super(context);
        init();
    }

    public MediaShowView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setBackgroundColor(Color.WHITE);
        setPadding(5, 5, 5, 5);
        // 图片
        imageView = new ImageView(getContext());
        imageView.setScaleType(ScaleType.FIT_CENTER);
        // 文字栏
        LinearLayout llBottom = new LinearLayout(getContext());
        llBottom.setOrientation(LinearLayout.HORIZONTAL);
        llBottom.setPadding(10, 0, 10, 0);
        llBottom.setBackgroundColor(Color.BLACK);
        // 文字栏图片
        ImageView ivPlay = new ImageView(getContext());
        ivPlay.setImageResource(R.drawable.ic_video_player);
        ivPlay.setScaleType(ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams imagelp = new LinearLayout.LayoutParams(36,
                36);
        imagelp.gravity = Gravity.CENTER_VERTICAL;
        llBottom.addView(ivPlay, imagelp);
        // 文字栏文字
        textView = new TextView(getContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(14);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(0,
                LayoutParams.FILL_PARENT, 1.0f);

        llBottom.addView(textView, textlp);
        // 添加
        addView(imageView, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        LayoutParams lllp = new LayoutParams(LayoutParams.FILL_PARENT, 60);
        lllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(llBottom, lllp);

    }

    public void addView(Drawable image, final String name)
    {
        imageView.setImageDrawable(image);
        textView.setText(name);

        View.OnClickListener ll = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                listener.doClick(name);

            }
        };
        setOnClickListener(ll);

    }

    /**
     * 点击事件监听
     * @param l
     */
    public void setOnClickedListener(OnClickedListener l)
    {
        this.listener = l;
    }

    /**
     * 点击事件监听器
     */
    public interface OnClickedListener
    {
        void doClick(String name);
    }
}
