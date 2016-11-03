package practicals.uuj.org.pocketmaths;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Calendar;

public class notesActivity extends Activity {

    String APPTAG = "COM337";

    private PocketMathsApplication myApp = null;
    private int nLoadEntryActivityRequestCode = 1;
    static String buttonStyle;
    int resID;

    private Button btnEditorPrefs;
    private Button btnNewEntry;
    private Button btnExit;
    private Button btnLoadEntry;
    private String strTitle = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Log.v(APPTAG, "MainActivity: onCreate() called");

        //Get the application context:
        myApp = ((PocketMathsApplication) getApplicationContext());
        SharedPreferences curPrefs = myApp.getEditorPrefs();

        Log.v(APPTAG, "Styling buttons buttonStyle not assigned");
        buttonStyle = curPrefs.getString(myApp.BUTTON_STYLE_KEY, myApp.DEFAULT_BUTTON_STYLE);
        Log.v(APPTAG, "Styling buttons buttonStyle assigned");

        //Get the view objects:
        btnEditorPrefs = (Button) findViewById(R.id.btnEditorPrefs);
        btnNewEntry = (Button) findViewById(R.id.btnNewEntry);
        btnExit = (Button) findViewById(R.id.returnButton);
        btnLoadEntry = (Button) findViewById(R.id.btnLoadEntry);

        determineButtonStyle(buttonStyle);
        Log.v(APPTAG, ":notesActivity: determineButtonStyle started");

        //Determine the start date (current date):
        final Calendar currentDate = Calendar.getInstance();

        //Set the global calendar object:
        myApp.setDateSelection(currentDate);

        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        //Create the database for the journal entries:
        //NOTE: This will create it if it doesn't already exist!
        //dbJournalDatabase = new DatabaseUtility(myApp);
        myApp.createJournalDatabase();
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        btnEditorPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an Intent for the EditorPreferences Activity:
                Intent newIntent = new Intent("org.uuj.practicals.Preferences");

                //Start the activity:
                startActivity(newIntent);
            }
        });

        btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create an Intent for the EditorPreferences Activity:
                Intent newIntent = new Intent("org.uuj.practicals.newEntry");
                Bundle extras = new Bundle();
                extras.putString("TITLE", strTitle);
                newIntent.putExtras(extras);
                startActivity(newIntent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLoadEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent("org.uuj.practicals.loadEntry");
                startActivityForResult(newIntent, nLoadEntryActivityRequestCode);
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
        Log.v(APPTAG, ":notesActivity: updateButtonStyle started");
    }

    private void updateButtonStyle(int i) {
        Log.v(APPTAG, "Styling buttons updateButtonStyle started");

        btnNewEntry.setBackgroundResource(i);
        btnLoadEntry.setBackgroundResource(i);
        btnEditorPrefs.setBackgroundResource(i);
        btnExit.setBackgroundResource(i);

        Log.v(APPTAG, "Styling buttons updateButtonStyle finished");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.v(APPTAG, "MainActivity: onActivityResult() called");
        if (requestCode == nLoadEntryActivityRequestCode) {
            if (resultCode == RESULT_OK) {
                strTitle = data.getData().toString();
                Intent newIntent = new Intent("org.uuj.practicals.newEntry");
                Bundle extras = new Bundle();
                extras.putString("ENTRYTITLE", strTitle);
                newIntent.putExtras(extras);
                startActivity(newIntent);
            }
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


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(APPTAG, "NotesActivity: onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        determineButtonStyle(buttonStyle);
        updateBackgroundColour();
        Log.v(APPTAG, "NotesActivity: onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "NotesActivity: onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "NotesActivity: onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "NotesActivity: onDestroy() called");
    }
}