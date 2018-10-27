package com.example.anis.widget_habbit;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.drawableText.DataSource;

import java.util.ArrayList;


/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider extends Activity implements RemoteViewsService.RemoteViewsFactory {
    Context mContext = null;
    DataSource mDataSource;
    SQLiteDatabase mydb;

    ArrayList<ImageItem> list=new ArrayList<>();


    public WidgetDataProvider(Context context) {
        mContext = context;

    }

    @Override
    public void onCreate(){


    }

    @Override
    public void onDataSetChanged() {
        this.list=syncdata();

    }

    @Override
    public void onDestroy() {
        this.list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews view  = new RemoteViews(mContext.getPackageName(), R.layout.grid_item_layout_widget);
        view.setTextViewText(R.id.NameContact, list.get(position).title);
        view.setImageViewBitmap(R.id.image, list.get(position).image);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(CollectionWidget.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putInt(CollectionWidget.EXTRA_STRING, list.get(position).ID);
        System.out.println("hhhhhhh ID= "+ list.get(position).ID);
        bundle.putInt("Count", list.get(position).nbr);
        fillInIntent.putExtras(bundle);
        view.setOnClickFillInIntent(R.id.linearwidget, fillInIntent);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public ArrayList<ImageItem> syncdata(){

        ArrayList<ImageItem> list =new ArrayList<>();

        try {
            mydb = mContext.openOrCreateDatabase("Habit_Tracker", MODE_PRIVATE, null);

            Cursor c = mydb.rawQuery("SELECT HABIT.CODE_HABIT,DESCRIPTION,COUNT_NUM FROM HABIT inner join TIME_HABIT on TIME_HABIT.CODE_HABIT =HABIT.CODE_HABIT ; ",null);
            int codeIndex = c.getColumnIndex("CODE_HABIT"); //ID

            int descriptionIndex = c.getColumnIndex("DESCRIPTION");
            int CountIndex = c.getColumnIndex("COUNT_NUM");
            c.moveToFirst();
            if (c != null) {
                do {

                        mDataSource = new DataSource(WidgetDataProvider.this, 3, c.getString(CountIndex));
                        final Drawable drawable = mDataSource.mDataSource.getDrawable();
                        list.add(new ImageItem(convertToBitmap(drawable, 500, 500), c.getString(descriptionIndex),c.getInt(codeIndex),c.getInt(CountIndex)));
                    System.out.println("hhhhhhhh "+ c.getString(codeIndex));
                } while (c.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            mydb.close();
        }
        return list;
    }
    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

}