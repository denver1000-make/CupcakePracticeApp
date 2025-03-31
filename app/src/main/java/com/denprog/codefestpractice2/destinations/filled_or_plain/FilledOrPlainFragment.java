package com.denprog.codefestpractice2.destinations.filled_or_plain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentFilledOrPlainBinding;
import com.denprog.codefestpractice2.destinations.design.DesignPickerFragmentArgs;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavorV2;

public class FilledOrPlainFragment extends Fragment {

    FragmentFilledOrPlainBinding binding;
    HomeActivityViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilledOrPlainBinding.inflate(inflater, container, false);
        binding.filledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                FilledOrPlainFragmentArgs args = FilledOrPlainFragmentArgs.fromBundle(getArguments());
                FilledOrPlainFragmentDirections.ActionFilledOrPlainFragmentToFillingFlavorFragment directions = FilledOrPlainFragmentDirections.actionFilledOrPlainFragmentToFillingFlavorFragment(args.getCakeFlavorV2(), args.getWhippedCreamFlavor());
                navController.navigate(directions);
            }
        });
        binding.plainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                FilledOrPlainFragmentArgs args = FilledOrPlainFragmentArgs.fromBundle(getArguments());
                FilledOrPlainFragmentDirections.ActionFilledOrPlainFragmentToDesignPickerFragment directions = FilledOrPlainFragmentDirections.actionFilledOrPlainFragmentToDesignPickerFragment(args.getCakeFlavorV2(), args.getWhippedCreamFlavor());
                navController.navigate(directions);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        loadTotal(this.viewModel, FilledOrPlainFragmentArgs.fromBundle(getArguments()));
    }

    public void loadTotal(HomeActivityViewModel homeActivityViewModel, FilledOrPlainFragmentArgs args) {
        homeActivityViewModel.totalPrice.setValue(args.getCakeFlavorV2().cakeFlavorPrice + args.getWhippedCreamFlavor().flavorPrice);
    }


}