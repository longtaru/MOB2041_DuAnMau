package com.example.demo.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.common.Common;
import com.example.demo.database.DatabaseHelper;
import com.example.demo.model.DanhMuc;
import com.example.demo.model.HoaDon;
import com.example.demo.model.HoaDonChiTiet;
import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import com.example.demo.model.SanPham;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private CheckBox chkRemember;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        Button btnLogin = findViewById(R.id.btnLogin);

        db = new DatabaseHelper(this);
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isInit = prefs.getBoolean("init", false);
        if (!isInit) {
            taoDuLieuNhanVien();
            taoDuLieuDanhMuc();
            taoDuLieuSanPham();
            taoDuLieuKhachHang();
            taoDuLieuHoaDon();
            taoDuLieuHoaDonChiTiet();
            prefs.edit().putBoolean("init", true).apply();
        }

       // Khi má»Ÿ á»©ng dá»¥ng, kiá»ƒm tra vĂ  tá»± Ä‘á»™ng Ä‘iá»n username/password
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        ghiNhoThongTinNguoiDung();      //tá»± Ä‘á»™ng láº¥y vĂ  Ä‘iá»n username/password
        btnLogin.setOnClickListener(view -> checkDangNhap());
    }

    private void ghiNhoThongTinNguoiDung() {
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean isRemembered = sharedPreferences.getBoolean("remember", false);

        edtUsername.setText(savedUsername);
        edtPassword.setText(savedPassword);
        chkRemember.setChecked(isRemembered);
    }

    private void checkDangNhap() {
        String maNhanVien = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (maNhanVien.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lĂ²ng nháº­p Ä‘áº§y Ä‘á»§ thĂ´ng tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        NhanVien nhanVien = db.layNhanVienBangMaNV(maNhanVien);

        // CĂ¡ch xá»­ lĂ½: Kiá»ƒm tra nhanVien cĂ³ tá»“n táº¡i (khĂ¡c null) trÆ°á»›c khi láº¥y máº­t kháº©u
        if (nhanVien != null) {
            if (password.equals(nhanVien.getMatKhau())) {
                if (chkRemember.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", maNhanVien);
                    editor.putString("password", password);
                    editor.putBoolean("remember", true);
                    editor.apply();
                } else {
                    sharedPreferences.edit().clear().apply();
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("CHUC_VU", nhanVien.getChucVu()); // truyá»n chá»©c vá»¥
                startActivity(intent);
                Common.maNhanVien = maNhanVien;
                finish();
            } else {
                Toast.makeText(this, "Sai máº­t kháº©u!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "TĂ i khoáº£n khĂ´ng tá»“n táº¡i!", Toast.LENGTH_SHORT).show();
        }
    }

    public void taoDuLieuSanPham() {
        DatabaseHelper db = new DatabaseHelper(this);

        SanPham sp1 = new SanPham("SP001", "NÆ°á»›c ngá»t Calpis", 12000, 50, "Lon", "2024-02-08", "DM01");
        db.themSanPham(sp1);

        SanPham sp2 = new SanPham("SP002", "TrĂ  xanh Ito En", 10000, 40, "Lon", "2024-02-08", "DM01");
        db.themSanPham(sp2);

        SanPham sp3 = new SanPham("SP003", "BĂ¡nh Pocky", 25000, 30, "Há»™p", "2024-02-07", "DM02");
        db.themSanPham(sp3);

        SanPham sp4 = new SanPham("SP004", "Sá»¯a Meiji", 15000, 20, "Há»™p", "2024-02-06", "DM03");
        db.themSanPham(sp4);

        SanPham sp5 = new SanPham("SP005", "MĂ¬ Udon", 5000, 100, "GĂ³i", "2024-02-05", "DM04");
        db.themSanPham(sp5);

        SanPham sp6 = new SanPham("SP006", "NÆ°á»›c khoĂ¡ng Suntory", 8000, 60, "Chai", "2024-02-04", "DM01");
        db.themSanPham(sp6);

        SanPham sp7 = new SanPham("SP007", "Snack Jagabee", 12000, 45, "GĂ³i", "2024-02-08", "DM05");
        db.themSanPham(sp7);

        SanPham sp8 = new SanPham("SP008", "TrĂ  gáº¡o lá»©t Genmaicha", 30000, 25, "Há»™p", "2024-02-08", "DM06");
        db.themSanPham(sp8);

        SanPham sp9 = new SanPham("SP009", "Dáº§u Äƒn Ajinomoto", 45000, 15, "Chai", "2024-02-03", "DM07");
        db.themSanPham(sp9);

        SanPham sp10 = new SanPham("SP010", "ÄÆ°á»ng nĂ¢u Okinawa", 20000, 50, "Bao", "2024-02-02", "DM08");
        db.themSanPham(sp10);

        SanPham sp11 = new SanPham("SP011", "NÆ°á»›c tÆ°Æ¡ng Kikkoman", 15000, 40, "Chai", "2024-02-09", "DM09");
        db.themSanPham(sp11);

        SanPham sp12 = new SanPham("SP012", "BĂ¡nh mochi Nháº­t Báº£n", 32000, 35, "Há»™p", "2024-02-10", "DM02");
        db.themSanPham(sp12);

        SanPham sp13 = new SanPham("SP013", "Bia Asahi", 45000, 25, "Lon", "2024-02-11", "DM10");
        db.themSanPham(sp13);

        SanPham sp14 = new SanPham("SP014", "Dáº§u gá»™i Tsubaki", 90000, 20, "Chai", "2024-02-12", "DM11");
        db.themSanPham(sp14);

        SanPham sp15 = new SanPham("SP015", "Sá»¯a tÆ°Æ¡i Morinaga", 12000, 30, "Há»™p", "2024-02-13", "DM03");
        db.themSanPham(sp15);

        SanPham sp16 = new SanPham("SP016", "MĂ¬ Ramen", 4000, 150, "GĂ³i", "2024-02-14", "DM04");
        db.themSanPham(sp16);

        SanPham sp17 = new SanPham("SP017", "Bá»™t giáº·t Attack", 125000, 20, "Bao", "2024-02-15", "DM12");
        db.themSanPham(sp17);

        SanPham sp18 = new SanPham("SP018", "DÆ°a lÆ°á»›i Nháº­t", 18000, 50, "Kg", "2024-02-16", "DM13");
        db.themSanPham(sp18);

        SanPham sp19 = new SanPham("SP019", "TĂ¡o Aomori", 50000, 40, "Kg", "2024-02-17", "DM13");
        db.themSanPham(sp19);

        SanPham sp20 = new SanPham("SP020", "Rau cáº£i Mizuna", 25000, 60, "Kg", "2024-02-18", "DM14");
        db.themSanPham(sp20);

    }

    public void taoDuLieuKhachHang() {
        DatabaseHelper db = new DatabaseHelper(this);

        db.themKhachHang(new KhachHang("KH001", "Náº·c danh", "", "0000", ""));
        db.themKhachHang(new KhachHang("KH002", "Tráº§n Thá»‹ BĂ­ch", "456 ÄÆ°á»ng XYZ, HĂ  Ná»™i", "0988777666", "tranthibich@example.com"));
        db.themKhachHang(new KhachHang("KH003", "LĂª HoĂ ng Nam", "789 ÄÆ°á»ng DEF, ÄĂ  Náºµng", "0912333444", "lehoangnam@example.com"));
        db.themKhachHang(new KhachHang("KH004", "Pháº¡m Minh Khang", "101 ÄÆ°á»ng GHI, Cáº§n ThÆ¡", "0922112233", "phamminhkhang@example.com"));
        db.themKhachHang(new KhachHang("KH005", "VĂµ Há»“ng PhĂºc", "202 ÄÆ°á»ng JKL, Háº£i PhĂ²ng", "0933445566", "vohongphuc@example.com"));
        db.themKhachHang(new KhachHang("KH006", "BĂ¹i Thá»‹ Lan", "303 ÄÆ°á»ng MNO, Nha Trang", "0944556677", "buithilan@example.com"));
        db.themKhachHang(new KhachHang("KH007", "Äáº·ng Quá»‘c Duy", "404 ÄÆ°á»ng PQR, Huáº¿", "0955667788", "dangquocduy@example.com"));
        db.themKhachHang(new KhachHang("KH008", "NgĂ´ VÄƒn HĂ¹ng", "505 ÄÆ°á»ng STU, VÅ©ng TĂ u", "0966778899", "ngovanhung@example.com"));
        db.themKhachHang(new KhachHang("KH009", "HoĂ ng Thá»‹ Háº¡nh", "606 ÄÆ°á»ng VWX, BiĂªn HĂ²a", "0977889900", "hoangthihanh@example.com"));
        db.themKhachHang(new KhachHang("KH010", "TrÆ°Æ¡ng Minh TĂº", "707 ÄÆ°á»ng YZA, BĂ¬nh DÆ°Æ¡ng", "0988990011", "truongminhtu@example.com"));
        db.themKhachHang(new KhachHang("KH011", "ÄoĂ n VÄƒn ThĂ nh", "808 ÄÆ°á»ng BCD, Long An", "0999001122", "doanvanthanh@example.com"));
        db.themKhachHang(new KhachHang("KH012", "Mai Thá»‹ Ngá»c", "909 ÄÆ°á»ng CDE, TĂ¢y Ninh", "0910012233", "maithingoc@example.com"));
        db.themKhachHang(new KhachHang("KH013", "Phan VÄƒn Máº¡nh", "111 ÄÆ°á»ng EFG, VÄ©nh Long", "0921123344", "phanvanmanh@example.com"));
        db.themKhachHang(new KhachHang("KH014", "Tráº§n HoĂ ng Lá»™c", "222 ÄÆ°á»ng GHI, SĂ³c TrÄƒng", "0932234455", "tranhoangloc@example.com"));
        db.themKhachHang(new KhachHang("KH015", "Nguyá»…n Thá»‹ Thanh", "333 ÄÆ°á»ng IJK, Báº¡c LiĂªu", "0943345566", "nguyenthithanh@example.com"));
        db.themKhachHang(new KhachHang("KH016", "LĂ½ Minh Äá»©c", "444 ÄÆ°á»ng KLM, CĂ  Mau", "0954456677", "lyminhduc@example.com"));
        db.themKhachHang(new KhachHang("KH017", "VÆ°Æ¡ng Há»“ng SÆ¡n", "555 ÄÆ°á»ng MNO, Äáº¯k Láº¯k", "0965567788", "vuonghongson@example.com"));
        db.themKhachHang(new KhachHang("KH018", "HĂ  Thá»‹ Nhung", "666 ÄÆ°á»ng OPQ, Gia Lai", "0976678899", "hathinhung@example.com"));
        db.themKhachHang(new KhachHang("KH019", "Trá»‹nh VÄƒn DÅ©ng", "777 ÄÆ°á»ng QRS, Kon Tum", "0987789900", "trinhvandung@example.com"));
        db.themKhachHang(new KhachHang("KH020", "Tá»‘ng HoĂ ng Anh", "888 ÄÆ°á»ng STU, PhĂº YĂªn", "0998890011", "tonghoanganh@example.com"));
    }

    public void taoDuLieuDanhMuc() {
        DatabaseHelper db = new DatabaseHelper(this);

        db.themDanhMuc(new DanhMuc("DM001", "Äá»“ uá»‘ng"));
        db.themDanhMuc(new DanhMuc("DM002", "BĂ¡nh káº¹o"));
        db.themDanhMuc(new DanhMuc("DM003", "Sá»¯a vĂ  cháº¿ pháº©m tá»« sá»¯a"));
        db.themDanhMuc(new DanhMuc("DM004", "MĂ¬ - ChĂ¡o - Phá»Ÿ"));
        db.themDanhMuc(new DanhMuc("DM005", "Thá»±c pháº©m khĂ´"));
        db.themDanhMuc(new DanhMuc("DM006", "Gia vá»‹"));
        db.themDanhMuc(new DanhMuc("DM007", "Äá»“ há»™p"));
        db.themDanhMuc(new DanhMuc("DM008", "Rau cá»§ quáº£"));
        db.themDanhMuc(new DanhMuc("DM009", "Thá»‹t - Háº£i sáº£n"));
        db.themDanhMuc(new DanhMuc("DM010", "Sáº£n pháº©m Ä‘Ă´ng láº¡nh"));
        db.themDanhMuc(new DanhMuc("DM011", "Äá»“ Äƒn nhanh"));
        db.themDanhMuc(new DanhMuc("DM012", "Thá»±c pháº©m chá»©c nÄƒng"));
        db.themDanhMuc(new DanhMuc("DM013", "HĂ³a má»¹ pháº©m"));
        db.themDanhMuc(new DanhMuc("DM014", "Äá»“ dĂ¹ng gia Ä‘Ă¬nh"));
        db.themDanhMuc(new DanhMuc("DM015", "Sáº£n pháº©m cho bĂ©"));
        db.themDanhMuc(new DanhMuc("DM016", "Äá»“ dĂ¹ng vÄƒn phĂ²ng"));
        db.themDanhMuc(new DanhMuc("DM017", "Thiáº¿t bá»‹ Ä‘iá»‡n tá»­"));
        db.themDanhMuc(new DanhMuc("DM018", "Sáº£n pháº©m thá»i trang"));
        db.themDanhMuc(new DanhMuc("DM019", "Äá»“ thá»ƒ thao"));
        db.themDanhMuc(new DanhMuc("DM020", "SĂ¡ch - VÄƒn hĂ³a pháº©m"));
    }

    public void taoDuLieuNhanVien() {
        DatabaseHelper db = new DatabaseHelper(this);

        db.themNhanVien(new NhanVien("NV001", "Nguyá»…n VÄƒn An", "12 LĂ½ Tá»± Trá»ng, Q1, TP.HCM", 1, 25000000, "admin123"));
        db.themNhanVien(new NhanVien("NV002", "Tráº§n Thá»‹ CĂºc", "34 LĂª Lá»£i, Q3, TP.HCM", 0, 12000000, "sales456"));
        db.themNhanVien(new NhanVien("NV003", "LĂª Minh HĂ¹ng", "56 Tráº§n HÆ°ng Äáº¡o, HĂ  Ná»™i", 0, 20000000, "accounting789"));
        db.themNhanVien(new NhanVien("NV004", "Pháº¡m Äá»©c Thá»‹nh", "78 Nguyá»…n Du, ÄĂ  Náºµng", 0, 9000000, "warehouse101"));
        db.themNhanVien(new NhanVien("NV005", "VĂµ HoĂ ng Yáº¿n", "90 Pasteur, Háº£i PhĂ²ng", 0, 11000000, "marketing202"));
        db.themNhanVien(new NhanVien("NV006", "BĂ¹i VÄƒn HĂ²a", "21 Phan ÄĂ¬nh PhĂ¹ng, Nha Trang", 0, 18000000, "hr303"));
        db.themNhanVien(new NhanVien("NV007", "Äáº·ng Ngá»c Hiáº¿u", "43 HoĂ ng Hoa ThĂ¡m, Huáº¿", 0, 7000000, "security404"));
        db.themNhanVien(new NhanVien("NV008", "NgĂ´ Thanh BĂ¬nh", "65 CĂ¡ch Máº¡ng ThĂ¡ng 8, VÅ©ng TĂ u", 0, 15000000, "it505"));
        db.themNhanVien(new NhanVien("NV009", "HoĂ ng Thá»‹ Dung", "87 Hai BĂ  TrÆ°ng, BiĂªn HĂ²a", 0, 17000000, "assistant606"));
        db.themNhanVien(new NhanVien("NV010", "TrÆ°Æ¡ng ÄĂ¬nh Khoa", "109 Nguyá»…n VÄƒn Trá»—i, BĂ¬nh DÆ°Æ¡ng", 0, 8000000, "delivery707"));
        db.themNhanVien(new NhanVien("NV011", "ÄoĂ n Thanh TĂ¹ng", "121 Äiá»‡n BiĂªn Phá»§, Long An", 0, 10000000, "cashier808"));
        db.themNhanVien(new NhanVien("NV012", "Mai Thá»‹ Lan", "143 VĂµ VÄƒn Kiá»‡t, TĂ¢y Ninh", 0, 9500000, "support909"));
        db.themNhanVien(new NhanVien("NV013", "Phan Ngá»c Quá»³nh", "165 TĂ´n Äá»©c Tháº¯ng, VÄ©nh Long", 0, 11000000, "consultant111"));
        db.themNhanVien(new NhanVien("NV014", "Tráº§n HoĂ ng Nam", "187 Nguyá»…n Tri PhÆ°Æ¡ng, SĂ³c TrÄƒng", 0, 22000000, "sales_manager222"));
        db.themNhanVien(new NhanVien("NV015", "Nguyá»…n Thá»‹ BĂ­ch", "209 Tráº§n Quang Kháº£i, Báº¡c LiĂªu", 0, 24000000, "finance333"));
        db.themNhanVien(new NhanVien("NV016", "LĂ½ Máº¡nh PhĂ¡t", "231 Nguyá»…n ÄĂ¬nh Chiá»ƒu, CĂ  Mau", 0, 10500000, "inventory444"));
        db.themNhanVien(new NhanVien("NV017", "VÆ°Æ¡ng Há»¯u Äáº¡t", "253 VĂµ Thá»‹ SĂ¡u, Äáº¯k Láº¯k", 0, 9800000, "maintenance555"));
        db.themNhanVien(new NhanVien("NV018", "HĂ  Thanh Háº£i", "275 Báº¡ch Äáº±ng, Gia Lai", 0, 13000000, "designer666"));
        db.themNhanVien(new NhanVien("NV019", "Trá»‹nh Minh Lá»™c", "297 Äá»‘ng Äa, Kon Tum", 0, 12000000, "market777"));
        db.themNhanVien(new NhanVien("NV020", "Tá»‘ng HoĂ ng TĂº", "319 HĂ¹ng VÆ°Æ¡ng, PhĂº YĂªn", 0, 16000000, "developer888"));
    }

    public void taoDuLieuHoaDon() {

// ThĂªm má»™t sá»‘ hĂ³a Ä‘Æ¡n máº«u
        DatabaseHelper db = new DatabaseHelper(this);

// ThĂªm má»™t sá»‘ hĂ³a Ä‘Æ¡n máº«u
// ThĂ¡ng 1
        db.themHoaDon(new HoaDon("HD001", "NV001", "KH001", "2024-01-05", 180000));
        db.themHoaDon(new HoaDon("HD002", "NV002", "KH002", "2024-01-15", 220000));
// ThĂ¡ng 2
        db.themHoaDon(new HoaDon("HD003", "NV003", "KH003", "2024-02-03", 300000));
        db.themHoaDon(new HoaDon("HD004", "NV001", "KH004", "2024-02-05", 250000));
// ThĂ¡ng 3
        db.themHoaDon(new HoaDon("HD005", "NV002", "KH005", "2024-03-08", 180000));
        db.themHoaDon(new HoaDon("HD006", "NV003", "KH006", "2024-03-12", 270000));
// ThĂ¡ng 4
        db.themHoaDon(new HoaDon("HD007", "NV004", "KH007", "2024-04-11", 275000));
        db.themHoaDon(new HoaDon("HD008", "NV005", "KH008", "2024-04-18", 195000));
// ThĂ¡ng 5
        db.themHoaDon(new HoaDon("HD009", "NV006", "KH009", "2024-05-05", 320000));
        db.themHoaDon(new HoaDon("HD010", "NV007", "KH010", "2024-05-21", 290000));
// ThĂ¡ng 6
        db.themHoaDon(new HoaDon("HD011", "NV008", "KH011", "2024-06-02", 310000));
        db.themHoaDon(new HoaDon("HD012", "NV009", "KH012", "2024-06-25", 350000));
// ThĂ¡ng 7
        db.themHoaDon(new HoaDon("HD013", "NV010", "KH013", "2024-07-17", 400000));
        db.themHoaDon(new HoaDon("HD014", "NV011", "KH014", "2024-07-22", 375000));
// ThĂ¡ng 8
        db.themHoaDon(new HoaDon("HD015", "NV012", "KH015", "2024-08-09", 280000));
        db.themHoaDon(new HoaDon("HD016", "NV001", "KH016", "2024-08-30", 390000));
// ThĂ¡ng 9
        db.themHoaDon(new HoaDon("HD017", "NV002", "KH017", "2024-09-11", 410000));
        db.themHoaDon(new HoaDon("HD018", "NV003", "KH018", "2024-09-27", 225000));
// ThĂ¡ng 10
        db.themHoaDon(new HoaDon("HD019", "NV004", "KH019", "2024-10-15", 450000));
        db.themHoaDon(new HoaDon("HD020", "NV005", "KH020", "2024-10-31", 395000));
// ThĂ¡ng 11
        db.themHoaDon(new HoaDon("HD021", "NV006", "KH011", "2024-11-10", 460000));
        db.themHoaDon(new HoaDon("HD022", "NV007", "KH012", "2024-11-25", 310000));
// ThĂ¡ng 12
        db.themHoaDon(new HoaDon("HD023", "NV008", "KH013", "2024-12-05", 500000));
        db.themHoaDon(new HoaDon("HD024", "NV009", "KH014", "2024-12-20", 420000));
// ThĂªm hĂ³a Ä‘Æ¡n cho nÄƒm 2025
// ThĂ¡ng 1 - 12 cá»§a nÄƒm 2025
        db.themHoaDon(new HoaDon("HD025", "NV001", "KH001", "2025-01-10", 185000));
        db.themHoaDon(new HoaDon("HD026", "NV002", "KH002", "2025-01-22", 225000));
        db.themHoaDon(new HoaDon("HD027", "NV003", "KH003", "2025-02-07", 305000));
        db.themHoaDon(new HoaDon("HD028", "NV004", "KH004", "2025-02-20", 260000));
        db.themHoaDon(new HoaDon("HD029", "NV005", "KH005", "2025-03-15", 190000));
        db.themHoaDon(new HoaDon("HD030", "NV006", "KH006", "2025-03-28", 275000));
        db.themHoaDon(new HoaDon("HD031", "NV007", "KH007", "2025-04-09", 280000));
        db.themHoaDon(new HoaDon("HD032", "NV008", "KH008", "2025-04-25", 200000));
        db.themHoaDon(new HoaDon("HD033", "NV009", "KH009", "2025-05-10", 330000));
        db.themHoaDon(new HoaDon("HD034", "NV010", "KH010", "2025-05-29", 295000));
        db.themHoaDon(new HoaDon("HD035", "NV011", "KH011", "2025-06-14", 315000));
        db.themHoaDon(new HoaDon("HD036", "NV012", "KH012", "2025-06-30", 355000));
        db.themHoaDon(new HoaDon("HD037", "NV001", "KH013", "2025-07-19", 410000));
        db.themHoaDon(new HoaDon("HD038", "NV002", "KH014", "2025-07-27", 380000));
        db.themHoaDon(new HoaDon("HD039", "NV003", "KH015", "2025-08-12", 290000));
        db.themHoaDon(new HoaDon("HD040", "NV004", "KH016", "2025-08-31", 400000));
        db.themHoaDon(new HoaDon("HD041", "NV005", "KH017", "2025-09-14", 420000));
        db.themHoaDon(new HoaDon("HD042", "NV006", "KH018", "2025-09-29", 230000));
        db.themHoaDon(new HoaDon("HD043", "NV007", "KH019", "2025-10-18", 460000));
        db.themHoaDon(new HoaDon("HD044", "NV008", "KH020", "2025-10-30", 400000));
        db.themHoaDon(new HoaDon("HD045", "NV009", "KH001", "2025-11-12", 470000));
        db.themHoaDon(new HoaDon("HD046", "NV010", "KH020", "2025-11-28", 320000));
        db.themHoaDon(new HoaDon("HD047", "NV011", "KH020", "2025-12-07", 510000));
        db.themHoaDon(new HoaDon("HD048", "NV012", "KH012", "2025-12-22", 430000));
    }

    public void taoDuLieuHoaDonChiTiet() {
        DatabaseHelper db = new DatabaseHelper(this);
        int soLuongRecords = 150;
        int hdIndex = 1;

        for (int i = 1; i <= soLuongRecords; ) {
            String maHoaDon = "HD" + String.format("%03d", hdIndex);
            int soSanPham = (int) (Math.random() * 6) + 5; // Má»—i hĂ³a Ä‘Æ¡n mua tá»« 5 Ä‘áº¿n 10 sáº£n pháº©m
            double tongTien = 0;

            for (int j = 0; j < soSanPham && i <= soLuongRecords; j++, i++) {
                String maHDCT = "HDCT" + String.format("%03d", i);
                String maSanPham = "SP" + String.format("%03d", ((i - 1) % 20) + 1);
                int soLuong = (int) (Math.random() * 10) + 1; // Sá»‘ lÆ°á»£ng ngáº«u nhiĂªn tá»« 1-10
                double donGia = db.layDonGiaSanPham(maSanPham); // Láº¥y Ä‘Æ¡n giĂ¡ tá»« báº£ng sáº£n pháº©m

                db.themHDCT(new HoaDonChiTiet(maHDCT, maHoaDon, maSanPham, soLuong, donGia));
                tongTien += soLuong * donGia;
            }

            // Cáº­p nháº­t tá»•ng tiá»n trong hĂ³a Ä‘Æ¡n
            db.capNhatTongTienHoaDon(maHoaDon, tongTien);

            hdIndex++;
            if (hdIndex > 48) {
                hdIndex = 1;
            }
        }
    }

}

