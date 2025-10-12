package com.example.androidApp.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidApp.presenter.UserAuth;
import com.example.androidapp.R;

public class ProfileFragment extends Fragment {
    Button log_out_button;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        log_out_button = view.findViewById(R.id.log_out);

        assert getActivity() != null;
        SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);

        log_out_button.setOnClickListener(v -> {
            logOutUser(sharedPref);
        });
    }

    private void logOutUser(SharedPreferences sharedPref) {
        UserAuth.logOut();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("api_key", "");
        editor.apply();

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, AuthFragment.class, null)
                .commit();
    }
}