package com.example.myapplication.footSteps;

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
import com.example.myapplication.dialog.FootStepsMoreDialog;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyIndividualDataSets;
import com.example.myapplication.readAndSaveAllFile.Hourly.ReadHourlyData;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FootStepsMore extends Fragment implements View.OnClickListener {
    private View view;
    private String callFrom = "footSteps";
    private BarChart barChart;
    private RecyclerView recyclerView;

    private ArrayList<HourlyIndividualDataSets> HourlyAllFootStepsData;  //holds all footSteps data
    private ArrayList<Double> sevenDaysData; //holds seven days data
    private ArrayList<String> xLabel; //holds xLabel for seven days data

    private MyAdapter recyclerViewAdapter;

    private File[] fileList; //holds list of the file store in the phone
    private Boolean firstPlotSevenDays = true;
    private static final String TAG = "FootSteps";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_foot_steps_more, container, false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.footStepsMoreArrowBack).setOnClickListener(this);
        view.findViewById(R.id.footStepsMoreHelp).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartFootStepsMoreScreen);
        recyclerView = view.findViewById(R.id.footStepsMoreRecyclerView);

        getFilesList();  // gets all the list of the the files available

        return view;
    }

    private void showFootStepsMoreHelpDialog() {
        FootStepsMoreDialog footStepsMoreDialog = new FootStepsMoreDialog();
        footStepsMoreDialog.show(getFragmentManager(), "Foot Steps Help");
    }

    private void startRecyclerView() {
        recyclerViewAdapter = new MyAdapter("footSteps", view.getContext(), HourlyAllFootStepsData);

        /**check if line that separates each day data has been added before or not */
        if (0 == recyclerView.getItemDecorationCount()) {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * This method gets recent seven days footSteps data & date stored in 'allData'
     * stores in ArrayList to plot the graph
     */
    private void getSevenDaysData() {
        sevenDaysData = new ArrayList<>();
        xLabel = new ArrayList<>();
        int days = 7;

        for (int i = 0; i < days; i++) {
            sevenDaysData.add(HourlyAllFootStepsData.get(i).getTotal());
            xLabel.add(HourlyAllFootStepsData.get(i).getDate().substring(5));
        }
    }

    private void showMainScreen() {
        // when switching between fragments old fragment is added in back stack,
        //check the stack count to remove it from stack which helps to return to old fragment
        if (getFragmentManager().getBackStackEntryCount() > 0) {
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
            HourlyAllFootStepsData = new ArrayList<>();
            new MyTask().execute();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footStepsMoreArrowBack:
                showMainScreen();
                break;
            case R.id.footStepsMoreHelp:
                showFootStepsMoreHelpDialog();
                break;
        }
    }

    /**
     * background task to the read the data
     */
    class MyTask extends AsyncTask {

        @Override
        protected void onPreExecute() { //called before the background task is started
            startRecyclerView();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Log.d(TAG, "FootSteps File Read AsyncTask Started");

            for (File file : fileList) {
                String date = file.getName().substring(5, 15);

                ReadHourlyData rData = new ReadHourlyData(callFrom, file);

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

            //only showing the average graph if there is at least 7 days of data
            if (HourlyAllFootStepsData.size() >= 7 && firstPlotSevenDays) {
                getSevenDaysData();
                PlotChart.barChart(view.getContext(), callFrom, barChart, sevenDaysData, xLabel);

                firstPlotSevenDays = false; //set it to false so that it won't get called get called every time after the plot
            }

            HourlyAllFootStepsData.add((HourlyIndividualDataSets) values[0]); //add the foot steps object to the arrayList
            recyclerViewAdapter.notifyDataSetChanged(); //notify the adapter to update the changes in the data
        }

        @Override
        protected void onPostExecute(Object o) { //called after the background task is complete
            Log.d(TAG, "Foot Steps File Read AsyncTask Complete");
        }
    }
}