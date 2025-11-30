package com.example.androidApp.view;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.androidApp.MainActivity;
import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.presenter.server.service.UserApi;
import com.example.androidApp.presenter.server.service.UserKeyApi;
import com.example.androidapp.R;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class AuthFragment extends Fragment {
    private EditText email_edit_text, password_edit_text;
    private SwitchCompat mode_switch;
    private SharedPreferences.Editor editor;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AuthFragment() {
        super(R.layout.fragment_auth);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editor = requireActivity().getPreferences(MODE_PRIVATE).edit();

        Button submit_button = view.findViewById(R.id.submit);
        email_edit_text = view.findViewById(R.id.email);
        password_edit_text = view.findViewById(R.id.password);
        mode_switch = view.findViewById(R.id.mode);

        submit_button.setOnClickListener(v -> {
            submit(view);
        });
    }

    private void submit(@NonNull View view) {
        MainActivity mainActivity = (MainActivity) requireActivity();
        String email = email_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();
        final boolean LOGIN_MODE = !mode_switch.isChecked();

        if (LOGIN_MODE) {
            UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);

            compositeDisposable.add(
                    RequestGenerator.getInstance().makeApiCall(
                            "Вход успешен",
                            "Неправильная почта/пароль",
                            userKeyApi.getUserKey(email, password),
                            key -> {

                                editor.putString("api_key", key);
                                editor.apply();

                                assert key != null;
                                UserAuth.getInstance().userAuth(key);
                            })
            );
        } else {
            UserApi userApi = ServiceGenerator.createService(UserApi.class);
            User user = new User(null, "", email, password, 0);

            compositeDisposable.add(
                    RequestGenerator.getInstance().makeApiCall(
                            "Регистрация успешна",
                            "Ошибка",
                            userApi.registerUser(user),
                            response -> {
                                mode_switch.setChecked(false);
                            })
            );
        }
    }
    // TODO: check time sleep moment

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
