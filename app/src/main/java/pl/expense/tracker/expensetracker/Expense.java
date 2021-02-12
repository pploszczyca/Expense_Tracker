package pl.expense.tracker.expensetracker;

import java.util.Date;

public class Expense {
    private float price;
    private String title;
    private String description;
    private String place;
    private Date date;
    private boolean isIncome;
    private boolean isExpended;

    public Expense(float price, String title, String description, Date date, boolean isIncome, String place) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isIncome = isIncome;
        this.place = place;
        isExpended = false;
    }

    public float getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public String getPlace() {
        return place;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean newIsExpended){
        isExpended = newIsExpended;
    }
}
