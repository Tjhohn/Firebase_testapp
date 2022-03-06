package edu.sdsmt.hohn_tanner.tutorial5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ColorSelectActivity extends AppCompatActivity {

    public static final String SELECTOR_COLOR = "ColorSelectActivity.selector_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_select);
    }

    public void selectColor(int color) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SELECTOR_COLOR , color);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
}