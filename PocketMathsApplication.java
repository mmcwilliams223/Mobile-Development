package practicals.uuj.org.pocketmaths;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PocketMathsApplication extends Application {

    //Define the global variables:
    private SharedPreferences prefs;
    private String strPrefName = "EditorPrefs";
    private Calendar calDateSelect;

    //Define keys for the SharedPreferences:
    public static final String FONT_SIZE_KEY = "fontsize";
    public static final String FONT_TYPE_KEY = "fonttype";

    public static final String COLOUR_RED_VALUE = "redValue";
    public static final String COLOUR_GREEN_VALUE = "greenValue";
    public static final String COLOUR_BLUE_VALUE = "blueValue";
    public static final String COLOUR_ALPHA_VALUE = "alphaValue";
    public static final String BUTTON_STYLE_KEY = "button_style";

    public static final int DEFAULT_COLOUR_RED_VALUE = 55;
    public static final int DEFAULT_COLOUR_GREEN_VALUE = 71;
    public static final int DEFAULT_COLOUR_BLUE_VALUE = 79;
    public static final int DEFAULT_COLOUR_ALPHA_VALUE = 255;

    public static final String DEFAULT_BUTTON_STYLE = "styled_button.xml";

    public static final String filename = "Calculations";

    //Private JournalDatabase object:
    private JournalDatabase dbJournalDatabase;

    ////////////////////////////////////////////////////////////////////
    //Public Database Creation/Access Methods:
    ////////////////////////////////////////////////////////////////////

    //Method to instantiate a new database (DatabaseUtility object):
    public void createJournalDatabase() {

        dbJournalDatabase = new JournalDatabase(this);

    }

    //Method to insert a new entry into the database:
    public boolean insertDatabaseEntry(String strNewTitle, String strNewEntry) {

        boolean bResult = false;

        //Open the database:
        dbJournalDatabase.open();

        //Inset the new entry into the database:
        bResult = dbJournalDatabase.insetEntry(strNewTitle, strNewEntry);

        //Close the database:
        dbJournalDatabase.close();

        //Return the result of the operation:
        return bResult;
    }

    //Method to insert a new entry into the database:
    public boolean deleteDatabaseEntry(String strNewTitle) {

        boolean bResult = false;

        //Open the database:
        dbJournalDatabase.open();

        //Inset the new entry into the database:
        bResult = dbJournalDatabase.deleteEntry(strNewTitle);

        //Close the database:
        dbJournalDatabase.close();

        //Return the result of the operation:
        return bResult;
    }


    //Method to update the named database entry with the specified text:
    public boolean updateDatabaseEntry(String strName, String strEntryText) {

        boolean bResult = false;

        //Open the database:
        dbJournalDatabase.open();

        try {

            //Update the database entry:
            bResult = dbJournalDatabase.updateEntry(strName, strEntryText);

        } catch (Exception ex) {

            //Print out the exception (if necessary):
            ex.printStackTrace();
        }

        //Close the database:
        dbJournalDatabase.close();

        //Return the text string for the entry:
        return bResult;
    }


    //Method to retrieve the text of a named entry:
    public String getDatabaseEntry(String strName) {

        String strEntryText = null;
        Cursor curEntryText = null;

        //Open the database:
        dbJournalDatabase.open();

        try {

            //Perform the query:
            curEntryText = dbJournalDatabase.getEntry(strName);

            //Check the result of the query:
            //NOTE: Need to moveTo first row in the result:
            if (curEntryText.moveToFirst()) {

                //Get the string from the database row result:
                strEntryText = curEntryText.getString(JournalDatabase.DBINDEX_ENTRYTEXT);
            }

        } catch (Exception ex) {

            //Print out the exception (if necessary):
            ex.printStackTrace();
        }

        //Close the database:
        dbJournalDatabase.close();

        //Return the text string for the entry:
        return strEntryText;
    }


    //Method to retrieve all entries from the database (store names in a string array):
    public String[] getEntryList() {

        int nIdx = 0;
        int nTotalNum = 0;
        String[] strNameList = null;
        Cursor newCursor = null;

        //Open the database:
        dbJournalDatabase.open();

        //Retrieve all entries from the database:
        newCursor = dbJournalDatabase.getEntryAll();

        //Determine the total number of entries retrieved from the database:
        nTotalNum = newCursor.getCount();

        //Only perform further processing if there are entries:
        if (nTotalNum > 0) {

            //Instantiate an array to store the entry names (new String[nTotalNum]):
            strNameList = new String[nTotalNum];

            //Move to the first entry retrieved (if possible):
            if (newCursor.moveToFirst()) {

                do {

                    //Obtain the string at column one in the database (DatabaseUtility.DBINDEX_ENTRYNAME):
                    String strTemp = newCursor.getString(JournalDatabase.DBINDEX_ENTRYNAME);

                    //Add the entry name string to the string array:
                    strNameList[nIdx] = strTemp;

                    //Increment the index into the string array:
                    nIdx++;

                } while (newCursor.moveToNext());
            }
        }

        //Close the database:
        dbJournalDatabase.close();

        //Return the list:
        return strNameList;
    }


    ////////////////////////////////////////////////////////////////////
    //Public SharedPreferences Methods:
    ////////////////////////////////////////////////////////////////////

    //Application global accessor methods:
    public void setEditorPrefs(SharedPreferences newPrefs) {
        prefs = newPrefs;
    }


    public SharedPreferences getEditorPrefs() {
        prefs = getSharedPreferences(strPrefName, MODE_PRIVATE);
        return prefs;
    }


    ////////////////////////////////////////////////////////////////////
    //Public Date Methods:
    ////////////////////////////////////////////////////////////////////

    public void setDateSelection(Calendar newDate) {
        calDateSelect = newDate;
    }


    public String getDateSelection() {

        Date dTime = null;
        String strCurrentDate = null;

        //Get a Date object from the Calendar object (for use with SimpleDateFormat):
        dTime = calDateSelect.getTime();

        //Format the date/time string:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        strCurrentDate = sdf.format(dTime);

        return strCurrentDate;
    }


    public String getDateTimeSelection() {

        Date dTime = null;
        String strCurrentDateTime = null;

        //Get a Date object from the Calendar object (for use with SimpleDateFormat):
        dTime = calDateSelect.getTime();

        //Format the date/time string:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        strCurrentDateTime = sdf.format(dTime);

        return strCurrentDateTime;
    }
}
