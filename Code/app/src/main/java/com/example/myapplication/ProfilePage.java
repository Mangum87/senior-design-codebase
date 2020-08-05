package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fitbitsample.fitbitdata.FitbitPref;
import com.fitbitsample.fitbitdata.FitbitSummary;
import com.fitbitsample.fitbitdata.FitbitUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ProfilePage extends AppCompatActivity {

    private CircleImageView profileImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    private TextView fullname;
    private TextView weight;
    private TextView address1;
    private TextView address2;
    private TextView phonenum;
    private TextView gender;
    private TextView email;
    private Button weightlog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //choose profile image from media
        weightlog= findViewById(R.id.button_weightlog);
        profileImage = findViewById(R.id.profile_image);
        /*weightlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new Intent(ProfilePage.this,
                            Class.forName("com.fitbitsample.activity.MainActivity"));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });*/
        profileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v){
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
            });
        //displays user name

        User user=SharedPrefManager.getInstance(this).getUser();
        FitbitUser fitbitUser = FitbitPref.getInstance(this).getfitbitUser();
        fullname=findViewById(R.id.fullname);
        fullname.setText(user.getFname()+" "+user.getLname());

        //...zip
        gender = findViewById(R.id.genderAge);
        gender.setText(fitbitUser.getGender()+ ", "+fitbitUser.getAge() );

        //displays address
        address1 = findViewById(R.id.address1);
        address1.setText(user.getAddress());
        //...city,state,zip
        address2 = findViewById(R.id.address2);
        address2.setText(user.getCity()+", "+user.getState()+", "+user.getZipcode());
        //...phone number
        phonenum = findViewById(R.id.phonenum);
        phonenum.setText(user.getPhone());

        //...email
        email = findViewById(R.id.email);
        email.setText(user.getEmail());

        //writedatatofile();

        //weight log graph
        LineChartView lineChartView = findViewById(R.id.showGraph);

        String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                "Oct", "Nov", "Dec"};
        int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#121493"));
        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }
        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);
        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        axis.setTextSize(15);
        axis.setTextColor(Color.parseColor("#03A9F4"));


        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
        /*
        int i = 1;
        //badges
        if (i == 1) {
            VectorMasterView badgeVector = (VectorMasterView) findViewById(R.id.imageView9);

            // find the correct path using name
            PathModel rectangle = badgeVector.getPathModelByName("rectangle");

            // set the stroke color
            rectangle.setStrokeColor(Color.parseColor("#03A9F4"));

            // set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
            //outline.setFillColor(Color.parseColor("#ED4337"));

        }

         */


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }/*
    private void writedatatofile()
    {

        try{

            FileOutputStream writer = openFileOutput("fitdata.csv", Context.MODE_PRIVATE);
            FitbitSummary fitbitSummary = FitbitPref.getInstance(this).getfitbitSummary();
            LoginResponse loginResponse = SharedPrefManager.getInstance(this).getLoginResponse();
            User user = SharedPrefManager.getInstance(this).getUser();

            StringBuilder newdata = new StringBuilder();
            newdata.append("auth_token");
            newdata.append(',');
            newdata.append("id");
            newdata.append(',');
            newdata.append("date");
            newdata.append(',');
            newdata.append("activeScore");
            newdata.append(',');
            newdata.append("activityCalories");
            newdata.append(',');
            newdata.append("caloriesBMR");
            newdata.append(',');
            newdata.append("caloriesOut");
            newdata.append(',');
            newdata.append("marginalCalories");
            newdata.append(',');
            newdata.append("steps");
            newdata.append(',');

            newdata.append("totaldistance");
            newdata.append(',');
            newdata.append("trackerdistance");
            newdata.append(',');

            newdata.append("loggedActivitiesdistance");
            newdata.append(',');
            newdata.append("moderateActivedistance");
            newdata.append(',');

            newdata.append("veryActivedistance");
            newdata.append(',');
            newdata.append("veryActiveMinutes");
            newdata.append(',');

            newdata.append("lightlyActivedistance");
            newdata.append(',');
            newdata.append("lightlyActiveMinutes");
            newdata.append(',');

            newdata.append("sedentaryActivedistance");
            newdata.append(',');
            newdata.append("sedentaryMinutes");
            newdata.append(',');

            newdata.append("restingHeartRate");
            newdata.append('\n');

            newdata.append(loginResponse.getAuth_token());
            newdata.append(',');
            newdata.append(user.getUser_id());
            newdata.append(',');
            newdata.append(Calendar.getInstance().getTime().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getActiveScore().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getActivityCalories().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getCaloriesBMR().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getCaloriesOut().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getMarginalCalories().toString());
            newdata.append(',');
            newdata.append(fitbitSummary.getSteps());
            newdata.append(',');

            newdata.append(fitbitSummary.getTotal());
            newdata.append(',');
            newdata.append(fitbitSummary.getTracker());
            newdata.append(',');


            newdata.append(fitbitSummary.getLoggedActivities());
            newdata.append(',');
            newdata.append(fitbitSummary.getModeratelyActive());
            newdata.append(',');

            newdata.append(fitbitSummary.getVeryActive());
            newdata.append(',');
            newdata.append(fitbitSummary.getVeryActiveMinutes());
            newdata.append(',');

            newdata.append(fitbitSummary.getLightlyActive());
            newdata.append(',');
            newdata.append(fitbitSummary.getLightlyActiveMinutes());
            newdata.append(',');

            newdata.append(fitbitSummary.getSedentaryActive());
            newdata.append(',');
            newdata.append(fitbitSummary.getSedentaryMinutes().toString());

            newdata.append("\n");

            writer.write(newdata.toString().getBytes());
            writer.close();
            Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();

            System.out.println("complete");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            Toast.makeText(this,"Not Done",Toast.LENGTH_LONG).show();
        }

    }
    */


}

