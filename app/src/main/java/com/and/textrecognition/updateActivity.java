package com.and.textrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class updateActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    String id = "";

    EditText editResult;
    ImageView updateBtn,copyBtn,shareBtn;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editResult=findViewById(R.id.editResult);
        updateBtn=findViewById(R.id.updateBtn);
        copyBtn=findViewById(R.id.copyBtn);
        shareBtn=findViewById(R.id.shareBtn);
        toolbar=findViewById(R.id.toolbar);

        dbHelper= new DatabaseHelper(updateActivity.this);

        id = getIntent().getExtras().getString("id");
        String title =getIntent().getExtras().getString("title");
        String time =getIntent().getExtras().getString("time");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(updateActivity.this,MainActivity.class));
            }
        });

        editResult.setText(title);
       //create_textDeteTime.setText(time);

        copyMethod();
        shareMethod();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = editResult.getText().toString();;

                if (sTitle.length()>0 ){
                    dbHelper.edit(id,sTitle,time);

//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.frameLayout, new saveFileActivity())
//                            .commit();
                }else
                    Toast.makeText(updateActivity.this, "failed", Toast.LENGTH_SHORT).show();



            }
        });


    }

    //=========================================

    private void copyMethod(){

        copyBtn.setOnClickListener(v -> {
            String textToCopy = editResult.getText().toString();

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            android.content.ClipData clip = android.content.ClipData.newPlainText("label", textToCopy);

            clipboard.setPrimaryClip(clip);
            Toast.makeText(updateActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
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