package com.denprog.codefestpractice2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.denprog.codefestpractice2.base.SelectionBase;
import com.denprog.codefestpractice2.databinding.ActivityHomeBinding;
import com.denprog.codefestpractice2.databinding.HeaderLayoutBinding;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.MainThreadRunner;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.util.HashMap;
import java.util.function.Consumer;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    HomeActivityViewModel viewModel;
    AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = com.denprog.codefestpractice2.databinding.ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);

        HomeActivityArgs args = HomeActivityArgs.fromBundle(getIntent().getExtras());
        viewModel.loadUserInfoToHeader(args.getEmail(), new SimpleOperationCallback<>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onFinished(User data) {
                MainThreadRunner.runOnMain(() -> {
                    viewModel.userMutableLiveData.setValue(data);
                    Bitmap bitmap = FileUtil.fromPathToBitmap(data.profilePicPath, HomeActivity.this);
                    HeaderLayoutBinding headerLayoutBinding = HeaderLayoutBinding.bind(binding.navigationView.getHeaderView(0));
                    headerLayoutBinding.headerEmail.setText(data.email);
                    headerLayoutBinding.headerUsername.setText(data.username);

                    headerLayoutBinding.imageView2.setImageBitmap(bitmap);
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel.totalPrice.observe(this, aFloat -> binding.appbar.appContent.totalDisplay.setText(aFloat + " Pesos"));

        NavController navController = NavHostFragment.findNavController(binding.appbar.appContent.fragmentContainerView2.getFragment());
        setSupportActionBar(binding.appbar.toolbar);
        this.appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment2, R.id.orderHistoryFragment).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupWithNavController(binding.navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = NavHostFragment.findNavController(binding.appbar.appContent.fragmentContainerView2.getFragment());
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}