package com.example.myapplication.miles;

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
import com.example.myapplication.dialog.MilesMoreHelpDialog;
import com.example.myapplication.readAndSaveAllFile.Hourly.HourlyIndividualDataSets;
import com.example.myapplication.readAndSaveAllFile.Hourly.ReadHourlyData;
import com.example.myapplication.recyclerView.MyAdapter;
import com.github.mikephil.charting.charts.BarChart;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MilesMore extends Fragment implements View.OnClickListener {
    private View view;
    private BarChart barChart;
    private RecyclerView recyclerView;

    private ArrayList<HourlyIndividualDataSets> HourlyAllMilesData;  //holds all miles data
    private ArrayList<Double> sevenDaysData; //holds seven days data
    private ArrayList<String> xLabel;//holds xLabel for seven days data
    private File[] fileList; //holds list of the file store in the phone

    private MyAdapter recyclerViewAdapter;

    private Boolean firstPlotSevenDays = true;
    private static final String TAG = "miles";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miles_more, container, false);

        /**set clickListener for back arrow and help */
        view.findViewById(R.id.milesMoreArrowBack).setOnClickListener(this);
        view.findViewById(R.id.milesMoreHelp).setOnClickListener(this);

        /**initialize the chart and recycler view */
        barChart = view.findViewById(R.id.barChartMilesMoreScreen);
        recyclerView = view.findViewById(R.id.milesMoreRecyclerView);

        getFilesList();  // gets all the list of the the files available

        return view;
    }

    private void showMilesMoreHelpDialog() {
        MilesMoreHelpDialog milesMoreHelpDialog = new MilesMoreHelpDialog();
        milesMoreHelpDialog.show(getFragmentManager(), "Miles Help");
    }

    private void startRecyclerView() {
        recyclerViewAdapter = new MyAdapter(TAG, view.getContext(), HourlyAllMilesData);
        /**check if line that separates each day data has been added before or not */
        if (0 == recyclerView.getItemDecorationCount()) {
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * This method gets recent seven days Miles walked data & date stored in 'allData'
     * stores in ArrayList to plot the graph
     */
    private void getSevenDaysData() {
        sevenDaysData = new ArrayList<>();
        xLabel = new ArrayList<>();
        int days = 7;

        for (int i = 0; i < days; i++) {
            sevenDaysData.add(HourlyAllMilesData.get(i).getTotal());
            xLabel.add(HourlyAllMilesData.get(i).getDate().substring(5));
        }
    }

    private void showMainScreen() {
        /**
         * when switching between fragments old fragment is added in back stack, check the stack count
         * to remove it from stack which helps to return to old fragment
         */
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
            Toast toast = Toast.makeText(view.getContext(), "No Miles Data Available. Please start using your FitBit to see your health progress.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            HourlyAllMilesData = new ArrayList<>();
            new MyTask().execute();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.milesMoreArrowBack:
                showMainScreen();
                break;
            case R.id.milesMoreHelp:
                showMilesMoreHelpDialog();
                break;
        }
    }

    class  MyTask extends AsyncTask{

        @Override
        protected void onPreExecute() { //called before the background task is started
            Log.d(TAG, "Miles File Read AsyncTask Started");
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
            if (HourlyAllMilesData.size() >= 7 && firstPlotSevenDays ) {
                getSevenDaysData();
                PlotChart.barChart(view.getContext(), TAG, barChart, sevenDaysData, xLabel);

                firstPlotSevenDays = false; //set it to false so that it won't get called get called every time after the plot
            }

            HourlyAllMilesData.add((HourlyIndividualDataSets) values[0]); //add the calories object to the arrayList
            recyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d(TAG, "Miles File Read AsyncTask Complete");
        }
    }
}