package pl.expense.tracker.expensetracker;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesUtilities {
    private ArrayList<Expense> expenses;

    public ExpensesUtilities() {
        initArray();
    }

    private void initArray(){
        expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis", new Date(), false));
        expenses.add(new Expense(-30, "Zakupy w Lidlu", "Opis", new Date(), false));
        expenses.add(new Expense(-100, "Kabel HDMI", "Opis", new Date(), false));
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public float sumAllElements(){
        float allSumOfMoney = 0;

        for(Expense expense: expenses){
            allSumOfMoney += expense.getPrice();
        }

        return allSumOfMoney;
    }
}
