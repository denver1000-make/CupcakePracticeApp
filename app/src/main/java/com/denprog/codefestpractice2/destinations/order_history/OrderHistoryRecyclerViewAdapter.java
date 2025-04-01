package com.denprog.codefestpractice2.destinations.order_history;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.destinations.order_history.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.codefestpractice2.databinding.FragmentHistoryItemBinding;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<CheckOutObj> mValues = new ArrayList<>();
    private SimpleLambdaCallback<CheckOutObj> onViewOrder;

    public OrderHistoryRecyclerViewAdapter(SimpleLambdaCallback<CheckOutObj> onViewOrder) {
        this.onViewOrder = onViewOrder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void refreshAdapter(List<CheckOutObj> checkOutObjList) {
        mValues.clear();
        mValues.addAll(checkOutObjList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.binding.imageView4.setImageBitmap(FileUtil.fromPathToBitmap(holder.mItem.designPath, holder.binding.getRoot().getContext()));
        holder.binding.cakeMessageDisplayCard.setText(holder.mItem.cakeMessage);
        holder.binding.totalPriceDisplayCard.setText("Total Price: " + holder.mItem.totalPrice + " Pesos");
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewOrder.doThing(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentHistoryItemBinding binding;
        public CheckOutObj mItem;

        public ViewHolder(FragmentHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.checkOutEntryId + "'";
        }
    }
}