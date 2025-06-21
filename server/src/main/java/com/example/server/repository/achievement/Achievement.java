package com.example.server.repository.achievement;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "achievements")
@Data
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Integer> image_ids;
    private Long user_id;
    private Long contest_id;

    @Transient
    private List<BufferedImage> images;

    public void addImage(BufferedImage image) {
        images.add(image);
    }

    public void removeImageIds(List<Long> image_ids) {
        String path2images = System.getProperty("user.dir") + "/" +
                this.user_id + "/" +
                this.contest_id;
        for (Long image_id : image_ids) {
            image_ids.remove(image_id);

            File file = new File(path2images + "/" + image_id.toString());

            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("файл не удалён, achievement id:" + this.id + " image_id: " + image_id);
            }
        }
    }
}
