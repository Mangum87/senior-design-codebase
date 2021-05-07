package com.example.myapplication.readAndSaveAllFile.Hourly;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.example.myapplication.LoginStuff.User;
import com.example.myapplication.SharedPrefManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReadHourlyAllData {
    private Context context;
    public static ArrayList<HourlyAllDataSets> HourlyAllData = new ArrayList<>(1);

    /**
     * this is the main arrayList that stores all data
     */

    public ReadHourlyAllData(Context context) {
        this.context = context;
        readFile();
    }

    /**
     * Filter files in directory to get all files with "allHourlyData" in the name.
     *
     * @return Array of files
     */
    private File[] getFilteredFile() {
        File file = new File(context.getFilesDir().getAbsolutePath()); // Get path to directory
        FilenameFilter filter = null;

        //gets today's date in the pattern below
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date = "2021-02-08";

        //initializing user object from shared preference to get the userID saved during login
        User user = SharedPrefManager.getInstance(context).getUser();

        final String fileName = "Date_" + date + "_User_id_" + /*user.getUser_id()*/"218817" + "_hourlydata.csv";

        filter = (dir, name) -> name.matches(fileName);


        return file.listFiles(filter); // Apply filter to directory
    }

    public void readFile() {
        File[] fileList = getFilteredFile();

        if (fileList.length == 0) { // No files found
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(context, "No Today's Hourly Data Available. Please Start Using your Fitbit to see your Health Progress.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        } else {
            for (File file : fileList) {

                //initialize all the arraylist
                ArrayList<String> timeStamp = new ArrayList<>();
                ArrayList<Double> calories = new ArrayList<>();
                ArrayList<Double> steps = new ArrayList<>();
                ArrayList<Double> miles = new ArrayList<>();
                ArrayList<Double> minutesSedentary = new ArrayList<>();
                ArrayList<Double> minutesLightlyActive = new ArrayList<>();
                ArrayList<Double> minutesFairlyActive = new ArrayList<>();
                ArrayList<Double> minutesVeryActive = new ArrayList<>();
                ArrayList<Double> heartRate = new ArrayList<>();

                String date = file.getName().substring(5, 15);

                String lineFromFile = "";
                boolean isFirstLine = true;

                try {
                    FileInputStream is = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    while ((lineFromFile = reader.readLine()) != null) {
                        /** split by ',' */
                        String[] tokens = lineFromFile.split(",");

                        if (isFirstLine) {
                            isFirstLine = false;
                        } else {
                            for (int i = 0; i < tokens.length; i++) {
                                if (i == 0) timeStamp.add(tokens[i].substring(0, 5));
                                else if (i == 1) calories.add(Double.parseDouble(tokens[i]));
                                else if (i == 4) steps.add(Double.parseDouble(tokens[i]));
                                else if (i == 5) miles.add(Double.parseDouble(tokens[i]));
                                else if (i == 8) heartRate.add(Double.parseDouble(tokens[i]));
                                else if (i == 9) minutesSedentary.add(Double.parseDouble(tokens[i]));
                                else if (i == 10) minutesLightlyActive.add(Double.parseDouble(tokens[i]));
                                else if (i == 11) minutesFairlyActive.add(Double.parseDouble(tokens[i]));
                                else if (i == 12) minutesVeryActive.add(Double.parseDouble(tokens[i]));
                            }
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HourlyAllDataSets hourlyAllDataSets = new HourlyAllDataSets(date, timeStamp, calories, steps, miles, minutesSedentary, minutesLightlyActive, minutesFairlyActive, minutesVeryActive, heartRate);
                HourlyAllData.add(hourlyAllDataSets);
            }
        }
    }
}
