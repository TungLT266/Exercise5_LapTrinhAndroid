package tunglt.todo;

import io.realm.RealmObject;

public class TodoList extends RealmObject {
    public String name;
    public int Date;
    public int Month;
    public int Year;
    public int Priority;
    public TodoList(){}

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
