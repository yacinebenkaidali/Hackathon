package Donnes;

public class Habit {
    public int  CODE_HABIT ;
    public String DESCRIPTION;
    public boolean CATEGORY;
    public String REGULARITY;
    public String HABIT_TYPE;
    public int CODE_USER;
    
    public Habit() {
    }

    public Habit( String DESCRIPTION, boolean CATEGORY, String REGULARITY, String HABIT_TYPE,int CODE_USER) {
        this.DESCRIPTION = DESCRIPTION;
        this.CATEGORY = CATEGORY;
        this.REGULARITY = REGULARITY;
        this.HABIT_TYPE = HABIT_TYPE;
        this.CODE_USER = CODE_USER;
    }

}
