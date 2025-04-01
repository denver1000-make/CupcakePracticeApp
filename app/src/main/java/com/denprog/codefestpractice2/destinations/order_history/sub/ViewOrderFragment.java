package com.denprog.codefestpractice2.destinations.order_history.sub;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentViewOrderBinding;
import com.denprog.codefestpractice2.util.FileUtil;

public class ViewOrderFragment extends Fragment {

    private ViewOrderViewModel mViewModel;
    private FragmentViewOrderBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentViewOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewOrderViewModel.class);
        ViewOrderFragmentArgs args = ViewOrderFragmentArgs.fromBundle(getArguments());

        if (args.getOrderToView().fillingFlavor == null) {
            binding.fillingFlavorLayout.setVisibility(View.GONE);
        } else {
            binding.fillingFlavorNameView.setText(args.getOrderToView().fillingFlavor);
            binding.fillingFlavorPriceView.setText(args.getOrderToView().fillingFlavorPrice + " Pesos");
        }

        binding.cakeFlavorNameView.setText(args.getOrderToView().selectedCakeFlavor);
        binding.cakeFlavorPrice.setText(args.getOrderToView().cakeFlavorPrice + " Pesos");
        binding.whippedCreamFlavorNameView.setText(args.getOrderToView().selectedWhippedFlavor);
        binding.whippedCreamPriceView.setText(args.getOrderToView().whippedFlavorPrice + " Pesos");
        binding.cakeMessageView.setText(args.getOrderToView().cakeMessage);
        binding.totalPriceView.setText(args.getOrderToView().totalPrice + " Pesos");
        binding.cakeDesign.setImageBitmap(FileUtil.fromPathToBitmap(args.getOrderToView().designPath, requireContext()));
    }

}