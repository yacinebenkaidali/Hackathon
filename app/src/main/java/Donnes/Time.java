package Donnes;

import java.util.Date;

public class Time {
    public int CODE_TIME;
    public int CODE_Habit;
    public int COUNT_NUM;
    //public Date DATE_HABIT;

    public Time() {
    }

    public Time( int CODE_Habit, int COUNT_NUM) {
        this.CODE_Habit = CODE_Habit;
        this.COUNT_NUM = COUNT_NUM;
    }

    public Time(int CODE_TIME, int COUNT_NUM, Date DATE_HABIT) {
        this.CODE_TIME = CODE_TIME;
        this.COUNT_NUM = COUNT_NUM;
        //this.DATE_HABIT = DATE_HABIT;
    }
}
