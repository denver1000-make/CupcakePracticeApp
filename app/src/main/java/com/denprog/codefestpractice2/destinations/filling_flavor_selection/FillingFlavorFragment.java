package com.denprog.codefestpractice2.destinations.filling_flavor_selection;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.base.SelectionBase;
import com.denprog.codefestpractice2.databinding.FragmentFillingFlavorListBinding;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.placeholder.PlaceholderContent;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class FillingFlavorFragment extends Fragment {
    public static final String FILLING_FLAVOR_SELECTION_KEY = "filling_flavor";
    FragmentFillingFlavorListBinding binding;
    FillingFlavorRecyclerViewAdapter adapter;
    HomeActivityViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFillingFlavorListBinding.inflate(inflater, container, false);
        this.adapter = new FillingFlavorRecyclerViewAdapter(new SimpleLambdaCallback<FillingFlavor>() {
            @Override
            public void doThing(FillingFlavor data) {
                FillingFlavorFragmentArgs args = FillingFlavorFragmentArgs.fromBundle(getArguments());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                FillingFlavorFragmentDirections.ActionFillingFlavorFragmentToDesignPickerFragment directions = FillingFlavorFragmentDirections.actionFillingFlavorFragmentToDesignPickerFragment(args.getCakeFlavorV2(), args.getWhippedCreamFlavorV2());
                directions.setFillingFlavorV2(new FillingFlavorV2(data.name, data.price));
                navController.navigate(directions);
            }
        });
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        FillingFlavorFragmentArgs args = FillingFlavorFragmentArgs.fromBundle(getArguments());
        viewModel.totalPrice.setValue(args.getCakeFlavorV2().cakeFlavorPrice + args.getWhippedCreamFlavorV2().flavorPrice);
    }
}