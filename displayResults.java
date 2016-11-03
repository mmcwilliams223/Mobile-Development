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


public class displayResults extends ActionBarActivity {

    TextView test;
    SharedPreferences previousCalcs;
    public static String filename = "CalculatorCalculations";
    final String APPTAG = "COM337";

    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        test = (TextView) findViewById(R.id.editText);
        returnButton = (Button) findViewById(R.id.returnButton);

        determineButtonStyle(buttonStyle);

        previousCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);

        //creating bundle to collect the passed data
        Intent i = getIntent();
        Bundle passedData = i.getExtras();
        String dataKey = passedData.getString("stringName");


        String prevCalcs = previousCalcs.getString(dataKey, "No Previous Calculations Stored.");

        if(prevCalcs == null || prevCalcs == ""){
            prevCalcs = "No previous calculations were stored.";
        }

        test.setText(String.valueOf(prevCalcs));

        returnButton.setOnClickListener(new View.OnClickListener() {
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
        Log.v(APPTAG, ":displayResults: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "displayResults: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "displayResults: onResume() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "displayResults: onPause() called"); }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "displayResults: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "displayResults: onDestroy() called");
    }
}
