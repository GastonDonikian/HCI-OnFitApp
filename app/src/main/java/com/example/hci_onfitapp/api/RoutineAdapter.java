package com.example.hci_onfitapp.api;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.databinding.RoutineCardBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>{
    private List<RoutineData> routinesList;
    private RoutineViewModel routinesViewModel;
    private View view;
    private int host;


    public RoutineAdapter(List<RoutineData> routinesList, RoutineViewModel routinesViewModel) {
        this.routinesList = routinesList;
        System.out.println("Routine Adapter");
        System.out.println(this.routinesList);
//        this.host = host;
        this.routinesViewModel = routinesViewModel;
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
//        System.out.println(routine.getUser().getAvatarUrl());
//        if(!routine.getUser().getAvatarUrl().equals("")) {
//            URL url = null;
//            Bitmap bmp = null;
//            try {
//                url = new URL(routine.getUser().getAvatarUrl());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            holder.itemView.userImage.setImageBitmap(bmp);
//        }
        int id = routine.getCategory().getId();
        switch (id) {
            case 1:
                holder.itemView.routineImg.setImageResource(R.drawable.encasa);
                routine.setImage(String.valueOf(R.drawable.encasa));
                break;

            case 2:
                holder.itemView.routineImg.setImageResource(R.drawable.pesas);
                routine.setImage(String.valueOf(R.drawable.pesas));
                break;

            case 3:
                holder.itemView.routineImg.setImageResource(R.drawable.running);
                routine.setImage(String.valueOf(R.drawable.running));
                break;
        }

        holder.itemView.setRData(routine);
//        holder.itemView.setClickListener(new RoutineClickListener(routinesViewModel, host));
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public void updateRoutines(List<RoutineData> routineCards) {
        System.out.println(Arrays.toString(routineCards.toArray()));
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
