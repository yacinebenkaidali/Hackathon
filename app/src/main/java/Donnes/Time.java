package Donnes;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Time {
    public int CODE_TIME;
    public long CODE_Habit;
    public int COUNT_NUM;
    public Date DATE_HABIT;

    public Time() {
    }

    public Time( int CODE_Habit, int COUNT_NUM) {
        this.CODE_Habit = CODE_Habit;
        this.COUNT_NUM = COUNT_NUM;
    }

    public Time( int COUNT_NUM, String DATE_HABIT) {
        this.COUNT_NUM = COUNT_NUM;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

    }
}
