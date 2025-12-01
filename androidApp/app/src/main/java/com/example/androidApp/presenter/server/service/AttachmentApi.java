package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Attachment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttachmentApi {
    @GET("/api/attachments")
    Single<List<Attachment>> getAttachments(@Query("achievementId") Long achievementId);
}
