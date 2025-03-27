package com.denprog.codefestpractice2.destinations.cake_flavor_selection;

import android.content.Context;
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
import com.denprog.codefestpractice2.databinding.FragmentCakeFlavorItemBinding;
import com.denprog.codefestpractice2.databinding.FragmentCakeFlavorListBinding;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj.CakeFlavor;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.placeholder.PlaceholderContent;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.HashMap;
public class CakeFlavorFragment extends Fragment {
    public static final String SELECTION_KEY = "cake_flavor";
    FragmentCakeFlavorListBinding binding;
    HomeActivityViewModel homeActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCakeFlavorListBinding.inflate(inflater, container, false);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
        binding.list.setAdapter(new CakeFlavorRecyclerViewAdapter(new SimpleLambdaCallback<CakeFlavor>() {
            @Override
            public void doThing(CakeFlavor data) {
                HashMap<String, SelectionBase> hashMap = homeActivityViewModel.selectionsPrice.getValue();
                if (hashMap == null) {
                    hashMap = new HashMap<>();
                }
                hashMap.put(SELECTION_KEY, data);
                homeActivityViewModel.selectionsPrice.setValue(hashMap);
                navController.navigate(R.id.whippedCreamFlavorFragment);
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