package com.denprog.codefestpractice2.destinations.order_history;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.codefestpractice2.HomeActivityViewModel;
import com.denprog.codefestpractice2.R;
import com.denprog.codefestpractice2.databinding.FragmentOrderHistoryBinding;
import com.denprog.codefestpractice2.databinding.FragmentOrderHistoryListBinding;
import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.destinations.order_history.placeholder.PlaceholderContent;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.util.List;
import java.util.Objects;

public class OrderHistoryFragment extends Fragment {
    FragmentOrderHistoryListBinding binding;
    HomeActivityViewModel homeActivityViewModel;
    OrderHistoryFragmentViewModel viewModel;
    OrderHistoryRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryListBinding.inflate(inflater, container, false);
        this.adapter = new OrderHistoryRecyclerViewAdapter();
        this.binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(OrderHistoryFragmentViewModel.class);
        this.homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);
        viewModel.fetchHistory(Objects.requireNonNull(this.homeActivityViewModel.userMutableLiveData.getValue()).userId, new SimpleOperationCallback<List<CheckOutObj>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onFinished(List<CheckOutObj> data) {
                adapter.refreshAdapter(data);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}