package com.example.myapplication.readAndSaveAllFile.Sleep;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.myapplication.LoginStuff.User;
import com.example.myapplication.SharedPrefManager;
import com.fitbitsample.FitbitDataType.SleepData.Sleep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


/**
 * Manages the sleep data files loading and makes them available.
 */
public class SleepFileManager {
    public static ArrayList<SleepFile> files;
    private Context context;
    private boolean callFromMainScreen;


    /**
     * Creates the manager for reading sleep data files.
     * All locally found files will be read immediately depending on when the call is made from.
     * @param con Context of app
     * @param callFromMainScreen to filter the files to read
     */
    public SleepFileManager(Context con, Boolean callFromMainScreen) {
        this.context = con;
        this.callFromMainScreen = callFromMainScreen;
        refreshAllFiles();
    }


    /**
     * Refresh the list of sleep data from locally stored all files.
     * if called from mainScreen only current date file will be refreshed
     */
    public void refreshAllFiles() {
        this.files = new ArrayList<SleepFile>(); // Always new list

        // Get all the sleep file names
        File[] list = getFilteredFiles();
        Arrays.sort(list, Comparator.comparingLong(File::lastModified).reversed()); // Sort by date in desc order

        if (list.length == 0) // No files found
        {
            Toast toast = Toast.makeText(context, "No Data Available. Please start using your FitBit to see your health progress.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else  // Read files into objects
        {
            for (File file : list) {
                SleepFile sleep = readFile(file);
                this.files.add(sleep); // Add to list
            }
        }
    }


    /**
     * Read the file and place data into
     * SleepFile objects.
     * @param file File to read
     * @return SleepFile object
     */
    private SleepFile readFile(File file) {
        SleepFile s = new SleepFile(file.getName());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))); // Set up file reader

            reader.readLine(); // Eat headers line

            // Loop vars
            String line, state, time;
            int sec;
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",");
                sec = Integer.parseInt(token[1]);
                s.addEvent(token[0], sec, token[2]);
            }

            reader.close(); // Close reader
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }


    /**
     * Filter files in directory to get all files with "sleepdata" in the name.
     * when called from 'mainScreen' it looks for the file with current date and returns only one file list
     * @return Array of files
     */
    private File[] getFilteredFiles() {
        File file = new File(context.getFilesDir().getAbsolutePath()); // Get path to directory
        FilenameFilter filter = null;
        if (callFromMainScreen) { //check case to filter the file filtering properties

            //gets today's date in the pattern below
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            date = "2021-02-24";
//            date = "2021-03-06";

            //initializing user object from shared preference to get the userID saved during login
            User user = SharedPrefManager.getInstance(context).getUser();

            final String fileName = "Date_" + date + "_User_id_" + /*user.getUser_id()*/"218817" + "_sleepdata.csv";

            filter = (dir, name) -> name.matches(fileName);
        } else {
            filter = (dir, name) -> name.contains("sleepdata");
        }


        return file.listFiles(filter); // Apply filter to directory
    }
}
