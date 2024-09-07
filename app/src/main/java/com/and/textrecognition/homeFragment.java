package com.and.textrecognition;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class homeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view= inflater.inflate(R.layout.fragment_home, container, false);
        RelativeLayout textScan = view.findViewById(R.id.textScan);
        RelativeLayout pdfReader = view.findViewById(R.id.pdfReader);


        textScan.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        pdfReader.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),pdfViewerActivity.class);
            startActivity(intent);
        });




            return  view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();

            Intent intent = new Intent(getContext(),scanActivity.class);

            intent.putExtra("img",uri);
            startActivity(intent);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }


    }
}