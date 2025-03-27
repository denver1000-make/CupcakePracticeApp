package com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavor;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.codefestpractice2.databinding.FragmentWhippedCreamFlavorItemBinding;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.ArrayList;
import java.util.List;

public class WhippedCreamFlavorRecyclerViewAdapter extends RecyclerView.Adapter<WhippedCreamFlavorRecyclerViewAdapter.ViewHolder> {

    private final List<WhippedCreamFlavor> mValues = new ArrayList<com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavor>();
    private SimpleLambdaCallback<WhippedCreamFlavor> simpleLambdaCallback;

    public WhippedCreamFlavorRecyclerViewAdapter(SimpleLambdaCallback<WhippedCreamFlavor> simpleLambdaCallback) {
        this.simpleLambdaCallback = simpleLambdaCallback;
        mValues.addAll(WhippedCreamFlavor.getWhippedCreamFlavor());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentWhippedCreamFlavorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        holder.mContentView.setText(mValues.get(position).price + " Pesos");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleLambdaCallback.doThing(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public WhippedCreamFlavor mItem;

        public ViewHolder(FragmentWhippedCreamFlavorItemBinding binding) {
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