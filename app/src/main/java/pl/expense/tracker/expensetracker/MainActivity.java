package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ExpenseRecyclerViewAdapter adapter;
    private RecyclerView expensesRecycleView;
    private ExpensesUtilities expensesUtilities;
    private TextView allSumInWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesUtilities = new ExpensesUtilities();
        expensesRecycleView = findViewById(R.id.expensesRecyclerView);
        allSumInWallet = findViewById(R.id.allSumInWallet);
        adapter = new ExpenseRecyclerViewAdapter(this, expensesUtilities);

        expensesRecycleView.setAdapter(adapter);
        expensesRecycleView.setLayoutManager(new LinearLayoutManager(this));

        allSumInWallet.setText(String.valueOf(expensesUtilities.sumAllElements()));

    }
}