package com.example.sms_anhtho;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_READ_SMS = 125;
    private static final int PERMISSION_REQUEST_READ_CALL_LOG = 126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonShowAllSms = findViewById(R.id.buttonShowAllSms);
        buttonShowAllSms.setOnClickListener(v -> {
            // Kiểm tra quyền đọc tin nhắn SMS
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_READ_SMS);
            } else {
                // Nếu đã có quyền, mở activity để hiển thị tin nhắn SMS
                openShowSmsActivity();
            }
        });

        Button buttonAccessCallLog = findViewById(R.id.buttonAccessCallLog);
        buttonAccessCallLog.setOnClickListener(v -> {
            // Kiểm tra quyền đọc lịch sử cuộc gọi
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_REQUEST_READ_CALL_LOG);
            } else {
                // Nếu đã có quyền, mở activity để hiển thị lịch sử cuộc gọi
                openShowCallLogActivity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Người dùng đã cấp quyền, mở activity để hiển thị tin nhắn SMS
                openShowSmsActivity();
            } else {
                // Xử lý khi người dùng từ chối quyền
                Toast.makeText(this, "Cần quyền truy cập tin nhắn để tiếp tục!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_READ_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Người dùng đã cấp quyền, mở activity để hiển thị lịch sử cuộc gọi
                openShowCallLogActivity();
            } else {
                // Xử lý khi người dùng từ chối quyền
                Toast.makeText(this, "Cần quyền truy cập lịch sử cuộc gọi để tiếp tục!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openShowSmsActivity() {
        Intent intent = new Intent(MainActivity.this, ShowSmsActivity.class);
        startActivity(intent);
    }

    private void openShowCallLogActivity() {
        Intent intent = new Intent(MainActivity.this, ShowCallLogActivity.class);
        startActivity(intent);
    }
}
