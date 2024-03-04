package io.smartir.smartir.website;

import com.fasterxml.jackson.databind.util.NativeImageUtil;
import io.smartir.smartir.website.entity.Article;
import io.smartir.smartir.website.entity.Tag;
import io.smartir.smartir.website.entity.Type;
import io.smartir.smartir.website.model.ArticleContentsModel;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HelperFunctions {

    public final static String TYPE_NAME = "test-type";
    public final static String TAG_NAME = "test-tag";

    public Type typeCreator() {
        Type type = new Type();
        type.setName(TYPE_NAME);
        return type;
    }
    public Tag tagCreator(Type type) {
        Tag tag = new Tag();
        tag.setName(TAG_NAME);
        tag.setType(type);
        return tag;
    }
    public Article articleCreator(List<Tag> tags, String title) {
        ArticleContentsModel model = new ArticleContentsModel("test", "test");
        Article article = new Article();
        article.setTags(tags);
        article.setImage("test-image");
        article.setTitle(title);
        article.setSummary("summary of test article");
        article.setBannerImage("test-banner-image");
        article.setContents(List.of(model));
        return article;
    }

    public List<Tag> createManyTags(List<String> tagNames, Type type) {
        List<Tag> tags = new ArrayList<>();
        tagNames.forEach(el->{
            Tag tag = new Tag();
            tag.setType(type);
            tag.setName(el);
            tags.add(tag);
        });
        return tags;
    }

    public MultipartFile createTestImage() {
        try {

            byte[] imageBytes = Files.readAllBytes(Path.of("src/test/resources/test.jpg"));

            MockMultipartFile file = new MockMultipartFile(
                    "image",
                    "test.jpg",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    imageBytes
            );
            return file;
        } catch (IOException e) {
            throw new RuntimeException("image is not found");
        }
    }
}
