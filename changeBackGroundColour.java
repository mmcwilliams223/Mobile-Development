package practicals.uuj.org.pocketmaths;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.RedirectException;

public class changeBackGroundColour extends Activity {

    SeekBar seekRed, seekGreen, seekBlue;
    View targetView;
    TextView blueTV, greenTV, redTV;
    Button saveButton, resetToDefaultButton, returnButton;
    String APPTAG = "COM337";
    int redC, greenC, blueC;
    static String buttonStyle;

    private RadioGroup rgButtonStyle;
    private RadioButton rbStyle1, rbStyle2, rbStyle3, rbStyle4, rbStyle5, rbStyle6;
    int resID;
    Drawable drawablePic;

    private PocketMathsApplication myApp = null;

    private float fFontSize = 0;
    private String strFontName = null;
    private Typeface tfType = Typeface.SANS_SERIF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_back_ground_colour);

        //Get the application context:
        myApp = ((PocketMathsApplication) getApplicationContext());

        //Create a SharedPreferences object for strPrefName (MODE_PRIVATE):
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        targetView = (View)findViewById(R.id.mainlayout);

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        rgButtonStyle = (RadioGroup) findViewById(R.id.buttonStyle);
        rbStyle1 = (RadioButton) findViewById(R.id.rbStyle1);
        rbStyle2 = (RadioButton) findViewById(R.id.rbStyle2);
        rbStyle3 = (RadioButton) findViewById(R.id.rbStyle3);
        rbStyle4 = (RadioButton) findViewById(R.id.rbStyle4);
        rbStyle5 = (RadioButton) findViewById(R.id.rbStyle5);
        rbStyle6 = (RadioButton) findViewById(R.id.rbStyle6);

        saveButton = (Button) findViewById(R.id.saveColourSettings);
        resetToDefaultButton = (Button) findViewById(R.id.resetToDefault);
        returnButton = (Button) findViewById(R.id.returnButton);

        if(buttonStyle.equals("styled_button.xml")) {
            rbStyle1.setChecked(true);
            resID = R.drawable.styled_button;
            updateButtonStyle(resID);
        } else if(buttonStyle.equals("styled_button2.xml")) {
            rbStyle2.setChecked(true);
            resID = R.drawable.styled_button2;
            updateButtonStyle(resID);
        } else if(buttonStyle.equals("styled_button3.xml")) {
            rbStyle3.setChecked(true);
            resID = R.drawable.styled_button3;
            updateButtonStyle(resID);
        } else if(buttonStyle.equals("styled_button4.xml")) {
            rbStyle4.setChecked(true);
            resID = R.drawable.styled_button4;
            updateButtonStyle(resID);
        } else if(buttonStyle.equals("styled_button5.xml")) {
            rbStyle5.setChecked(true);
            resID = R.drawable.styled_button5;
            updateButtonStyle(resID);
        } else if(buttonStyle.equals("styled_button6.xml")) {
            rbStyle6.setChecked(true);
            resID = R.drawable.styled_button6;
            updateButtonStyle(resID);
        } else {

        }

        Log.v(APPTAG, ":changeBackGroundColour: updateButtonStyle started");

        blueTV = (TextView) findViewById(R.id.BLUEtext);
        greenTV = (TextView) findViewById(R.id.GREENtext);
        redTV = (TextView) findViewById(R.id.REDtext);

        seekRed = (SeekBar)findViewById(R.id.seekred);
        seekGreen = (SeekBar)findViewById(R.id.seekgreen);
        seekBlue = (SeekBar)findViewById(R.id.seekblue);

        seekRed.setOnSeekBarChangeListener(seekChangeListener);
        seekGreen.setOnSeekBarChangeListener(seekChangeListener);
        seekBlue.setOnSeekBarChangeListener(seekChangeListener);

        fFontSize = curPrefs.getFloat(myApp.FONT_SIZE_KEY, 10);
        strFontName = curPrefs.getString(myApp.FONT_TYPE_KEY, "SANS_SERIF");

        redC = curPrefs.getInt(myApp.COLOUR_RED_VALUE, 55);
        seekRed.setProgress(redC);

        greenC = curPrefs.getInt(myApp.COLOUR_GREEN_VALUE, 71);
        seekGreen.setProgress(greenC);

        blueC = curPrefs.getInt(myApp.COLOUR_BLUE_VALUE, 79);
        seekBlue.setProgress(blueC);

        redTV.setText("RED: " + seekRed.getProgress());
        greenTV.setText("GREEN: " + seekGreen.getProgress());
        blueTV.setText("BLUE: " + seekBlue.getProgress());

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.rgb(redC, greenC, blueC));

        blueTV.setTextSize(fFontSize);
        greenTV.setTextSize(fFontSize);
        redTV.setTextSize(fFontSize);

        //Set the font type and associated radio button:
        if (strFontName.equals("SANS_SERIF")) {
            tfType = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        } else if (strFontName.equals("SERIF")) {
            tfType = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
        } else if (strFontName.equals("MONOSPACE")) {
            tfType = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
        }

        // Font size set
        blueTV.setTypeface(tfType);
        greenTV.setTypeface(tfType);
        redTV.setTypeface(tfType);

        rbStyle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button.xml";
                updateButtonStyle(R.drawable.styled_button);
            }
        });
        rbStyle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button2.xml";
                updateButtonStyle(R.drawable.styled_button2);
            }
        });
        rbStyle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button3.xml";
                updateButtonStyle(R.drawable.styled_button3);
            }
        });
        rbStyle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button4.xml";
                updateButtonStyle(R.drawable.styled_button4);
            }
        });
        rbStyle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button5.xml";
                updateButtonStyle(R.drawable.styled_button5);
            }
        });
        rbStyle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStyle = "styled_button6.xml";
                updateButtonStyle(R.drawable.styled_button6);
            }
        });

        Log.v(APPTAG, ":changeBackGroundColour: layout setup");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences curPrefs = myApp.getEditorPrefs();
                SharedPreferences.Editor editor = curPrefs.edit();

                editor.putInt(myApp.COLOUR_RED_VALUE, seekRed.getProgress());
                editor.putInt(myApp.COLOUR_GREEN_VALUE, seekGreen.getProgress());
                editor.putInt(myApp.COLOUR_BLUE_VALUE, seekBlue.getProgress());
                editor.putString(myApp.BUTTON_STYLE_KEY, buttonStyle);

                editor.commit();

                Toast.makeText(getApplicationContext(), "Settings saved successfully.", Toast.LENGTH_SHORT).show();
                Log.v(APPTAG, ":ColourPreferencesActivity: " + "Values saved for colour settings");
            }
        });

        resetToDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekBlue.setProgress(myApp.DEFAULT_COLOUR_BLUE_VALUE);
                seekGreen.setProgress(myApp.DEFAULT_COLOUR_GREEN_VALUE);
                seekRed.setProgress(myApp.DEFAULT_COLOUR_RED_VALUE);

                redTV.setText("RED: " + seekRed.getProgress());
                greenTV.setText("GREEN: " + seekGreen.getProgress());
                blueTV.setText("BLUE: " + seekBlue.getProgress());

                blueC = seekBlue.getProgress();
                greenC = seekGreen.getProgress();
                redC = seekRed.getProgress();

                // The default style is applied to RadioButton1
                rbStyle1.setChecked(true);
                buttonStyle = myApp.DEFAULT_BUTTON_STYLE;
                resID = R.drawable.styled_button;
                updateButtonStyle(resID);

                updateBackgroundColor();

                Toast.makeText(getApplicationContext(), "Settings reset successfully. Click save to save these settings.", Toast.LENGTH_SHORT).show();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekChangeListener
            = new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            // TODO Auto-generated method stub
            updateBackgroundColor();
            redTV.setText("RED: " + seekRed.getProgress());
            greenTV.setText("GREEN: " + seekGreen.getProgress());
            blueTV.setText("BLUE: " + seekBlue.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStopTrackingTouch(SeekBar arg0) {
            // TODO Auto-generated method stub

        }};

    private void updateBackgroundColor() {
        redC = seekRed.getProgress();
        greenC = seekGreen.getProgress();
        blueC = seekBlue.getProgress();

        targetView.setBackgroundColor(Color.rgb(redC, greenC, blueC));
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        saveButton.setBackgroundResource(i);
        resetToDefaultButton.setBackgroundResource(i);
        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }
}