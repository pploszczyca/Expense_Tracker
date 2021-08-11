package pl.expense.tracker.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

public class FavouriteList extends AppCompatActivity {

    private RecyclerView favouriteRecyclerView;
    private ExpensesUtilities expensesUtilities;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        expensesUtilities = ExpensesUtilities.getInstance(this);

        favouriteRecyclerView = findViewById(R.id.favouriteRecyclerView);
        ExpenseRecyclerViewAdapter adapter = new ExpenseRecyclerViewAdapter(this, expensesUtilities.getFavouriteExpenses(), ExpenseRecyclerViewAdapter.FAVOURITE_LIST_VIEW);

        favouriteRecyclerView.setAdapter(adapter);
        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home: {
                goToMainActivity();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToMainActivity(){
        Intent intent = new Intent(FavouriteList.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        FavouriteList.this.startActivity(intent);
    }
}