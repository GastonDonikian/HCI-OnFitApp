package com.example.hci_onfitapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;

import com.example.hci_onfitapp.AppPreferences;
import com.example.hci_onfitapp.HomeActivity;
import com.example.hci_onfitapp.LoginActivity;
import com.example.hci_onfitapp.MainActivity;
import com.example.hci_onfitapp.R;
import com.example.hci_onfitapp.databinding.FragmentLoginBinding;
import com.example.hci_onfitapp.viewModel.UserFactory;
import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private TextInputLayout username;
    private TextInputLayout password;
    private TextView errorMessage;
    private Activity mActivity;

    public UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(getLayoutInflater());
        username = binding.editTextTextEmailAddress;
        password = binding.editTextTextPassword;
        errorMessage = binding.errorMessage;
        //progressBarHolder = binding.progressBarHolder;

        View view = binding.getRoot();
        Button loginBtn = view.findViewById(R.id.button_to_log_in);
        loginBtn.setOnClickListener(v -> tryLogin());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity =(Activity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this, new UserFactory(getActivity().getApplication())).get(UserViewModel.class);
    }

    private void tryLogin() {
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        viewModel.tryLogin(username.getEditText().getText().toString(), password.getEditText().getText().toString());

        viewModel.getLoginError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                switch (error.getCode()) {
                    case 4:
                        errorMessage.setText("Invalid username or password, try again.");
                        password.setError(" ");
                        username.setError(" ");
                        new Handler().postDelayed(() -> {
                            password.setError(null);
                            username.setError(null);
                            errorMessage.setText("");
                        }, 3000);
                        viewModel.setLoginErrorCode(null);
                        break;
                    case 8:
                        errorMessage.setText("Your account is not verified, redirecting to verification screen.");
                        new Handler().postDelayed(() -> Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginToVerifyFragment()), 3000);
                        viewModel.setUserData();
                        viewModel.setLoginErrorCode(null);
                        break;
                    default:
                        errorMessage.setText("Something went wrong, try again.");
                        new Handler().postDelayed(() -> errorMessage.setText(""), 3000);
                        break;
                }

            }
        });

        viewModel.getToken().observe(getViewLifecycleOwner(), authToken -> {
            if (authToken != null) {
                AppPreferences preferences = new AppPreferences(getContext());
                preferences.setAuthToken(authToken.getToken());
                Intent intent2 = new Intent(getActivity(), HomeActivity.class); //si no es un link voy al home
                Bundle aux = getArguments();
                if (aux != null && aux.get("RoutineId") != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("routineId", Integer.parseInt(aux.getString("RoutineId")));
                    new NavDeepLinkBuilder(getActivity()).setComponentName(HomeActivity.class).setGraph(R.navigation.my_nav).setArguments(bundle).createTaskStackBuilder().startActivities();
                } else {
                    startActivity(intent2);
                }
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                getActivity().finish();
            }
        });

    }

    //Validation functions
    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "^[a-zA-Z0-9\\-_]{1,20}$"; //No white spaces, must contain at most 20 characters.

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        //} else if (!val.matches(checkspaces)) {
          //  username.setError("No white spaces are allowed!");
            //return false;
       } else {
            username.setError(null);
          //  username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^[a-zA-Z0-9\\-_]{8,}$"; //No whitespaces, must contain more than 8 characters.

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } //else if (!val.matches(checkPassword)) {
            //password.setError("Password should contain at least 8 characters!");
           // return false;
        //}
         else {
            password.setError(null);
            //password.setErrorEnabled(false);
            return true;
        }
    }
}
