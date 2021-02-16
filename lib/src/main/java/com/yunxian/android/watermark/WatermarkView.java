package com.yunxian.android.watermark;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 文本水印控件
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/2/17 2:30 AM
 */
public class WatermarkView extends View {

    private WatermarkDrawable watermarkDrawable;

    public WatermarkView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public WatermarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public WatermarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WatermarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        watermarkDrawable = new WatermarkDrawable(context.getResources());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatermarkView, defStyleAttr, defStyleRes);
        String text = a.getString(R.styleable.WatermarkView_watermark_text);
        if (!TextUtils.isEmpty(text)) {
            watermarkDrawable.setText(text);
        }
        int textSize = a.getDimensionPixelSize(R.styleable.WatermarkView_watermark_textSize, -1);
        if (textSize > 0) {
            watermarkDrawable.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        int textColor = a.getColor(R.styleable.WatermarkView_watermark_textColor, WatermarkDrawable.DEFAULT_TEXT_COLOR);
        watermarkDrawable.setTextColor(textColor);

        float degrees = a.getFloat(R.styleable.WatermarkView_watermark_degrees, 0);
        watermarkDrawable.setDegrees(degrees);

        int marginLeft = a.getDimensionPixelOffset(R.styleable.WatermarkView_watermark_marginLeft, 0);
        int marginTop = a.getDimensionPixelOffset(R.styleable.WatermarkView_watermark_marginTop, 0);
        int marginRight = a.getDimensionPixelOffset(R.styleable.WatermarkView_watermark_marginRight, 0);
        int marginBottom = a.getDimensionPixelOffset(R.styleable.WatermarkView_watermark_marginBottom, 0);
        watermarkDrawable.setMargin(marginLeft, marginTop, marginRight, marginBottom);

        a.recycle();

        setBackground(watermarkDrawable);
    }

    /**
     * 设置水印文案字号
     *
     * @param size 字号，单位为SP
     */
    @MainThread
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置水印文案字号
     *
     * @param unit 字号单位
     * @param size 字号
     */
    @MainThread
    public void setTextSize(int unit, float size) {
        watermarkDrawable.setTextSize(unit, size);
    }

    /**
     * 设置水印文案
     *
     * @param text 水印文案，不得为空
     */
    public void setText(@NonNull String text) {
        watermarkDrawable.setText(text);
    }

    /**
     * 设置水印文案文本色
     *
     * @param color 水印文本色
     */
    public void setTextColor(@ColorInt int color) {
        watermarkDrawable.setTextColor(color);
    }

    /**
     * 设置水印间留白
     *
     * @param left   与左边水印图的留白距离
     * @param top    与上边水印图的留白距离
     * @param right  与右边水印图的留白距离
     * @param bottom 与下边水印图的留白距离
     */
    public void setMargin(int left, int top, int right, int bottom) {
        watermarkDrawable.setMargin(left, top, right, bottom);
    }

    /**
     * 设置水印文案旋转角度
     *
     * @param degrees 旋转角度
     */
    public void setDegrees(float degrees) {
        watermarkDrawable.setDegrees(degrees);
    }

}
