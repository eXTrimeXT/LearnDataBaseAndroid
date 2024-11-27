package com.example.leardatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Button button_add, button_read, button_clear;
    EditText et_name, et_gmail;

    DB_Helper db_helper;

    public void MyToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = findViewById(R.id.button_add);
        button_read = findViewById(R.id.button_read);
        button_clear = findViewById(R.id.button_clear);
        et_name = findViewById(R.id.et_name);
        et_gmail = findViewById(R.id.et_gmail);

        db_helper = new DB_Helper(this);
    }

    public void Click(View view) {
        SQLiteDatabase database = db_helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (view.getId()) {
            case R.id.button_add:
                String name = et_name.getText().toString();
                String gmail = et_gmail.getText().toString();

                contentValues.put(DB_Helper.KEY_NAME, name);
                contentValues.put(DB_Helper.KEY_GMAIL, gmail);

                MyToast("Add name = " + name);
                MyToast("Add gmail = " + gmail);

                database.insert(DB_Helper.TABLE_CONTACTS, null, contentValues);
                break;

            case R.id.button_read:
                Cursor cursor = database.query(DB_Helper.TABLE_CONTACTS, null, null, null,
                        null, null, null);
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DB_Helper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DB_Helper.KEY_NAME);
                    int gmailIndex = cursor.getColumnIndex(DB_Helper.KEY_GMAIL);
                    do {
                        String TEXT =
                                "ID = " + cursor.getInt(idIndex) +
                                ", NAME = " + cursor.getString(nameIndex) +
                                ", GMAIL = " + cursor.getString(gmailIndex);

                        MyToast(TEXT);
                    } while (cursor.moveToNext());
                } else {
                    MyToast("DATABASE IS EMPTY!");
                }
                cursor.close();
                break;

            case R.id.button_clear:
                database.delete(DB_Helper.TABLE_CONTACTS, null, null);
                MyToast("DATABASE IS CLEAR!");
                break;
        }
        db_helper.close();
    }
}