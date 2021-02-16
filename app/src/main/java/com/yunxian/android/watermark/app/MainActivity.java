package com.yunxian.android.watermark.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.yunxian.android.watermark.WatermarkDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatermarkDrawable drawable = new WatermarkDrawable(getResources());
        drawable.setText("123        456");
        int horizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int verticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        drawable.setMargin(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        drawable.setDegrees(-20);
        View view = findViewById(android.R.id.content);
        view.setForeground(drawable);
    }
}