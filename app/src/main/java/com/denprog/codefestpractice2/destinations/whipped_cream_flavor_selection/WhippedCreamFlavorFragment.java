package com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection;

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
import com.denprog.codefestpractice2.databinding.FragmentWhippedCreamFlavorItemBinding;
import com.denprog.codefestpractice2.databinding.FragmentWhippedCreamFlavorListBinding;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavor;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.placeholder.PlaceholderContent;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.HashMap;

public class WhippedCreamFlavorFragment extends Fragment {
    public static final String SELECTION_KEY = "whipped_flavor";
    private HomeActivityViewModel homeActivityViewModel;
    private FragmentWhippedCreamFlavorListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWhippedCreamFlavorListBinding.inflate(inflater, container, false);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.list.setAdapter(new WhippedCreamFlavorRecyclerViewAdapter(new SimpleLambdaCallback<WhippedCreamFlavor>() {
            @Override
            public void doThing(WhippedCreamFlavor data) {
                HashMap<String, SelectionBase> hashMap = homeActivityViewModel.selectionsPrice.getValue();
                hashMap.put(SELECTION_KEY, data);
                homeActivityViewModel.selectionsPrice.postValue(hashMap);
                navController.navigate(R.id.filledOrPlainFragment);
            }
        }));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
    }
}