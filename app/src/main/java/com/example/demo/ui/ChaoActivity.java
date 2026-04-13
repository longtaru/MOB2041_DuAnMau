package com.example.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

public class ChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chao);

        // Chuyá»ƒn sang mĂ n hĂ¬nh Ä‘Äƒng nháº­p sau 1.5 giĂ¢y
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ChaoActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 1500);
    }
}
