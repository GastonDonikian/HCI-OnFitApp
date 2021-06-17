package com.example.hci_onfitapp.fragments;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.google.android.material.chip.ChipGroup;

public class ChipListener implements ChipGroup.OnCheckedChangeListener {

    private RoutineViewModel viewModel;

    public ChipListener(RoutineViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        Integer id = null;

        if (checkedId == R.id.destacado) {
            id = 0;
        } else if (checkedId == R.id.en_casa) {
            id = 1;
        } else if (checkedId == R.id.running) {
            id = 2;
        } else if (checkedId == R.id.pesas) {
            id = 3;
        } else if (group.getCheckedChipId() == checkedId) {
            id = -1;
        }
        viewModel.filterRoutines(id);
    }

}
