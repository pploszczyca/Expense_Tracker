package pl.expense.tracker.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ExpenseRecyclerViewAdapter adapter;
    private RecyclerView expensesRecycleView;
    private TextView allSumInWallet;
    private FloatingActionButton addButton;
    private ExpensesUtilities expensesUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expensesUtilities = ExpensesUtilities.getInstance(this);

        expensesRecycleView = findViewById(R.id.expensesRecyclerView);
        allSumInWallet = findViewById(R.id.allSumInWallet);
        addButton = findViewById(R.id.addButton);
        adapter = new ExpenseRecyclerViewAdapter(this, expensesUtilities.getExpenses(), ExpenseRecyclerViewAdapter.ALL_OPTIONS_VIEW);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favouriteListButton:{
                Intent intent = new Intent(MainActivity.this, FavouriteList.class);
                MainActivity.this.startActivity(intent);

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}