package com.example.myapplication.amazonS3;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class pullBucketData extends AppCompatActivity {


    public int[] S3Bucket_object() throws Exception {
        final int[] a = {0};
        String bucketName = "mobilebucket";
        String userID = "144244";
        String date = "2020-11-21";
        String keyName = "Date_" + date + "_User_id_" + userID + "_fitbitdata.csv";

//        //store data from s3 bucket
//        ArrayList<String> headers = new ArrayList<>();
//        ArrayList<Integer> data = new ArrayList<>();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //object to call credentials class
                Credentials c = new Credentials();

                BasicAWSCredentials credentials = new BasicAWSCredentials(c.getAccess_key_id(), c.getSecret_access_key());
                AmazonS3 s3Client = new AmazonS3Client(credentials);
                S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
                InputStream objectData = object.getObjectContent();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
                    String line;
                    boolean is_firstLine = true;
                    while ((line = reader.readLine()) != null) {
//                        String[] parsed = line.split(",");
//                        if(is_firstLine) {
//                            for(int i = 0; i<parsed.length; i++){
//                                headers.add(parsed[i]);
//                            }
//                            is_firstLine = false;
//                        }
//                        else{
//                            for(int i = 0; i < parsed.length; i++){
//                                data.add(Integer.parseInt(parsed[i]));
//                            }
//                        }

                        System.out.println(line);
                    }
                    objectData.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return a;
    }
}
