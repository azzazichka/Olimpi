package com.example.androidApp.view;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.example.androidApp.MainActivity;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidapp.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;


public class AuthFragment extends Fragment {
    private Button submit_button;
    private EditText email_edit_text, password_edit_text;
    private SwitchCompat mode_switch;
    private ContentLoadingProgressBar loading_progress_bar;
    private Activity parentActivity;

    public AuthFragment() {
        super(R.layout.fragment_auth);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentActivity = requireActivity();

        submit_button = view.findViewById(R.id.submit);
        email_edit_text = view.findViewById(R.id.email);
        password_edit_text = view.findViewById(R.id.password);
        mode_switch = view.findViewById(R.id.mode);
        loading_progress_bar = parentActivity.findViewById(R.id.loading);

        submit_button.setOnClickListener(v -> {
            submit(view);
        });
    }

    private void submit(@NonNull View view) {
        ((MainActivity) requireActivity()).showLoad(loading_progress_bar);

        parentActivity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final boolean LOGIN_MODE = !mode_switch.isChecked();
        if (LOGIN_MODE) {
            Thread logInThread = getLogInThread(view);
            logInThread.start();
        } else {
            Thread registerThread = getRegisterThread(view);
            registerThread.start();
        }
    }

    @NonNull
    private Thread getRegisterThread(@NonNull View view) {
        Runnable registerTask = () -> {
            try {
                Response<ResponseBody> response = UserAuth.getInstance().register(email_edit_text.getText().toString(), password_edit_text.getText().toString());
                if (response.isSuccessful()) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        mode_switch.setChecked(false);
                        Toast.makeText(view.getContext(), "Аккаунт создан успешно", Toast.LENGTH_LONG).show();
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(view.getContext(), "Данный email занят", Toast.LENGTH_LONG).show();
                    });
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            new Handler(Looper.getMainLooper()).post(() -> {
                ((MainActivity) requireActivity()).hideLoad(loading_progress_bar);
            });
        };
        return new Thread(registerTask);
    }

    @NonNull
    private Thread getLogInThread(@NonNull View view) {
        Runnable logInTask = () -> {
            try {
                Response<String> response = UserAuth.getInstance().logIn(email_edit_text.getText().toString(), password_edit_text.getText().toString());
                if (response.isSuccessful()) {
                    assert getActivity() != null;

                    SharedPreferences sharedPref = getActivity().getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    String key = response.body();

                    editor.putString("api_key", key);
                    editor.apply();

                    assert key != null;
                    UserAuth.getInstance().userAuth(key);

                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(view.getContext(), "Неправильная почта/пароль", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                ((MainActivity) requireActivity()).hideLoad(loading_progress_bar);
            });
        };

        return new Thread(logInTask);
    }
}
