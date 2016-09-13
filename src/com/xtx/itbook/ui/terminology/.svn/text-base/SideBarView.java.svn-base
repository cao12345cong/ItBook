package com.xtx.itbook.ui.terminology;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.xtx.itbook.ui.R;

/**
 * 产品术语，A—Z侧边搜索条自定义view
 * @author caocong
 * 2013.10.21
 */
public class SideBarView extends View
{

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;// 字母被点击监听器

    private String [] charArr =
        {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z", "#"
        };

    private int choose = -1;// 按下的字母在charArr数组中的索引

    private Paint paint = new Paint();// 画笔

    private TextView mTextDialog; // 字母提示框

    public void setTextView(TextView mTextDialog)
    {
        this.mTextDialog = mTextDialog;
    }

    public SideBarView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public SideBarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SideBarView(Context context)
    {
        super(context);
    }

    /**
     * 绘制视图
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 画背景，长和宽
        canvas.drawColor(Color.parseColor("#00000000"));
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / charArr.length;
        // 画文字
        for (int i = 0; i < charArr.length; i++)
        {
            paint.setColor(getResources().getColor(R.color.term_sideletter));
            paint.setTypeface(Typeface.DEFAULT);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // 背景颜色
            if (i == choose)
            {
                paint.setColor(Color.parseColor("#b1a29f"));
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2
                    - paint.measureText(String.valueOf(charArr[i])) / 2;
            float yPos = singleHeight * (i + 1);
            canvas.drawText(String.valueOf(charArr[i]), xPos, yPos, paint);
            paint.reset();
        }
    }

    /**
     * 触碰事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        final int action = event.getAction();
        final float y = event.getY();//
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * charArr.length);//

        switch (action)
        {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.parseColor("#00000000"));// 改变背景颜色
                choose = -1;//
                invalidate();
                if (mTextDialog != null)
                {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                // setBackgroundResource(R.drawable.sidebar_background);//
                // 按下时背景颜色改变
                if (oldChoose != c)
                {
                    if (c >= 0 && c < charArr.length)
                    {
                        if (listener != null)
                        {
                            listener.onTouchingLetterChanged(charArr[c]);
                        }
                        if (mTextDialog != null)
                        {
                            mTextDialog.setText(charArr[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 设置字母被点击监听器
     * 
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener)
    {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 
     * 字母被点击监听器接口
     * 
     */
    public interface OnTouchingLetterChangedListener
    {
        public void onTouchingLetterChanged(String s);
    }

}