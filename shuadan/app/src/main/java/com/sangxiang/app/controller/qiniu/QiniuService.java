package com.sangxiang.app.controller.qiniu;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sangxiang.app.config.QiNiuConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Service
public class QiniuService {
    @Autowired
    private Auth qiNiuAuth;

    @Autowired
    private QiNiuConfig qiNiuConfig;

    private static final Logger logger = LoggerFactory.getLogger (QiniuService.class);
    
	public String getCommonToken() {
		 StringMap policy = new StringMap();
	     String returnBody = "{\"url\":\"" + qiNiuConfig.getBaseUrl() + "/$(key)\",\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}";
	     policy.put("returnBody", returnBody);
	     return qiNiuAuth.uploadToken(qiNiuConfig.getBucket(), null, qiNiuConfig.getExpires(), policy);
	}
	
	
    public String upload(HttpServletRequest request) throws IOException, ServletException {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        Part part = request.getPart("customFileName");
        String path =request.getServletContext().getRealPath(File.separator + "cache");

        part.write(path);

        File file = new File(path);

        Response response = uploadManager.put(file, null, this.getCommonToken(), null, null, false);
        file.delete();
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return qiNiuConfig.getBaseUrl()+"/"+putRet.key;
    }
}
