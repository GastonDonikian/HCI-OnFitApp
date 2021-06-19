package com.example.hci_onfitapp.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private int currentExercise = -1;
    FragmentExcerciseBinding binding;

    private Context parentContext;

    private ImageView infoButton;

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
//        CycleExerciseData exercise = exerciseList.getContent().get(position);
//        holder.itemView.setEData(exercise);
//        if (exercise.isRunning()){
//            holder.itemView.exerciseContainer.setBackgroundColor(parentContext.getColor(R.color.executionNotSelected));
//            holder.itemView.exerciseName.setTextColor(parentContext.getColor(R.color.mainTextColorAlternative));
//            holder.itemView.repsExercise.setTextColor(parentContext.getColor(R.color.mainTextColorAlternative));
//            holder.itemView.timeExercise.setTextColor(parentContext.getColor(R.color.mainTextColorAlternative));
//            holder.itemView.infoButton.setColorFilter(parentContext.getColor(R.color.mainTextColorAlternative));
//        }
//        else{
//            holder.itemView.exerciseContainer.setBackgroundColor(parentContext.getColor(R.color.executionSelected));
//            holder.itemView.exerciseName.setTextColor(parentContext.getColor(R.color.primaryColorAlternative));
//            holder.itemView.repsExercise.setTextColor(parentContext.getColor(R.color.primaryColorAlternative));
//            holder.itemView.timeExercise.setTextColor(parentContext.getColor(R.color.primaryColorAlternative));
//            holder.itemView.infoButton.setColorFilter(parentContext.getColor(R.color.primaryColorAlternative));
//        }

//        holder.itemView.infoButton.setOnClickListener(v -> openExerciseInfoDialog(exercise));
//        if(exercise.getRepetitions() != 0) {
//            holder.itemView.repsExercise.setText(exercise.getRepetitions());
//        } else
//            holder.itemView.repsExercise.setText(exercise.getDuration());
    }

    @Override
    public int getItemCount() {
        return 3;
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