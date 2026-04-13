package com.example.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.adapter.KhachHangAdapter;
import com.example.demo.database.DatabaseHelper;
import com.example.demo.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class QuanLyKhachHangActivity extends AppCompatActivity implements KhachHangAdapter.OnKhachHangClickListener {
    public static final String KHACH_HANG = "KHACH_HANG";
    private KhachHangAdapter khachHangAdapter;
    private List<KhachHang> danhSachKhachHang;
    private DatabaseHelper db;

    //quay vá» mĂ n hĂ¬nh trÆ°á»›c
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_khach_hang);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lvKhachHang = findViewById(R.id.lvKhachHang);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Quáº£n lĂ½ khĂ¡ch hĂ ng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
        FloatingActionButton fabThemKhachHang = findViewById(R.id.fabThemKhachHang);

        danhSachKhachHang = db.layTatCaKhachHang();
        khachHangAdapter = new KhachHangAdapter(this, danhSachKhachHang);
        khachHangAdapter.setOnKhachHangClickListener(this);
        lvKhachHang.setAdapter(khachHangAdapter);

        fabThemKhachHang.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditKhachHangActivity.class);
            intent.putExtra("Type", 1);
            this.startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        danhSachKhachHang.clear();
        danhSachKhachHang.addAll(db.layTatCaKhachHang());
        khachHangAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditKhachHang(KhachHang khachHang) {
        Intent intent = new Intent(this, EditKhachHangActivity.class);
        intent.putExtra("Type", 0);
        intent.putExtra(KHACH_HANG, khachHang);

        startActivity(intent);
    }

    @Override
    public void onDeleteKhachHang(KhachHang khachHang) {
        DatabaseHelper db = new DatabaseHelper(this);
        boolean isDeleted = db.xoaKhachHang(khachHang.getMaKhachHang());
        if (isDeleted) {
            danhSachKhachHang.remove(khachHang);
            khachHangAdapter.notifyDataSetChanged();
            Toast.makeText(this, "XoĂ¡ khĂ¡ch hĂ ng thĂ nh cĂ´ng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "XoĂ¡ khĂ¡ch hĂ ng tháº¥t báº¡i", Toast.LENGTH_SHORT).show();
        }
    }
}


