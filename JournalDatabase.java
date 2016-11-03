package practicals.uuj.org.pocketmaths;

/**
 * Created by Jordan on 19/04/15.
 */
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JournalDatabase {

    //Private database string constants:
    private static final String DBNAME = "BasicJournalDB";
    private static final String DBTABLE = "journalentries";

    //Public database string constants:
    public static final String DBKEY_ROWID = "id";
    public static final String DBKEY_ENTRYNAME = "entryname";
    public static final String DBKEY_ENTRYTEXT = "entrytext";

    //Database integer constants:
    public static final int DBINDEX_ENTRYNAME = 1;
    public static final int DBINDEX_ENTRYTEXT = 2;
    private static final int DB_VERSION = 1;

    //Private database creation/deletion string constants:
    private static final String DB_CREATE =
            "create table journalentries (id integer primary key autoincrement, "
                    + "entryname text not null, entrytext text not null);";

    private static final String DB_DESTROY = "DROP TABLE IF EXISTS " + DBTABLE;

    public static final String APPTAG = "COM337";

    //Application context:
    private final Context app_context;

    //Private database helper object (DBHelper):
    private DBHelper DBHelper;

    //Private database object (SQLiteDatabase):
    private SQLiteDatabase dBase;

    //Constructor:
    public JournalDatabase(Context context) {

        this.app_context = context;
        DBHelper = new DBHelper(app_context);

    }

    ////////////////////////////////////////////////////////////////////
    //Private Database Helper Class (DBHelper)
    ////////////////////////////////////////////////////////////////////
    private static class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context) {
            super(context, DBNAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DB_CREATE);
            } catch (SQLiteException sqlex) {
                sqlex.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(APPTAG, " DatabaseUtility::onUpgrade - Upgrading database from version "
                    + oldVersion + " to " + newVersion);

            //Delete the existing database table:
            db.execSQL(DB_DESTROY);

            //Create a new version of the database table:
            onCreate(db);
        }
    }

    ////////////////////////////////////////////////////////////////////
    //End of Private DatabaseHelper Class
    ////////////////////////////////////////////////////////////////////


    //Method to open the database:
    public JournalDatabase open() throws SQLException {

        dBase = DBHelper.getWritableDatabase();
        return this;
    }

    //Method to close the database:
    public void close() {

        DBHelper.close();
    }


    //Method to insert an entry into the database:
    public boolean insetEntry(String strEntryTitle, String strEntryText) {

        boolean bInsertSuccess = false;

        //Instantiate a new ContentValues object:
        ContentValues initialContentValues = new ContentValues();

        initialContentValues.put(DBKEY_ENTRYNAME, strEntryTitle);
        initialContentValues.put(DBKEY_ENTRYTEXT, strEntryText);

        //Insert the initial content values into the database:
        //NOTE: The call to the database inset method will return a new row ID:
        bInsertSuccess = (dBase.insert(DBTABLE, null, initialContentValues) > 0);

        //Return the result:
        return bInsertSuccess;
    }

    //Method to delete an entry from the database:
    public boolean deleteEntry(String strEntryTitle) {

        boolean bDeleteSuccess = false;

//        bDeleteSuccess = (dBase.delete(DBTABLE, DBKEY_ENTRYNAME + "=" + strEntryTitle, null) > 0);

        bDeleteSuccess = (dBase.delete(DBTABLE, DBKEY_ENTRYNAME + "=?", new String[] { strEntryTitle }) > 0);

        return bDeleteSuccess;
    }


    //Method to retrieve all the entries in the database:
    public Cursor getEntryAll() {

        String[] strArrayQuery;

        //Create the query string array:
        strArrayQuery = new String[] {DBKEY_ROWID, DBKEY_ENTRYNAME, DBKEY_ENTRYTEXT};

        //Perform (return) the database query:
        return dBase.query(DBTABLE, strArrayQuery, null, null, null, null, null);
    }

    //Method to retrieve a specified entry from the database:
    public Cursor getEntry(String strEntry) throws SQLException {

        String[] strQuery;
        String[] strSelectionArgs;

        //Create the query string array:
        strQuery = new String[] {DBKEY_ROWID, DBKEY_ENTRYNAME, DBKEY_ENTRYTEXT};

        //Create the selection arguments string array:
        strSelectionArgs = new String[] {strEntry};

        //NOTE: Replace null with the database query method call (dBase.query())
        Cursor newCursor = dBase.query(DBTABLE, strQuery,
                DBKEY_ENTRYNAME + " = " + "?",
                strSelectionArgs, null, null, null, null);

        //Return the result set:
        return newCursor;
    }


    //Method to update an entry in the database:
    public boolean updateEntry(String strEntryName, String strEntryText) {

        boolean bEntrySuccess = false;
        ContentValues newContentValues;
        String[] strWhereClause;

        //Create the WHERE clause arguments string array:
        //NOTE: This should contain the name of the database entry (strEntryName)
        strWhereClause = new String[] {strEntryName};

        //Instantiate a new ContactValues object:
        newContentValues = new ContentValues();
        newContentValues.put(DBKEY_ENTRYTEXT, strEntryText);

		/* Put database update method call in here (dBase.update())! */
        //bEntrySuccess = (dBase.insert(DBTABLE, null, newContentValues) > 0);
        bEntrySuccess = (dBase.update(DBTABLE, newContentValues, DBKEY_ENTRYNAME + " = " + "?",
                strWhereClause)>0);

        //Return the result of the update:
        return bEntrySuccess;
    }
}
