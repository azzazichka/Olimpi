package com.example.androidApp.view;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.androidApp.MainActivity;
import com.example.androidApp.presenter.UserAuth;
import com.example.androidapp.R;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Response;


public class AuthFragment extends Fragment {
    private Button submit_button;
    private EditText email_edit_text, password_edit_text;
    private SwitchCompat mode_switch;
    private ProgressBar loading_progress_bar;

    public AuthFragment() {
        super(R.layout.auth_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit_button = view.findViewById(R.id.submit);
        email_edit_text = view.findViewById(R.id.email);
        password_edit_text = view.findViewById(R.id.password);
        mode_switch = view.findViewById(R.id.mode);
        loading_progress_bar = view.findViewById(R.id.loading);

        submit_button.setOnClickListener(v -> {
            submit(view);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void submit(@NonNull View view) {
        loading_progress_bar.setVisibility(VISIBLE);

        submit_button.setClickable(false);
        mode_switch.setClickable(false);

        final boolean LOGIN_MODE = !mode_switch.isChecked();
        if (LOGIN_MODE) {
            Thread logInThread = getLogInThread(view);
            logInThread.start();
        } else {
            try {
                UserAuth.register(email_edit_text.getText().toString(), password_edit_text.getText().toString());
                mode_switch.setChecked(false);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @NonNull
    private Thread getLogInThread(@NonNull View view) {
        Runnable logInTask = () -> {
            try {
                Response<String> response = UserAuth.logIn(email_edit_text.getText().toString(), password_edit_text.getText().toString());
                if (response.isSuccessful()) {
                    assert getActivity() != null;
                    SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove("api-key");
                    String key = response.body();
                    editor.putString("api_key", key);
                    editor.apply();

                    Log.i("AZZA", "key: " + key);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .remove(this)
                            .commit();
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(view.getContext(), "Неправильная почта/пароль", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                submit_button.setClickable(true);
                mode_switch.setClickable(true);
                loading_progress_bar.setVisibility(GONE);
            });
        };

        return new Thread(logInTask);
    }
}
