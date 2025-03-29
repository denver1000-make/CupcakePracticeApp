package com.denprog.codefestpractice2.destinations.design;

import android.graphics.Bitmap;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDesignPickerBinding.inflate(inflater, container, false);
        this.adapter = new PremadeImagesRecyclerViewAdapter(new SimpleLambdaCallback<PreMadeDesigns>() {
            @Override
            public void doThing(PreMadeDesigns data) {

            }
        });
        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
            }
        });
        binding.proceedWithCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap selectedCustomImage = viewModel.bitmapMutableLiveData.getValue();
                if (selectedCustomImage != null) {
                    HashMap<String, SelectionBase> selectedMaps = homeActivityViewModel.selectionsPrice.getValue();
                    selectedMaps.put(DESIGN_SELECTION_KEY, new Design("Custom Img", 1000, selectedCustomImage));
                    homeActivityViewModel.selectionsPrice.setValue(selectedMaps);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                    navController.navigate(DesignPickerFragmentDirections.actionDesignPickerFragmentToPersonalDetailsFragment());
                } else {
                    Toast.makeText(requireContext(), "No Selected Custom Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(DesigPickerFragmentViewModel.class);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        this.viewModel.bitmapMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.imageButton.setImageBitmap(bitmap);
            }
        });
    }
}