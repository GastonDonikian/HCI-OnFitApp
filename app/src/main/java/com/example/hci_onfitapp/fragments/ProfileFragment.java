package com.example.hci_onfitapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.data.RoutineData;
import com.example.hci_onfitapp.databinding.FragmentProfileBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.example.hci_onfitapp.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileFragment extends Fragment {

   // private FavouritesRoutinesViewModel favRoutinesViewModel;
    private UserViewModel userViewModel;
    private RoutineViewModel routineViewModel;
    private List<RoutineData> routineData = new ArrayList<>();
    private FragmentProfileBinding binding;

   // private FavoriteAdapter favoriteAdapter;

    private RecyclerView favoriteCardsList;

    private TextView username;
    private TextView fullName;
    private TextView phone;
    private TextView birthdate;
    private ImageView profilePic;
    private View view;

    private Spinner spinner;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        view = binding.getRoot();

        profilePic = binding.profilePic;

        //favoriteCardsList = binding.favRecycler;

        //((MainActivity) getActivity()).setNavigationVisibility(true);


//        userViewModel.getUserData();


//
//        loginBtn.setOnClickListener((v) -> {
//
//            NavController navController = Navigation.findNavController(v);
//            @NonNull NavDirections action = MainFragmentDirections.actionMainFragmentToLogin();
//            //action.setRoutineId(arg1);
//            navController.navigate(action);
//
//        });
        ImageView settingsIcon = view.findViewById(R.id.imageView4);
        settingsIcon.setOnClickListener((v) -> {
            NavController navController = Navigation.findNavController(v);
            @NonNull NavDirections action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment();
            navController.navigate(action);
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // favRoutinesViewModel = new ViewModelProvider(getActivity()).get(FavouritesRoutinesViewModel.class);
       // favRoutinesViewModel.updateData();

        //favoriteAdapter = new FavoriteAdapter(new ArrayList<>(), new ViewModelProvider(getActivity()).get(RoutinesViewModel.class));

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        routineViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);

        //favoriteCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        //favoriteCardsList.setAdapter(favoriteAdapter);

        TextView nombre = view.findViewById(R.id.user_name);
        nombre.setText(userViewModel.getUserInfo().getValue().getFirstName() + " " + userViewModel.getUserInfo().getValue().getLastName());

        Button favRoutines = view.findViewById(R.id.button3);

        Button createdRoutines = view.findViewById(R.id.button2);

        Button birthdayBtn = view.findViewById(R.id.button4);
        String date = userViewModel.getUserInfo().getValue().getDate();
        long timeInMilis = (long) (System.currentTimeMillis() - Float.parseFloat(date));
        int days = (int) (timeInMilis / (1000*60*60*24));
        birthdayBtn.setText(String.valueOf(days) + " Dias entrenando");

        routineViewModel.getFavouriteRoutines(); //Seria mas como un updateFavouriteRoutines pero no tengo ganas de cambiar el nombre
        routineViewModel.getUserFavouriteRoutines().observe(getViewLifecycleOwner(), routineData -> {
            favRoutines.setText(routineData.size() + " rutinas favoritas");
        });

        routineViewModel.updateUserRoutines();
        routineViewModel.getUserRoutines().observe(getViewLifecycleOwner(), routineData -> {
            createdRoutines.setText(routineData.size() + " rutinas creadas");
        });

        seedProfile();
    }


    private void seedProfile() {
        userViewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            if (userInfo != null) {
                binding.setUserInformation(userInfo);
                if (!userInfo.getAvatarUrl().equals("")) {
                    Glide.with(binding.getRoot()).load(userInfo.getAvatarUrl()).into(binding.profilePic);
                }
            }
        });
/*
        favRoutinesViewModel.getFavouriteRoutines().observe(getViewLifecycleOwner(), favourites -> {
            if (favourites != null) {
                favoriteCardsList.setVisibility(View.VISIBLE);
                favoriteAdapter.updateFavoriteList(favourites);
            }
        });
        */
    }

/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.app_bar_settings).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_settings) {
            settings();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void settings() {
        Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionMeFragmentToSettingsFragment());
    }
*/

}