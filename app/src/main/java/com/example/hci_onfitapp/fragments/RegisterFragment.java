package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.api.User;
import com.example.hci_onfitapp.databinding.FragmentRegisterBinding;
import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {
    private TextInputLayout email, username, password, name, lastName, confirmPassword;
    private TextView errorMessage;

    private FragmentRegisterBinding binding;

    private UserViewModel viewModel;

    private FrameLayout progressBarHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        email = binding.editTextTextEmailAddress;
        password = binding.editTextTextPassword;
        username = binding.editTextTextEmailAddress;
        name = binding.nombre;
        lastName = binding.apellido;
        confirmPassword = binding.confirmarContrasenia;
        Button registerBtn = binding.buttonToRegister;
//        errorMessage = binding.registerErrorMessage;
//        progressBarHolder = binding.progressBarHolder;

        View view = binding.getRoot();

        registerBtn.setOnClickListener(v -> tryRegister());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {});

//        viewModel.getRegisterError().observe(getViewLifecycleOwner(), error -> {
//            if (error != null) {
//                if (error.getCode() == 2) {
//                    errorMessage.setText(R.string.already_exists);
//                    password.setError(" ");
//                    username.setError(" ");
//                    email.setError(" ");
//                    new Handler().postDelayed(() -> {
//                        password.setError(null);
//                        username.setError(null);
//                        email.setError(null);
//                        errorMessage.setText("");
//                    }, 5000);
//                    viewModel.setRegisterError(null);
//                } else {
//                    errorMessage.setText(R.string.default_error);
//                    new Handler().postDelayed(() -> {
//                        errorMessage.setText("");
//                    }, 3000);
//                    viewModel.setRegisterError(null);
//                }
//            }
//        });

        viewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            if (userData != null) {
                Navigation.findNavController(getView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToVerifyFragment());
            }
        });
    }

    private void tryRegister() {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }
        User userInfo = new User(username.getEditText().getText().toString()
                , password.getEditText().getText().toString()
                , name.getEditText().getText().toString()
                , lastName.getEditText().getText().toString()
                , "other"
                , (long) 0
                , email.getEditText().getText().toString()
                , ""
                , ""
        );

        viewModel.tryRegister(userInfo);
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
//        String checkspaces = "^[a-zA-Z0-9\\-_]{1,20}$"; //No white spaces, must contain at most 20 characters.

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
//        } else if (!val.matches(checkspaces)) {
//            username.setError("No white spaces are allowed!");
//            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String val2 = confirmPassword.getEditText().getText().toString().trim();
        //String checkPassword = "^[a-zA-Z0-9\\-_]{8,}$"; //No whitespaces, must contain more than 8 characters.

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.compareTo(val2) != 0) {
            password.setError("Passwords are not equal");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}
