package com.example.myapplication.readAndSaveAllFile.Summary;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.example.myapplication.readAndSaveAllFile.Sleep.SleepFile;

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
 * Manages the FitBit summary data collection for all files
 * located on disk.
 */
public class SummaryManager
{
    private ArrayList<SummaryFile> files;
    private Context context;


    /**
     * Creates the manager for reading summary data files.
     * All locally found files will be read immediately.
     * @param con Context of app
     */
    public SummaryManager(Context con)
    {
        this.context = con;
        refreshFiles();
    }


    /**
     * Refresh the list of summary data from locally
     * stored files.
     */
    public void refreshFiles()
    {
        this.files = new ArrayList<SummaryFile>(30); // Always new list

        // Get all the summary file names
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
                SummaryFile sum = readFile(file);
                this.files.add(sum);
            }
        }
    }


    /**
     * Read the file and place data into SummaryFile objects.
     * @param file File to read
     * @return SummaryFile object
     */
    private SummaryFile readFile(File file)
    {
        SummaryFile f = new SummaryFile(file.getName());

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))); // Set up file reader

            reader.readLine(); // Eat headers line

            // Read contents
            String line = reader.readLine();
            if(line != null)
            {
                String[] token = line.split(",");
                if(token.length == 45)
                {
                    f.setSleep(new SummarySleep(token)); // Set the sleep data
                    f.setActivity(new SummaryActivity(token)); // Set the activity data
                }
            }

            reader.close(); // Close reader
        }
        catch (IOException e) { e.printStackTrace(); }

        return f;
    }


    /**
     * Filter files in directory to get all
     * files with "fitbit" in the name.
     * @return Array of files
     */
    private File[] getFilteredFiles()
    {
        File file = new File(context.getFilesDir().getAbsolutePath()); // Get path to directory

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return name.contains("fitbit");
            }
        };

        return file.listFiles(filter); // Apply filter to directory
    }


    /**
     * Returns the summary data read by the manager.
     * @return SummaryFile array
     */
    public ArrayList<SummaryFile> getSummaryData()
    {
        return this.files;
    }
}
