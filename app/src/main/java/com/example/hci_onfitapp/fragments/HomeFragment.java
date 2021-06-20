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
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.RoutineAdapter;
import com.example.hci_onfitapp.databinding.FragmentHomeBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.example.hci_onfitapp.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private RoutineViewModel viewModel;
    private UserViewModel userViewModel;
    private RoutineAdapter routinesAdapter;
    private RecyclerView recyclerView;
    private FragmentHomeBinding binding;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    boolean searching = false;
    boolean noMoreEntries = false;
    private int routineId;
    private int routineIdRating;
    private int routineIdLatest;

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
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        routinesAdapter = new RoutineAdapter(new ArrayList<>(), viewModel, RoutineListener.FAV_ID);

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


        ImageSlider imageSlider = view.findViewById(R.id.home_slider);

        List<SlideModel> slideModels = new ArrayList<>();

        if (Locale.getDefault().getLanguage() != "en") {
            slideModels.add(new SlideModel("https://images.unsplash.com/photo-1571902943202-507ec2618e8f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=668&q=80", "Rutina Mejor Valorada"));
            slideModels.add(new SlideModel("https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1951&q=80", "Última Rutina Creada"));
        }
        else {
            slideModels.add(new SlideModel("https://images.unsplash.com/photo-1571902943202-507ec2618e8f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=668&q=80", "Best Rated Routine"));
            slideModels.add(new SlideModel("https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1951&q=80", "Last Created Routine"));
        }
        imageSlider.setImageList(slideModels, true);


        viewModel.updateRoutines();
        viewModel.updateRoutinesByDate();
        imageSlider.setItemClickListener(i -> {
            NavController navController = Navigation.findNavController(view);
            switch (i) {
                case 0:
                    viewModel.updateRoutines();
                    viewModel.getUserRoutines().observe(getViewLifecycleOwner(), routineData -> {
                        routineIdRating = routineData.get(0).getId();
                    });
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToViewRoutineFragment().setRoutineId(routineIdRating));
                    break;

                case 1:
                    viewModel.updateRoutinesByDate();
                    viewModel.getRoutinesByDate().observe(getViewLifecycleOwner(), routineData -> {
                        routineIdLatest = routineData.get(0).getId();
                    });
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToViewRoutineFragment().setRoutineId(routineIdLatest));
                    break;
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