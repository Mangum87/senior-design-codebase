package com.example.myapplication.readAndSaveAllFile.Sleep;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;



/**
 * Manages the sleep data files loading and makes them available.
 */
public class SleepFileManager
{
    public static ArrayList<SleepFile> files;
    private Context context;


    /**
     * Creates the manager for reading sleep data files.
     * All locally found files will be read immediately.
     * @param con Context of app
     */
    public SleepFileManager(Context con)
    {
        this.context = con;
        refreshFiles();
    }


    /**
     * Refresh the list of sleep data from locally
     * stored files.
     */
    public void refreshFiles()
    {
        this.files = new ArrayList<SleepFile>(30); // Always new list

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
            for(File file : list)
            {
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
    private SleepFile readFile(File file)
    {
        SleepFile s = new SleepFile(file.getName());

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))); // Set up file reader

            reader.readLine(); // Eat headers line

            // Loop vars
            String line, state, time;
            int sec;
            while((line = reader.readLine()) != null)
            {
                String[] token = line.split(",");
                sec = Integer.parseInt(token[1]);
                s.addEvent(token[0], sec, token[2]);
            }

            reader.close(); // Close reader
        }
        catch (IOException e) { e.printStackTrace(); }

        return s;
    }


    /**
     * Filter files in directory to get all
     * files with "sleep" in the name.
     * @return Array of files
     */
    private File[] getFilteredFiles()
    {
        File file = new File(context.getFilesDir().getAbsolutePath()); // Get path to directory

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return name.contains("sleep");
            }
        };

        return file.listFiles(filter); // Apply filter to directory
    }


    /**
     * Returns the sleep data read by the manager.
     * @return SleepFile array
     */
    public ArrayList<SleepFile> getSleepData()
    {
        return this.files;
    }
}
