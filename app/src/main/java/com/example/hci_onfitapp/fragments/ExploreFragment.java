package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hci_onfitapp.HomeActivity;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.databinding.FragmentExploreBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.chip.ChipGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private RoutineViewModel viewModel;

    private RoutineAdapter routinesAdapter;

    private FragmentExploreBinding binding;

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ChipGroup chipGroup;

    boolean noMoreEntries = false;
    boolean searching = false;

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        nestedScrollView = binding.scrollView;
        recyclerView = binding.recyclerView;
        progressBar = binding.progressBar;
        chipGroup = binding.chipGroupRoutines;
        swipeRefreshLayout = binding.swipeRefresh;

        //((HomeActivity) getActivity()).setNavigationVisibility(true);

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

        viewModel.getRoutinesFirstLoad().observe(getViewLifecycleOwner(), firstLoad -> {
            if (firstLoad != null) {
                if (firstLoad) {
                    viewModel.updateData();
                    viewModel.setRoutinesFirstLoad(false);
                }
            }
        });

        viewModel.getRoutineCards().observe(getViewLifecycleOwner(), routines -> {
            if (routines != null) {
                routinesAdapter.updateRoutines(routines);
            }
            System.out.println("explore fragment");
            System.out.println(routines);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
                    routinesAdapter.resetRoutines();
                    viewModel.resetData();
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

        chipGroup.setOnCheckedChangeListener(new ChipListener(viewModel));
        int filtered = viewModel.getFilterId();
        if (filtered != -1) {
            int id = 0;
            if (filtered == 0) {
                id = R.id.destacado;
            } else if (filtered == 1) {
                id = R.id.en_casa;
            } else if (filtered == 2) {
                id = R.id.running;
            } else if (filtered == 3) {
                id = R.id.pesas;
            }
            chipGroup.check(id);
        }

        nestedScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!searching && !noMoreEntries && !nestedScrollView.canScrollVertically(1)) {
                        searching = true;
                        viewModel.updateData();
                    }
                });
    }
}