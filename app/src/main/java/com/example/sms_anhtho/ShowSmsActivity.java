package com.example.sms_anhtho;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowSmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sms);

        // Gọi hàm để đọc tin nhắn
        readSmsMessages();
    }

    private void readSmsMessages() {
        List<String> smsList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri smsUri = Uri.parse("content://sms/"); // URI cho tin nhắn SMS

        // Truy vấn cơ sở dữ liệu SMS
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(smsUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int bodyColumnIndex = cursor.getColumnIndex("body");
                if (bodyColumnIndex != -1) {
                    do {
                        String body = cursor.getString(bodyColumnIndex);
                        smsList.add(body);
                    } while (cursor.moveToNext());
                } else {
                    Toast.makeText(this, "Không tìm thấy cột 'body' trong cơ sở dữ liệu SMS!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không có tin nhắn nào để hiển thị!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có lỗi xảy ra khi đọc tin nhắn!", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        displayMessages(smsList);
    }

    private void displayMessages(List<String> smsList) {
        ListView listView = findViewById(R.id.listViewSms);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsList);
        listView.setAdapter(adapter);
    }
}
