package com.example.myapplication.readAndSaveAllFile;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ReadAndSaveMultipleFile {
    private Context context;
    public static Boolean hasData = false;
    public static ArrayList<MultipleFileData> allData = new ArrayList<MultipleFileData>(); /**  this is the main arrayList that stores all data */

    /**
     * initialize the constructor to pass the context of the parent class
     */
    public ReadAndSaveMultipleFile(Context current) {
        this.context = current;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void readAllFilesList() {

        //check for todays filename/////////////////////////////////////////////////////////////////////////////////
//        //gets today's date in the pattern below
//        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//        //initializing user object from shared preference to get the userID saved during login
//        User user = SharedPrefManager.getInstance(context).getUser();
//
//        //finalize the filename to read
//        //final String todayFileName = "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv"; **Uncomment to set it to work for current date.**

        ////////////////////////////////////////////////////////////////////////////////////////

        File file = new File(context.getFilesDir(), "files");
        file = new File(file.getAbsolutePath());
        File destDir = file.getParentFile();

        /** using lambda function to filter the files according to user id */
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                //define here you filter condition for every single file
                return name.contains("hourlydata"); //todo: add user id filter too
            }
        };

        File[] files = destDir.listFiles(filter);
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed()); /** sort in reversed date order */

        if (files.length == 0) {
            Toast toast = Toast.makeText(context, "No Data Available. Please Start Using your Fitbit to see your Health Progress.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            hasData = false;
        } else {
            for (int i = 0; i < files.length; i++) {
                readOneFileData(files[i].getName());
            }
            hasData = true;
        }
    }

    private void readOneFileData(String filename) {
        String date = filename.substring(5, 15);
        File file = new File(context.getFilesDir(), filename);
//        file = new File(String.valueOf(file.getAbsoluteFile()));

        //initialize all the arraylist
        ArrayList<String> timeStamp = new ArrayList<>();
        ArrayList<Double> calories = new ArrayList<>();
        ArrayList<Double> caloriesBMR = new ArrayList<>();
        ArrayList<Double> steps = new ArrayList<>();
        ArrayList<Double> distance = new ArrayList<>();
        ArrayList<Double> floors = new ArrayList<>();
        ArrayList<Double> elevation = new ArrayList<>();
        ArrayList<Double> minutesSedentary = new ArrayList<>();
        ArrayList<Double> minutesLightlyActive = new ArrayList<>();
        ArrayList<Double> minutesFairlyActive = new ArrayList<>();
        ArrayList<Double> minutesVeryActive = new ArrayList<>();
        ArrayList<Double> activityCalories = new ArrayList<>();
        ArrayList<Double> heartRate = new ArrayList<>();

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
                        if (i == 0) {
                            timeStamp.add(tokens[i].substring(0,5)); //removing the seconds part because it's just '00'
                        } else if (i == 1) {
                            calories.add(Double.parseDouble(tokens[i]));
                        } else if (i == 14) {
                            caloriesBMR.add(Double.parseDouble(tokens[i]));
                        } else if (i == 4) {
                            steps.add(Double.parseDouble(tokens[i]));
                        } else if (i == 5) {
                            distance.add(Double.parseDouble(tokens[i]));
                        } else if (i == 6) {
                            floors.add(Double.parseDouble(tokens[i]));
                        } else if (i == 7) {
                            elevation.add(Double.parseDouble(tokens[i]));
                        } else if (i == 8) {
                            heartRate.add(Double.parseDouble(tokens[i]));
                        } else if (i == 9) {
                            minutesSedentary.add(Double.parseDouble(tokens[i]));
                        } else if (i == 10) {
                            minutesLightlyActive.add(Double.parseDouble(tokens[i]));
                        } else if (i == 11) {
                            minutesFairlyActive.add(Double.parseDouble(tokens[i]));
                        } else if (i == 12) {
                            minutesVeryActive.add(Double.parseDouble(tokens[i]));
                        } else if (i == 13) {
                            activityCalories.add(Double.parseDouble(tokens[i]));
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** store the object of data for each day */
        MultipleFileData multipleFileData = new MultipleFileData(date, timeStamp, calories, caloriesBMR, steps, distance, floors, elevation, minutesSedentary, minutesLightlyActive, minutesFairlyActive, minutesVeryActive, activityCalories, heartRate);
        allData.add(multipleFileData);
    }
}
