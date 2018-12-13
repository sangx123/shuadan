package com.sangxiang.util;

import com.sangxiang.config.ApiConfig;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Component
public class UploadUtil {
	
	/* 
	 * Java文件操作 获取不带扩展名的文件名 
	 * 
	 *  Created on: 2011-8-2 
	 *      Author: blueeagle 
	 */  
	    public String getFileNameNoEx(String filename) {   
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');   
	            if ((dot >-1) && (dot < (filename.length()))) {   
	                return filename.substring(dot + 1, filename.length());   
	            }   
	        }   
	        return filename;   
	    }

    /**
     * 上传
     * @param request
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> upload(HttpServletRequest request, String docBase, String path) {
    	
    	ApiConfig config = SpringUtils.getBean(ApiConfig.class);
		
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
		 //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
        	//转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
            	Map<String,Object> fMap = new HashMap<String,Object>();
            	String filepath = "";
            	//取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                
                if (file != null) 
                {
                	String extName = getFileNameNoEx(file.getOriginalFilename());
                	long fileSize = file.getSize();
//					// 重命名上传后的文件名
//                	String nowName = getNewName(extName);
//					final ByteArrayOutputStream srcImageData = new ByteArrayOutputStream();
//					try {
//						IOUtils.copy(file.getInputStream(), srcImageData);
//						// 上传图片
//						FileUtils.copyInputStreamToFile(new ByteArrayInputStream(srcImageData.toByteArray()), new File(getFilePath(docBase, nowName)));
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					filepath = getFilePath(path, nowName);
//                	try 
//                	{
//                		if(fileSize>0)
//                		{
////                			filepath = FilesysUtils.uploadFileByInputStream(file.getInputStream(), extName, fileSize);
//                			
//                		}
//					} 
//                	catch (IOException e) 
//                	{
//						e.printStackTrace();
//					}
                	//TODO  图片上传处理
                	
				}
                if(StrUtil.isNotEmpty(filepath))
                {
                	fMap.put("fileUrl", config.getFileUrl());
                	fMap.put("filePath", filepath);
                	res.add(fMap);
                }
            }
        }
		return res;
	}

    /**
     * 获取文件绝对路径
     * @param request
     * @param host
     * @return
     */
    public static String getFileUrl(HttpServletRequest request, String host) {
        String path = request.getScheme() + "://" + host + request.getContextPath() + "/";
        int port = request.getServerPort();
        if (80 != port) {
            path = request.getScheme() + "://" + host + ":" + request.getServerPort() + request.getContextPath() + "/";
        }
        return path;
    }

    /**
     * 文件路径
     * @param type
     * @return
     */
    public String getFilePath(String filepath, String filename) {
    	StringBuilder sb = new StringBuilder(filepath).append(DateUtil.format(new Date(), "yyyyMMdd")).append("/").append(filename);
        return sb.toString();
    }
    
    /**
     * 文件重命名
     * @param suffixes
     * @return
     */
    public String getNewName(String suffixes){
    	return suffixes + "/" + UUID.randomUUID().toString() + "." + suffixes;
    }

    /**
     * 删除文件
     * @param path
     */
    public static void delFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
