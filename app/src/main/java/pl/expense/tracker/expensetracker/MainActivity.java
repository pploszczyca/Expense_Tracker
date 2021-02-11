package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ExpenseRecyclerViewAdapter adapter;
    private RecyclerView expensesRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesRecycleView = findViewById(R.id.expensesRecyclerView);
        adapter = new ExpenseRecyclerViewAdapter(this);

        expensesRecycleView.setAdapter(adapter);
        expensesRecycleView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(-50, "Zakupy w Biedronce", "Opis"));
        expenses.add(new Expense(-50, "Zakupy w Lidlu", "Opis"));

        adapter.setExpenses(expenses);
    }
}