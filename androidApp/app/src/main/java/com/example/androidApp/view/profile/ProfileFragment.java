package com.example.androidApp.view.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidApp.MainActivity;
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

        TextView user_info = view.findViewById(R.id.user_info);
        user_info.setText(UserAuth.getInstance().getUser().toString());

        log_out_button.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).logOutUser();
        });
    }
}