package com.example.hci_onfitapp.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hci_onfitapp.MainActivity;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.databinding.FragmentProfileBinding;
import com.example.hci_onfitapp.viewModel.RoutineViewModel;
import com.example.hci_onfitapp.viewModel.UserViewModel;

public class ProfileFragment extends Fragment {

   // private FavouritesRoutinesViewModel favRoutinesViewModel;
    private UserViewModel userViewModel;
    private RoutineViewModel routineViewModel;

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

        //username = binding.userName;
//        fullName = binding.userName;
        //phone = binding.phone;
        //birthdate = binding.birthDate;
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

        System.out.println(routineViewModel.getUserRoutines().getValue());

        TextView nombre = view.findViewById(R.id.user_name);
        nombre.setText(userViewModel.getUserInfo().getValue().getFirstName() +" "+
                userViewModel.getUserInfo().getValue().getLastName());

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