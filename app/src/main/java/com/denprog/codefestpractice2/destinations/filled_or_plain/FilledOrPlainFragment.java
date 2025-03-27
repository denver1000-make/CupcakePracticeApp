package com.denprog.codefestpractice2.destinations.filled_or_plain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentFilledOrPlainBinding;

public class FilledOrPlainFragment extends Fragment {

    FragmentFilledOrPlainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilledOrPlainBinding.inflate(inflater, container, false);
        binding.filledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                navController.navigate(R.id.fillingFlavorFragment);
            }
        });
        binding.plainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                navController.navigate(R.id.designPickerFragment);
            }
        });
        return binding.getRoot();
    }


}