package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Attachment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttachmentApi {
    @GET("/api/attachments")
    Single<List<Attachment>> getAttachments(@Query("achievementId") Long achievementId);

    @POST("/api/attachments")
    Single<Long> createAttachment(@Body Attachment attachment);

    @DELETE("/api/attachments")
    Single<ResponseBody> deleteAttachment(@Query("attachmentId") Long attachmentId);
}
