package com.and.textrecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class resultActivity extends AppCompatActivity {

    EditText editResult;

    MaterialToolbar toolbar;


    ImageView saveBtn,copyBtn,shareBtn;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        editResult = findViewById(R.id.editResult);
        toolbar = findViewById(R.id.toolbar);
        saveBtn = findViewById(R.id.saveBtn);
        copyBtn = findViewById(R.id.copyBtn);
        shareBtn = findViewById(R.id.shareBtn);

        copyMethod();
        shareMethod();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(resultActivity.this,MainActivity.class));
            }
        });

        String receivedText = getIntent().getStringExtra("result");
        if (receivedText != null && !receivedText.isEmpty()) {
            editResult.setText(receivedText);
        }

        dbHelper= new DatabaseHelper(this);

        saveBtn .setOnClickListener(v -> {


            String sTitle = editResult.getText().toString();

            if (sTitle.length()>0){

                dbHelper.addNotes(sTitle);

                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();

//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, new saveFileActivity())
//                        .commit();

            }else
                Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show();





});

    }

//=======================================
    private void copyMethod(){

        copyBtn.setOnClickListener(v -> {
            String textToCopy = editResult.getText().toString();

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            android.content.ClipData clip = android.content.ClipData.newPlainText("label", textToCopy);

            clipboard.setPrimaryClip(clip);
            Toast.makeText(resultActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        });
    }

    private void shareMethod(){
        shareBtn.setOnClickListener(v -> {

            String textToShare = editResult.getText().toString();


            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);


            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }
}