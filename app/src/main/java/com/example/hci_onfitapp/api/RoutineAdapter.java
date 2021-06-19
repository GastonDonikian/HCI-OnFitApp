package com.example.hci_onfitapp.api;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.databinding.RoutineCardBinding;
import com.example.hci_onfitapp.fragments.RoutineListener;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>{
    private List<RoutineData> routinesList;
    private RoutineViewModel routinesViewModel;
    private View view;
    int from;


    public RoutineAdapter(List<RoutineData> routinesList, RoutineViewModel routinesViewModel, int from) {
        this.routinesList = routinesList;
        this.routinesViewModel = routinesViewModel;
        this.from = from;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RoutineCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.routine_card, parent, false);
        view = binding.getRoot();
        return new RoutineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        RoutineData routine = routinesList.get(position);
        int id = routine.getCategory().getId();
        switch (id) {
            case 1:
                holder.itemView.routineImg.setImageResource(R.drawable.encasa);
                routine.setImage(String.valueOf(R.drawable.encasa));
                holder.itemView.RoutineCardConstrainLayout.setBackgroundColor(Color.parseColor("#B495C2"));
                break;

            case 2:
                holder.itemView.routineImg.setImageResource(R.drawable.pesas);
                routine.setImage(String.valueOf(R.drawable.pesas));
                holder.itemView.RoutineCardConstrainLayout.setBackgroundColor(Color.parseColor("#7885FF"));
                break;

            case 3:
                holder.itemView.routineImg.setImageResource(R.drawable.running);
                routine.setImage(String.valueOf(R.drawable.running));
                holder.itemView.RoutineCardConstrainLayout.setBackgroundColor(Color.parseColor("#F1B0B8"));
                break;
        }

        holder.itemView.setRData(routine);
        holder.itemView.setListener(new RoutineListener(routinesViewModel, from));
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public void updateRoutines(List<RoutineData> routineCards) {
        routinesList.clear();
        routinesList.addAll(routineCards);
        notifyDataSetChanged();
    }

    public void resetRoutines() {
        routinesList.clear();
        notifyDataSetChanged();
    }

    public List<RoutineData> getRoutinesList() {
        return routinesList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public RoutineViewModel getRoutinesViewModel() {
        return routinesViewModel;
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder {
        public RoutineCardBinding itemView;

        public RoutineViewHolder(@NonNull RoutineCardBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
