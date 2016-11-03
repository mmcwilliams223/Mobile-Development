package practicals.uuj.org.pocketmaths;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    final String APPTAG = "COM337";

    PocketMathsApplication myApp;
    SharedPreferences curPrefs;
    static String buttonStyle;
    int resID;

    EditText circle_sphere_input, square_cube_input, userScore_input, outOf_input;
    Button square_cube, circle_sphere, percentage, calculator,
           BMICalculator, aboutButton, quadraticSolver, preferencesButton,
           notesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(APPTAG, ":MainActivity: " + "on create called");

        //Get the application context:
        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //instantiating all the buttons and corresponding EditText fields
        aboutButton = (Button) findViewById(R.id.button_about);
        BMICalculator = (Button) findViewById(R.id.button_BMI_Calculator);
        quadraticSolver = (Button) findViewById(R.id.button_quadraticSolver);
        preferencesButton = (Button) findViewById(R.id.button_preferences);
        notesButton = (Button) findViewById(R.id.button_notes);

        circle_sphere = (Button) findViewById(R.id.button_circle_calculations);
        circle_sphere_input = (EditText) findViewById(R.id.circle_inputBox);

        square_cube = (Button) findViewById(R.id.button_square_cube_calculations);
        square_cube_input = (EditText) findViewById(R.id.square_inputBox);

        calculator = (Button) findViewById(R.id.button_calculator);

        percentage = (Button) findViewById(R.id.button_percentage_calculations);
        userScore_input = (EditText) findViewById(R.id.score_input_percentage);
        outOf_input = (EditText) findViewById(R.id.outOf_input_percentage);

        Log.v(APPTAG, ":MainActivity: " + "determineButtonStyle");
        determineButtonStyle(buttonStyle);

        circle_sphere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = circle_sphere_input.getText().toString();

                Log.v(APPTAG, ":MainActivity: " + "data stored correctly");

                if (data != null && !(data.equals(""))) {
                    //creating intent and putting extras
                    Intent i = new Intent(getApplicationContext(), circle_sphere.class);
                    i.putExtra("circle key data", data);
                    Log.v(APPTAG, ":MainActivity: " + "data put into extra");
                    //Start the circle/sphere calculations
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "No number was entered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        square_cube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = square_cube_input.getText().toString();

                if (data != null && !(data.equals(""))) {
                    //creating intent and putting extras
                    Intent i = new Intent(getApplicationContext(), square_cube.class);
                    i.putExtra("square key data", data);
                    Log.v(APPTAG, ":MainActivity: " + "data put into extra");
                    //Start the circle/sphere calculations
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "No number was entered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the calculator
                startActivity(new Intent("org.uuj.practicals.calculator"));
            }
        });

        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data1 = userScore_input.getText().toString();
                String data2 = outOf_input.getText().toString();

                if ((data1 != null && !(data1.equals("")) || (data2 != null && !(data2.equals(""))))) {
                    if((Double.parseDouble(data2) < Double.parseDouble(data1))){
                        if(data1 == null || data2 == null){
                            Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //creating intent and putting extras to pass data
                    Intent i = new Intent(getApplicationContext(), percentage.class);
                    i.putExtra("percentage score data", data1);
                    i.putExtra("out of data", data2);
                    Log.v(APPTAG, ":MainActivity: " + "data put into extra");
                    //Start the new activity
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        BMICalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the BMICalculator
                startActivity(new Intent("org.uuj.practicals.BMICalculator"));
            }
        });
        quadraticSolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the quadraticCalculator
                startActivity(new Intent("org.uuj.practicals.QuadraticSolver"));
            }
        });
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the preferences activity
                startActivity(new Intent("org.uuj.practicals.Preferences"));
            }
        });
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the notes activity
                startActivity(new Intent("org.uuj.practicals.notes"));
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the about page
                startActivity(new Intent("org.uuj.practicals.about"));
            }
        });
    }

    //below the methods save the data and apply it to the correct EditText if the orientation is changed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        if(containsData(square_cube_input)){
            double data = Double.parseDouble(square_cube_input.getText().toString());
            savedInstanceState.putDouble("squareData", data);
            Log.v(APPTAG, "data saved");
        }
        if(containsData(circle_sphere_input)){
            double data = Double.parseDouble(circle_sphere_input.getText().toString());
            savedInstanceState.putDouble("circleData", data);
        }
        if(containsData(userScore_input)){
            double data = Double.parseDouble(userScore_input.getText().toString());
            savedInstanceState.putDouble("userScoreData", data);
        }
        if(containsData(outOf_input)){
            double data = Double.parseDouble(outOf_input.getText().toString());
            savedInstanceState.putDouble("outOfData", data);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        if (savedInstanceState.containsKey("squareData")); {
            double data = savedInstanceState.getDouble("squareData");
            square_cube_input.setText(Double.toString(data));
            Log.v(APPTAG, "Data retrieved ");
        }
        if (savedInstanceState.containsKey("circleData")); {
            double data = savedInstanceState.getDouble("circleData");
            circle_sphere_input.setText(Double.toString(data));
        }
        if (savedInstanceState.containsKey("userScoreData")); {
            double data = savedInstanceState.getDouble("userScoreData");
            userScore_input.setText(Double.toString(data));
        }
        if (savedInstanceState.containsKey("outOfData")); {
            double data = savedInstanceState.getDouble("outOfData");
            outOf_input.setText(Double.toString(data));
        }
    }

    public void updateBackgroundColour() {
        Log.v(APPTAG, ":MainActivity: " + "updateBackGroundColour started");
        //Create a SharedPreferences object for strPrefName (MODE_PRIVATE):
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        int redC = curPrefs.getInt(myApp.COLOUR_RED_VALUE, 55);
        int greenC = curPrefs.getInt(myApp.COLOUR_GREEN_VALUE, 71);
        int blueC = curPrefs.getInt(myApp.COLOUR_BLUE_VALUE, 79);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.rgb(redC, greenC, blueC));
        Log.v(APPTAG, ":MainActivity: " + "updateBackGroundColour finished");
    }

    //method to check if an EditText contains data
    private boolean containsData(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return true; //contains data
        } else {
            return false; //doesn't contain data
        }
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
        Log.v(APPTAG, ":MainActivity: " + "updateButtonStyle called");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        square_cube.setBackgroundResource(i);
        circle_sphere.setBackgroundResource(i);
        percentage.setBackgroundResource(i);
        calculator.setBackgroundResource(i);
        BMICalculator.setBackgroundResource(i);
        aboutButton.setBackgroundResource(i);
        quadraticSolver.setBackgroundResource(i);
        preferencesButton.setBackgroundResource(i);
        notesButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "MainActivity: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        determineButtonStyle(buttonStyle);
        updateBackgroundColour();
        Log.v(APPTAG, "MainActivity: onResume() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "MainActivity: onPause() called"); }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "MainActivity: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "MainActivity: onDestroy() called");
    }
}
