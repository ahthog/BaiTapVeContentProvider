package com.example.sms_anhtho;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ShowCallLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_call_log);

        // Gọi hàm để đọc lịch sử cuộc gọi
        readCallLog();
    }

    private void readCallLog() {
        List<String> callList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Uri callLogUri = Uri.parse("content://call_log/calls"); // URI để truy cập lịch sử cuộc gọi

        Cursor cursor = contentResolver.query(callLogUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int numberColumnIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeColumnIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateColumnIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationColumnIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            do {
                String number = cursor.getString(numberColumnIndex);
                String type = cursor.getString(typeColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                String duration = cursor.getString(durationColumnIndex);

                callList.add("Number: " + number + ", Type: " + type + ", Date: " + date + ", Duration: " + duration);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Toast.makeText(this, "Không có lịch sử cuộc gọi nào để hiển thị!", Toast.LENGTH_SHORT).show();
        }

        displayCallLog(callList);
    }

    private void displayCallLog(List<String> callList) {
        ListView listView = findViewById(R.id.listViewCallLog);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, callList);
        listView.setAdapter(adapter);
    }
}
