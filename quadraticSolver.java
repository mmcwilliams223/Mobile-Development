package practicals.uuj.org.pocketmaths;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class quadraticSolver extends ActionBarActivity {
    Button returnButton, solveButton;
    TextView xSquared, xNormal, nonX, answerField, addField;
    EditText xSquaredInput, xNormalInput, nonXInput;

    public static String filename = "QuadraticCalculations";
    String previousCalculations;
    SharedPreferences previousQuadraticCalcs;

    String dataKey = "quadraticCalcs";

    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;

    final String APPTAG = "COM337";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic_solver);

        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //Called to put a new line in the shared preferences file to split up the calculations
        newCalculation();

        returnButton = (Button) findViewById(R.id.returnButton);
        solveButton = (Button) findViewById(R.id.solveButton);
        determineButtonStyle(buttonStyle);

        xSquared = (TextView) findViewById(R.id.xSquared);
        xNormal = (TextView) findViewById(R.id.xNormal);
        nonX = (TextView) findViewById(R.id.nonX);
        answerField = (TextView) findViewById(R.id.answerField);
        addField = (TextView) findViewById(R.id.xSquaredAdd);

        xSquaredInput = (EditText) findViewById(R.id.xSquaredInput);
        xNormalInput = (EditText) findViewById(R.id.xNormalInput);
        nonXInput = (EditText) findViewById(R.id.nonXInput);

        xSquared.setText(Html.fromHtml("x<sup>2</sup>"));
        xNormal.setText("x + ");
        nonX.setText("");
        addField.setText("+");

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double answerOne, answerTwo;
                String xSquaredNumber, xNormalNumber, nonXNumber;

                Log.v(APPTAG, "variables not given values");

                xSquaredNumber = xSquaredInput.getText().toString();
                xNormalNumber = xNormalInput.getText().toString();
                nonXNumber = nonXInput.getText().toString();

                if((xSquaredNumber.equals("") || xSquaredNumber == null) ||
                        (xNormalNumber.equals("") || xNormalNumber == null) ||
                        (nonXNumber.equals("") || nonXNumber == null)){
                    Toast.makeText(getApplicationContext(), "Invalid Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.v(APPTAG, "variables given values");

                answerOne = solve(Double.parseDouble(xSquaredNumber), Double.parseDouble(xNormalNumber), Double.parseDouble(nonXNumber), "+");
                answerTwo = solve(Double.parseDouble(xSquaredNumber), Double.parseDouble(xNormalNumber), Double.parseDouble(nonXNumber), "-");

                if(Double.isNaN(answerOne) || Double.isNaN(answerTwo))
                {
                    answerField.setText("There are no possible solutions.");
                }
                else if(answerOne.equals(answerTwo)){
                    answerField.setText("The solution is: \n x is equal to " + String.format("%.2f",answerOne) + ".");
                    saveData(xSquaredNumber, xNormalNumber, nonXNumber, answerOne, answerTwo);
                }
                else
                {
                    //displaying answers to the user
                    answerField.setText("The solutions are: \n x is equal to " + String.format("%.2f",answerOne)
                            + " or " + String.format("%.2f",answerTwo) + ".");
                    saveData(xSquaredNumber, xNormalNumber, nonXNumber, answerOne, answerTwo);
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.v(APPTAG, ":quadraticSolver: layout setup");
    }

    public Double solve(Double xSquared, Double xNormal, Double nonX, String operator){
        Double x;
        if(operator.equals("+")){
            x = (-(xNormal) + Math.sqrt((xNormal * xNormal) - (4 * xSquared * nonX))) / (2 * xSquared);
            return x;
        }

        x = (-(xNormal) - Math.sqrt((xNormal * xNormal) - (4 * xSquared * nonX))) / (2 * xSquared);
        return x;
    }

    protected void saveData(String numberOne, String numberTwo, String numberThree, Double xOne, Double xTwo ){
        previousQuadraticCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = previousQuadraticCalcs.edit();
        previousCalculations = previousQuadraticCalcs.getString(dataKey, "");

        if(xOne.equals(xTwo)){
            previousCalculations = previousCalculations + numberOne + Html.fromHtml("x<sup>2</sup>") + " + " + numberTwo + " + " + numberThree + ".\n"
            + "x is equal to " + String.format("%.2f",xOne) + ". \n\n";
        }
        else {
            previousCalculations = previousCalculations + numberOne + Html.fromHtml("x<sup>2</sup>") + " + " + numberTwo + " + " + numberThree + ".\n"
            + "x is equal to " + String.format("%.2f",xOne) + " or " + String.format("%.2f",xTwo) + ".\n\n";
        }

        editor.putString(dataKey,previousCalculations);
        editor.commit();
        Log.v(APPTAG, ":quadraticSolver: editor committed");
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
        Log.v(APPTAG, ":quadraticSolver: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        solveButton.setBackgroundResource(i);
        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    protected void newCalculation(){
        previousQuadraticCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = previousQuadraticCalcs.edit();
        previousCalculations = previousQuadraticCalcs.getString(dataKey, "");

        previousCalculations = previousCalculations + "\n";

        editor.putString(dataKey, previousCalculations);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "quadraticSolver: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "quadraticSolver: onResume() called");
    }
    @Override
    protected void onPause() { super.onPause();
        Log.v(APPTAG, "quadraticSolver: onPause() called"); }
    @Override
    protected void onStop() { super.onStop();
        Log.v(APPTAG, "quadraticSolver: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "quadraticSolver: onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quadratic_solver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.see_prev_calcs) {
            Intent i = new Intent(getApplicationContext(), displayResults.class);
            i.putExtra("stringName", dataKey);
            startActivity(i);
            return true;
        }
        if (id == R.id.clear_previousCalculations) {
            confirmChoice();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmChoice() {
        // We implements here our logic
        createDialog();
    }

    private void createDialog() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Are you sure you want to delete previous calculations?");
        alertDlg.setCancelable(false); // We avoid that the dialog can be cancelled, forcing the user to choose one of the options

        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                previousQuadraticCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
                SharedPreferences.Editor editor = previousQuadraticCalcs.edit();
                previousCalculations = previousQuadraticCalcs.getString(dataKey, "");

                previousCalculations = "";

                editor.putString(dataKey,previousCalculations);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Previous Calculation Cleared!!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // We do nothing
            }
        });

        alertDlg.create().show();
        alertDlg.setCancelable(false);
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
}
