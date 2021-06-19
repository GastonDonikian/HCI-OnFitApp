package com.example.hci_onfitapp.fragments;

import android.view.View;
import android.widget.TextView;

import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.model.FavouritesModel;

public class FavouritesListener implements View.OnClickListener{

    private FavouritesModel favouritesModel;
    private boolean fav = false;


    public FavouritesListener(FavouritesModel favouritesModel) {
        this.favouritesModel = favouritesModel;
    }

    @Override
    public void onClick(View v) {
        int routineId = Integer.parseInt(((TextView) v.findViewById(R.id.routineId)).getText().toString());
        if(!fav) {
            favouritesModel.favRoutine(routineId);

            fav = true;
        }else{
            favouritesModel.unfavRoutine(routineId);
            fav = false;
        }
    }
}
