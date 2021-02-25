package com.example.myapplication.amazonS3;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.myapplication.LoginStuff.LoginResponse;
import com.example.myapplication.SharedPrefManager;
import com.example.myapplication.LoginStuff.User;
import com.fitbitsample.FitbitDataType.Hourly.Dataset;
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.FitbitSummary;
import com.fitbitsample.FitbitSharedPref.HeartRateInfo;
import com.fitbitsample.FitbitSharedPref.SleepInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
  class: amazonS3main
  Function: write the user fitbit summary data to file and then upload to S3 bucket using
            Transfer Utility library
            More info: https://aws.amazon.com/blogs/mobile/introducing-the-transfer-utility-for-the-aws-sdk-for-android/
 */
public class amazonS3main extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    User user = SharedPrefManager.getInstance(this).getUser();

    @RequiresApi(api = Build.VERSION_CODES.O)

    /**
        method: main
        @param : context , get the Context from the health_status page as upload initiates
                from this page (for now)
        >>>>Should implement this method to initiate automatically within certain interval of time
     */
    public void main(Context context) throws Exception {

        //bucket name of the S3 bucket created
        final String bucketName = "mobilebucket";

        //name of the file to be uploaded
        final String sumKeyName = "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv";
        final String hourKeyName = "Date_" + date + "_User_id_" + user.getUser_id() + "_hourlydata.csv";
        final String sleepKeyName = "Date_" + date + "_User_id_" + user.getUser_id() + "_sleepdata.csv";

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:2dcdd80c-a010-4d3f-96ad-ba76adcc266e", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        //method call to write fitbit data to file
        writedatatofile(bucketName, sumKeyName, credentialsProvider, context);
        writehourlydatatofile(bucketName, hourKeyName, credentialsProvider, context);
        writesleepdatatofile(bucketName, sleepKeyName, credentialsProvider, context);
    }

    /**
        method: writedatatofile()
        @param: bucketName, type String
        @param: keyName, type String : name of the file to upload
        @param: credentialsProvider, type CognitoCachingCredentialsProvider
        @param: context, type context
        function: write summary data to file in .csv format and upload to S3 bucket
     */
    private void writedatatofile(String bucketName, String keyName, CognitoCachingCredentialsProvider credentialsProvider, Context context) {
        try {
            File file = new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv");

            //OutputStream writer = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/fitdata.csv");
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            //BufferedWriter bw = new BufferedWriter(fw);

            LoginResponse loginResponse = SharedPrefManager.getInstance(this).getLoginResponse();
            User user = SharedPrefManager.getInstance(this).getUser();
            FitbitSummary fitbitSummary = FitbitPref.getInstance(this).getfitbitSummary();
            HeartRateInfo heartRateInfo = FitbitPref.getInstance(this).getHeartdata();
            SleepInfo sleepInfo = FitbitPref.getInstance(this).getSleepData();

            StringBuilder newdata = new StringBuilder();

            newdata.append("ID");
            newdata.append(',');
            newdata.append("Date");
            newdata.append(',');
            newdata.append("activeScore");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("trackerdistance");
            newdata.append(',');
            newdata.append("loggedActivitiesdistance");
            newdata.append(',');
            newdata.append("duration");
            newdata.append(',');
            newdata.append("efficiency");
            newdata.append(',');
            newdata.append("totalMinutesAsleep");
            newdata.append(',');
            newdata.append("restingHeartRate");
            newdata.append(',');
            newdata.append("(Out of Range)");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("min");
            newdata.append(',');
            newdata.append("max");
            newdata.append(',');
            newdata.append("minutes");
            newdata.append(',');
            newdata.append("(Fat Burn)");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("min");
            newdata.append(',');
            newdata.append("max");
            newdata.append(',');
            newdata.append("minutes");
            newdata.append(',');
            newdata.append("(Cardio)");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("min");
            newdata.append(',');
            newdata.append("max");
            newdata.append(',');
            newdata.append("minutes");
            newdata.append(',');
            newdata.append("(Peak)");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("min");
            newdata.append(',');
            newdata.append("max");
            newdata.append(',');
            newdata.append("minutes");
            newdata.append(',');
            newdata.append("deepCount");
            newdata.append(',');
            newdata.append("deepMinutes");
            newdata.append(',');
            newdata.append("deepAvg");
            newdata.append(',');
            newdata.append("lightCount");
            newdata.append(',');
            newdata.append("lightMinutes");
            newdata.append(',');
            newdata.append("lightAvg");
            newdata.append(',');
            newdata.append("remCount");
            newdata.append(',');
            newdata.append("remMinutes");
            newdata.append(',');
            newdata.append("remAvg");
            newdata.append(',');
            newdata.append("wakeCount");
            newdata.append(',');
            newdata.append("wakeMinutes");
            newdata.append(',');
            newdata.append("wakeAvg");
            newdata.append(',');
            newdata.append("minutesAwake");
            newdata.append(',');
            newdata.append("timeToSleep");
            newdata.append(',');
            newdata.append("startTime");
            newdata.append('\n');

            newdata.append(user.getUser_id());
            newdata.append(',');
            newdata.append(Calendar.getInstance().getTime().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getActiveScore().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getCaloriesOut().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getTracker());
            newdata.append(',');
            newdata.append(fitbitSummary.getLoggedActivities());
            newdata.append(',');
            newdata.append(sleepInfo.getDuration());
            newdata.append(',');
            newdata.append(sleepInfo.getEfficiency());
            newdata.append(',');
            newdata.append(sleepInfo.getMinutesAsleep());
            newdata.append(',');
            newdata.append(heartRateInfo.getRestingRate());
            newdata.append(',');
            newdata.append("-1");
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMinutes());
            newdata.append(',');
            newdata.append("-1");
            newdata.append(',');
            newdata.append(heartRateInfo.getFatCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMinutes());
            newdata.append(',');
            newdata.append("-1");
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMinutes());
            newdata.append(',');
            newdata.append("-1");
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMinutes());
            newdata.append(',');
            newdata.append(sleepInfo.getDeepCount());
            newdata.append(',');
            newdata.append(sleepInfo.getDeepMinutes());
            newdata.append(',');
            newdata.append(sleepInfo.getDeepAvg());
            newdata.append(',');
            newdata.append(sleepInfo.getLightCount());
            newdata.append(',');
            newdata.append(sleepInfo.getLightMinutes());
            newdata.append(',');
            newdata.append(sleepInfo.getLightAvg());
            newdata.append(',');
            newdata.append(sleepInfo.getRemCount());
            newdata.append(',');
            newdata.append(sleepInfo.getRemMinutes());
            newdata.append(',');
            newdata.append(sleepInfo.getRemAvg());
            newdata.append(',');
            newdata.append(sleepInfo.getWakeCount());
            newdata.append(',');
            newdata.append(sleepInfo.getWakeMinutes());
            newdata.append(',');
            newdata.append(sleepInfo.getWakeAvg());
            newdata.append(',');
            newdata.append(sleepInfo.getMinutesAwake());
            newdata.append(',');
            newdata.append(sleepInfo.getMinuteToFallAsleep());
            newdata.append(',');
            newdata.append(sleepInfo.getTime());
            newdata.append('\n');

            writer.write(newdata.toString());
            writer.close();
            //Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();

            System.out.println("complete");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            //Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show();
        }

        //create S3 client
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

        //pass the s3 client to Transfer Utility
        TransferUtility transferUtility = new TransferUtility(s3, context);

        //Upload file to S3 bucket
        TransferObserver observer = transferUtility.upload(
                bucketName,//this is the bucket name on S3
                keyName, //this is the path and name
                new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv") //path to the file locally
        );
        System.out.println("Summarized Upload Done");
    }

    /**
        method: writehourlydatatofile()
        @param: bucketName, type String
        @param: keyName, type String : name of the file to upload
        @param: credentialsProvider, type CognitoCachingCredentialsProvider
        @param: context, type context
        function: write intraday data to file in .csv format and upload to S3 bucket
     */
    private void writehourlydatatofile(String bucketName, String keyName, CognitoCachingCredentialsProvider credentialsProvider, Context context) {
        try {
            File file = new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_hourlydata.csv");
            FileWriter writer = new FileWriter(file.getAbsoluteFile());

            HeartRateInfo heart = FitbitPref.getInstance(this).getHeartdata();
            FitbitSummary fitbitSummary = FitbitPref.getInstance(this).getfitbitSummary();

            ArrayList<Dataset> calorieHourly = FitbitPref.getInstance(this).getCalorieData();
            ArrayList<Dataset> stepHourly = FitbitPref.getInstance(this).getStepData();
            ArrayList<Dataset> distanceHourly = FitbitPref.getInstance(this).getDistanceData();
            ArrayList<Dataset> floorsHourly = FitbitPref.getInstance(this).getFloorsData();
            ArrayList<Dataset> elevationHourly = FitbitPref.getInstance(this).getElevationData();

            StringBuilder newdata = new StringBuilder();

            newdata.append("time");
            newdata.append(",");
            newdata.append("calories");
            newdata.append(',');
            newdata.append("caloriesLevel");
            newdata.append(',');
            newdata.append("caloriesMets");
            newdata.append(',');

            newdata.append("steps");
            newdata.append(',');
            newdata.append("distance");
            newdata.append(',');
            newdata.append("floors");
            newdata.append(',');
            newdata.append("elevation");
            newdata.append(',');
            newdata.append("heartRate");
            newdata.append(',');

            newdata.append("minutesSedentary");
            newdata.append(',');
            newdata.append("minutesLightlyActive");
            newdata.append(',');
            newdata.append("minutesFairlyActive");
            newdata.append(',');
            newdata.append("minutesVeryActive");
            newdata.append(',');

            newdata.append("activityCalories");
            newdata.append(',');
            newdata.append("caloriesBMR");
            newdata.append('\n');


            for(int i = 0; i < 96; i++)
            {
                newdata.append(calorieHourly.get(i).getTime());
                newdata.append(",");
                newdata.append(calorieHourly.get(i).getValue());
                newdata.append(",");
                newdata.append(calorieHourly.get(i).getLevel());
                newdata.append(",");
                newdata.append(calorieHourly.get(i).getMets());
                newdata.append(",");
                newdata.append(stepHourly.get(i).getValue());
                newdata.append(",");
                newdata.append(distanceHourly.get(i).getValue());
                newdata.append(",");
                newdata.append(floorsHourly.get(i).getValue());
                newdata.append(",");
                newdata.append(elevationHourly.get(i).getValue());
                newdata.append(",");
                newdata.append(heart.getData().get(i).getValue());

                if(i == 0)
                {
                    newdata.append(",");
                    newdata.append(fitbitSummary.getSedentaryMinutes());
                    newdata.append(",");
                    newdata.append(fitbitSummary.getLightlyActiveMinutes());
                    newdata.append(",");
                    newdata.append(fitbitSummary.getFairlyActiveMinutes());
                    newdata.append(",");
                    newdata.append(fitbitSummary.getVeryActiveMinutes());
                    newdata.append(",");
                    newdata.append(fitbitSummary.getActivityCalories());
                    newdata.append(",");
                    newdata.append(fitbitSummary.getCaloriesBMR());
                }

                newdata.append('\n');
            }

            writer.write(newdata.toString());
            writer.close();
            //Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();

            System.out.println("complete");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            //Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show();
        }

        //create S3 client
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

        //pass the s3 client to Transfer Utility
        TransferUtility transferUtility = new TransferUtility(s3, context);

        //Upload file to S3 bucket
        TransferObserver observer = transferUtility.upload(
                bucketName,//this is the bucket name on S3
                keyName, //this is the path and name
                new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_hourlydata.csv") //path to the file locally
        );
        System.out.println("Hourly Upload Done");
    }


    /**
        method: writesleepdatatofile()
        @param: bucketName, type String
        @param: keyName, type String : name of the file to upload
        @param: credentialsProvider, type CognitoCachingCredentialsProvider
        @param: context, type context
        function: write sleep data to file in .csv format and upload to S3 bucket
     */
    private void writesleepdatatofile(String bucketName, String keyName, CognitoCachingCredentialsProvider credentialsProvider, Context context) {
        try {
            File file = new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_sleepdata.csv");

            //OutputStream writer = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/fitdata.csv");
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            //BufferedWriter bw = new BufferedWriter(fw);

            LoginResponse loginResponse = SharedPrefManager.getInstance(this).getLoginResponse();
            User user = SharedPrefManager.getInstance(this).getUser();
            SleepInfo sleepInfo = FitbitPref.getInstance(this).getSleepData();

            StringBuilder newdata = new StringBuilder();

            newdata.append("level");
            newdata.append(',');
            newdata.append("seconds");
            newdata.append(',');
            newdata.append("time");
            newdata.append('\n');

            for(int i = 0; i < sleepInfo.getData().size(); i++)
            {
                newdata.append(sleepInfo.getData().get(i).getLevel());
                newdata.append(",");
                newdata.append(sleepInfo.getData().get(i).getSeconds());
                newdata.append(",");
                newdata.append(sleepInfo.getData().get(i).getDateTime());
                newdata.append('\n');
            }

            writer.write(newdata.toString());
            writer.close();
            //Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();

            System.out.println("complete");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            //Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show();
        }

        //create S3 client
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);

        //pass the s3 client to Transfer Utility
        TransferUtility transferUtility = new TransferUtility(s3, context);

        //Upload file to S3 bucket
        TransferObserver observer = transferUtility.upload(
                bucketName,//this is the bucket name on S3
                keyName, //this is the path and name
                new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_sleepdata.csv") //path to the file locally
        );
        System.out.println("Sleep Upload Done");
    }

}


