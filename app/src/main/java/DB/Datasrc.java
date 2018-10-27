package DB;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.anis.widget_habbit.CollectionWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public List<Habit> findallhabit(Context context,String CODE_UTILISATEUR) {
        List<Habit> habits = new ArrayList<>();

        db = context.openOrCreateDatabase("Habit_Tracker", Context.MODE_PRIVATE, null);
        String allhabits = "select * from HABIT"+
                "    inner join TIME_HABIT on TIME_HABIT.CODE_HABIT=HABIT.CODE_HABIT;" ;

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
                        h1.Count= Integer.parseInt(c.getString((c.getColumnIndex("COUNT_NUM"))));
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

        long id=db.insert("HABIT", null, values);

        Time time = new Time() ;
        time.COUNT_NUM = 0 ;
        time.CODE_Habit = id ;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        String datenow=df.format(date);

        time.DATE_HABIT= java.sql.Date.valueOf(datenow);
        createTime(time);
    }


    public void deleteHabit(Habit h)
    {
        db.execSQL("DELETE FROM HABIT WHERE CODE_HABIT ="+h.CODE_HABIT );

    }

    public void createUser(UTILISATEUR user) {
        ContentValues values = new ContentValues();
        values.put("NAME",user.NAME);
        values.put("SUR_NAME", user.SUR_NAME);

        db.insert("UTILISATEUR", null, values);
    }

    public void createTime(Time time) {
        ContentValues values = new ContentValues();
        values.put("CODE_HABIT", time.CODE_Habit);
        values.put("COUNT_NUM", time.COUNT_NUM);
        values.put("DATE_HABIT", String.valueOf(time.DATE_HABIT));
        System.out.println("CODE_Habit"+time.CODE_Habit+"COUNT_NUM"+time.COUNT_NUM);
        db.insert("TIME_HABIT", null, values);
    }

    public void updateTime(int code,int count,String dat)
    {
        ContentValues values=new ContentValues();
        values.put("COUNT_NUM",count);
        db.update("TIME_HABIT",values,"CODE_HABIT = "+code+"  and strftime(\"%Y-%m-%d\\\", date(DATE_HABIT))=strftime(\"%Y-%m-%d\\\", date(\"" + dat + "\"))",null);

    }
    public void IncrementHabit(Context context, int ID, int count) {
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put("COUNT_NUM", count + 1);
            db.update("TIME_HABIT", cv, "CODE_HABIT= ?", new String[]{String.valueOf(ID)});

            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.setComponent(new ComponentName(context, CollectionWidget.class));
            context.sendBroadcast(intent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }


    }


}
