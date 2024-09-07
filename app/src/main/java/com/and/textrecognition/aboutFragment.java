package com.and.textrecognition;



import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;


public class aboutFragment extends Fragment {

    MaterialToolbar toolbar;

    CardView shareBtn,policyBtn,rateBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        shareBtn = view.findViewById(R.id.shareBtn);
        policyBtn = view.findViewById(R.id.policyBtn);
        rateBtn = view.findViewById(R.id.rateBtn);


        shareBtn.setOnClickListener(v -> {
            ShareApp(getContext());
        });

        policyBtn.setOnClickListener(v -> {
            privacy_policy();
        });

        rateBtn.setOnClickListener(v -> {
          rateUSMethod();
        });




        return  view;
    }


    //Share App code
    private void ShareApp(Context context){
        // code here
        final String appPakageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Now : https://play.google.com/store/apps/details?id=" + appPakageName );
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    //privacy_policy_link_open_code
    private void privacy_policy(){
        try {

            String download_link = "https://sites.google.com/view/privacy-policy-2048-puzzle/home";
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(download_link));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void rateUSMethod(){

        String appName = getContext().getPackageName();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName));
        getContext().startActivity(intent);
    }
}