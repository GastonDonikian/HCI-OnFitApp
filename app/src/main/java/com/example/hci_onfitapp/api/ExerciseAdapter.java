package com.example.hci_onfitapp.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.CycleExerciseData;
import com.example.hci_onfitapp.api.model.PagedList;
import com.example.hci_onfitapp.databinding.FragmentExcerciseBinding;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private PagedList<CycleExerciseData> exerciseList;
    FragmentExcerciseBinding binding;

    public ExerciseAdapter(PagedList<CycleExerciseData> exerciseList) {

        this.exerciseList = exerciseList;
    }

    public void updateExercises(List<CycleExerciseData> newExercisesList) {
        exerciseList.getContent().clear();
        exerciseList.getContent().addAll(newExercisesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_excercise, parent, false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        if(exerciseList.getContent() == null)
            return;
        CycleExerciseData exercise = exerciseList.getContent().get(position);
        holder.itemView.setEData(exercise);
        holder.itemView.repsExercise.setVisibility(exercise.getRepetitions() != 0 ? View.VISIBLE : View.GONE);
        holder.itemView.durExercise.setVisibility(exercise.getDuration() != 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return exerciseList.getTotalCount();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public FragmentExcerciseBinding itemView;

        public ExerciseViewHolder(@NonNull FragmentExcerciseBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
//
//    public void openExerciseInfoDialog(ExerciseData exerciseData) {
//        ShowExerciseDialog showExerciseDialog = new ShowExerciseDialog(exerciseData);
//        showExerciseDialog.show(((AppCompatActivity) parentContext).getSupportFragmentManager(), "example dialog");
//    }

    public List<CycleExerciseData> getExerciseList() {
        return exerciseList.getContent();
    }

    public CycleExerciseData getExercise(int index) {
        return exerciseList.getContent().get(index);
    }

}