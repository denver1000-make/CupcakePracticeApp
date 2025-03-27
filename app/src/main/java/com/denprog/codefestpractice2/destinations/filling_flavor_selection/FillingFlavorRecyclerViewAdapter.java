package com.denprog.codefestpractice2.destinations.filling_flavor_selection;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.codefestpractice2.destinations.filling_flavor_selection.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.codefestpractice2.databinding.FragmentFillingFlavorBinding;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FillingFlavorRecyclerViewAdapter extends RecyclerView.Adapter<FillingFlavorRecyclerViewAdapter.ViewHolder> {

    private final List<FillingFlavor> mValues = new ArrayList<>();
    private SimpleLambdaCallback<FillingFlavor> simpleLambdaCallback;

    public FillingFlavorRecyclerViewAdapter(SimpleLambdaCallback<FillingFlavor> simpleLambdaCallback) {
        this.simpleLambdaCallback = simpleLambdaCallback;
        mValues.addAll(FillingFlavor.getAllFillingsFlavors());
    }

    public void refreshList(List<FillingFlavor> mValues) {
        this.mValues.clear();
        this.mValues.addAll(mValues);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentFillingFlavorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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
        public FillingFlavor mItem;

        public ViewHolder(FragmentFillingFlavorBinding binding) {
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