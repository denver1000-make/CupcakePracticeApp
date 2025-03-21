package com.denprog.codefestpractice2.destinations.register;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentRegisterBinding;
import com.denprog.codefestpractice2.destinations.state.RegisterFormState;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String email = binding.emailField.getText().toString();
                String password = binding.passwordField.getText().toString();
                String confirmPassword = binding.confirmPasswordField.getText().toString();
                String userName = binding.usernameField.getText().toString();
                mViewModel.onDataChanged(email, userName, password, confirmPassword);
            }
        };
        this.binding.confirmPasswordField.addTextChangedListener(textWatcher);
        this.binding.passwordField.addTextChangedListener(textWatcher);
        this.binding.emailField.addTextChangedListener(textWatcher);
        this.binding.usernameField.addTextChangedListener(textWatcher);

        mViewModel.mutableLiveData.observe(getViewLifecycleOwner(), new Observer<RegisterFormState>() {
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState != null) {
                    binding.registerAction.setEnabled(registerFormState.isDataValid);
                    if (registerFormState.userNameError != null) {
                        binding.usernameField.setError(registerFormState.userNameError);
                        return;
                    }
                    if (registerFormState.emailError != null) {
                        binding.emailField.setError(registerFormState.emailError);
                        return;
                    }
                    if (registerFormState.passwordError != null) {
                        binding.passwordField.setError(registerFormState.passwordError);
                        return;
                    }
                    if (registerFormState.confirmPasswordError != null) {
                        binding.confirmPasswordField.setError(registerFormState.confirmPasswordError);
                        return;
                    }
                } else {
                    binding.registerAction.setEnabled(false);
                }
            }
        });

        binding.registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}