package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yacinebenkaidali.habit_tracker.R;

import java.util.ArrayList;
import java.util.List;

import Donnes.Habit;



public class Habit_Adapter extends ArrayAdapter<Habit>
{
    public ArrayList<Habit> habits;
    Context context;


    public Habit_Adapter(Context context, int resource, List<Habit> objects,String CODE_USER)
    {
        super(context, resource, objects);
        habits=new ArrayList<>(objects);
        Log.d("content", String.valueOf(habits.size()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_veiw_habit,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.Description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.Regularity = (TextView) convertView.findViewById(R.id.regularity);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final Habit habit=habits.get(position);
        viewHolder.Description.setText(habit.DESCRIPTION);
        viewHolder.Regularity.setText(habit.REGULARITY);


        return convertView;

    }

    @Override
    public int getCount() {
        if(habits == null)
            return 0;
        return habits.size();
    }

    @Override
    public Habit getItem(int i) {
        return habits.get(i);
    }

    private class ViewHolder
    {
        TextView Description;
        TextView Regularity;
    }
}
