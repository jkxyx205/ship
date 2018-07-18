package com.yodean.oa.common.plugin.document.service;

import com.yodean.oa.common.Global;
import com.yodean.oa.common.plugin.document.dto.ImageDocument;
import com.yodean.oa.common.plugin.document.entity.Document;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rick on 2018/3/22.
 */
@Service
public class ImageHandler {

    private static Logger logger = LoggerFactory.getLogger(ImageHandler.class);

    private static final double COMPRESS_SIZE = 500 * 1024;

    private static final double COMPRESS_SMALL_SIZE = 50 * 1024;

    /***
     * 图片裁剪
     * @param folder
     * @param image
     * @param position
     * @param w
     * @param h
     * @param aspectRatioW
     * @param aspectRatioH
     * @return
     * @throws IOException
     */
    public Document cropPic(String folder, ImageDocument image, Position position, int w, int h, int aspectRatioW, int aspectRatioH) throws IOException {
        if (!image.getContentType().startsWith("image")) {
            return image;
        }

        String uuidName = UUID.randomUUID().toString();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        String path = image.getFileAbsouteThumbnailPath(); //裁剪缩略图，不是原图

        //TODO 多线程是否有问题
        executor.submit(() -> {
            try {
                Integer[] wh = cropSize(w, h, aspectRatioW, aspectRatioH);

                File file = new File(path);

                String uuidFullName = uuidName + "." + image.getExt();
                File folderPath = new File(Global.DOCUMENT + File.separator + folder);
                FileUtils.forceMkdir(folderPath);
                File imageFile = new File(folderPath, uuidFullName);

                Thumbnails.of(file)
                        .size(wh[0], wh[1])
                        .sourceRegion(position, wh[0], wh[1])
//						.scale(.5)
                        .toFile(imageFile);
            } catch (IOException e) {
                logger.error("image[{}] crop error", image.getId());
            }
        });

        image.setName(uuidName);
        image.setSource(image.getUrlPath());
        image.setThumbnail(image.getUrlThumbnailPath());
        image.setThumbnailSmall(image.getUrlThumbnailSmallPath());

        //setPath必须放到最后一个
        image.setPath((folder + File.separator + uuidName).replace(File.separator, DocumentHandler.FOLDER_SEPARATOR));

        return image;
    }

    public Document cropPic(String folder, ImageDocument image, int x, int y, int w, int h, int aspectRatioW, int aspectRatioH) throws IOException {
        Position position = new Coordinate(x, y);
        return cropPic(folder, image, position, w, h, aspectRatioW, aspectRatioH);
    }



    public Document cropPic(String folder, ImageDocument image, int aspectRatioW, int aspectRatioH) throws IOException {
                File file = new File(image.getFileAbsouteThumbnailPath());
                BufferedImage srcImage = ImageIO.read(file);
        return cropPic(folder, image, Positions.CENTER,srcImage.getWidth(), srcImage.getHeight(), aspectRatioW, aspectRatioH);
    }




    private static Integer[] cropSize(Integer w, Integer h, int aspectRatioW, int aspectRatioH) {
        if(aspectRatioW == 0 || aspectRatioH == 0)
            return new Integer[]{w, h};

        if (w * aspectRatioH != h * aspectRatioW) {

            int r;

            if (w * aspectRatioH > h * aspectRatioW) {
                r = h / aspectRatioH;
            } else {
                r = w / aspectRatioW;
            }

            w = aspectRatioW * r;
            h = aspectRatioH * r;
        }

        Integer[] arr = new Integer[2];
        arr[0] = w;
        arr[1] = h;
        return arr;

    }

    /***
     * 图片压缩
     */
    public void compress(Document doc) throws IOException {
        String ext = doc.getExt();

        File srcFile = new File(doc.getFileAbsolutePath());
        File folder = srcFile.getParentFile();
        String uuidName = doc.getPath().substring(doc.getPath().lastIndexOf(DocumentHandler.FOLDER_SEPARATOR));

        //图片过大保存缩略图
        String fileStoreThumbnailName = uuidName + "-thumbnail." + ext;
        String fileStoreThumbnailSmallName = uuidName + "-thumbnail-small." + ext;

        File thumbnailFile = new File(folder, fileStoreThumbnailName);
        File thumbnailSmallFile = new File(folder, fileStoreThumbnailSmallName);

        if(doc.getSize() > COMPRESS_SIZE) {
            Thumbnails.of(srcFile)
                    .size(1920, 1080)
                    .toFile(thumbnailFile);

        } else {
            org.apache.commons.io.FileUtils.copyFile(srcFile, thumbnailFile);
        }

        if(doc.getSize() > COMPRESS_SMALL_SIZE) {

            //small picture
            Thumbnails.of(srcFile)
                    .size(400, 400)
                    .toFile(thumbnailSmallFile);

        } else {
            org.apache.commons.io.FileUtils.copyFile(srcFile, thumbnailSmallFile);
        }
    }
}