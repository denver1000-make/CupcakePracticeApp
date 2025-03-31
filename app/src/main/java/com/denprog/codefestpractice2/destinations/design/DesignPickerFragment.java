package com.denprog.codefestpractice2.destinations.design;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.base.SelectionBase;
import com.denprog.codefestpractice2.databinding.FragmentDesignPickerBinding;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavor;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavorV2;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.HashMap;

public class DesignPickerFragment extends Fragment {

    public static final String DESIGN_SELECTION_KEY = "cake_design";

    FragmentDesignPickerBinding binding;
    PremadeImagesRecyclerViewAdapter adapter;
    DesigPickerFragmentViewModel viewModel;
    HomeActivityViewModel homeActivityViewModel;

    ActivityResultLauncher<PickVisualMediaRequest> imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o != null) {
                viewModel.bitmapMutableLiveData.setValue(FileUtil.convertUriToBitmap(o, requireContext()));
            }
        }
    });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDesignPickerBinding.inflate(inflater, container, false);
        this.adapter = new PremadeImagesRecyclerViewAdapter(new SimpleLambdaCallback<PreMadeDesigns>() {
            @Override
            public void doThing(PreMadeDesigns data) {
                DesignPickerFragmentArgs args = DesignPickerFragmentArgs.fromBundle(getArguments());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                DesignPickerFragmentDirections.ActionDesignPickerFragmentToPersonalDetailsFragment directions = DesignPickerFragmentDirections.actionDesignPickerFragmentToPersonalDetailsFragment(data.name, data.price, args.getWhippedCreamFlavorV2(), args.getCakeFlavorV2());
                directions.setResource(data.resId);
                directions.setFillingFlavorV2(args.getFillingFlavorV2());
                navController.navigate(directions);
            }
        });
        binding.imageButton.setOnClickListener(view -> imagePicker.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()));
        binding.proceedWithCustom.setOnClickListener(view -> {
            Bitmap selectedCustomImage = viewModel.bitmapMutableLiveData.getValue();
            if (selectedCustomImage != null) {
                DesignPickerFragmentArgs args = DesignPickerFragmentArgs.fromBundle(getArguments());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                DesignPickerFragmentDirections.ActionDesignPickerFragmentToPersonalDetailsFragment destinations = DesignPickerFragmentDirections.actionDesignPickerFragmentToPersonalDetailsFragment("Custom", 1000f, args.getWhippedCreamFlavorV2(), args.getCakeFlavorV2());
                destinations.setFillingFlavorV2(args.getFillingFlavorV2());
                destinations.setResource(-0);
                navController.navigate(destinations);
            } else {
                Toast.makeText(requireContext(), "No Selected Custom Image", Toast.LENGTH_SHORT).show();
            }
        });
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(DesigPickerFragmentViewModel.class);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        this.viewModel.bitmapMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.imageButton.setImageBitmap(bitmap);
            }
        });
        loadTotal(homeActivityViewModel, DesignPickerFragmentArgs.fromBundle(getArguments()));
    }

    public void loadTotal(HomeActivityViewModel homeActivityViewModel, DesignPickerFragmentArgs args) {
        FillingFlavorV2 fillingFlavorV2 = args.getFillingFlavorV2();
        homeActivityViewModel.totalPrice.setValue(args.getCakeFlavorV2().cakeFlavorPrice + args.getWhippedCreamFlavorV2().flavorPrice + (fillingFlavorV2 == null ? 0f : fillingFlavorV2.fillingFlavorPrice));
    }
}