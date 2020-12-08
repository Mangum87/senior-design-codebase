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
import com.fitbitsample.FitbitSharedPref.FitbitPref;
import com.fitbitsample.FitbitSharedPref.FitbitSummary;
import com.fitbitsample.FitbitSharedPref.HeartRateInfo;
import com.fitbitsample.FitbitSharedPref.SleepInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
  class: amazonS3main
  Function: write the user fitbit summary data to file and then upload to S3 bucket using
            Transfer Utility library
            More info: https://aws.amazon.com/blogs/mobile/introducing-the-transfer-utility-for-the-aws-sdk-for-android/

 */
public class amazonS3main extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    User user = SharedPrefManager.getInstance(this).getUser();

    @RequiresApi(api = Build.VERSION_CODES.O)

    /*
        method: main
        @param : context , get the Context from the health_status page as upload initiates
                from this page (for now)
        >>>>Should implement this method to initiate automatically within certain interval of time
     */
    public void main(Context context) throws Exception {

        //bucket name of the S3 bucket created
        final String bucketName = "mobilebucket";

        //name of the file to be uploaded
        final String keyName = "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv";

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:2dcdd80c-a010-4d3f-96ad-ba76adcc266e", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        //method call to write fitbit data to file
        writedatatofile(bucketName, keyName, credentialsProvider, context);

    }

    /*
        method: writedatatofile()
        @param: bucketName, type String
        @param: keyName, type String : name of the file to upload
        @param: credentialsProvider, type CognitoCachingCredentialsProvider
        @param: context, type context
        function: write data to file in .csv format and upload to S3 bucket
     */
    private void writedatatofile(String bucketName, String keyName, CognitoCachingCredentialsProvider credentialsProvider, Context context) {
        try {
            File file = new File(context.getFilesDir(), "Date_" + date + "_User_id_" + user.getUser_id() + "_fitbitdata.csv");

            //OutputStream writer = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/fitdata.csv");
            FileWriter writer = new FileWriter(file.getAbsoluteFile());
            //BufferedWriter bw = new BufferedWriter(fw);

            FitbitSummary fitbitSummary = FitbitPref.getInstance(this).getfitbitSummary();
            LoginResponse loginResponse = SharedPrefManager.getInstance(this).getLoginResponse();
            User user = SharedPrefManager.getInstance(this).getUser();
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
            newdata.append(heartRateInfo.getRangeCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getRangeMinutes());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getFatMinutes());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getCardioMinutes());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakCalorie());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMin());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMax());
            newdata.append(',');
            newdata.append(heartRateInfo.getPeakMinutes());
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
        System.out.println("Upload Done");
    }

}


