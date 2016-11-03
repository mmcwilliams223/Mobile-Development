package practicals.uuj.org.pocketmaths;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;


public class square_cube extends ActionBarActivity {
    final String APPTAG = "COM337";
    TextView t1, t2, t3, t4;
    double area, circumference, surfaceArea, volume, keyValue;
    Button returnButton;

    String dataKey = "squareCubeCalcs";
    String previousCalculations;
    SharedPreferences previousCalcs;
    PocketMathsApplication myApp;
    static String buttonStyle;
    int resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_cude);

        myApp = (PocketMathsApplication) getApplicationContext();
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //creating bundle to collect the passed data
        Intent i = getIntent();
        Bundle passedData = i.getExtras();
        String newData = passedData.getString("square key data");
        Log.v(APPTAG, ":square_cube.class: " + "data passed");

        //setting up TextViews and buttons
        t1 = (TextView) findViewById(R.id.square_answer11);
        t2 = (TextView) findViewById(R.id.square_answer21);
        t3 = (TextView) findViewById(R.id.cube_answer11);
        t4 = (TextView) findViewById(R.id.cube_answer21);
        Log.v(APPTAG, ":square_cube.class: " + "views setup successful");

        returnButton = (Button) findViewById(R.id.returnButton);
        determineButtonStyle(buttonStyle);

        //preforming the calculations below
        keyValue = Double.parseDouble(newData);
        area = Math.pow(keyValue, 2);
        circumference = (4 * keyValue);
        surfaceArea = 6 * Math.pow(keyValue, 2);
        volume = Math.pow(keyValue, 3);
        Log.v(APPTAG, ":square_cube.class: " + "calculations preformed successfully");

        //setting the TextViews and formatting the answers to 2 decimal places
        t1.setText(String.format("%.2f",circumference));
        t2.setText(String.format("%.2f",area));
        t3.setText(String.format("%.2f",surfaceArea));
        t4.setText(String.format("%.2f",volume));
        Log.v(APPTAG, ":square_cube.class: " + "data set to TextViews and formatted");

        saveData(keyValue, area, circumference, surfaceArea, volume);
        Log.v(APPTAG, ":square/cube: data saved");

        //action listener for the return button
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.v(APPTAG, ":square/cube: layout setup");
    }

    protected void saveData(Double keyValue, Double area, Double circumference, Double surfaceArea, Double volume){
        previousCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = previousCalcs.edit();
        previousCalculations = previousCalcs.getString(dataKey, "");

        previousCalculations = previousCalculations + "Side Length = " + keyValue + "\nThe square's area will be " + String.format("%.2f",area) + " and the circumference will be " + String.format("%.2f",circumference) + ". "
                + "The cube's surfaceArea will be " + String.format("%.2f",surfaceArea) + " and the volume will be " + String.format("%.2f",volume) + ".\n";

        editor.putString(dataKey, previousCalculations);
        editor.commit();
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
        Log.v(APPTAG, ":square/cube: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
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
                previousCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
                SharedPreferences.Editor editor = previousCalcs.edit();
                previousCalculations = previousCalcs.getString(dataKey, "");

                previousCalculations = "";

                editor.putString(dataKey, previousCalculations);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "square/cube: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "square/cube: onResume() called");
    }
    @Override
    protected void onPause() { super.onPause();
        Log.v(APPTAG, "square/cube: onPause() called"); }
    @Override
    protected void onStop() { super.onStop();
        Log.v(APPTAG, "square/cube: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "square/cube: onDestroy() called");
    }
}
