package com.sangxiang.app.controller.file;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.controller.qiniu.QiniuService;
import com.sangxiang.app.utils.AppResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileController extends AppBaseController {

    // token 过期时间， 默认2小时后过期
    @Value("${upload.imagePath}")
    private String imagePath;

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadImage")
    public AppResult uploadImage(@RequestParam(value = "image") MultipartFile file) throws Exception {

        String fileName = "";
        if (!file.isEmpty()) {
            BufferedOutputStream out = null;
            File fileSourcePath = new File(imagePath);

            if (!fileSourcePath.exists()) {
                fileSourcePath.mkdirs();
            }
            fileName = file.getOriginalFilename();
            //LOGGER.info("上传的文件名为：" + fileName);
            out = new BufferedOutputStream(
                    new FileOutputStream(new File(fileSourcePath, fileName)));
            out.write(file.getBytes());
            out.flush();
            System.out.println(fileName.toString());
        }
        return success("188.131.235.188/images/" + fileName);

    }

    @PostMapping("/uploadImageList")
    public AppResult<String> uploadImageList(@RequestParam(value = "imageList") MultipartFile files[]) {
        File uploadDirectory = new File(imagePath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        //这里可以支持多文件上传
        if (files != null && files.length >= 1) {
            BufferedOutputStream bw = null;
            try {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                        //创建输出文件对象
                        File outFile = new File(imagePath + "/" + currentDate() + getFileType(fileName));
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success("upload successful");
    }

    private Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件后缀名     *     * @param fileName     * @return
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }

    /**
     * 获取当前时间 年月日时分秒毫秒
     **/
    public static String currentDate() {
        String result = "";
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd_HHmm_ssSSS");
            Date data = new Date();
            result = dfs.format(data);
        } catch (Exception e) {
        }
        return result;
    }

}
