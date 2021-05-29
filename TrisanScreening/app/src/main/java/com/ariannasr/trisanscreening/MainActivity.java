package com.ariannasr.trisanscreening;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SendPost() throws Exception {
        //Sets permissions for Android internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "https://frm-cvd-ca.esolg.ca/Township-of-King/Screening-Form";
        try {
            //Sends a GET request to the screening website to get a CSRF token
            Connection.Response getconnection = Jsoup.connect(url).method(Connection.Method.GET).execute();
                Document welcomePage = getconnection.parse();
                Element veritokenElement = welcomePage.select("#_Form > input").first();
                String veritoken = veritokenElement.attr("value");
                //Saves the CSRF cookie to be used for the POST request
                Map CSRFcookie = getconnection.cookies();
                final EditText nametext = (EditText) findViewById(R.id.nametext);
                final EditText phonetext = (EditText) findViewById(R.id.phonetext);
                String name = nametext.getText().toString();
                String phone = phonetext.getText().toString();
                //Sends a POST request to the screening website with the form information & CSRF token
                Connection.Response postconnection = Jsoup.connect(url)
                        .cookies(CSRFcookie)
                        .data("__RequestVerificationToken", veritoken)
                        //Name field
                        .data("Q_b9849eb2-813d-4d0a-a1ce-643f1c8af986_0", name)
                        //Email field
                        .data("Q_62ebd8c8-7d45-481d-a8b1-ad54a390a029_0", "anasr@my.yorku.ca")
                        //Phone number field
                        .data("Q_f4a3bb2e-ebab-4660-a9a1-75c0d79fe0b4_0", "")
                        //Staff or visitor field
                        .data("Q_20cd46d1-3b95-46ad-81a3-b7b4d7fe7bf9_0", "b9558536-87d3-4395-aceb-ac3e012b4bad")
                        //Facility fields
                        .data("Q_012d8ddf-e75a-4266-ae78-f59502862aa9_0", "Trisan Centre")
                        .data("Q_6b61b457-f325-41d7-9784-5cb4a959223f_0", "All Areas")
                        //Checkboxes
                        .data("Q_301151e9-02c5-48b6-ae09-4b605bcbc99f_0", "85b25799-c250-44a0-a23f-4676ac322159")
                        .data("Q_eb129e60-dce7-438b-9666-bb92112c1c92_0", "f9474264-4bd1-4696-9ef9-ed5eae3cb617")
                        .data("Q_dac07f48-d9e0-4395-9b3b-6c288d5bec1f_0", "4e6aa5ab-b058-4b8d-a8b7-05d229d57c16")
                        .data("Q_972f65e9-bff1-4528-8560-a4d1da5d325d_0", "")
                        //Misc.
                        .data("FormId", "276caa59-80a5-4ced-9b9d-025e1d753b4a")
                        .data("_ACTION", "Continue")
                        .data("PageIndex", "1")
                        //Send the POST
                        .method(Connection.Method.POST)
                        .execute();
                System.out.println(postconnection.body());
            final ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkmark));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(View view) throws Exception {
        final Button button = (Button) findViewById(R.id.button);
        //Makes the submit button un-clickable while form is being submitted
        button.setEnabled(false);
        SendPost();
        //Once the form has been submitted the button is made clickable again
        button.setEnabled(true);
        //If request is successful clear the text fields
        final EditText nametext = (EditText) findViewById(R.id.nametext);
        final EditText phonetext = (EditText) findViewById(R.id.phonetext);
        nametext.getText().clear();
        phonetext.getText().clear();
    }
}

