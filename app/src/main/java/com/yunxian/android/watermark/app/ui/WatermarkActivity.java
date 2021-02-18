package com.yunxian.android.watermark.app.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yunxian.android.watermark.WatermarkDrawable;
import com.yunxian.android.watermark.app.R;

/**
 * @author A Shuai
 * @email ls1110924@gmail.com
 * @date 2021/2/18 9:15 PM
 */
public class WatermarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);

        WatermarkDrawable drawable = new WatermarkDrawable(getResources());
        drawable.setText("123        456");
        int horizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int verticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        drawable.setMargin(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        drawable.setDegrees(-20);
        View view = findViewById(android.R.id.content);
        view.setForeground(drawable);
        view.setForegroundGravity(Gravity.FILL);
    }
}
