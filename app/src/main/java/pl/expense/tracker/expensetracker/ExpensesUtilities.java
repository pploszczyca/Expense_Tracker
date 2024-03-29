package pl.expense.tracker.expensetracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesUtilities {
    private static ExpensesUtilities instance;
    private final ExpensesService expensesService;

    private ExpensesUtilities(Context context){
        expensesService = new ExpensesService(context);

        if(null == getExpenses()){
            initExpensesArray();
        }

        if(null == getFavouriteExpenses()){
            expensesService.saveArrayByKey(new ArrayList<Expense>() , ExpensesService.FAVOURITE_EXPENSES_KEY);
        }
    }

    private void initExpensesArray() {
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis", new Date(), false, "Wadowice", 0));
        expenses.add(new Expense(-30, "Zakupy w Lidlu", "Opis", new Date(), false, "Wadowice", 1));
        expenses.add(new Expense(-100, "Kabel HDMI", "Opis", new Date(), false, "Kraków", 2));
        expenses.add(new Expense(500, "Na życie", "Opis", new Date(), true, "", 3));

        expensesService.saveArrayByKey(expenses, ExpensesService.ALL_EXPENSES_KEY);
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

    public void addElement(Expense expense) {
        expensesService.addToArray(expense, ExpensesService.ALL_EXPENSES_KEY);
    }

    public void removeElement(Expense expense){
        expensesService.removeFromArray(expense, ExpensesService.ALL_EXPENSES_KEY);
    }

    public ArrayList<Expense> getExpenses() {
        return expensesService.getArrayByKey(ExpensesService.ALL_EXPENSES_KEY);
    }

    private List<String> getExpensesArrayElements(IGetExpenseStringElement getExpensesArrayInterface) {
        List<String> expensesTitles = new ArrayList<>();
        for(Expense expense: getExpenses()) {
            if(!expensesTitles.contains(getExpensesArrayInterface.getStringElement(expense)) && !getExpensesArrayInterface.getStringElement(expense).equals("")){
                expensesTitles.add(getExpensesArrayInterface.getStringElement(expense));
            }
        }

        return expensesTitles;
    }

    public List<String> getExpensesTitles() {
        return getExpensesArrayElements(Expense::getTitle);
    }

    public List<String> getExpensesPlaces() {
        return getExpensesArrayElements(Expense::getPlace);
    }

    public ArrayList<Expense> getFavouriteExpenses() {
        return expensesService.getArrayByKey(ExpensesService.FAVOURITE_EXPENSES_KEY);
    }

    public void addToFavourites(Expense expense){
        expensesService.addToArray(expense, ExpensesService.FAVOURITE_EXPENSES_KEY);
    }

    public void removeFromFavourites(Expense expense){
        expensesService.removeFromArray(expense, ExpensesService.FAVOURITE_EXPENSES_KEY);
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
            maxId = Math.max(expense.getId(), maxId);
        }

        return maxId;
    }

    public int getNewItemId(){
        return findMaxId()+1;
    }
}
