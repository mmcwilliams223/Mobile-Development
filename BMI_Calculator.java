package practicals.uuj.org.pocketmaths;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class BMI_Calculator extends ActionBarActivity {

    RadioButton metricButton, imperialButton;
    TextView heightSentence, weightSentence, BMIAnswer;
    EditText heightInput, weightInput;
    Button calculateBMI, returnButton;

    final String APPTAG = "COM337";

    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi__calculator);

        //Get the application context:
        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        metricButton = (RadioButton) findViewById(R.id.radioMetric);
        imperialButton = (RadioButton) findViewById(R.id.radioImperial);
        calculateBMI = (Button) findViewById(R.id.button_calculateBMI);
        returnButton = (Button) findViewById(R.id.returnButton);

        determineButtonStyle(buttonStyle);

        heightSentence = (TextView) findViewById(R.id.heightSentence);
        weightSentence = (TextView) findViewById(R.id.weightSentence);
        BMIAnswer = (TextView) findViewById(R.id.BMIAnswer);

        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);

        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(APPTAG, "BMICalculator: data not attributes");

                String data1 = heightInput.getText().toString();
                String data2 = weightInput.getText().toString();
                Double result;

                Log.v(APPTAG, "BMICalculator: data given attributes");

                if((data1 != null && !(data1.equals("")) || (data2 != null && !(data2.equals(""))))){
                    if(metricButton.isChecked()){
                        result = (Double.parseDouble(data2) / Math.pow((Double.parseDouble(data1) / 100), 2));
                        BMIAnswer.setText("Your BMI is " + String.format("%.1f",result) + bmiStatus(result));
                    }
                    else if(imperialButton.isChecked()){
                        result = (Double.parseDouble(data2) / (Math.pow(Double.parseDouble(data1), 2)))*703;
                        BMIAnswer.setText("Your BMI is " + String.format("%.1f",result) + bmiStatus(result));
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        metricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightSentence.setText("Height (cm):");
                weightSentence.setText("Weight (kg):");
            }
        });
        imperialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightSentence.setText("Height (inches):");
                weightSentence.setText("Weight (lbs):");
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String bmiStatus(Double bmiValue)
    {
        if (bmiValue < 18.5) {
            return "\nYou are Underweight";
        } else if (bmiValue < 25) {
            return "\nYou are Normal";
        } else if (bmiValue < 30) {
            return "\nYou are Overweight";
        } else {
            return "\nYou are Obese";
        }
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
        Log.v(APPTAG, ":BMICalculator: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        calculateBMI.setBackgroundResource(i);
        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "BMICalculator: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "BMICalculator: onResume() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "BMICalculator: onPause() called"); }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "BMICalculator: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "BMICalculator: onDestroy() called");
    }
}
