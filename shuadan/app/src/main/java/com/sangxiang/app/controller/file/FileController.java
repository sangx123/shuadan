package com.sangxiang.app.controller.file;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.controller.qiniu.QiniuService;
import com.sangxiang.app.utils.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class FileController extends AppBaseController {

         // token 过期时间， 默认2小时后过期
    @Value("${upload.imagePath}")
    private String imagePath;
    /**
     * 上传图片
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
        return success("188.131.235.188/images/"+fileName);

    }
}
