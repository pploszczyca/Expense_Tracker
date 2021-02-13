package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ExpenseRecyclerViewAdapter adapter;
    private RecyclerView expensesRecycleView;
    private ExpensesUtilities expensesUtilities;
    private TextView allSumInWallet;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesUtilities = new ExpensesUtilities();
        expensesRecycleView = findViewById(R.id.expensesRecyclerView);
        allSumInWallet = findViewById(R.id.allSumInWallet);
        addButton = findViewById(R.id.addButton);
        adapter = new ExpenseRecyclerViewAdapter(this, expensesUtilities);

        expensesRecycleView.setAdapter(adapter);
        expensesRecycleView.setLayoutManager(new LinearLayoutManager(this));

        allSumInWallet.setText(String.valueOf(expensesUtilities.sumAllElements()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpense.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}