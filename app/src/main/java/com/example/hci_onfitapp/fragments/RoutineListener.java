package com.example.hci_onfitapp.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

public class RoutineListener implements View.OnClickListener {
    private RoutineViewModel routineViewModel;
    private int from;
    public static final int ROUTINES_ID = 1;
    public static final int FAV_ID = 2;

    public RoutineListener(RoutineViewModel routineViewModel) {
        this.routineViewModel = routineViewModel;
    }

    @Override
    public void onClick(View view) {
        int routineId = Integer.parseInt(((TextView) view.findViewById(R.id.routineId)).getText().toString());
        routineViewModel.getRoutineById(routineId);
        NavDirections action1 = ExploreFragmentDirections.actionExploreFragmentToViewRoutineFragment().setRoutineId(routineId);
        Navigation.findNavController(view).navigate(action1);
    }
}
