package com.denprog.codefestpractice2.destinations.checkout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.base.SelectionBase;
import com.denprog.codefestpractice2.databinding.FragmentCheckOutBinding;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.CakeFlavorFragment;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj.CakeFlavor;
import com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj.CakeFlavorV2;
import com.denprog.codefestpractice2.destinations.design.DesigPickerFragmentViewModel;
import com.denprog.codefestpractice2.destinations.design.Design;
import com.denprog.codefestpractice2.destinations.design.DesignPickerFragment;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavor;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavorFragment;
import com.denprog.codefestpractice2.destinations.filling_flavor_selection.FillingFlavorV2;
import com.denprog.codefestpractice2.destinations.personal_details.PersonalDetails;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.WhippedCreamFlavorFragment;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavor;
import com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj.WhippedCreamFlavorV2;
import com.denprog.codefestpractice2.dialog.LoadingDialog;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;
import com.denprog.codefestpractice2.util.TaskLoaderWithProgress;

import java.util.HashMap;

public class CheckOutFragment extends Fragment {
    private CheckOutViewModel mViewModel;
    private FragmentCheckOutBinding binding;
    private HomeActivityViewModel viewModel;
    private ProgressDialog loadingDialog;
    private DesigPickerFragmentViewModel desigPickerFragmentViewModel;
    public static final String SAVED_DESIGN_PATHS = "saved_designs";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CheckOutViewModel.class);
        this.desigPickerFragmentViewModel = new ViewModelProvider(requireActivity()).get(DesigPickerFragmentViewModel.class);
        this.viewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        CheckOutFragmentArgs args = CheckOutFragmentArgs.fromBundle(getArguments());

        CakeFlavorV2 cakeFlavorV2 = args.getCakeFlavorV2();
        WhippedCreamFlavorV2 whippedCreamFlavorV2 = args.getWhippedCreamFlavorV2();
        FillingFlavorV2 fillingFlavorV2 = args.getFillingFlavorV2();
        Design design;

        if (args.getResource() != -1 && args.getResource() != 0) {
            design = new Design(args.getDesignName(), args.getDesignPrice(), BitmapFactory.decodeResource(getResources(), args.getResource()));
        } else {
            design = new Design(args.getDesignName(), args.getDesignPrice(), desigPickerFragmentViewModel.bitmapMutableLiveData.getValue());
        }

        float totalPrice = cakeFlavorV2.cakeFlavorPrice +
                whippedCreamFlavorV2.flavorPrice +
                (fillingFlavorV2 == null ? 0f : fillingFlavorV2.fillingFlavorPrice) +
                design.price;
        binding.totalPriceDisplay.setText(totalPrice + " Pesos");
        binding.setHasFilling(fillingFlavorV2 != null);
        binding.setDesign(design);
        binding.setCakeFlavorInf(cakeFlavorV2);
        binding.setWhippedCreamFlavor(whippedCreamFlavorV2);
        binding.cakeDesignImage.setImageBitmap(design.bitmap);
        binding.checkOutBtn.setOnClickListener(view -> {
            User user = viewModel.userMutableLiveData.getValue();
            mViewModel.saveImageAndRetrievePath(requireContext(), design.bitmap, user.username, data -> {
                if (data != null) {
                    mViewModel.checkOut(new CheckOutObj(
                            user.userId,
                            cakeFlavorV2.cakeFlavorName,
                            cakeFlavorV2.cakeFlavorPrice,
                            whippedCreamFlavorV2.flavorName,
                            whippedCreamFlavorV2.flavorPrice,
                            fillingFlavorV2 == null ? null : fillingFlavorV2.fillingFlavorName,
                            fillingFlavorV2 == null ? -0f : fillingFlavorV2.fillingFlavorPrice,
                            data,
                            design.price,
                            args.getCakeMessage(),
                            totalPrice));
                }
            });
        });

        mViewModel.mutableLiveData.observe(getViewLifecycleOwner(), checkOutState -> {
            if (checkOutState instanceof CheckOutState.Loading) {
                showLoading();
            } else if (checkOutState instanceof CheckOutState.Complete) {
                hideLoading();
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
                navController.navigate(CheckOutFragmentDirections.actionCheckOutFragmentToHomeFragment2());
                desigPickerFragmentViewModel.bitmapMutableLiveData.setValue(null);
                viewModel.totalPrice.setValue(0f);
            } else if (checkOutState instanceof CheckOutState.Error) {
                hideLoading();
                showError(((CheckOutState.Error) checkOutState).errorMessage).show();
            }
        });
    }

    public AlertDialog showError(String message) {
        return new AlertDialog.Builder(requireActivity())
                .setTitle("An Error Occurred")
                .setMessage(message)
                .create();
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(requireActivity());
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCancelable(false);
        }

        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void hideLoading () {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.hide();
            loadingDialog = null;
        }
    }
}