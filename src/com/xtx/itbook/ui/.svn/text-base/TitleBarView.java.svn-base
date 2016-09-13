package com.xtx.itbook.ui;

import java.lang.ref.Reference;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 标题栏view
 * @author caocong
 * 2013.11.07
 * 
 * 使用说明：
 * xml加上xmlns:itbook="http://schemas.android.com/apk/res/com.xtx.itbook.ui"
 * <com.xtx.itbook.ui.TitleBarView android:id="@+id/titlebar"
 * android:layout_width="fill_parent" 
 * android:layout_height="48dp"
 * itbook:left_drawble="@drawable/ic_goback_selector"
 * itbook:right_drawble="@drawable/ic_goback_selector" 
 * itbook:showmenuable="true" 是否显示下拉菜单 
 * itbook:searchable="true" 是否弹出搜索
 * itbook:title_name="R.string.faq" 标题名 />
 */

public class TitleBarView extends LinearLayout
{
    private Context context;

    private TitleBarListener listener;

    ImageView ivLeft, ivRight;

    TextView tvTitle;

    ImageView ivArrow;

    LinearLayout llSearchbar, lltitle;

    EditText etSearch;

    boolean isShowSearch = false;

    public TitleBarView(Context context)
    {
        super(context);
        this.context = context;
    }

    public TitleBarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        initAttr(attrs);

    }

    private void initAttr(AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TitleBarView);
        int leftDrawalbe = a.getResourceId(
                R.styleable.TitleBarView_left_drawble, R.drawable.ic_goback);
        int RightDrawalbe = a.getResourceId(
                R.styleable.TitleBarView_right_drawble, R.drawable.ic_finder);
        boolean showMenuable = a.getBoolean(
                R.styleable.TitleBarView_showmenuable, false);
        boolean searchable = a.getBoolean(R.styleable.TitleBarView_searchable,
                false);
        String titleName = a.getString(R.styleable.TitleBarView_title_name);
        initView(leftDrawalbe, RightDrawalbe, titleName, showMenuable,
                searchable);

    }

    public void setTitle(String title)
    {
        if (title == null)
        {
            tvTitle.setText("");
        }
        tvTitle.setText(title);
    }

    private void initView(int leftDrawalbe, int rightDrawalbe,
            String titleName, boolean showMenuable, boolean searchable)
    {
        View root = LayoutInflater.from(context).inflate(R.layout.titlebar, null);
        addView(root);
        // 初始化view
        ivLeft = (ImageView) root.findViewById(R.id.iv_titlebar_goback);
        ivRight = (ImageView) root.findViewById(R.id.iv_titlebar_right);
        tvTitle = (TextView) root.findViewById(R.id.tv_titlebar_title);
        ivArrow = (ImageView) root.findViewById(R.id.iv_titlebar_title_arrow);
        llSearchbar = (LinearLayout) root
                .findViewById(R.id.ll_titlebar_searchbar);
        lltitle = (LinearLayout) root.findViewById(R.id.ll_titlebar_title);
        etSearch = (EditText) root.findViewById(R.id.et_titlebar_search);
        // 初始化titlebar
        ivLeft.setImageResource(leftDrawalbe);
        ivLeft.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                listener.onClickLeftImg(v);
            }
        });

        ivRight.setImageResource(rightDrawalbe);

        if (titleName != null)
        {
            tvTitle.setText(titleName);
        }

        // 是否需要点击标题弹出下拉菜单
        if (!showMenuable)
        {
            ivArrow.setVisibility(View.GONE);
            lltitle.setOnClickListener(null);
        }
        else
        {
            lltitle.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    listener.onClickTitleText(v);
                }
            });
        }
        // 是否需要弹出搜索框
        if (!searchable)
        {
            ivRight.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    listener.onClickRightImg(v);
                }
            });
        }
        else
        {
            ivRight.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (!isShowSearch)
                    {
                        llSearchbar.setVisibility(View.VISIBLE);
                        lltitle.setVisibility(View.GONE);
                        isShowSearch = true;
                    }
                    else
                    {
                        llSearchbar.setVisibility(View.GONE);
                        lltitle.setVisibility(View.VISIBLE);
                        isShowSearch = false;
                    }

                }
            });

            etSearch.addTextChangedListener(new TextWatcher()
            {

                @Override
                public void onTextChanged(CharSequence s, int start,
                        int before, int count)
                {
                    listener.onSearchTextChanged(s, start, before, count);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                        int count, int after)
                {

                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

        }
        invalidate();

    }


    /**
     * 设置监听器
     * @param listener
     */
    public void setTitleBarListener(TitleBarListener listener)
    {
        this.listener = listener;
    }

    /**
     * 向外的接口
     * @author caocong
     *
     */
    public interface TitleBarListener
    {
        // 左按钮点击
        void onClickLeftImg(View v);

        // 右按钮点击
        void onClickRightImg(View v);

        // 标题点击
        void onClickTitleText(View v);

        // 搜索框文字变化 ,见onTextChanged
        void onSearchTextChanged(CharSequence s, int start, int before,
                int count);

    }

}
