package practicals.uuj.org.pocketmaths;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class calculator extends ActionBarActivity {

    boolean clear_screen = true;
    boolean operator_state = false ;
    boolean insert_state = false;
    boolean last_click = false;

    final String APPTAG = "COM337";
    String dataKey = "calculatorCalcs";
    PocketMathsApplication myApp;
    //Operand1 & Operand2 are the numbers used to complete calculations
    float Operand1 = 0f;
    float Operand2 = 0f;
    float Answer = 0f;
    String Operator = "";
    public static String filename = "CalculatorCalculations";
    String previousCalculations;

    SharedPreferences previousCalcs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the layout of the page
        setContentView(R.layout.activity_calculator);

        //Get the application context:
        myApp = (PocketMathsApplication) getApplicationContext();
        newCalculation();
    }

    //this method is linked to each button in the xml file
    //whenever a button is clicked this method is called
    //the correct case it used and the corresponding text in inserted
    public void ButtonClickHandler(View v){
        EditText display = (EditText) findViewById(R.id.display);
        switch(v.getId()){
            case R.id.button0 :
                insert_text("0");
                break;
            case R.id.button1 :
                insert_text("1");
                break;
            case R.id.button2 :
                insert_text("2");
                break;
            case R.id.button3 :
                insert_text("3");
                break;
            case R.id.button4 :
                insert_text("4");
                break;
            case R.id.button5 :
                insert_text("5");
                break;
            case R.id.button6 :
                insert_text("6");
                break;
            case R.id.button7 :
                insert_text("7");
                break;
            case R.id.button8 :
                insert_text("8");
                break;
            case R.id.button9 :
                insert_text("9");
                break;
            case R.id.buttonAnswer:
                insert_text(String.valueOf(Answer));
                break;
            case R.id.buttonPoint :
                //if it doesn't contain a '.' or if the calculator is in operator state
                if (!display.getText().toString().contains(".") || this.operator_state){
                    insert_text(".");
                }
                break;
            case R.id.buttonAdd :
                set_operator("+");
                break;
            case R.id.buttonSub :
                set_operator("-");
                break;
            case R.id.buttonMulti:
                set_operator("*");
                break;
            case R.id.buttonDiv:
                set_operator("÷");
                break;
            case R.id.buttonSqr:
                set_operator("√");
                break;
            case R.id.buttonPow:
                set_operator("^");
                break;
            case R.id.buttonOneOver:
                set_operator("d");
                break;
            case R.id.buttonEquals:
                if(display.getText().toString().length() > 0 && this.Operator != ""){
                    calculator();
                    this.clear_screen = true;
                    this.Operand1 = 0f;
                    this.Operand2 = 0f;
                    this.Operator = "";
                    this.operator_state = false ;
                }
                break;
            //deletes the last key put in or resets the screen to 0 after everything else is gone
            case R.id.buttonDelete:
                if(display.getText().toString().length() > 1){
                    String screen_new = display.getText().toString().substring(0, display.getText().toString().length()-1);
                    display.setText(screen_new);
                    this.clear_screen = false;
                }
                else{
                    display.setText("0");
                    this.clear_screen = true;
                }
                break;
            //clear all values the calculator holds
            case R.id.buttonClear:
                this.Operand1 = 0f;
                this.Operand2 = 0f;
                this.Operator = "";
                this.operator_state = false ;
                this.insert_state = false;
                this.last_click = false;
                this.clear_screen = true;
                display.setText("0");
                break;
        }
    }

    public void insert_text(String text){
        EditText display = (EditText) findViewById(R.id.display);
        if(this.clear_screen){
            display.setText("");
            this.clear_screen = false;
        }
        this.insert_state = true;
        this.last_click = true;
        display.append(text);
    }

    public void set_operator(String operator){
        EditText display = (EditText) findViewById(R.id.display);
        if (display.getText().toString().equals(".")){
            display.setText("0");
        }

        //to open the calculator before another operator and operand is set
        //calculations are preformed here
        if (this.insert_state && this.operator_state && this.last_click){
            calculator();
        }

        if (display.getText().toString().length() > 0 ){
            this.Operand1 = Float.parseFloat(display.getText().toString());
        }

        this.operator_state = true;
        this.clear_screen = true;
        this.last_click = false;

        //sets the operator for the calculation
        if (operator.equals("+")){
            this.Operator = "+";
        }
        else if (operator.equals("-")){
            this.Operator = "-";
        }
        else if (operator.equals("*")){
            this.Operator = "*";
        }
        else if (operator.equals("÷")){
            this.Operator = "÷";
        }

        //calculator is not needed for this method
        else if (operator.equals("√")){
            this.Answer = (float) Math.sqrt(Float.parseFloat(display.getText().toString()));
            display.setText(this.Answer + "");
            this.clear_screen = true;
            this.Operand1 = 0f;
            this.Operand2 = 0f;
            this.Operator = "";
            this.last_click = true;
            this.operator_state = false ;
        }

        //calculator is not needed for this method
        else if (operator.equals("d")){
            this.Answer = 1 / Float.parseFloat(display.getText().toString());
            display.setText(this.Answer + "");
            this.clear_screen = true;
            this.Operand1 = 0f;
            this.Operand2 = 0f;
            this.Operator = "";
            this.last_click = true;
            this.operator_state = false ;
        }
        else if (operator.equals("^")){
            this.Operator = "^";
        }
    }

    //called when the requirements have been met with suitable numbers to pass in
    public void calculator(){
        EditText display = (EditText) findViewById(R.id.display);
        if (display.getText().toString().equals(".")){
            display.setText("0");
        }
        if (display.getText().toString().length() > 0){
            this.Operand2 = Float.parseFloat(display.getText().toString());
        }
        if (this.Operator.equals("+")) {
            this.Answer = this.Operand1 + this.Operand2;
        } else if (this.Operator.equals("-")){
            this.Answer = this.Operand1 - this.Operand2;
        } else if (this.Operator.equals("*")){
            this.Answer =this.Operand1 * this.Operand2;
        } else if (this.Operator.equals("÷")){
            this.Answer = this.Operand1 / this.Operand2;
        }else if (this.Operator.equals("^")){
            this.Answer = (float) Math.pow(this.Operand1, this.Operand2); //operand1 to the power of operand2
        }else{
            this.Answer = Float.parseFloat(display.getText().toString());
        }

        display.setText(this.Answer + "");
        saveData(this.Operand1, this.Operator,this.Operand2, this.Answer);
    }

    protected void saveData(Float numberOne, String operand, Float numberTwo, Float answer){
        previousCalcs = getSharedPreferences(myApp.filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = previousCalcs.edit();
        previousCalculations = previousCalcs.getString(dataKey, "");

        previousCalculations = previousCalculations + numberOne + " " + operand + " " + numberTwo + " = " + answer + "\n";

        editor.putString(dataKey,previousCalculations);
        editor.commit();
        Log.v(APPTAG, ":calculator: editor committed");
    }

    protected void newCalculation(){
        previousCalcs = getSharedPreferences(filename, MODE_PRIVATE);
        SharedPreferences.Editor editor = previousCalcs.edit();
        previousCalculations = previousCalcs.getString("previousCalculations", "");

        previousCalculations = previousCalculations + "\n";

        editor.putString("previousCalculations",previousCalculations);
        editor.commit();
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "Calculator: onStart() called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "Calculator: onResume() called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "Calculator: onPause() called"); }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "Calculator: onStop() called"); }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "Calculator: onDestroy() called");
    }
}