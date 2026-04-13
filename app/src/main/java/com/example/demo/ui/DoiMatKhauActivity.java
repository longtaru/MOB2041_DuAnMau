package com.example.demo.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.common.Common;
import com.example.demo.database.DatabaseHelper;

import java.util.Objects;

public class DoiMatKhauActivity extends AppCompatActivity {
    private EditText edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMatKhauMoi;
    private Button btnLuu, btnHuy;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        edtMatKhauCu = findViewById(R.id.edtOldPassword);
        edtMatKhauMoi = findViewById(R.id.edtNewPassword);
        edtNhapLaiMatKhauMoi = findViewById(R.id.edtConfirmPassword);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Äá»•i máº­t kháº©u");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        // Láº¥y mĂ£ nhĂ¢n viĂªn tá»« Intent Ä‘á»ƒ biáº¿t nhĂ¢n viĂªn nĂ o Ä‘ang Ä‘Äƒng nháº­p

        btnLuu.setOnClickListener(v -> doiMatKhau());
        btnHuy.setOnClickListener(v -> {
            edtMatKhauCu.setText("");
            edtMatKhauMoi.setText("");
            edtNhapLaiMatKhauMoi.setText("");
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void doiMatKhau() {
        String matKhauCu = edtMatKhauCu.getText().toString().trim();
        String matKhauMoi = edtMatKhauMoi.getText().toString().trim();
        String nhapLaiMatKhauMoi = edtNhapLaiMatKhauMoi.getText().toString().trim();

        // Kiá»ƒm tra khĂ´ng Ä‘Æ°á»£c bá» trá»‘ng
        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || nhapLaiMatKhauMoi.isEmpty()) {
            Toast.makeText(this, "Vui lĂ²ng nháº­p Ä‘áº§y Ä‘á»§ thĂ´ng tin", Toast.LENGTH_SHORT).show();
            return;
        }
        // Kiá»ƒm tra máº­t kháº©u cÅ© cĂ³ Ä‘Ăºng khĂ´ng
        if (!db.kiemTraMatKhauCu(Common.maNhanVien, matKhauCu)) {
            Toast.makeText(this, "Máº­t kháº©u cÅ© khĂ´ng chĂ­nh xĂ¡c!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Kiá»ƒm tra máº­t kháº©u má»›i vĂ  xĂ¡c nháº­n pháº£i giá»‘ng nhau
        if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
            Toast.makeText(this, "Máº­t kháº©u má»›i khĂ´ng khá»›p!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Náº¿u Ä‘Ăºng, tiáº¿n hĂ nh cáº­p nháº­t máº­t kháº©u má»›i vĂ o SQLite
        if (db.capNhatMatKhauMoi(Common.maNhanVien, matKhauMoi)) {
            Toast.makeText(this, "Äá»•i máº­t kháº©u thĂ nh cĂ´ng!", Toast.LENGTH_SHORT).show();
            finish(); // ÄĂ³ng activity sau khi Ä‘á»•i máº­t kháº©u
        } else {
            Toast.makeText(this, "Äá»•i máº­t kháº©u tháº¥t báº¡i, vui lĂ²ng thá»­ láº¡i!", Toast.LENGTH_SHORT).show();
        }
    }
}

