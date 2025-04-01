package com.denprog.codefestpractice2.destinations.order_history;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentOrderHistoryBinding;
import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.destinations.order_history.placeholder.PlaceholderContent;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;

import java.util.List;

public class OrderHistoryFragment extends Fragment {
    FragmentOrderHistoryBinding binding;
    OrderHistoryRecyclerViewAdapter adapter;
    OrderHistoryViewModel viewModel;
    HomeActivityViewModel homeActivityViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        adapter = new OrderHistoryRecyclerViewAdapter(data -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView2);
            navController.navigate(OrderHistoryFragmentDirections.actionOrderHistoryFragmentToViewOrderFragment(data));
        });
        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(OrderHistoryViewModel.class);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        this.homeActivityViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null) {
                    return;
                }

                viewModel.fetchOrderHistory(user.userId);
            }
        });

        viewModel.mutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<CheckOutObj>>() {
            @Override
            public void onChanged(List<CheckOutObj> checkOutObjList) {
                adapter.refreshAdapter(checkOutObjList);
            }
        });
    }
}