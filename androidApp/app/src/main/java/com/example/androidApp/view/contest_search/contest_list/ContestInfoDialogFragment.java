package com.example.androidApp.view.contest_search.contest_list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidApp.model.entity.Contest;
import com.example.androidapp.R;

public class ContestInfoDialogFragment extends DialogFragment {

    public static ContestInfoDialogFragment newInstance(Contest contest) {
        ContestInfoDialogFragment fragment = new ContestInfoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("contest", contest);
        fragment.setArguments(args);
        return fragment;
    }

    Contest contest;

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
        View view = inflater.inflate(R.layout.fragment_dialog_contest_info, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }

        TextView contest_title = view.findViewById(R.id.contest_title_dialog);
        TextView contest_lvl = view.findViewById(R.id.contest_lvl_dialog);
        TextView contest_info = view.findViewById(R.id.contest_info_dialog);
        ImageButton close_btn = view.findViewById(R.id.btn_close_dialog);
        ImageButton add_btn = view.findViewById(R.id.btn_add_dialog);


        if (contest != null) {
            contest_title.setText(contest.getTitle());
            contest_lvl.setText(contest.getLvl().toString() + "ур");
            contest_info.setText(contest.toString());
        }

        close_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            ContestAddDialogFragment customDialog = ContestAddDialogFragment.newInstance(contest);
            customDialog.show(getParentFragmentManager(), "contest add dialog");
            dismiss();
        });
        return view;
    }
}
