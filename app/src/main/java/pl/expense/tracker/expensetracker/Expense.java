package pl.expense.tracker.expensetracker;

public class Expense {
    private float price;
    private String title;
    private String description;

    public Expense(float price, String title, String description) {
        this.price = price;
        this.title = title;
        this.description = description;
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
}
