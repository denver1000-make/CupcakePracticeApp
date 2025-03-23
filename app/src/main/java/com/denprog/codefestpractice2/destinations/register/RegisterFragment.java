package com.denprog.codefestpractice2.destinations.register;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.Toast;

import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentRegisterBinding;
import com.denprog.codefestpractice2.destinations.state.RegisterFormState;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.MainThreadRunner;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o != null) {
                mViewModel.selectedProfileUri.setValue(FileUtil.convertUriToBitmap(o, requireContext()));
            }
        }
    });

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding binding;

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

        int args = RegisterFragmentArgs.fromBundle(getArguments()).getValidationVersion();
        binding.toggleVersion.setText("Toggle Version current version is " + args);
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
                if (args == 1) {
                    mViewModel.onDataChanged(email, userName, password, confirmPassword);
                } else {
                    mViewModel.onDataChangedV2(email, userName, password, confirmPassword);
                }
            }
        };
        this.binding.imageView.setOnClickListener(view -> imagePicker.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()));
        this.binding.confirmPasswordField.addTextChangedListener(textWatcher);
        this.binding.passwordField.addTextChangedListener(textWatcher);
        this.binding.emailField.addTextChangedListener(textWatcher);
        this.binding.usernameField.addTextChangedListener(textWatcher);

        mViewModel.mutableLiveData.observe(getViewLifecycleOwner(), registerFormState -> {
            if (registerFormState != null) {
                binding.registerAction.setEnabled(registerFormState.isDataValid);
                if (registerFormState.userNameError != null) {
                    binding.usernameField.setError(registerFormState.userNameError);
                }
                if (registerFormState.emailError != null) {
                    binding.emailField.setError(registerFormState.emailError);
                }
                if (registerFormState.passwordError != null) {
                    binding.passwordField.setError(registerFormState.passwordError);
                }
                if (registerFormState.confirmPasswordError != null) {
                    binding.confirmPasswordField.setError(registerFormState.confirmPasswordError);
                }
            } else {
                binding.registerAction.setEnabled(false);
            }
        });

        mViewModel.selectedProfileUri.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    binding.imageView.setImageBitmap(bitmap);
                }
            }
        });

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        binding.toggleVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragmentDirections.ActionRegisterFragmentSelf directions = RegisterFragmentDirections.actionRegisterFragmentSelf();
                directions.setValidationVersion(args == 1 ? 2 : 1);
                navController.navigate(directions);
            }
        });

        binding.registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailField.getText().toString();
                String username = binding.usernameField.getText().toString();
                String password = binding.passwordField.getText().toString();
                Bitmap bitmap = mViewModel.selectedProfileUri.getValue();
                mViewModel.register(requireContext(), bitmap, email, username, password, new SimpleOperationCallback<User>() {
                    @Override
                    public void onLoading() {
                        MainThreadRunner.runOnMain(RegisterFragment.this::showProgressDialog);
                    }

                    @Override
                    public void onFinished(User data) {
                        MainThreadRunner.runOnMain(() -> {
                            hideProgress();
                            Bundle args = new Bundle();
                            args.putString("email", data.email);
                            args.putString("password", data.password);
                            navController.navigate(R.id.loginFragment, args);
                        });
                    }

                    @Override
                    public void onError(String message) {
                        hideProgress();
                    }
                });
            }
        });

        binding.redirectToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("email", null);
                args.putString("password", null);
                navController.navigate(R.id.loginFragment, args);
            }
        });
    }


    ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            progressDialog.setIndeterminate(true);
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
            progressDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
    }
}