package com.example.myapplication;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.myapplication.amazonS3.amazonS3main;
import com.fitbitsample.FitbitActivity.FitbitDataFormat;
import com.fitbitsample.ViewFragments.ViewFitbitDataFragment;

import java.util.Date;

/**
 This class schedules a job to the android system to run a thread periodically to sync data from fit bit into the app and store it into s3 bucket.
 Reference: https://codinginflow.com/tutorials/android/jobscheduler
 @author Kritan Duwal
 */
public class Job extends JobService
{
    //Test variable
    private static final String TAG = "Job";
    private boolean jobCancelled = false;
    private Handler mainHandler = new Handler();


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getApplicationContext();
                        ViewFitbitDataFragment sync = new ViewFitbitDataFragment();
                        sync.sync(context);

                        amazonS3main az = new amazonS3main();
                        try {
                            az.main(context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                Log.d(TAG, "Job Finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
