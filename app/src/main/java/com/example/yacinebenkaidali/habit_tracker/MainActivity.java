package com.example.yacinebenkaidali.habit_tracker;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.sample.MainActivitychart;
import com.anychart.sample.charts.ColumnChartActivity;
import com.anychart.sample.charts.LineChartActivity;
import com.example.anis.widget_habbit.CollectionWidget;
import com.example.anis.widget_habbit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.Habit_Adapter;
import DB.Datasrc;
import Donnes.Habit;
import Donnes.Time;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.view.Menu.NONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DataEntryDialog.OnCompleteListener
        {
    ListView lv;
    Habit h;
    public Habit_Adapter adapter;
    Datasrc dbsrc;
    public List<Habit> habits = new ArrayList<>();
    int CONTEXT_MENU_EDIT = 1;
    int CONTEXT_MENU_DELETE = 2;
    String desc;
    public boolean CATEGORY;
    public String REGULARITY;
    public String HABIT_TYPE;
    int count, code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateContent();
        lv = (ListView) findViewById(R.id.list_view_habits);

        if ((lv != null) || (habits.size() != 0)) {
            adapter = new Habit_Adapter(MainActivity.this, R.layout.list_view_habit1, habits, "");
            lv.setAdapter(adapter);
            registerForContextMenu(lv);
        }
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                h = (Habit) lv.getItemAtPosition(position);
                desc = h.DESCRIPTION;
                CATEGORY = h.CATEGORY;
                REGULARITY = h.REGULARITY;
                HABIT_TYPE = h.HABIT_TYPE;
                return false;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, " from lv", Toast.LENGTH_SHORT).show();
                ImageButton addButton = view.findViewById(R.id.addCount);
                final TextView countt = view.findViewById(R.id.count);
                Habit h1 = (Habit) lv.getItemAtPosition(position);
                code = h1.CODE_HABIT;
                count = h1.Count;
                Time t = new Time();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = Calendar.getInstance().getTime();
                final String datenow = df.format(date);
                t.DATE_HABIT = java.sql.Date.valueOf(datenow);
                Intent in = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(in);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if(v.getId()==R.id.list_view_habits){
                    AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) menuInfo ;
                    menu.setHeaderTitle("Manage") ;
                    menu.add(Menu.NONE,1,0,"EDIT") ;
                    menu.add(Menu.NONE,2,1,"DELETE") ;

                }

                super.onCreateContextMenu(menu, v, menuInfo);
            }

            @Override
            public boolean onContextItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();
                if (id == CONTEXT_MENU_EDIT) {
                    DataEntryDialog dialog = new DataEntryDialog();

                    Bundle bundle = new Bundle();
                    bundle.putString("desc", desc);
                    bundle.putBoolean("cat", CATEGORY);
                    bundle.putString("reg", REGULARITY);
                    bundle.putString("type", HABIT_TYPE);
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "DialogFragmentWithSetter");

                } else if (id == CONTEXT_MENU_DELETE) {
                    dbsrc.open();
                    dbsrc.deleteHabit(h);
                    dbsrc.close();
                    generateContent();
                    lv.setAdapter(new Habit_Adapter(MainActivity.this, R.layout.list_view_habit1, habits, ""));
// update widget
                    Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
                    intent.setComponent(new ComponentName(getApplicationContext(), CollectionWidget.class));
                    getApplicationContext().sendBroadcast(intent);

                }

                return super.onContextItemSelected(item);
            }

            @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == CONTEXT_MENU_EDIT) {
            DataEntryDialog dialog = new DataEntryDialog();

            Bundle bundle = new Bundle();
            bundle.putString("desc", desc);
            bundle.putBoolean("cat", CATEGORY);
            bundle.putString("reg", REGULARITY);
            bundle.putString("type", HABIT_TYPE);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "DialogFragmentWithSetter");

        } else if (id == CONTEXT_MENU_DELETE) {
            dbsrc.open();
            dbsrc.deleteHabit(h);
            dbsrc.close();
            generateContent();
            lv.setAdapter(new Habit_Adapter(MainActivity.this, R.layout.list_view_habit1, habits, ""));
// update widget
            Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
            intent.setComponent(new ComponentName(getApplicationContext(), CollectionWidget.class));
            getApplicationContext().sendBroadcast(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.current) {
            // Handle the camera action

        } else if (id == R.id.stat) {
            Intent in=new Intent(MainActivity.this,ColumnChartActivity.class);
            startActivity(in);
        } else if (id == R.id.friends) {

        } else if (id == R.id.abt) {
            Intent in=new Intent(MainActivity.this,About.class);
            startActivity(in);

        } else if (id == R.id.Stt) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void generateContent() {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        habits = dbsrc.findallhabit(this, "");

        if (habits.size() == 0) {
            Toast.makeText(MainActivity.this, "No habits !", Toast.LENGTH_LONG).show();
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
        if (time.equals("hi")) {
            generateContent();
            lv.setAdapter(new Habit_Adapter(MainActivity.this, R.layout.list_view_habit1, habits, ""));
        }
    }
}
