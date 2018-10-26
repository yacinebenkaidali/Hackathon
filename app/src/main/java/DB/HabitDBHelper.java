package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HabitDBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "Habit_Tracker";

    private static final String CREATE_HABIT="CREATE TABLE HABIT (" +
            "CODE_HABIT Integer  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "CODE_USER Integer ," +
            "DESCRIPTION VARCHAR(130)  NOT NULL ," +
            "HABIT_TYPE VARCHAR(130)  NOT NULL ," +
            "CATEGORY BIT NOT NULL," +
            "REGULARITY VARCHAR," +
            "FOREIGN KEY (CODE_USER) REFERENCES UTILISATEUR (CODE_USER)" +
            ");";

    private static final String CREATE_HABIT_TIME="CREATE TABLE TIME_HABIT (" +
            "CODE_TIME Integer  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "CODE_HABIT VARCHAR(15)  NOT NULL," +
            "COUNT_NUM int ," +
            "DATE_HABIT DATE," +
            "FOREIGN KEY (CODE_HABIT) REFERENCES HABIT (CODE_HABIT)" +
            ");";

    private  static final String CREATE_USER="CREATE TABLE UTILISATEUR (" +
            "CODE_USER Integer  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "NAME VARCHAR(130)  NOT NULL ," +
            "SUR_NAME VARCHAR(130)  NOT NULL " +
            ");";

    public HabitDBHelper(Context context)
    {
        super(context, "Habit_Tracker", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HABIT);
        db.execSQL(CREATE_HABIT_TIME);
        db.execSQL(CREATE_USER);
        Log.d("db", "created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
