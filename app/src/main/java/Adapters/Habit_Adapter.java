package Adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anis.widget_habbit.CollectionWidget;
import com.example.anis.widget_habbit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DB.Datasrc;
import Donnes.Habit;



public class Habit_Adapter extends ArrayAdapter<Habit>
{
    public ArrayList<Habit> habits;Datasrc dbsrc;Habit habit;
    Context context;ViewHolder viewHolder = null;
     int nbfois [];private int layout;


    public Habit_Adapter(Context context, int resource, List<Habit> objects,String CODE_USER)
    {
        super(context, resource, objects);
        habits=new ArrayList<>(objects);
        this.context =context;
        layout = resource;
        Log.d("content", String.valueOf(habits.size()));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder mainViewholder = null;

       if(convertView==null){
           LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_view_habit1,parent,false);

          viewHolder = new ViewHolder();
           viewHolder.Description =  convertView.findViewById(R.id.description);
            viewHolder.Regularity =  convertView.findViewById(R.id.regularity);
            viewHolder.Count =  convertView.findViewById(R.id.count);
            viewHolder.button = convertView.findViewById(R.id.addCount);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        habit=habits.get(position);
        viewHolder.Description.setText(habit.DESCRIPTION);
        viewHolder.Regularity.setText(habit.REGULARITY);
        viewHolder.Count.setText(String.valueOf(habit.Count));


        mainViewholder = (ViewHolder) convertView.getTag();

        viewHolder.button.setTag(position);
       viewHolder.Count.setTag(position);
        final ViewHolder finalMainViewholder = mainViewholder;
       mainViewholder.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Habit h =habits.get(position);
                int i=0;h.Count++;
                finalMainViewholder.Count.setText(String.valueOf(h.Count));
                SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                Date date = Calendar.getInstance().getTime();
                final String datenow=df.format(date);
                Datasrc db = new Datasrc(getContext()) ;
                db.open();
                db.updateTime(h.CODE_HABIT,h.Count,datenow);
                db.close();
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.setComponent(new ComponentName(context, CollectionWidget.class));
                context.sendBroadcast(intent);
           }
        });


       return convertView;

    }


//    @Override
//    public int getItemViewType(int position) {
//       return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return getCount();
//    }



    private class ViewHolder
    {
        TextView Description;
        TextView Regularity;
        TextView Count;
        ImageButton button;
    }
}
