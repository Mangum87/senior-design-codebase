package com.example.myapplication.heartRate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chart.PlotChart;
import com.example.myapplication.dialog.HeartRateMoreDialog;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyIndividualDataSets;
import com.example.myapplication.readAndSaveAllFile.Hourly.ReadHourlyData;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.LineChart;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HeartRateMore extends Fragment implements View.OnClickListener {
    private View view;
    private LineChart lineChart;
    private RecyclerView recyclerView;

    private ArrayList<HourlyIndividualDataSets> HourlyAllHeartRateData;  //holds all heartRate data
    private ArrayList<Double> thirtyDayAverage; //holds thirty days data
    private ArrayList<String> xLabel; //holds xLabel for thirty days data
    private File[] fileList; //holds list of the file store in the phone

    private MyAdapter recyclerViewAdapter;

    private Boolean firstPlotSevenDays = true;
    private static final String TAG = "heartRate";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heart_rate_more,container,false);

        view.findViewById(R.id.hr_more_arrow_back).setOnClickListener(this);
        view.findViewById(R.id.hr_more_help).setOnClickListener(this);
        lineChart = view.findViewById(R.id.lineChartHeartRateMoreScreen);

        recyclerView = view.findViewById(R.id.heartRateMoreRecyclerView);

        getFilesList();  // gets all the list of the the files available

        return view;
    }

    private void showHrMoreHelpDialog(){
        HeartRateMoreDialog heartRateMoreDialog = new HeartRateMoreDialog();
        heartRateMoreDialog.show(getFragmentManager(),"Heart Rate Help");
    }

    private void startRecyclerView(){
        recyclerViewAdapter = new MyAdapter(TAG, view.getContext(), HourlyAllHeartRateData);
        /**check if line that separates each day data has been added before or not */
        if(0 == recyclerView.getItemDecorationCount()){
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void getThirtyDayAverageData() {
        thirtyDayAverage = new ArrayList<>();
        xLabel = new ArrayList<>();
        int daysForAverage = 30;

        /** checks if there recent 30 days data or not and if not then stores average value of heartRate data that's available */
        if(HourlyAllHeartRateData.size() < 30){
            for(int i = 0; i < HourlyAllHeartRateData.size(); i++) {
                thirtyDayAverage.add(HourlyAllHeartRateData.get(i).getAverage());
                xLabel.add(HourlyAllHeartRateData.get(i).getDate().substring(5)); /** only taking month and day excluding year */
            }
        }

        /** gets implemented when there is more than 30 days of data and stores average value of heartRate of recent 30 days */
        else{
            for(int i = 0; i < daysForAverage; i++) {
                thirtyDayAverage.add(HourlyAllHeartRateData.get(i).getAverage());
                xLabel.add(HourlyAllHeartRateData.get(i).getDate().substring(5));
            }
        }
    }

    private void showMainScreen(){
        /**
         * when switching between fragments old fragment is added in back stack,
         * check the stack count to remove it from stack which helps to return to old fragment
         */
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            return;
        }
    }

    /**
     * gets all files list and executes the background task to read and store data
     */
    private void getFilesList() {
        File file = new File(view.getContext().getFilesDir().getAbsolutePath()); // Get path to directory
        FilenameFilter filter = (dir, name) -> name.contains("hourly");

        fileList = file.listFiles(filter);
        Arrays.sort(fileList, Comparator.comparingLong(File::lastModified).reversed()); // sort in reversed date order

        if (fileList.length == 0) {
            Toast toast = Toast.makeText(view.getContext(), "No FootSteps Data Available. Please start using your FitBit to see your health progress.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            HourlyAllHeartRateData = new ArrayList<>();
            new MyTask().execute();
        }
    }

    @Override
    public void onClick (View view){
        switch (view.getId()) {
            case R.id.hr_more_arrow_back:
                showMainScreen();
                break;
            case R.id.hr_more_help:
                showHrMoreHelpDialog();
                break;
        }
    }

    class MyTask extends AsyncTask{

        @Override
        protected void onPreExecute() { //called before the background task is started
            Log.d(TAG, "HeartRate File Read AsyncTask Started");
            startRecyclerView();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            for (File file : fileList) {
                String date = file.getName().substring(5, 15);

                ReadHourlyData rData = new ReadHourlyData(TAG, file);

                HourlyIndividualDataSets hourlyIndividualDataSets = new HourlyIndividualDataSets(date, rData.getTimeStamp(), rData.getData());
                publishProgress(hourlyIndividualDataSets);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {

            // only showing the average graph if there is at least 7 days of data
            if(HourlyAllHeartRateData.size() >= 7 && firstPlotSevenDays){
                getThirtyDayAverageData();
                PlotChart.lineChart(view.getContext(),false, lineChart, thirtyDayAverage, xLabel);

                firstPlotSevenDays = false; //set it to false so that it won't get called get called every time after the plot
            }

            HourlyAllHeartRateData.add((HourlyIndividualDataSets) values[0]); //add the calories object to the arrayList
            recyclerViewAdapter.notifyDataSetChanged(); //notify the adapter to update the changes in the data
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d(TAG, "HeartRate File Read AsyncTask Complete");
        }
    }
}