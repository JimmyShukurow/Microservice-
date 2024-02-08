package io.smartir.smartir.website.service;


import io.smartir.smartir.website.config.BeanNames;
import io.smartir.smartir.website.exceptions.UnsupportedImageTypeException;
import io.smartir.smartir.website.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Slf4j
public class FileService {

    private final Path uploadImagePath;
    private final Path uploadBannerImagePath;

    public FileService(@Qualifier(BeanNames.ARTICLE_IMAGE_UPLOAD_PATH_BEAN_NAME) Path uploadImagePath,
                       @Qualifier(BeanNames.ARTICLE_BANNER_IMAGE_UPLOAD_PATH_BEAN_NAME) Path uploadBannerImagePath) {
        this.uploadImagePath = uploadImagePath;
        this.uploadBannerImagePath = uploadBannerImagePath;
    }

    public Path moveBannerImageFile(MultipartFile multipartFile) throws Exception {
        var fileExt = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        Optional.of(FileUtil.isSupportedImageType(fileExt)).filter(e -> e)
                .orElseThrow(UnsupportedImageTypeException::new);
        var fileName = String.format("%s_%s.%s", fileExt, String.valueOf(System.currentTimeMillis()), fileExt);
        var uploadedFileLocation = uploadBannerImagePath.resolve(fileName.substring(0,17)+"/"+fileName);
        File uploadFile = new File("BannerImage/"+fileName.substring(0,17)+"/");
        uploadFile.mkdir();
        multipartFile.transferTo(uploadedFileLocation);
        return uploadedFileLocation;
    }

    public Path moveImgFile(MultipartFile multipartFile) throws Exception {
        var fileExt = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        Optional.of(FileUtil.isSupportedImageType(fileExt)).filter(e -> e)
                .orElseThrow(UnsupportedImageTypeException::new);
        var fileName = String.format("%s_%s.%s", fileExt, String.valueOf(System.currentTimeMillis()), fileExt);
        var uploadedFileLocation = uploadImagePath.resolve(fileName.substring(0,17)+"/"+fileName);
        System.out.println("asdasd:"+uploadedFileLocation);
        File uploadFile = new File("Image/"+fileName.substring(0,17)+"/");
        uploadFile.mkdir();
        multipartFile.transferTo(uploadedFileLocation);
        return uploadedFileLocation;
    }

}
