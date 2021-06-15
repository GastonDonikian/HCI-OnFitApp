package com.example.hci_onfitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.hci_onfitapp.databinding.FragmentVerifyBinding;

import com.example.hci_onfitapp.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class VerifyFragment extends Fragment {
    private UserViewModel viewModel;
    private TextInputLayout email, code;
    private Button resendBtn, verifyBtn;
    private FragmentVerifyBinding binding;

    private FrameLayout progressBarHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVerifyBinding.inflate(getLayoutInflater());

        email = binding.editTextTextEmailAddress;
        code = binding.codigoVerificacion;

        resendBtn = binding.buttonResend;
        verifyBtn = binding.buttonToVerify;

        View view = binding.getRoot();

        verifyBtn.setOnClickListener(v -> tryVerify(view));
        resendBtn.setOnClickListener(v -> resendVerification());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

//        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
//            if (isLoading != null) {
//                if (isLoading) {
//                    progressBarHolder.setVisibility(View.VISIBLE);
//                } else {
//                    progressBarHolder.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    private void resendVerification() {
        if (!validateEmail()) {
            return;
        }
        viewModel.resendVerification(email.getEditText().getText().toString());
    }

    private void tryVerify(View v) {
        viewModel.verifyUser(code.getEditText().getText().toString());
        viewModel.getVerified().observe(getViewLifecycleOwner(), verified -> {
            if (verified) {
                Bundle b = getArguments();

                NavController navController = Navigation.findNavController(v);
                @NonNull NavDirections action = VerifyFragmentDirections.actionVerifyFragmentToLogin();
//                if(b!=null){
//                    navController.navigate(action.setRoutineId(b.getString("RoutineId")));
//                }
//                else
                    navController.navigate(action);
            }
        });
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
}
