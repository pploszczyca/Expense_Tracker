package pl.expense.tracker.expensetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExpensesService {

    public static final String ALL_EXPENSES_KEY = "all_expenses";
    public static final String FAVOURITE_EXPENSES_KEY = "favourite_expenses";

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Type type;

    public ExpensesService(Context context){
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        gson = new Gson();
        type = new TypeToken<ArrayList<Expense>>(){}.getType();
    }

    public void saveArrayByKey(ArrayList<Expense> expenses, String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(expenses));
        editor.commit();
    }

    public ArrayList<Expense> getArrayByKey(String key){
        return gson.fromJson(sharedPreferences.getString(key, null), type);
    }

    public void addToArray(Expense expense, String key){
        ArrayList<Expense> expenses = getArrayByKey(key);
        expenses.add(expense);
        saveArrayByKey(expenses, key);
    }

    public void removeFromArray(Expense expense, String key){
        ArrayList<Expense> expenses = getArrayByKey(key);
        expenses.remove(expense);
        saveArrayByKey(expenses, key);
    }
}
