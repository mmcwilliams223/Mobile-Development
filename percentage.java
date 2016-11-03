package practicals.uuj.org.pocketmaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class percentage extends ActionBarActivity {
    Button returnButton;
    TextView t1;
    Double percentage;
    final String APPTAG = "COM337";

    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);

        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //creating bundle to collect the passed data
        Intent i = getIntent();
        Bundle passedData = i.getExtras();
        String scoreData = passedData.getString("percentage score data");
        String outOfData = passedData.getString("out of data");
        Log.v(APPTAG, ":percentage.class: " + "data passed");

        //setting up TextViews and buttons
        t1 = (TextView) findViewById(R.id.percentage);

        returnButton = (Button) findViewById(R.id.returnButton);
        determineButtonStyle(buttonStyle);
        Log.v(APPTAG, ":percentage.class: " + "content setup successful");


        //preforming calculations
        percentage = (Double.parseDouble(scoreData) / Double.parseDouble(outOfData)) * 100;
        Log.v(APPTAG, ":percentage.class: " + "calculations preformed");

        //setting the TextViews and formatting the answers to 2 decimal places
        t1.setText(String.format("%.2f",percentage) + "%");
        Log.v(APPTAG, ":percentage.class: " + "content set to views");

        //action listener for the return button
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
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


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "percentage: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "percentage: onResume() called");
    }
    @Override
    protected void onPause() { super.onPause();
        Log.v(APPTAG, "percentage: onPause() called"); }
    @Override
    protected void onStop() { super.onStop();
        Log.v(APPTAG, "percentage: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "percentage: onDestroy() called");
    }
}
