package com.denprog.codefestpractice2.destinations.design;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denprog.codefestpractice2.databinding.PremadeDesignItemBinding;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.ArrayList;
import java.util.List;

public class PremadeImagesRecyclerViewAdapter extends RecyclerView.Adapter<PremadeImagesRecyclerViewAdapter.ViewHolder> {

    List<PreMadeDesigns> preMadeDesignsList = new ArrayList<>();

    private SimpleLambdaCallback<PreMadeDesigns> onPressed;

    public PremadeImagesRecyclerViewAdapter(SimpleLambdaCallback<PreMadeDesigns> onPressed) {
        this.onPressed = onPressed;
        this.preMadeDesignsList.addAll(PreMadeDesigns.getPreMadeDesigns());
    }

    public void refreshList(List<PreMadeDesigns> preMadeDesignsList) {
        this.preMadeDesignsList.clear();
        this.preMadeDesignsList.addAll(preMadeDesignsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PremadeDesignItemBinding binding = PremadeDesignItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PreMadeDesigns preMadeDesigns = preMadeDesignsList.get(position);
        holder.binding.imageView3.setImageResource(preMadeDesigns.resId);
        holder.binding.Name.setText(preMadeDesigns.name);
        holder.binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPressed.doThing(preMadeDesigns);
            }
        });
    }

    @Override
    public int getItemCount() {
        return preMadeDesignsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PremadeDesignItemBinding binding;

        public ViewHolder(PremadeDesignItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
