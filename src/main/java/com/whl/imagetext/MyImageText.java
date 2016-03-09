package com.whl.imagetext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wanghailong on 2016/3/6/0006.
 */
public class MyImageText extends View {
    private Bitmap mIconBitmap;//图片
    private Canvas mCanvas;
    private Paint mPaint;

    private Bitmap mBitmap;
    private String mText;//图片下的文字
    private int mTextColor = 0x00000000;//字体颜色
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private Paint mTextPaint;//画字用
    private Paint mBitmapPaint;//画图片用
    private Rect mTextBounds;//文字的粗
    private Rect mIconRect;

    /**
     * *新的
     */
    //private TextPaint mPaint;//字体的画笔
    public MyImageText(Context context) {
        this(context, null);
    }

    public MyImageText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyImgeText);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.MyImgeText_micon:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) ta.getDrawable(attr);
                    mIconBitmap = bitmapDrawable.getBitmap();
                    break;
                case R.styleable.MyImgeText_mtext:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.MyImgeText_text_size:
                    mTextSize = ta.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyImgeText_text_color:
                    mTextColor = ta.getColor(attr, 0x00000000);
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
        /**
         * 设置字体画笔的相关属性
         */
        mTextPaint = new Paint();
        mTextBounds = new Rect();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - mTextBounds.height());//图片的宽度,应该是在View中宽高的最小值，这个宽度是icon的可见区域
        int left = getMeasuredWidth() / 2 - iconWidth / 2;//图片的中心左边角坐标
        int top = (getMeasuredHeight() - mTextBounds.height()) / 2 - iconWidth
                / 2;//图片中心上面的坐标

        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);//图片的绘制区域，在view的中心
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        int x = getMeasuredWidth() / 2 - mTextBounds.width() / 2;
        int y = mIconRect.bottom + mTextBounds.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }
}
