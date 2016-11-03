package practicals.uuj.org.pocketmaths;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;

public class About extends ActionBarActivity {

    String APPTAG = "COM337";
    TextView txtView1, txtView2;
    Button returnBtn, sendEmailButton;
    LinearLayout linLayout;

    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the application context:
        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //creating LayoutParams
        LinearLayout.LayoutParams linearParameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //creating layout and specifying orientation & gravity
        linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        //creating the button parameters
        ViewGroup.MarginLayoutParams buttonMarginParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                                           ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonMarginParams.setMargins(100, 100, 100, 1000);
        ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(buttonMarginParams);
        ViewGroup.LayoutParams textViewParameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        //setting up the TextViews and Buttons
        txtView1 = new TextView(this);
        txtView1.setGravity(Gravity.CENTER);
        txtView1.setText("COM337 - Assignment 3 \nDeveloped by Jordan Thomson,\n Michael McWilliams\n & Patrick Kelly");
        txtView1.setLayoutParams(textViewParameters);

        returnBtn = new Button(this);
        returnBtn.setGravity(Gravity.CENTER);
        returnBtn.setText("Return");
        returnBtn.setTextColor(Color.WHITE);
        returnBtn.setBackgroundResource(R.drawable.styled_button);
        returnBtn.setLayoutParams(buttonParams);
        determineButtonStyle(buttonStyle);

        //adding the TextViews and buttons to the layout
        linLayout.addView(txtView1);
        linLayout.addView(returnBtn);

        //setting up the content view
        this.addContentView(linLayout, linearParameters);
        Log.v(APPTAG, ":About: layout setup");

        //action listener for return button
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void updateBackgroundColour() {
        //Create a SharedPreferences object for strPrefName (MODE_PRIVATE):
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        int redC = curPrefs.getInt(myApp.COLOUR_RED_VALUE, 55);
        int greenC = curPrefs.getInt(myApp.COLOUR_GREEN_VALUE, 71);
        int blueC = curPrefs.getInt(myApp.COLOUR_BLUE_VALUE, 79);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.rgb(redC, greenC, blueC));
    }

    private void determineButtonStyle(String buttonStyle) {
        if(buttonStyle.equals("styled_button.xml")) {
            resID = R.drawable.styled_button;
        } else if(buttonStyle.equals("styled_button2.xml")) {
            resID = R.drawable.styled_button2;
        } else if(buttonStyle.equals("styled_button3.xml")) {
            resID = R.drawable.styled_button3;
        } else if(buttonStyle.equals("styled_button4.xml")) {
            resID = R.drawable.styled_button4;
        } else if(buttonStyle.equals("styled_button5.xml")) {
            resID = R.drawable.styled_button5;
        } else if(buttonStyle.equals("styled_button6.xml")) {
            resID = R.drawable.styled_button6;
        } else {

        }
        updateButtonStyle(resID);
        Log.v(APPTAG, ":About: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        returnBtn.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "about: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "about: onResume() called");
    }
    @Override
    protected void onPause() { super.onPause();
        Log.v(APPTAG, "about: onPause() called"); }
    @Override
    protected void onStop() { super.onStop();
        Log.v(APPTAG, "about: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "about: onDestroy() called");
    }
}
