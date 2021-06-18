package com.example.hci_onfitapp.api.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.api.model.FavouritesModel;
import com.example.hci_onfitapp.databinding.FragmentViewRoutineBinding;
import com.example.hci_onfitapp.fragments.FavouritesListener;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteViewHolder> {
    private List<RoutineData> routinesList;
    private FavouritesModel routinesViewModel;
    private View view;


    public FavouritesAdapter(List<RoutineData> routinesList, FavouritesModel routinesViewModel) {
        this.routinesList = routinesList;
        this.routinesViewModel = routinesViewModel;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentViewRoutineBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_routine, parent, false);
        view = binding.getRoot();
        return new FavouritesAdapter.FavouriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        RoutineData routine = routinesList.get(position);

        holder.itemView.setRoutine(routine);
        holder.itemView.setFavsListener(new FavouritesListener(routinesViewModel));
        holder.itemView.floatingActionButtonFavorite.setOnClickListener(new FavouritesListener(routinesViewModel));
        //holder.itemView.setFavsListener(new FavouritesListener(routinesViewModel));
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

    public FavouritesModel getRoutinesViewModel() {
        return routinesViewModel;
    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public FragmentViewRoutineBinding itemView;

        public FavouriteViewHolder(@NonNull FragmentViewRoutineBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }

}
