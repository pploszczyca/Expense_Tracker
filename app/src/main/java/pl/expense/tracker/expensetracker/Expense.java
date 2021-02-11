package pl.expense.tracker.expensetracker;

import java.util.Date;

public class Expense {
    private float price;
    private String title;
    private String description;
    private Date date;
    private boolean isIncome;

    public Expense(float price, String title, String description, Date date, boolean isIncome) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isIncome = isIncome;
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
}
