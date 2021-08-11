package pl.expense.tracker.expensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder> {

    public static int ALL_OPTIONS_VIEW = 0;
    public static int FAVOURITE_LIST_VIEW = 1;

    private final Context motherContext;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final ArrayList<Expense> expenses;
    private final ExpensesUtilities expensesUtilities;
    private final int viewOption;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ExpenseRecyclerViewAdapter(Context motherContext, ArrayList<Expense> expenses, int viewOption) {
        expensesUtilities = ExpensesUtilities.getInstance(motherContext);
        this.motherContext = motherContext;
        this.expenses = expenses;
        notifyDataSetChanged();
        this.viewOption = viewOption;

        setNotExpendedInAllElement();
        sortExpensesByLastDateFirst();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortExpensesByLastDateFirst() {
        expenses.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    }

    private void setNotExpendedInAllElement(){
        for(Expense expense: expenses){
            expense.setExpended(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        holder.expenseName.setText(expense.getTitle());
        holder.expensePrice.setText(String.valueOf(expense.getPrice()));
        holder.expensePrice.setTextColor(holder.colorOfExpensePrice(expense.isIncome()));
        holder.expenseDate.setText(formatter.format(expense.getDate()));
        holder.expensePlace.setText(expense.getPlace());
        holder.expensePlace.setVisibility(expense.getPlace().equals("") ? View.GONE : View.VISIBLE);
        holder.placeText.setVisibility(expense.getPlace().equals("") ? View.GONE : View.VISIBLE);
        holder.expenseDescription.setText(expense.getDescription());
        holder.expenseDescription.setVisibility(expense.getDescription().equals("") ? View.GONE : View.VISIBLE);
        holder.descriptionText.setVisibility(expense.getDescription().equals("") ? View.GONE : View.VISIBLE);
        holder.expandedCardInfo.setVisibility(expense.isExpended() ? View.VISIBLE : View.GONE);

        if(viewOption == FAVOURITE_LIST_VIEW){
            holder.removeButton.setVisibility(View.GONE);
        }

        if(expensesUtilities.isFavourite(expenses.get(position))){
            setButtonImageAndText(holder.addToFavouriteButton, R.attr.favourite_filled_icon, "Remove from Favourite");
        } else {
            setButtonImageAndText(holder.addToFavouriteButton, R.attr.favourite_icon, "Add to Favourite");
        }

    }

    private void setButtonImageAndText(Button button, int imageId, String text){
        TypedValue typedValue = new TypedValue();
        motherContext.getTheme().resolveAttribute(imageId, typedValue, true);

        if(typedValue.resourceId != 0){
            imageId  = typedValue.resourceId;
        } else {
            imageId = typedValue.data;
        }

        button.setCompoundDrawablesRelativeWithIntrinsicBounds(imageId, 0, 0, 0);
        button.setText(text);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final MaterialCardView parent;
        private final TextView expenseName;
        private final TextView expensePrice;
        private final TextView expenseDate;
        private final TextView placeText;
        private final TextView expensePlace;
        private final TextView descriptionText;
        private final TextView expenseDescription;
        private final ImageView arrowButton;
        private final RelativeLayout expandedCardInfo;
        private final Button removeButton;
        private final Button addToFavouriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            expenseName = itemView.findViewById(R.id.expenseName);
            expensePrice = itemView.findViewById(R.id.expensePrice);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expensePlace = itemView.findViewById(R.id.expensePlace);
            placeText = itemView.findViewById(R.id.placeText);
            expenseDescription = itemView.findViewById(R.id.expenseDescription);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            arrowButton = itemView.findViewById(R.id.expendViewArrow);
            expandedCardInfo = itemView.findViewById(R.id.expandedCard);
            removeButton = itemView.findViewById(R.id.removeButton);
            addToFavouriteButton = itemView.findViewById(R.id.addToFavouriteButton);

            arrowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Expense expense = expenses.get(getAdapterPosition());
                    expense.setExpended(!expense.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                    materialAlertDialogBuilder.setMessage("Do want to remove this element?");
                    materialAlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            expensesUtilities.removeFromFavourites(expenses.get(getAdapterPosition()));
                            expensesUtilities.removeElement(expenses.get(getAdapterPosition()));
                            expenses.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    materialAlertDialogBuilder.show();
                }
            });

            addToFavouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Expense expense = expenses.get(getAdapterPosition());

                    if(expensesUtilities.isFavourite(expense)){
                        if(viewOption == FAVOURITE_LIST_VIEW){
                            expenses.remove(expense);
                        }

                        expensesUtilities.removeFromFavourites(expense);
                        showSnackBar("Item is removed from favourites");
                    } else {
                        if(viewOption == FAVOURITE_LIST_VIEW){
                            expenses.add(expense);
                        }

                        expensesUtilities.addToFavourites(expense);
                        showSnackBar("Item is added to favourites");
                    }

                    notifyDataSetChanged();
                }
            });
        }

        private void showSnackBar(String message){
            Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
        }

        private int colorOfExpensePrice(boolean isIncome){
            return parent.getResources().getColor(isIncome? R.color.incomeColor : R.color.expenseColor);
        }
    }
}
