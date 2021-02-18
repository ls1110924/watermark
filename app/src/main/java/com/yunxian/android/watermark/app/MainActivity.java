package com.yunxian.android.watermark.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.yunxian.android.watermark.WatermarkDrawable;
import com.yunxian.android.watermark.app.ui.WatermarkActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump_watermark).setOnClickListener(this::onBtnClick);
    }

    private void onBtnClick(View v) {
        final int id = v.getId();
        if (id == R.id.jump_watermark) {
            startActivity(new Intent(this, WatermarkActivity.class));
        }
    }

}