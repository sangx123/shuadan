package com.sangxiang.app.controller.task;

import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.controller.login.UserLoginInfo;
import com.sangxiang.app.controller.login.UserLoginParam;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.app.utils.StringUtils;
import com.sangxiang.dao.mapper.SysUserMapper;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/task")
public class TaskController extends AppBaseController {
    // token 过期时间， 默认2小时后过期
    @Value("${upload.imagePath}")
    private String imagePath;

    // token 过期时间， 默认2小时后过期
    @Value("${upload.imageURL}")
    private String imageURL;

    @PostMapping("/createTask")
    public AppResult<String> createTask(@RequestParam(value = "imageList") MultipartFile files[], String content,String title ,String benJing,String jiangLi,String peopleNum) {
        List<String> imageUrlList = new ArrayList<String>();
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
                        String imageName=currentDate() + getFileType(fileName);
                        String filePath=imagePath + "/" + imageName;
                        //创建输出文件对象
                        File outFile = new File(filePath);
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                        imageUrlList.add(imageURL+imageName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return  fail(AppExecStatus.FAIL,"图片上传失败，请重新提交任务");
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
        content=StringUtils.replceImgSrc(content,imageUrlList);
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
            SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date data = new Date();
            result = dfs.format(data);
        } catch (Exception e) {
        }
        return result;
    }
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private SysUserMapper sysUserMapper;
//    /**
//     * 用户登录
//     */
//    @PostMapping("/login")
//    public AppResult<UserLoginInfo> login(@RequestBody UserLoginParam data) {
//        String pushToken = null!=data.getPushToken()?data.getPushToken():null;
//        String mobile = data.getMobile();
//        String password = data.getPassword();
//        SysUser loginInfo=null;
//        if(data.getAuthCode()!=null&&!data.getAuthCode().isEmpty()){
//            //验证码登录
//            loginInfo= sysUserMapper.fetchOneByMobile(mobile);
//
//        }else {
//            //用户名密码登录
//            checkParam(mobile, "缺少账号");
//            checkParam(password, "缺少密码");
//            loginInfo = sysUserService.authenticateName(mobile, password, pushToken);
//        }
//        if(loginInfo==null){
//            return  fail(AppExecStatus.FAIL,"用户名或手机号不存在!");
//        }
//
//        String userToken = UserTokenManager.getInstance().saveUserToken(loginInfo.getId().longValue());
//        UserLoginInfo userLoginInfo=new UserLoginInfo();
//        userLoginInfo.setUserToken(userToken);
//        return success(userLoginInfo);
//
//    }
//
//    /**
//     * 用户重置密码
//     * @param data
//     * @return
//     */
//    @PostMapping("/resetPassword")
//    public AppResult resetPassword(@RequestBody UserLoginParam data) {
//        SysUser user=null;
//        String pushToken = null!=data.getPushToken()?data.getPushToken():null;
//        String mobile = data.getMobile();
//        String password = data.getPassword();
//        checkParam(mobile, "缺少账号");
//        checkParam(password, "缺少密码");
//
//        user= sysUserMapper.fetchOneByMobile(data.getMobile());
//        if(user==null){
//            return  fail(AppExecStatus.FAIL,"该手机号不存在!");
//        }
//        String salt = RandomStringUtils.randomAlphanumeric(20);
//        //String salt="WjGGv0Mo2ozFbc5y4Olb";
//        user.setPassword(new Sha256Hash(password,salt).toHex());
//        user.setSalt(salt);
//        sysUserMapper.updateByPrimaryKey(user);
//        return success("success");
//    }
//
//
//    /**
//     * 创建用户
//     */
//    @ApiOperation(value="注册用户")
//    @PostMapping("/register")
//    // @RequiresPermissions("sys:user:save")
//    public AppResult<UserLoginInfo> register(@RequestBody SysUser sysUser){
//        int result=checkUser(sysUser);
//        if(0!=result){
//            if(result==-1) {
//                return  fail(AppExecStatus.FAIL,"用户名已存在!");
//            }
//			else if(result==-2) {
//				return  fail(AppExecStatus.FAIL,"手机号已存在!");
//			}
//        }
//        sysUser.setCreateTime(new Date());
//        //sha256加密
//        String salt = RandomStringUtils.randomAlphanumeric(20);
//        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(),salt).toHex());
//        sysUser.setSalt(salt);
//        //用户状态：0-启用；1-停用；2-锁定；
//        sysUser.setState(0);
//        sysUser.setName(sysUser.getName());
//        sysUser.setMobile(sysUser.getMobile());
//        sysUser.setMoney(0f);
//        sysUserService.addUser(sysUser);
//        String userToken = UserTokenManager.getInstance().saveUserToken(sysUser.getId().longValue());
//        UserLoginInfo userLoginInfo=new UserLoginInfo();
//        userLoginInfo.setUserToken(userToken);
//        return success(userLoginInfo);
//    }
//
//    private int checkUser(SysUser sysUser){
//        int result=0;
//        Example example=new Example(SysUser.class);
//        if(StringUtil.isNotEmpty(sysUser.getName())){
//            example.clear();
//            Example.Criteria criteria=example.createCriteria();
//            if(null!=sysUser.getId()) {
//                criteria.andNotEqualTo("id",sysUser.getId());
//            }
//            criteria.andEqualTo("name",sysUser.getName());
//            List<SysUser> list=sysUserService.selectByExample(example);
//            if(null!=list && list.size()>0) {
//                result=-1;
//                return  result;
//            }
//        }
//		if(StringUtil.isNotEmpty(sysUser.getMobile())){
//			example.clear();
//			Example.Criteria criteria=example.createCriteria();
//			if(null!=sysUser.getId()) {
//				criteria.andNotEqualTo("id",sysUser.getId());
//			}
//			criteria.andEqualTo("mobile",sysUser.getMobile());
//			List<SysUser> list=sysUserService.selectByExample(example);
//			if(null!=list && list.size()>0) {
//				result=-2;
//                return  result;
//			}
//		}
//        return  result;
//    }

}
