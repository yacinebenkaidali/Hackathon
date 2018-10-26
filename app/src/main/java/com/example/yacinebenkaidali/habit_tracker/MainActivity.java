package com.example.yacinebenkaidali.habit_tracker;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapters.Habit_Adapter;
import DB.Datasrc;
import Donnes.Habit;
import Donnes.Time;
import Donnes.UTILISATEUR;

public class MainActivity  extends AppCompatActivity implements DataEntryDialog.OnCompleteListener
{
    ListView lv;
    public Habit_Adapter adapter;
    Datasrc dbsrc;
    public List<Habit> habits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createTestingContent();
        generateContent();
        lv=(ListView) findViewById(R.id.list_view_habits);

        if ((lv != null)||(habits.size()!=0))
        {
            adapter=new Habit_Adapter(MainActivity.this, R.layout.list_veiw_habit, habits,"");
            lv.setAdapter(adapter);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public  void createTestingContent()
    {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        dbsrc.createHabit(new Habit("sport",true,"daily","sport",1));
        dbsrc.createHabit(new Habit("reading",true,"weekly","reading",1));
        dbsrc.createHabit(new Habit("gaming",true,"weekly","gaming",2));
        dbsrc.createHabit(new Habit("action",false,"monthly","action",2));

        dbsrc.createUser(new UTILISATEUR("ben kaid ali","yacine"));
        dbsrc.createUser(new UTILISATEUR("ouldimam","zineddine"));
        dbsrc.createUser(new UTILISATEUR("aguida","mohamed anis"));

        dbsrc.createTime(new Time(1,5));
        dbsrc.createTime(new Time(3,8));
        dbsrc.createTime(new Time(2,4));
        dbsrc.createTime(new Time(4,6));
        dbsrc.close();
    }
public void generateContent()
{

    dbsrc.open();
    habits = dbsrc.findallhabit(this,"1");

    if (habits.size() == 0)
    {
        Toast.makeText(MainActivity.this, "No habits !", Toast.LENGTH_LONG).show();
    }else{
        Toast.makeText(this, "there's a content", Toast.LENGTH_SHORT).show();
    }
    dbsrc.close();
}
    private void showDialog() {
        DataEntryDialog dialog = new DataEntryDialog();
        dialog.show(getSupportFragmentManager(), "DIALOG_FRAGMENT");
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbsrc.close();
    }
    public void onComplete(String time) {
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
        if (time.equals("hi"))
        {
            generateContent();
            lv.setAdapter(new Habit_Adapter(MainActivity.this, R.layout.list_veiw_habit, habits,""));
        }
    }


}
