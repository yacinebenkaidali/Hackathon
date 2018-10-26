package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Donnes.Habit;
import Donnes.Time;
import Donnes.UTILISATEUR;

public class Datasrc {
    SQLiteOpenHelper dbhelp;
    public SQLiteDatabase db;

    public Datasrc(Context context)
    {
        dbhelp = new HabitDBHelper(context);
    }

    public void open() {
        db = dbhelp.getWritableDatabase();
    }

    public void close() {

        dbhelp.close();
    }
    public List<Habit> findallhabit(Context context, String CODE_USER) {
        List<Habit> habits = new ArrayList<>();

        db = context.openOrCreateDatabase("Habit_Tracker", Context.MODE_PRIVATE, null);
        String allhabits = "select HABIT.* from UTILISATEUR " +
                "    inner join HABIT on HABIT.CODE_USER=UTILISATEUR.CODE_USER;" ;

        try{
            Cursor c = db.rawQuery(allhabits, null);
            Log.wtf("cursor", "findallhabit: "+String.valueOf(c.getCount()));
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        Habit h1 = new Habit();
                        h1.CATEGORY=c.getInt(c.getColumnIndex("CATEGORY"))>0;
                        h1.CODE_HABIT=c.getInt((c.getColumnIndex("CODE_HABIT")));
                        h1.DESCRIPTION =c.getString((c.getColumnIndex("DESCRIPTION")));
                        h1.HABIT_TYPE=c.getString((c.getColumnIndex("HABIT_TYPE")));
                        h1.REGULARITY=c.getString((c.getColumnIndex("REGULARITY")));

                        habits.add(h1);
                    } while (c.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return habits;
    }


    public void createHabit(Habit h) {
        ContentValues values = new ContentValues();
        values.put("CODE_USER",h.CODE_USER);
        values.put("DESCRIPTION", h.DESCRIPTION);
        values.put("CATEGORY", h.CATEGORY);
        values.put("REGULARITY", h.REGULARITY);
        values.put("HABIT_TYPE", h.HABIT_TYPE);

        db.insert("HABIT", null, values);

    }

    public void createUser(UTILISATEUR user) {
        ContentValues values = new ContentValues();
        values.put("NAME",user.NAME);
        values.put("SUR_NAME", user.SUR_NAME);

        db.insert("UTILISATEUR", null, values);
    }

    public void createTime(Time time) {
        ContentValues values = new ContentValues();
        values.put("CODE_TIME",time.CODE_TIME);
        values.put("CODE_HABIT", time.CODE_Habit);
        values.put("COUNT_NUM", time.COUNT_NUM);
        //values.put("DATE_HABIT", String.valueOf(time.DATE_HABIT));

        db.insert("TIME_HABIT", null, values);
    }

}
