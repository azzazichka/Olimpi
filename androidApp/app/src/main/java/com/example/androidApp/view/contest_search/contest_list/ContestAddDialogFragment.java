package com.example.androidApp.view.contest_search.contest_list;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidApp.MainActivity;
import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.ContestApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidapp.R;

import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ContestAddDialogFragment extends DialogFragment {
    public static ContestAddDialogFragment newInstance(Contest contest) {
        ContestAddDialogFragment fragment = new ContestAddDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("contest", contest);
        fragment.setArguments(args);
        return fragment;
    }

    Contest contest;
    UserEvent userEvent = new UserEvent();
    Calendar date = Calendar.getInstance();
    ImageButton clear_date, clear_notification;
    TextView date_text_view, notification_text_view;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contest = (Contest) getArguments().getSerializable("contest");
        }
        userEvent.setUser_id(UserAuth.getInstance().getUser().getId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_contest_add, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }

        TextView contest_title = view.findViewById(R.id.contest_title_dialog);
        ImageButton close_btn = view.findViewById(R.id.btn_close_dialog);
        ImageButton add_btn = view.findViewById(R.id.btn_add_dialog);

        clear_date = view.findViewById(R.id.btn_clear_date);
        date_text_view = view.findViewById(R.id.date_text_view);
        TextView date_picker = view.findViewById(R.id.date_picker);

        clear_notification = view.findViewById(R.id.btn_clear_notification);
        notification_text_view = view.findViewById(R.id.notification_text_view);
        TextView notification_picker = view.findViewById(R.id.notification_picker);

        if (contest != null) {
            contest_title.setText(contest.getTitle());
            userEvent.setTitle(contest.getTitle());
            userEvent.setContest_id(contest.getId());
        }

        clear_date.setOnClickListener(v -> {
            date_text_view.setText("");
            clear_date.setVisibility(GONE);
            date_text_view.setVisibility(GONE);
            userEvent.setStart_time(null);
            userEvent.setEnd_time(null);
        });

        clear_notification.setOnClickListener(v -> {
            notification_text_view.setText("");
            clear_notification.setVisibility(GONE);
            notification_text_view.setVisibility(GONE);
            userEvent.setNotification_time(null);
        });

        date_picker.setOnClickListener(v -> showDatePickers());
        notification_picker.setOnClickListener(v -> showNotificationPickers());


        close_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);
            AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
            MainActivity mainActivity = (MainActivity) getActivity();
            Achievement achievement = new Achievement(userEvent.getUser_id(), userEvent.getContest_id());

            compositeDisposable.add(
                    RequestGenerator.getInstance().getDisposable(
                            "Олимпиада добавлена",
                            userEventApi.createUserEvent(userEvent)
                                    .flatMap(r -> achievementApi.createAchievement(achievement)),
                            r -> dismiss())
            );


        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    private void showDatePickers() {
        Calendar currentCalendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener listenerTimeEnd =
        (view, hourOfDay, minute) -> {
            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
            date.set(Calendar.MINUTE, minute);
            userEvent.setEnd_time(date.getTime());

            date_text_view.setVisibility(VISIBLE);
            String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                    '\n' + DateConverter.date2String(userEvent.getStart_time(), "HH:mm") +
                    "-" + DateConverter.date2String(userEvent.getEnd_time(), "HH:mm");
            date_text_view.setText(text);
            clear_date.setVisibility(VISIBLE);
            view.setVisibility(GONE);
        };


        TimePickerDialog timeEndPickerDialog = new TimePickerDialog(
                requireContext(),
                listenerTimeEnd,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);


        TimePickerDialog.OnTimeSetListener listenerTimeStart =
                (view, hourOfDay, minute) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute);
                    userEvent.setStart_time(date.getTime());
                    timeEndPickerDialog.show();
                    view.setVisibility(GONE);
                };


        TimePickerDialog timeStartPickerDialog = new TimePickerDialog(
                requireContext(),
                listenerTimeStart,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);

        DatePickerDialog.OnDateSetListener listenerDay =
            (view, year, month, dayOfMonth) -> {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                timeStartPickerDialog.show();
                view.setVisibility(GONE);
            };


        DatePickerDialog dayPickerDialog = new DatePickerDialog(
                requireContext(),
                listenerDay,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));
        dayPickerDialog.show();
    }

    private void showNotificationPickers() {
        Calendar currentCalendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener listenerTime =
                (view, hourOfDay, minute) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute);
                    userEvent.setNotification_time(date.getTime());

                    notification_text_view.setVisibility(VISIBLE);

                    String text = DateConverter.date2String(userEvent.getNotification_time(), "dd.MM.yy") +
                            " " + DateConverter.date2String(userEvent.getNotification_time(), "HH:mm");
                    notification_text_view.setText(text);
                    clear_notification.setVisibility(VISIBLE);
                    view.setVisibility(GONE);
                };


        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                listenerTime,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);

        DatePickerDialog.OnDateSetListener listenerDay =
                (view, year, month, dayOfMonth) -> {
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    timePickerDialog.show();
                    view.setVisibility(GONE);
                };


        DatePickerDialog dayPickerDialog = new DatePickerDialog(
                requireContext(),
                listenerDay,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));
        dayPickerDialog.show();
    }
}
