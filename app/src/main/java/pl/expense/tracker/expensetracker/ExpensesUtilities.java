package pl.expense.tracker.expensetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class ExpensesUtilities {
    private static ExpensesUtilities instance;

    private SharedPreferences sharedPreferences;
    private static final String ALL_EXPENSES_KEY = "all_expenses";
    private static final String FAVOURITE_EXPENSES_KEY = "favourite_expenses";
    private static Gson gson;
    private static Type type;

    private ExpensesUtilities(Context context){
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        gson = new Gson();
        type = new TypeToken<ArrayList<Expense>>(){}.getType();

        if(null == getExpenses()){
            initExpensesArray();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(null == getFavouriteExpenses()){
            editor.putString(FAVOURITE_EXPENSES_KEY, gson.toJson(new ArrayList<Expense>()));
            editor.commit();
        }

    }

    private void initExpensesArray() {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis", new Date(), false, "Wadowice", 0));
        expenses.add(new Expense(-30, "Zakupy w Lidlu", "Opis", new Date(), false, "Wadowice", 1));
        expenses.add(new Expense(-100, "Kabel HDMI", "Opis", new Date(), false, "Kraków", 2));
        expenses.add(new Expense(500, "Na życie", "Opis", new Date(), true, "", 3));

        saveToArrayByKey(ALL_EXPENSES_KEY, expenses);
    }

    public static ExpensesUtilities getInstance(Context context){
        if(instance == null){
            instance = new ExpensesUtilities(context);
        }

        return instance;
    }

    public float sumAllElements() {
        ArrayList<Expense> expenses = getExpenses();

        float allSumOfMoney = 0;

        for (Expense expense : expenses) {
            allSumOfMoney += expense.getPrice();
        }

        return allSumOfMoney;
    }

    private void saveToArrayByKey(String key, ArrayList<Expense> expenses){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(expenses));
        editor.commit();
    }

    public void addElement(Expense expense) {
        ArrayList<Expense> expenses = getExpenses();
        expenses.add(expense);
        saveToArrayByKey(ALL_EXPENSES_KEY, expenses);
    }

    public void removeElement(Expense expense){
        ArrayList<Expense> expenses = getExpenses();
        expenses.remove(expense);
        saveToArrayByKey(ALL_EXPENSES_KEY, expenses);
    }

    private ArrayList<Expense> getArrayByKey(String key){
        return gson.fromJson(sharedPreferences.getString(key, null), type);
    }

    public ArrayList<Expense> getExpenses() {
        return getArrayByKey(ALL_EXPENSES_KEY);
    }

    public ArrayList<Expense> getFavouriteExpenses() {
        return getArrayByKey(FAVOURITE_EXPENSES_KEY);
    }

    public void addToFavourites(Expense expense){
        ArrayList<Expense> favouriteExpenses = getFavouriteExpenses();
        favouriteExpenses.add(expense);
        saveToArrayByKey(FAVOURITE_EXPENSES_KEY, favouriteExpenses);
    }

    public void removeFromFavourites(Expense expense){
        ArrayList<Expense> favouriteExpenses = getFavouriteExpenses();
        favouriteExpenses.remove(expense);
        saveToArrayByKey(FAVOURITE_EXPENSES_KEY, favouriteExpenses);
    }

    public boolean isFavourite(Expense searchingExpense){
        ArrayList<Expense> favouriteExpenses = getFavouriteExpenses();

        for(Expense expense: favouriteExpenses){
            if(searchingExpense.equals(expense)) return true;
        }

        return false;
    }

    private int findMaxId(){
        ArrayList<Expense> expenses = getExpenses();
        int maxId = -1;

        for(Expense expense: expenses){
            maxId = expense.getId() > maxId ? expense.getId(): maxId;
        }

        return maxId;
    }

    public int getNewItemId(){
        return findMaxId()+1;
    }
}
