package pl.expense.tracker.expensetracker;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesUtilities {
    private static ArrayList<Expense> expenses = initArray();
    
    private static ArrayList<Expense> initArray(){
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis", new Date(), false, "Wadowice"));
        expenses.add(new Expense(-30, "Zakupy w Lidlu", "Opis", new Date(), false, "Wadowice"));
        expenses.add(new Expense(-100, "Kabel HDMI", "Opis", new Date(), false, "Kraków"));
        expenses.add(new Expense(500, "Na życie", "Opis", new Date(), true, ""));

        return expenses;
    }

    public static ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public static float sumAllElements(){
        float allSumOfMoney = 0;

        for(Expense expense: expenses){
            allSumOfMoney += expense.getPrice();
        }

        return allSumOfMoney;
    }

    public static void addElement(Expense expense){
        expenses.add(expense);
    }

    public static void removeElement(int position){
        expenses.remove(position);
    }

    public static Expense getElement(int position){
        return expenses.get(position);
    }

    public static int getSize(){
        return expenses.size();
    }
}
