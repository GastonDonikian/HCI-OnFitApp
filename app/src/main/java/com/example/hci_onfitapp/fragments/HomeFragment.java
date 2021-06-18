package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.databinding.FragmentHomeBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RoutineViewModel viewModel;
    private RoutineAdapter routinesAdapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    boolean searching = false;
    boolean noMoreEntries = false;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        nestedScrollView = binding.scrollView;
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = binding.progressBar;
        swipeRefreshLayout = binding.swipeRefresh;
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);

        routinesAdapter = new RoutineAdapter(new ArrayList<>(), viewModel);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);
        viewModel.getFavouriteRoutines();
        viewModel.getRoutinesFirstLoadFavs().observe(getViewLifecycleOwner(), firstLoadFavs -> {
            if (firstLoadFavs != null) {
                if (firstLoadFavs) {
                    viewModel.updateDataFavs();
                    viewModel.setRoutinesFirstLoad(false);
                }

            }
        });
        viewModel.getFavouriteRoutines();
        viewModel.getUserFavouriteRoutines().observe(requireActivity(), routines -> {
            if (routines != null) {
                routinesAdapter.updateRoutines(routines);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
                    routinesAdapter.resetRoutines();
                    viewModel.resetDataFavs();
                    viewModel.getFavouriteRoutines();
                    swipeRefreshLayout.setRefreshing(false);

                }
        );

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    searching = false;
                }
            }
        });

        viewModel.getNoMoreEntries().observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                noMoreEntries = value;
            }
        });


        nestedScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!searching && !noMoreEntries && !nestedScrollView.canScrollVertically(1)) {
                        searching = true;
                        viewModel.updateDataFavs();
                    }
                });
    }
}