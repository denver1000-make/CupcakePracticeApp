package com.denprog.codefestpractice2.destinations.checkout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.base.SelectionBase;
import com.denprog.codefestpractice2.databinding.FragmentCheckOutBinding;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.CakeFlavorFragment;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj.CakeFlavor;
import com.denprog.codefestpractice2.destinations.design.Design;
import com.denprog.codefestpractice2.destinations.design.DesignPickerFragment;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavor;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavorFragment;
import com.denprog.codefestpractice2.destinations.personal_details.PersonalDetails;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.WhippedCreamFlavorFragment;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavor;
import com.denprog.codefestpractice2.room.entity.User;

import java.util.HashMap;

public class CheckOutFragment extends Fragment {

    private CheckOutViewModel mViewModel;
    private FragmentCheckOutBinding binding;
    private HomeActivityViewModel viewModel;

    public static CheckOutFragment newInstance() {
        return new CheckOutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CheckOutViewModel.class);
        this.viewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        this.viewModel.personalDetailsMutableLiveData.observe(getViewLifecycleOwner(), new Observer<PersonalDetails>() {
            @Override
            public void onChanged(PersonalDetails personalDetails) {
                binding.firstNameDisplay.setText(personalDetails.firstName);
                binding.middleNameDisplay.setText(personalDetails.middleName);
                binding.lastNameDisplay.setText(personalDetails.lastName);
            }
        });

        this.viewModel.selectionsPrice.observe(getViewLifecycleOwner(), new Observer<HashMap<String, SelectionBase>>() {
            @Override
            public void onChanged(HashMap<String, SelectionBase> stringSelectionBaseHashMap) {
                loadIntoView();
            }
        });
        loadIntoView();
    }

    public void loadIntoView() {
        HashMap<String, SelectionBase> selectionsMap = this.viewModel.selectionsPrice.getValue();
        SelectionBase cakeFlavor =  selectionsMap.get(CakeFlavorFragment.SELECTION_KEY);
        SelectionBase whippedCreamFlavor = selectionsMap.get(WhippedCreamFlavorFragment.SELECTION_KEY);
        SelectionBase fillingFlavor = selectionsMap.get(FillingFlavorFragment.FILLING_FLAVOR_SELECTION_KEY);
        if (cakeFlavor instanceof CakeFlavor) {
            binding.cakeFlavorNameDisplay.setText(cakeFlavor.name);
            binding.cakeFlavorPriceDisplay.setText(cakeFlavor.price + " Pesos");
        } else if (whippedCreamFlavor instanceof WhippedCreamFlavor) {
            binding.whippedCreamFlavorNameDisplay.setText(whippedCreamFlavor.name);
            binding.whippedCreamPrice.setText(whippedCreamFlavor.price + " Pesos");
        }

        if (fillingFlavor != null) {
            binding.setHasFilling(true);
            if (fillingFlavor instanceof FillingFlavor) {
                binding.fillingFlavorNameDisplay.setText(fillingFlavor.name);
                binding.fillingFlavorPriceDisplay.setText(fillingFlavor.price + " Pesos");
            }
        } else {
             binding.setHasFilling(false);
        }


        SelectionBase designSelection = selectionsMap.get(DesignPickerFragment.DESIGN_SELECTION_KEY);
        if (designSelection instanceof Design) {
            Design design = (Design) designSelection;
            binding.cakeDesignImage.setImageBitmap(design.bitmap);
            binding.cakeDesignNameDisplay.setText(design.name);
            binding.designPriceDisplay.setText(design.price + " Pesos");
        }

        float total = 0f;

        if (selectionsMap != null) {
            for (SelectionBase value : selectionsMap.values()) {
                total += value.price;
            }
        }

        CheckOutFragmentArgs args = CheckOutFragmentArgs.fromBundle(getArguments());
        binding.firstNameDisplay.setText(args.getFirstName());
        binding.middleNameDisplay.setText(args.getMiddleName());
        binding.lastNameDisplay.setText(args.getLastName());

        binding.totalPriceDisplay.setText(total + " Pesos");
    }
}