package com.denprog.codefestpractice2.destinations.order_history;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.destinations.order_history.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.codefestpractice2.databinding.FragmentOrderHistoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.ViewHolder> {

    private List<CheckOutObj> mValues = new ArrayList<>();

    public void refreshAdapter(List<CheckOutObj> checkOutObjList) {
        this.mValues.clear();
        this.mValues.addAll(checkOutObjList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOrderHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).selectedCakeFlavor);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public CheckOutObj mItem;

        public ViewHolder(FragmentOrderHistoryBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}