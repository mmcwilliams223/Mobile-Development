package practicals.uuj.org.pocketmaths;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class newEntry extends Activity {

    String APPTAG = "COM337";

    private PocketMathsApplication myApp = null;
    static String buttonStyle;
    int resID;

    private TextView tvEditTextTitle;
    private EditText etTextBox;
    private Button btnSaveEntry;
    private Button returnButton;
    private Button deleteButton;

    private String strEntryTitle = null;
    private String strEntryFilename = "";
    private String strLoadEntryText = "";
    private float fSize = 0;
    private String strName = null;
    private Typeface tfFontType = Typeface.SANS_SERIF;
    private boolean bJournalEntryLoaded = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        Log.v(APPTAG, "EditorActivity: onCreate() called");

        //Get the application context:
        myApp = ((PocketMathsApplication) getApplicationContext());
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //Get the View objects:
        tvEditTextTitle = (TextView) findViewById(R.id.tvEditTextBoxTitle);
        etTextBox = (EditText) findViewById(R.id.editTextBox);
        btnSaveEntry = (Button) findViewById(R.id.btnSaveEntry);
        returnButton = (Button) findViewById(R.id.returnButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        deleteButton.setVisibility(View.INVISIBLE);

        determineButtonStyle(buttonStyle);

        Log.v(APPTAG, ":newEntry: layout setup");

        //Obtain the Bundle passed by Main Activity:
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            //Parse the bundle and generate the index value:
            strEntryFilename = extras.getString("ENTRYTITLE");

            //If the title is null, then its a new entry
            //otherwise load the file and display it:
            if (strEntryFilename == null) {

                strEntryTitle = myApp.getDateTimeSelection();

            } else {


                //Attempt to retrieve the entry from the database:
                bJournalEntryLoaded = retrieveJournalEntryText(strEntryFilename);
                if (bJournalEntryLoaded) {

                    //Assign the title for the journal entry:
                    strEntryTitle = strEntryFilename;

                    //Assign the input text to the EditText box:
                    etTextBox.setText(strLoadEntryText);

                    btnSaveEntry.setText("Update Entry");
                    returnButton.setText("Return");

                    // Making the delete entry button appear for the loaded entry
                    deleteButton.setVisibility(View.VISIBLE);

                } else {

                    //Unable to load the file, so display a message:
                    Toast.makeText(getBaseContext(), "Unable to load selected journal entry!", Toast.LENGTH_SHORT).show();

                    //Finish the activity:
                    finish();
                }

            }
        }

        // Set the title for the journal entry:
        tvEditTextTitle.setText("Journal Entry: " + strEntryTitle);

        // Setting the text size
        fSize = curPrefs.getFloat(PocketMathsApplication.FONT_SIZE_KEY, 12);
        strName = curPrefs.getString(PocketMathsApplication.FONT_TYPE_KEY, "SANS_SERIF");
        etTextBox.setTextSize(fSize);

        // Setting the font type
        if (strName.equals("SANS_SERIF")) {
            tfFontType = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        } else if (strName.equals("SERIF")) {
            tfFontType = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
        } else if (strName.equals("MONOSPACE")) {
            tfFontType = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
        }

        etTextBox.setTypeface(tfFontType);

        //Create the click event listener for the Save Entry button (OnClickListener):
        btnSaveEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Get the text from the EditText box:
                String strNewEntryText = etTextBox.getText().toString();

                //Insert/Update the database entry (as appropriate):
                if (!bJournalEntryLoaded) {

                    //Attempt to insert the journal entry into the database:
                    if (myApp.insertDatabaseEntry(strEntryTitle, strNewEntryText)) {

                        //The saved message should only be displayed the first time. On successive saves of the same entry
                        //the appended message should be shown instead, therefore set the flag (which sets the response):
                        Toast.makeText(getBaseContext(), "Entry inserted into database successfully!", Toast.LENGTH_SHORT).show();
                        bJournalEntryLoaded = true;

                        // Making the delete entry button appear for the loaded entry
                        deleteButton.setVisibility(View.VISIBLE);

                        btnSaveEntry.setText("Update Entry");
                        returnButton.setText("Return");

                    } else {
                        Toast.makeText(getBaseContext(), "Unable to inset entry into database!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // updating the entry in the database
                    if (myApp.updateDatabaseEntry(strEntryTitle, strNewEntryText)) {
                        Toast.makeText(getBaseContext(), "Entry in database updated successfully!", Toast.LENGTH_SHORT).show();
                        Log.v(APPTAG, ":newEntry: entry updated successfully");
                    } else {
                        Toast.makeText(getBaseContext(), "Unable to update entry in database!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!bJournalEntryLoaded) {
                    etTextBox.setText("");
                }
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Attempt to delete the journal entry from the database:
                try {
                    confirmChoice();
                }
                catch(NullPointerException e) {
                    Toast.makeText(getBaseContext(), "Unable to delete entry from database!!", Toast.LENGTH_SHORT).show();
                }
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
        Log.v(APPTAG, ":newEntry: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        btnSaveEntry.setBackgroundResource(i);
        returnButton.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    //Private method to retrieve the text from the specified database entry:
    private boolean retrieveJournalEntryText(String strFilename) {

        boolean bRet = false;
        String strTemp = null;

        //Retrieve the entry text from the database and
        //store in the strLoadEntryText variable:
        strTemp = myApp.getDatabaseEntry(strFilename);
        if (strTemp != null) {

            strLoadEntryText = strTemp;
            bRet = true;
        }

        return bRet;
    }

    public void confirmChoice() {
        // We implements here our logic
        createDialog();
    }

    private void createDialog() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Are you sure you want to delete this?");
        alertDlg.setCancelable(false); // We avoid that the dialog can be cancelled, forcing the user to choose one of the options

        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newEntry.super.onBackPressed();
                myApp.deleteDatabaseEntry(strEntryTitle);
                Toast.makeText(getBaseContext(), "Entry deleted from database!!", Toast.LENGTH_SHORT).show();
                finish();
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
        Log.v(APPTAG, "newEntry: onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "newEntry: onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "newEntry: onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "newEntry: onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "newEntry: onDestroy() called");
    }
}