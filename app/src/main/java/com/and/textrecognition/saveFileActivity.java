package com.and.textrecognition;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class saveFileActivity extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
    }

    DatabaseHelper dbHelper;
    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String ,String > hashMap;

    GridView  gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_file_activity, container, false);

        gridView = view.findViewById(R.id.gridView);

        dbHelper= new DatabaseHelper(getContext());

        allData(dbHelper.getallData());

        return view;
    }


    //=============================================

    //=============================================

    public  void allData(Cursor cursor){




        //Cursor cursor =  dbHelper.getallData();

        if (cursor!=null && cursor.getCount()>0){

            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){

                int id =cursor.getInt(0);
                String title =cursor.getString(1);
                String time =cursor.getString(2);




                hashMap = new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("title",""+title);
                hashMap.put("time",""+time);


                arrayList.add(hashMap);

            }

            gridView.setAdapter(new MyAdapter());
        }else{


        }
    }




    // =============================================
    // =============================================
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater =getLayoutInflater();
            View myView = inflater.inflate(R.layout.item,parent,false);


            TextView notesTitle = myView.findViewById(R.id.notesTitle);
            TextView notesDate = myView.findViewById(R.id.notesDate);
            LinearLayout layItem = myView.findViewById(R.id.layItem);
            ImageView deleteBtn = myView.findViewById(R.id.deleteBtn);
            ImageView editBtn = myView.findViewById(R.id.editBtn);




            HashMap<String,String> myMap = arrayList.get(position);
            String id =myMap.get("id");
            String title =myMap.get("title");
            String time =myMap.get("time");

            notesTitle.setText(title);

            notesDate.setText(time);


            layItem.setOnClickListener( v -> {


            });


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.menus_item, null);
                    alert.setView(mView);

                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCancelable(true);






                    mView.findViewById(R.id.DeleteBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            View mView = getLayoutInflater().inflate(R.layout.layout_delete_note, null);
                            alert.setView(mView);

                            final AlertDialog AalertDialog = alert.create();
                            AalertDialog.setCancelable(false);

                            mView.findViewById(R.id.chancelBTN).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AalertDialog.dismiss();
                                }
                            });


                            mView.findViewById(R.id.okBTN).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    dbHelper.deleteNotes(id);
                                    allData(dbHelper.getallData());
                                    notifyDataSetChanged();
                                    onResume();
                                    AalertDialog.dismiss();
                                    Toast.makeText(getContext(), "texts Delete Successful!", Toast.LENGTH_SHORT).show();


                                }
                            });



                            AalertDialog.show();




                        }
                    });






                    alertDialog.show();

                }
            });

            editBtn.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(),updateActivity.class);

                intent.putExtra("id",id);
                intent.putExtra("title",title);;
                intent.putExtra("time",time);

                startActivity(intent);


            });

            return myView;
        }}





}