package com.example.androidApp.view.profile.achievements.attachment_list;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidApp.RecyclerViewInterface;
import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Attachment;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.AttachmentApi;
import com.example.androidApp.view.contest_search.contest_list.DiffCallbackContest;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.MyViewHolder> {
    public List<Attachment> attachments;
    private final RecyclerViewInterface recyclerViewInterface;

    public AttachmentAdapter(RecyclerViewInterface recyclerViewInterface) {
        this.attachments = new ArrayList<>();
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new MyViewHolder(itemView, recyclerViewInterface);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == attachments.size() ? R.layout.attachment_list_btn : R.layout.attachment_list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == attachments.size()) return;
        Attachment item = attachments.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImageBytes(), 0, item.getImageBytes().length);
        holder.attachmentImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return attachments.size() + 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView attachmentImage;
        public ImageButton deleteAttachment;
        public ImageView addAttachment;
        public static final int CLICK = 0;
        public static final int DELETE = 1;

        public MyViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            attachmentImage = view.findViewById(R.id.attachment_image);
            deleteAttachment = view.findViewById(R.id.delete_attachment_btn);
            addAttachment = view.findViewById(R.id.add_attachment_btn);

            if (deleteAttachment != null && recyclerViewInterface != null) {
                deleteAttachment.setOnClickListener(v -> {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos, DELETE);
                    }
                });
            }

            view.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos, CLICK);
                    }
                }
            });
        }
    }
}
