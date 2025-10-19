package com.example.androidApp.model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidApp.model.dao.AchievementDao;
import com.example.androidApp.model.dao.AttachmentDao;
import com.example.androidApp.model.dao.ContestDao;
import com.example.androidApp.model.dao.SubjectDao;
import com.example.androidApp.model.dao.UserEventDao;
import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Attachment;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.Subject;
import com.example.androidApp.model.entity.UserEvent;

@Database(entities = {
        Achievement.class,
        Attachment.class,
        Contest.class,
        Subject.class,
        UserEvent.class,
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE; // volatile - update for all threads

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "olimpi_room_db").build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract AchievementDao achievementDao();

    public abstract AttachmentDao attachmentDao();

    public abstract ContestDao contestDao();

    public abstract SubjectDao subjectDao();

    public abstract UserEventDao userEventDao();

//    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase database) {
//
//        }
//    };
}
