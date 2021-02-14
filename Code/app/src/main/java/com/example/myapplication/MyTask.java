package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.RequiresPermission;

import com.example.myapplication.readAndSaveAllFile.MultipleFileData;
import com.example.myapplication.readAndSaveAllFile.ReadAndSaveMultipleFile;
import com.example.myapplication.recyclerView.MyAdapter;

import java.util.ArrayList;

public class MyTask extends AsyncTask<Void, ArrayList, ArrayList> {
    String  callFrom;
    MyAdapter adapter;

    public MyTask( MyAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected ArrayList doInBackground(Void... voids) {
        for(int i = 0; i < ReadAndSaveMultipleFile.allData.size() ; i++ ){
            publishProgress(ReadAndSaveMultipleFile.allData);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(ArrayList... values) {
        //adapter.add(values[0]);
    }



    //    @Override
//    protected String doInBackground(void.. integers) {
//        for(int i = 0; i < ReadAndSaveMultipleFile.allData.size() ; i++ ){
//            publishProgress(ReadAndSaveMultipleFile.allData);
//        }
//
//        return null;
//    }
//
//
//
//
//    @Override
//    protected void onPostExecute(String s) {
//
//    }
}
