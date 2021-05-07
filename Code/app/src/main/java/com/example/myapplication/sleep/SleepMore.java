package com.example.myapplication.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.dialog.SleepMoreHelpDialog;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFile;
import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFileManager;
import com.example.myapplication.recyclerView.MyAdapter;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;
import com.github.mikephil.charting.charts.BarChart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SleepMore extends Fragment implements View.OnClickListener{
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;

    MyAdapter recyclerViewAdapter;
    Handler handler = new Handler();

    private ArrayList<SleepFile> SleepData; //holds all sleep data
    private ArrayList<Double> sevenDaysData; //holds seven days data
    private ArrayList<String> xLabel; //holds xLabel for seven days data


    private File[] fileList;
    private Boolean firstPlotSevenDays = true;
    private static final String TAG = "sleep";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sleep_more,container,false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.sleep_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.sleep_more_help).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartSleepMoreScreen);
        recyclerView = view.findViewById(R.id.recyclerViewSleepMoreScreen);

        // gets all the list of the the files available
        getFilesList();

        return view;
    }


    private void showMainScreen(){
        //when switching between fragments old fragment is added in back stack, check the stack count
        // to remove it from stack which helps to return to old fragment
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            return;
        }
    }

    /**
     * This method gets recent seven days sleep data & date stored in 'files'
     * stores in ArrayList to plot the graph
     */
    private void getSevenDaysData(){
        sevenDaysData = new ArrayList<>();
        xLabel = new ArrayList<>();
        int days = 7;

        for(int i = 0; i < days; i++){
            sevenDaysData.add((double) SleepData.get(i).getTotalHoursSlept());
            xLabel.add(SleepData.get(i).getDate().substring(5));
        }
    }

    private void showSleepMoreHelpDialog(){
        SleepMoreHelpDialog sleepMoreHelpDialog = new SleepMoreHelpDialog();
        sleepMoreHelpDialog.show(getFragmentManager(),"Sleep Help");
    }

    private void startRecyclerView(){
        recyclerViewAdapter = new MyAdapter(view.getContext(),"sleep", SleepData);
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount())
        {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

//        //set scroll listener
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if(!recyclerView.canScrollVertically(1) && dy != 0){ //check for scroll down
//                    loadingProgress.setVisibility(View.VISIBLE);
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadingProgress.setVisibility(view.GONE);
//                        }
//                    },2000);
//                }
//            }
//        });

    }

    /** gets all files list and executes the background task to read and store data */
    public void getFilesList(){
        File file = new File(view.getContext().getFilesDir().getAbsolutePath()); // Get path to directory
        FilenameFilter filter =  (dir, name) -> name.contains("sleepdata");

       fileList = file.listFiles(filter);
       Arrays.sort(fileList, Comparator.comparingLong(File::lastModified).reversed()); // sort in reversed date order

       if(fileList.length == 0){
           Toast toast = Toast.makeText(view.getContext(), "No Sleep Data Available. Please start using your FitBit to see your health progress.", Toast.LENGTH_LONG);
           toast.setGravity(Gravity.CENTER, 0, 0);
           toast.show();
       }
       else {
           SleepData = new ArrayList<>();
           new MyTask().execute();
       }
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.sleep_more_arrow_back:
                showMainScreen();
                break;
            case R.id.sleep_more_help:
                showSleepMoreHelpDialog();
                break;
        }
    }

    /** background task to the read the data */
    class  MyTask extends AsyncTask{

        @Override
        protected void onPreExecute() { //called before the background() is started
            Log.d(TAG, "Sleep File Read AsyncTask Started");
            startRecyclerView();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            for(File file: fileList){
                SleepFile sleepFile = SleepFileManager.readFile(file);
                publishProgress(sleepFile);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {  //called when task has update

            // only showing the bar graph if there is at least 7 days of data
            if(SleepData.size() >= 7 && firstPlotSevenDays){
                getSevenDaysData();
                PlotChart.barChart(view.getContext(), "sleep", barChart, sevenDaysData, xLabel);

                firstPlotSevenDays = false; //set it to false so that it won't get called get called every time after the plot
            }

            SleepData.add((SleepFile) values[0]); //add the sleep object to the arrayList
            recyclerViewAdapter.notifyDataSetChanged(); //notify the adapter to update the changes in the data
        }

        @Override
        protected void onPostExecute(Object o) { //called after the background task is complete
            Log.d(TAG, "Sleep File Read AsyncTask Complete");
        }
    }
}