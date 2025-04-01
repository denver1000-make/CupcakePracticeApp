package com.denprog.codefestpractice2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.denprog.codefestpractice2.databinding.ActivityHomeBinding;
import com.denprog.codefestpractice2.databinding.HeaderLayoutBinding;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.MainThreadRunner;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    HomeActivityViewModel viewModel;
    AlertDialog confirmationDialog;
    ProgressDialog progressDialog;
    AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = com.denprog.codefestpractice2.databinding.ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controllerCompat = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controllerCompat.hide(WindowInsetsCompat.Type.systemBars());
        controllerCompat.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        NavController navController = getNavController();
        setSupportActionBar(binding.appbar.toolbar);
        this.appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.orderHistoryFragment).setOpenableLayout(binding.drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("Navigation", "Current Destination: " + getResources().getResourceName(destination.getId()));
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        HomeActivityArgs args = HomeActivityArgs.fromBundle(getIntent().getExtras());
        viewModel.loadUserInfoToHeader(args.getEmail(), new SimpleOperationCallback<>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onFinished(User data) {
                Bitmap bitmap = FileUtil.fromPathToBitmap(data.profilePicPath, HomeActivity.this);
                MainThreadRunner.runOnMain(() -> {
                    viewModel.userMutableLiveData.setValue(data);
                    HeaderLayoutBinding headerLayoutBinding = HeaderLayoutBinding.bind(binding.navigationView.inflateHeaderView(R.layout.header_layout));
                    headerLayoutBinding.headerEmail.setText(data.email);
                    headerLayoutBinding.headerUsername.setText(data.username);
                    headerLayoutBinding.imageView2.setImageBitmap(bitmap);

                    binding.navigationView.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.navigationView.invalidate();
                        }
                    });
                });
            }

            @Override
            public void onError(String message) {
                MainThreadRunner.runOnMain(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        viewModel.totalPrice.observe(this, aFloat -> binding.appbar.totalDisplay.setText(aFloat + " Pesos"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = getNavController();
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        NavController navController = navHostFragment.getNavController();
        return navController;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.extra_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            showConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showConfirmationDialog() {
        if (confirmationDialog == null) {
            confirmationDialog = new AlertDialog.Builder(this)
                    .setTitle("Are you sure you want to Log Out?")
                    .setMessage("Confirm Action")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.logOut(new SimpleOperationCallback<Void>() {
                                @Override
                                public void onLoading() {
                                    showProgressDialog();
                                }

                                @Override
                                public void onFinished(Void data) {
                                    hideDialog();
                                    finish();
                                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                                    dialogInterface.dismiss();
                                }

                                @Override
                                public void onError(String message) {
                                    hideDialog();
                                    Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create();
        }
        if (!confirmationDialog.isShowing()) {
            confirmationDialog.show();
        }
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog = null;
    }
}