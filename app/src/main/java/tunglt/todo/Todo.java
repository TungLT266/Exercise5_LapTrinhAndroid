package tunglt.todo;

import io.realm.RealmObject;

public class Todo extends RealmObject {
    public String name;
    public int Date;
    public int Month;
    public int Year;
    public int Priority;

    public Todo() {}

//    public Todo(String name,int date,int month,int year,int priority) {
//        this.name=name;
//        this.Date=date;
//        this.Month=month;
//        this.Year=year;
//        this.Priority=priority;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(int date) {
        Date = date;
    }

    public int getDate() {
        return Date;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getMonth() {
        return Month;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getYear() {
        return Year;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getPriority() {
        return Priority;
    }
}
