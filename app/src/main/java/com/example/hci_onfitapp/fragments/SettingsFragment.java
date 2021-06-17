package com.example.hci_onfitapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.AppPreferences;
import com.example.hci_onfitapp.HomeActivity;
import com.example.hci_onfitapp.MainActivity;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.databinding.FragmentSettingsBinding;
import com.example.hci_onfitapp.viewModel.UserViewModel;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    AppPreferences preferences;
    UserViewModel userviewModel;

    View view;

    private HomeActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userviewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        preferences = new AppPreferences(getActivity().getApplication());
        //binding.logOutButtom.setOnClickListener(v -> logout());
        //switchDarkMode = binding.enableDarkModeSwitch;
       // if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
         //   switchDarkMode.setChecked(true);
       // switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
       //     preferences.setNightModeState(isChecked);
        //    restartApp();
       // });

       // main = (HomeActivity) getActivity();

       // main.showUpButton();
       // main.setNavigationVisibility(false);

        Button loginBtn = view.findViewById(R.id.cerrarSesion);
        loginBtn.setOnClickListener(v -> logout());

        Button deleteAccountBtn = view.findViewById(R.id.eliminarCuenta);
        deleteAccountBtn.setOnClickListener(v -> deleteAccount());


        ImageView settingsIcon = view.findViewById(R.id.imageView4);
        settingsIcon.setOnClickListener((v) -> {
            NavController navController = Navigation.findNavController(v);
            @NonNull NavDirections action = SettingsFragmentDirections.actionSettingsFragmentToProfileFragment();
            navController.navigate(action);
        });

        Button aceptarButton = view.findViewById(R.id.aceptarCambios);
        aceptarButton.setOnClickListener(v -> acceptChanges(10));

        return view;
    }

    // Recomendado: lavarse los ojos con lavandina despues de leer esta funcion
    // Hice esta asquerocidad porque no se hacer un async await. No juzgar
    private void acceptChanges(int countRec) {
        if (countRec <= 0) return;
        System.out.println(userviewModel.getUserInfo().getValue().getFirstName());
        EditText nombre = view.findViewById(R.id.Nombre);
        System.out.println(nombre.getText());
        EditText apellido = view.findViewById(R.id.Apellido);
        System.out.println(apellido.getText());
        EditText avatar_url = view.findViewById(R.id.Avatar);
        System.out.println(avatar_url.getText());


        User user = new User(userviewModel.getUserInfo().getValue().getUsername(),
                userviewModel.getUserInfo().getValue().getPassword(),
                userviewModel.getUserInfo().getValue().getFirstName(),
                userviewModel.getUserInfo().getValue().getLastName(),
                userviewModel.getUserInfo().getValue().getGender(),
                userviewModel.getUserInfo().getValue().getBirthdate(),
                userviewModel.getUserInfo().getValue().getEmail(),
                userviewModel.getUserInfo().getValue().getPhone(),
                userviewModel.getUserInfo().getValue().getAvatarUrl());

        validation(nombre, apellido, avatar_url, user);
//          TODO no funciona el modifyUser deSystem.out.println("funciona"); UserViewModel
        userviewModel.modifyUser(user);
        userviewModel.setUserData();

        acceptChanges(countRec-1);
    }
    private void validation(EditText nombre, EditText apellido, EditText avatar, User user) {
        if (nombre.getText() != null && !nombre.getText().toString().equals("")) {
            user.setFirstName(nombre.getText().toString());
        }
        if (apellido.getText() != null && !apellido.getText().toString().equals("")) {
            user.setLastName(apellido.getText().toString());
        }
        if (avatar.getText() != null && !avatar.getText().toString().equals("")) {
            user.setAvatarUrl(avatar.getText().toString());
        }
    }

    private void logout() {
        userviewModel.logout();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getActivity().finish(); //elimino la actividad del stack
    }


    private void deleteAccount() {
        userviewModel.deleteAccount();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getActivity().finish(); //elimino la actividad del stack
    }
}


