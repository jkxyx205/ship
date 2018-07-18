package com.yodean.oa.common.plugin.document.service;

import com.yodean.oa.common.Global;
import com.yodean.oa.common.plugin.document.entity.Document;
import com.yodean.oa.common.plugin.document.enums.FileType;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rick on 2018/3/22.
 */
@Service
public class DocumentHandler {

    private static Logger logger = LoggerFactory.getLogger(DocumentHandler.class);

    public static final String FOLDER_SEPARATOR = "/"; // path分割符

    @Resource
    private ImageHandler imageHandler;

    /***
     * 获取上传文件基本信息
     * @param file
     * @return
     */
    private Document getInfoFromFile(MultipartFile file) {
        Document document = new Document();
        document.setFullName(file.getOriginalFilename());
        document.setFileType(FileType.FILE);
        document.setContentType(file.getContentType());
        document.setSize(file.getSize());

        return document;
    }


    /***
     * 存储文件
     * @param folder
     * @param file
     * @return
     */
    public Document store(String folder, MultipartFile file) throws IOException {
//        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String uuidName = UUID.randomUUID().toString();
        String uuidFullName = uuidName; //!文件存储,不需要带后缀。处理重命名的问题。如果带后缀，重命名下载会有问题

        File folderPath = new File(Global.DOCUMENT + File.separator + folder);
        FileUtils.forceMkdir(folderPath);

        File srcFile = new File(folderPath, uuidFullName); //图片存储路径

		file.transferTo(srcFile);

        Document doc = getInfoFromFile(file);

        doc.setPath((folder+ File.separator + uuidName).replace(File.separator, FOLDER_SEPARATOR));

        if (doc.getContentType().startsWith("image")) {//处理图片文件
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                try {
                    imageHandler.compress(doc);

                    //自动裁剪1:1
//                    ImageDocument image = new ImageDocument(doc);
//                    imageHandler.cropPic(folder, image, 1, 1);
                    //修改裁剪图名称
//                    FileUtils.moveFile(new File(image.getFileAbsouteThumbnailSmallPath()),
//                            new File(Global.DOCUMENT + File.separator + folder, image.getName() + "-thumbnail-small-1x1." + image.getExt()));
                } catch (IOException e) {
                    logger.error("image[{}] compress error", doc.getId());
                }
            });
        }

        return doc;

    }



}
