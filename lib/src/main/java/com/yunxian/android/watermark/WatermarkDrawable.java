package com.yunxian.android.watermark;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 水印Drawable
 *
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/2/16 11:48 PM
 */
public class WatermarkDrawable extends Drawable {

    public static final int DEFAULT_TEXT_SIZE_4_DP = 10;
    public static final int DEFAULT_TEXT_COLOR = Color.parseColor("#999999");

    private final Resources resources;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 水印文案
    @Nullable
    private String text;
    // 旋转角度
    private float degrees = 0;
    // 水印之间的留白
    private final Rect margin = new Rect();

    // 保持中间结果-水印图以及水印图着色器
    @Nullable
    private Bitmap markerImg;
    @Nullable
    private Shader markerShader;

    public WatermarkDrawable(@NonNull Resources resources) {
        this.resources = resources;
        init();
    }

    private void init() {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_TEXT_SIZE_4_DP, resources.getDisplayMetrics()));
        paint.setColor(DEFAULT_TEXT_COLOR);
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
        float textSizePx = TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
        if (Float.compare(paint.getTextSize(), textSizePx) != 0) {
            paint.setTextSize(textSizePx);
            invalidateMarker();
        }
    }

    /**
     * 设置水印文案
     *
     * @param text 水印文案，不得为空
     */
    public void setText(@NonNull String text) {
        if (!TextUtils.isEmpty(text) && !TextUtils.equals(text, this.text)) {
            this.text = text;
            invalidateMarker();
        }
    }

    /**
     * 设置水印文案文本色
     *
     * @param color 水印文本色
     */
    public void setTextColor(@ColorInt int color) {
        if (paint.getColor() != color) {
            paint.setColor(color);
            invalidateMarker();
        }
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
        margin.set(left, top, right, bottom);
        invalidateMarker();
    }

    /**
     * 设置水印文案旋转角度
     *
     * @param degrees 旋转角度
     */
    public void setDegrees(float degrees) {
        if (Float.compare(this.degrees, degrees) != 0) {
            this.degrees = degrees;
            invalidateMarker();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 延迟创建水印图
        rebuildMarkerLazy();
        if (markerShader == null) {
            return;
        }
        paint.setShader(markerShader);
        Rect rect = getBounds();
        if (rect.isEmpty()) {
            rect = canvas.getClipBounds();
        }
        canvas.drawRect(rect, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /**
     * 属性发生变化后，仅丢弃之前的水印图，不创建新的水印图，只在绘制时进行一次创建，避免多次属性修改时的重复创建问题
     */
    @MainThread
    private void invalidateMarker() {
        if (markerImg != null) {
            markerImg.recycle();
            markerImg = null;
        }
        markerShader = null;

        invalidateSelf();
    }

    private void rebuildMarkerLazy() {
        // 已经创建锅，或者没有设置水印文案，则不创建
        if (markerImg != null || markerShader != null || TextUtils.isEmpty(text)) {
            return;
        }
        Rect textBounds = new Rect();
        assert text != null;
        paint.getTextBounds(text, 0, text.length(), textBounds);

        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, textBounds.width() / 2.0f, textBounds.height() / 2.0f);

        RectF srcRect = new RectF(0, 0,
                textBounds.width(),
                textBounds.height());
        RectF destRect = new RectF();
        matrix.mapRect(destRect, srcRect);

        markerImg = Bitmap.createBitmap(Math.round(destRect.width()) + margin.left + margin.right,
                Math.round(destRect.height()) + margin.top + margin.bottom,
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(markerImg);
        canvas.save();
        canvas.translate(margin.left, margin.top);
        canvas.translate(-destRect.left, -destRect.top);
        canvas.concat(matrix);
        canvas.drawText(text, 0, -textBounds.top, paint);
        canvas.restore();

        markerShader = new BitmapShader(markerImg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

}
