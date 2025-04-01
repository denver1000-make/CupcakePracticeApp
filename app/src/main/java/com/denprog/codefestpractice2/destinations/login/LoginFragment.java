package com.denprog.codefestpractice2.destinations.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentLoginBinding;
import com.denprog.codefestpractice2.dialog.LoadingDialog;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.MainThreadRunner;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment implements SimpleOperationCallback<User> {

    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;
    private AlertDialog saveCredentialsPrompt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        mViewModel.checkIfThereIsSavedLogin(data -> {
            MainThreadRunner.runOnMain(() -> {
                if (data != null) {
                    redirectToHome(data, navController);
                } else {
                    binding.loginFormLayout.setVisibility(View.VISIBLE);
                    checkForRegistrationRedirectArgs();
                    setupTextWatcher();
                    setupLoginButton(navController);
                }
            });
        });
        binding.registerRedirect.setOnClickListener(view -> navController.navigate(R.id.registerFragment));
    }

    public void checkForRegistrationRedirectArgs() {
        LoginFragmentArgs args = LoginFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        String password = args.getPassword();
        if (email != null && password != null) {
            binding.emailLoginField.setText(email);
            binding.passwordLoginField.setText(password);
        }
    }

    public void setupLoginButton(NavController navController) {
        binding.button.setOnClickListener(view -> {
            binding.button.setEnabled(false);
            String emailValue = binding.emailLoginField.getText().toString();
            String passwordValue = binding.passwordLoginField.getText().toString();
            showProgressDialog();
            CompletableFuture<User> manualLoginCompletableFuture = mViewModel.login(emailValue, passwordValue);
            manualLoginCompletableFuture.thenAccept(user -> MainThreadRunner.runOnMain(() -> {
                hideProgress();
                showSaveCredentialsPrompt((dialogInterface, i) -> {
                    mViewModel.saveUserToDb(user, LoginFragment.this);
                }, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    redirectToHome(user, navController);
                }, dialogInterface -> {
                    redirectToHome(user, navController);
                });
            }));

            manualLoginCompletableFuture.exceptionally(new Function<Throwable, User>() {
                @Override
                public User apply(Throwable throwable) {
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            binding.button.setEnabled(true);
                            hideProgress();
                            Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return null;
                }
            });
        });
    }

    public void setupTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = binding.emailLoginField.getText().toString();
                String password = binding.passwordLoginField.getText().toString();
                mViewModel.onDataChanged(email, password);
            }
        };

        mViewModel.loginFormStateMutableLiveData.observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
                if (loginFormState.passwordFieldError != null) {
                    binding.passwordLoginField.setError(loginFormState.passwordFieldError);
                }

                if (loginFormState.emailFieldError != null) {
                    binding.emailLoginField.setError(loginFormState.emailFieldError);
                }

                binding.button.setEnabled(loginFormState.isDataValid != null && loginFormState.isDataValid);
            }
        });

        binding.emailLoginField.addTextChangedListener(textWatcher);
        binding.passwordLoginField.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgress();
        hideSaveCredentialsPrompt();
    }

    private void redirectToHome(User user, NavController navController) {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity2(user.email, user.password));
        requireActivity().finish();
    }

    private void showSaveCredentialsPrompt(DialogInterface.OnClickListener positive,  DialogInterface.OnClickListener negative, DialogInterface.OnDismissListener dismissListener) {
        if (saveCredentialsPrompt == null) {
            saveCredentialsPrompt = new AlertDialog.Builder(requireContext())
                    .setTitle("Save Login")
                    .setMessage("Save login credentials to skip login next time you use the app.")
                    .setPositiveButton("Save Credentials", positive)
                    .setNegativeButton("Don't Save", negative)
                    .setOnDismissListener(dismissListener)
                    .create();
        }

        if (!saveCredentialsPrompt.isShowing()) {
            saveCredentialsPrompt.show();
        }
    }

    private void hideSaveCredentialsPrompt() {
        if (saveCredentialsPrompt != null && saveCredentialsPrompt.isShowing()) {
            saveCredentialsPrompt.dismiss();
            saveCredentialsPrompt = null;
        }
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
    public void onLoading() {
        showProgressDialog();
    }

    @Override
    public void onFinished(User data) {
        hideProgress();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
        redirectToHome(data, navController);
    }

    @Override
    public void onError(String message) {
        hideProgress();
    }
}