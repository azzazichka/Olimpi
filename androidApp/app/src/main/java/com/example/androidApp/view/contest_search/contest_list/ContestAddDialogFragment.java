package com.example.androidApp.view.contest_search.contest_list;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidapp.R;

import java.util.Calendar;

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
    ImageButton clear_date;
    TextView date_text_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contest = (Contest) getArguments().getSerializable("contest");
        }
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
        date_picker.setOnClickListener(v -> {
            showDatePickers();
        });

        close_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }

    private TimePickerDialog createTimePickerDialog(Calendar currentCalendar,
                                                    TimePickerDialog.OnTimeSetListener listener) {
        return new TimePickerDialog(
                requireContext(),
                listener,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);
    }
    private void showDatePickers() {
        Calendar currentCalendar = Calendar.getInstance();

        TimePickerDialog timeEndPickerDialog = createTimePickerDialog(currentCalendar,
            (TimePicker view, int hourOfDay, int minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                userEvent.setEnd_time(date.getTime());

                date_text_view.setVisibility(VISIBLE);
                String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                        '\n' + DateConverter.date2String(userEvent.getStart_time(), "hh:mm") +
                        "-" + DateConverter.date2String(userEvent.getEnd_time(), "hh:mm");
                date_text_view.setText(text);
                clear_date.setVisibility(VISIBLE);
        });
        TimePickerDialog timeStartPickerDialog = createTimePickerDialog(currentCalendar,
            (TimePicker view, int hourOfDay, int minute) -> {

                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                userEvent.setStart_time(date.getTime());
                view.setVisibility(GONE);
                timeEndPickerDialog.show();
        });
        DatePickerDialog dayPickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    view.setVisibility(GONE);
                    timeStartPickerDialog.show();
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));;
        dayPickerDialog.show();
    }
}
