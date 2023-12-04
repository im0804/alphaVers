package com.example.alphavers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class timeActivity extends AppCompatActivity {
    //Date date = new Date();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    private EditText etName;
    private TextView savedTv;
    private Map<String, String> db;
    String alltext;
    ValueEventListener vel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        etName = findViewById(R.id.etName);
        savedTv = findViewById(R.id.savedTV);
        db = new HashMap<>();
    }
    public void save(View view) {
        String text = etName.getText().toString();
        String timestamp = getCurrentTimestampWithMilliseconds();
        db.put(timestamp, text);
        StringBuilder allTexts = new StringBuilder();
        for (Map.Entry<String, String> entry : db.entrySet()) {
            timestamp = entry.getKey();
            text = entry.getValue();
            allTexts.append("Timestamp: ").append(timestamp).append("\n").append("Text: ").append(text).append("\n\n");
        }
        myRef.setValue(allTexts.toString());
    }

    public void showSavedText(View view) {
        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alltext = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(timeActivity.this, "failed", Toast.LENGTH_LONG).show();
            }
        };
        myRef.addValueEventListener(vel);
        savedTv.setText(alltext);
    }

    private String getCurrentTimestampWithMilliseconds() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String str = item.getTitle().toString();
        if (str.equals("login")){
            Intent si = new Intent(this, loginActivity.class);
            startActivity(si);
        }
        else if (str.equals("camera")){
            Intent si = new Intent(this, cameraActivity.class);
            startActivity(si);
        }
        else if (str.equals("gallery")){
            Intent si = new Intent(this, galleryActivity.class);
            startActivity(si);
        }
        else if (str.equals("time")){
        }
        else {
            Intent si = new Intent(this, mapViewActivity.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }


}