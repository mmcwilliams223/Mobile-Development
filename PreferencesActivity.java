package practicals.uuj.org.pocketmaths;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.graphics.Typeface;

public class PreferencesActivity extends Activity {

    String APPTAG = "COM337";

    private PocketMathsApplication myApp = null;
    static String buttonStyle;
    int resID;

    private SeekBar sbFontSize;
    private RadioGroup rgFontStyle;
    private RadioButton rbNormal;
    private RadioButton rbSerif;
    private RadioButton rbMono;
    private TextView tvPrefText;
    private Button btnSavePrefs;
    private Button btnChangeBackgroundColour;
    private Button btnExitPrefs;

    private float fFontSize = 0;
    private String strFontName = null;
    private Typeface tfType = Typeface.SANS_SERIF;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferenes);

        Log.v(APPTAG, "EditorPreferencesActivity: onCreate() called");

        //Get the application context:
        myApp = ((PocketMathsApplication) getApplicationContext());
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //Get the View objects:
        tvPrefText = (TextView) findViewById(R.id.tvShowPrefText);
        sbFontSize = (SeekBar) findViewById(R.id.sbFontSize);
        rgFontStyle = (RadioGroup) findViewById(R.id.rgFontStyle);
        rbNormal = (RadioButton) findViewById(R.id.rbNormal);
        rbSerif = (RadioButton) findViewById(R.id.rbSerif);
        rbMono = (RadioButton) findViewById(R.id.rbMono);
        btnSavePrefs = (Button) findViewById(R.id.btnSavePrefs);
        btnChangeBackgroundColour = (Button) findViewById(R.id.btnChangeBackgroundColor);
        btnExitPrefs = (Button) findViewById(R.id.btnExitPrefs);

        determineButtonStyle(buttonStyle);

        fFontSize = curPrefs.getFloat(myApp.FONT_SIZE_KEY, 10);
        strFontName = curPrefs.getString(myApp.FONT_TYPE_KEY, "SANS_SERIF");

        sbFontSize.setProgress((int)fFontSize);
        tvPrefText.setTextSize(fFontSize);

        //Set the font type and associated radio button:
        if (strFontName.equals("SANS_SERIF")) {

            tfType = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
            rbNormal.setChecked(true);

        } else if (strFontName.equals("SERIF")) {

            tfType = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
            rbSerif.setChecked(true);

        } else if (strFontName.equals("MONOSPACE")) {

            tfType = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
            rbMono.setChecked(true);
        }

        tvPrefText.setTypeface(tfType);
        Log.v(APPTAG, ":PreferencesActivity: layout setup");

        //Create the change event listener for the font size seek bar (OnSeekBarChangeListener):
        sbFontSize.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Change the font size on seekBar progress change
                fFontSize = progress;
                tvPrefText.setTextSize(fFontSize);
            }
        });

        //Create the event listener for the RadioGroup (OnCheckedChangeListener):
        rgFontStyle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Typeface tf = null;

                //Check the radio buttons and set the style of the test:
                if (rbNormal.isChecked()) {
                    tf = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
                    tvPrefText.setTypeface(tf);
                    strFontName = "SANS_SERIF";

                } else if (rbSerif.isChecked()) {
                    tf = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
                    tvPrefText.setTypeface(tf);
                    strFontName = "SERIF";

                } else if (rbMono.isChecked()) {
                    tf = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
                    tvPrefText.setTypeface(tf);
                    strFontName = "MONOSPACE";
                }
            }
        });

        btnSavePrefs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences curPrefs = myApp.getEditorPrefs();
                SharedPreferences.Editor editor = curPrefs.edit();
                editor.putFloat(myApp.FONT_SIZE_KEY, fFontSize);
                editor.putString(myApp.FONT_TYPE_KEY, strFontName);
                editor.commit();

                Toast.makeText(getBaseContext(), "Preferences saved successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnChangeBackgroundColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Finish the activity:
                Intent i = new Intent(getApplicationContext(), changeBackGroundColour.class);
                Log.v(APPTAG, ":PreferencesActivity: " + "ChangeColourClass intent started");
                //Start the new activity
                startActivity(i);
            }
        });


        btnExitPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void determineButtonStyle(String buttonStyle) {
        SharedPreferences curPrefs = myApp.getEditorPrefs();
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
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
        Log.v(APPTAG, ":PreferencesActivity: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        btnSavePrefs.setBackgroundResource(i);
        btnChangeBackgroundColour.setBackgroundResource(i);
        btnExitPrefs.setBackgroundResource(i);

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
        Log.v(APPTAG, "PreferencesActivity: onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        determineButtonStyle(buttonStyle);
        updateBackgroundColour();
        Log.v(APPTAG, "PreferencesActivity: onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "PreferencesActivity: onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "PreferencesActivity: onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "PreferencesActivity: onDestroy() called");
    }
}
