package com.denprog.codefestpractice2.destinations.personal_details;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentPersonalDetailsBinding;

public class PersonalDetailsFragment extends Fragment {

    private PersonalDetailsViewModel mViewModel;
    private FragmentPersonalDetailsBinding binding;
    private HomeActivityViewModel homeActivityViewModel;
    public static PersonalDetailsFragment newInstance() {
        return new PersonalDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPersonalDetailsBinding.inflate(inflater, container, false);
        binding.proceedCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstNameField.getText().toString();
                String lastName = binding.lastNameField.getText().toString();
                String middleName = binding.middleNameField.getText().toString();
                String cakeMessage = binding.cakeMessageField.getText().toString();
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                homeActivityViewModel.personalDetailsMutableLiveData.setValue(new PersonalDetails(firstName, lastName, middleName, cakeMessage));
                navController.navigate(PersonalDetailsFragmentDirections.actionPersonalDetailsFragmentToCheckOutFragment(firstName, middleName, lastName, cakeMessage));

            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String firstName = binding.firstNameField.getText().toString();
                String lastName = binding.lastNameField.getText().toString();
                String middleName = binding.middleNameField.getText().toString();
                String cakeMessage = binding.cakeMessageField.getText().toString();
                mViewModel.onDataChange(firstName, middleName, lastName, cakeMessage);
            }
        };
        binding.cakeMessageField.addTextChangedListener(textWatcher);
        binding.lastNameField.addTextChangedListener(textWatcher);
        binding.middleNameField.addTextChangedListener(textWatcher);
        binding.firstNameField.addTextChangedListener(textWatcher);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(PersonalDetailsViewModel.class);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        mViewModel.personalDetailsFormStateMutableLiveData.observe(getViewLifecycleOwner(), new Observer<PersonalDetailsFormState>() {
            @Override
            public void onChanged(PersonalDetailsFormState personalDetailsFormState) {
                if (personalDetailsFormState == null) {
                    return;
                }
                if (personalDetailsFormState.firstNameError != null) {
                    binding.firstNameField.setError(personalDetailsFormState.firstNameError);
                } else if (personalDetailsFormState.middleNameError != null) {
                    binding.middleNameField.setError(personalDetailsFormState.middleNameError);
                } else if (personalDetailsFormState.lastNameError != null) {
                    binding.lastNameField.setError(personalDetailsFormState.lastNameError);
                } else if (personalDetailsFormState.cakeMessageError != null) {
                    binding.cakeMessageField.setError(personalDetailsFormState.cakeMessageError);
                } else {
                    if (personalDetailsFormState.isDataValid == null) {
                        binding.proceedCheckout.setEnabled(false);
                    } else {
                        binding.proceedCheckout.setEnabled(personalDetailsFormState.isDataValid);
                    }
                }
            }
        });
    }

}