package pl.expense.tracker.expensetracker;

import java.util.ArrayList;
import java.util.Date;

public class ExpensesUtilities {
    private static ArrayList<Expense> expenses = initExpensesArray();
    private static ArrayList<Expense> favouriteExpenses = new ArrayList<>();

    private static ArrayList<Expense> initExpensesArray() {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis", new Date(), false, "Wadowice", 0));
        expenses.add(new Expense(-30, "Zakupy w Lidlu", "Opis", new Date(), false, "Wadowice", 1));
        expenses.add(new Expense(-100, "Kabel HDMI", "Opis", new Date(), false, "Kraków", 2));
        expenses.add(new Expense(500, "Na życie", "Opis", new Date(), true, "", 3));

        return expenses;
    }

    public static float sumAllElements() {
        float allSumOfMoney = 0;

        for (Expense expense : expenses) {
            allSumOfMoney += expense.getPrice();
        }

        return allSumOfMoney;
    }

    public static void addElement(Expense expense) {
        expenses.add(expense);
    }

    public static ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public static ArrayList<Expense> getFavouriteExpenses() {
        return favouriteExpenses;
    }

    public static void addToFavourites(Expense expense){
        favouriteExpenses.add(expense);
    }

    public static void removeFromFavourites(Expense expense){
        favouriteExpenses.remove(expense);
    }

    public static boolean isFavourite(Expense searchingExpense){
        for(Expense expense: favouriteExpenses){
            if(searchingExpense.equals(expense)) return true;
        }

        return false;
    }

    private static int findMaxId(){
        int maxId = -1;

        for(Expense expense: expenses){
            maxId = expense.getId() > maxId ? expense.getId(): maxId;
        }

        return maxId;
    }

    public static int getNewItemId(){
        return findMaxId()+1;
    }
}
