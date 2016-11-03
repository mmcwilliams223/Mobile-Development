package practicals.uuj.org.pocketmaths;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class loadEntry extends ListActivity {

    String APPTAG = "COM337";

    private PocketMathsApplication myApp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor("#ff37474f"));

        String[] strFileList = null;

        Log.v(APPTAG, "LoadEntryActivity: onCreate() called");

        //Get the application context:
        myApp = ((PocketMathsApplication) getApplicationContext());

        //Retrieve the list of entry names from the database:
        strFileList = myApp.getEntryList();

        //Check if the list is empty:
        if (strFileList != null) {

            //Populate the list view:
            //Create an adapter for the list (ArrayAdapter):
            ArrayAdapter<String> listArrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.simplelist, strFileList);

            //Set the adapter:
            setListAdapter(listArrayAdapter);

        } else {

            //Provide a suitable message to the user and finish this activity:
            Toast.makeText(getBaseContext(), "WARNING: No Journal Entries found in the database!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //Create a click event listener for the ListView (onListItemClick):
    @Override
    protected void onListItemClick(ListView parent, View v, int position, long id) {

        String strListSelection = (String) getListAdapter().getItem(position);
        Toast.makeText(this, "Journal Entry " + strListSelection + " selected!", Toast.LENGTH_LONG).show();

        //Finish the activity (pass the list item string back to the calling activity):
        //Create an intent to return data to the calling activity:
        Intent retDataIntent = new Intent();

        //Return data to the calling activity:
        retDataIntent.setData(Uri.parse(strListSelection));
        setResult(RESULT_OK, retDataIntent);

        //Finish the activity:
        finish();
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
        Log.v(APPTAG, "loadEntryActivity: onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBackgroundColour();
        Log.v(APPTAG, "loadEntryActivity: onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(APPTAG, "loadEntryActivity: onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(APPTAG, "loadEntryActivity: onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(APPTAG, "loadEntryActivity: onDestroy() called");
    }
}
