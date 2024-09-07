package com.and.textrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class scanActivity extends AppCompatActivity {

    ImageView imageView;
    Intent intent;

    RelativeLayout resultBtn;
    Uri uri;

    TextRecognizer textRecognizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        imageView = findViewById(R.id.imageView);
        resultBtn = findViewById(R.id.resultBtn);



        intent = getIntent();

        uri = (Uri) intent.getParcelableExtra("img");
        imageView.setImageURI(uri);

        textRecognizer =  TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        resultBtn.setOnClickListener(v -> {
            recognizeText();
        });
    }

    //================================================
    private void recognizeText() {

        if (uri != null) {

            try {
                InputImage inputImage = InputImage.fromFilePath(scanActivity.this,uri);

                Task<Text> result = textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @SuppressLint("SuspiciousIndentation")
                            @Override
                            public void onSuccess(Text text) {


                                String recongText = text.getText();

                                if ( !recongText.isEmpty()){

                                    Intent intent1 = new Intent(scanActivity.this,resultActivity.class);
                                    intent1.putExtra("result",recongText);
                                    startActivity(intent1);
                                }else
                                    Toast.makeText(scanActivity.this, "No text Here :)", Toast.LENGTH_SHORT).show();
                                onBackPressed();



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(scanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //================================================
    }
}