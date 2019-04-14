package com.sangxiang.app.controller.task;

import com.github.pagehelper.PageInfo;
import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.app.utils.StringUtils;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserTask;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.dao.service.TaskService;
import com.sangxiang.dao.service.UserTaskService;
import com.sangxiang.model.ApplyTaskParam;
import com.sangxiang.model.Login.HomeTaskParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController extends AppBaseController {
    // token 过期时间， 默认2小时后过期
    @Value("${upload.imagePath}")
    private String imagePath;

    // token 过期时间， 默认2小时后过期
    @Value("${upload.imageURL}")
    private String imageURL;

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    TaskService taskService;

    @Autowired
    SysUserService sysUserService;
    /**
     * 创建任务
     * @param files
     * @param content
     * @param title
     * @param benJing
     * @param jiangLi
     * @param peopleNum
     * @return
     */
    @PostMapping("/createTask")
    public AppResult<String> createTask(@RequestHeader("userToken") String userToken,@RequestParam(value = "imageList") MultipartFile files[], String content,String title ,String benJing,String jiangLi,String peopleNum,String type) {
        int userId = UserTokenManager.getInstance().getUserIdFromToken(userToken).intValue();
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



        Task task=new Task();
        task.setTitle(title);
        task.setContent(content);
        task.setCreateTime(new Date());
        task.setUserid(userId);
        task.setGoodsPrice(Float.parseFloat(benJing));
        task.setWorkerPrice(Float.parseFloat(jiangLi));
        task.setWorkerNum(Integer.valueOf(peopleNum));
        task.setTotalPrice((task.getGoodsPrice()+task.getWorkerPrice())*task.getWorkerNum());
        task.setType(type);
        task.setState(0);

        SysUser sysUser=sysUserService.queryUser(userId);
        task.setUsername(sysUser.getMobile());
        taskService.createTask(task);
        return success("任务创建成功");
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


    @PostMapping(value = "/getHomeTaskList")
    @ApiOperation(value="任务大厅任务")
    public AppResult<PageInfo<Task>> getHomeTaskList(@RequestBody HomeTaskParam param){
        PageInfo<Task> pageInfo= taskService.findPage(param.getPageNumber(),param.getPageSize(),param.getState());
        for(int i=0;i<pageInfo.getList().size();i++){
            Task item=pageInfo.getList().get(i);
            List<String> list=new ArrayList<>();
            if(item.getContent().contains("<img")){
                List<String> textList = StringUtils.cutStringByLineTag(item.getContent());
                for (int j = 0; j <textList.size()&&j<3 ; j++) {
                    String  text = textList.get(j);
                    if(text.contains("<img")){
                        list.add(StringUtils.getImgSrc(text));
                    }
                }
            }
            item.setImages(list);
        }
        return success(pageInfo);
    }


    @PostMapping(value = "/getMyPublishTaskList")
    @ApiOperation(value="我发布的任务列表")
    public AppResult<PageInfo<Task>> getMyPublishTaskList(@RequestHeader("userToken") String userToken,@RequestBody HomeTaskParam param){
        int userId = UserTokenManager.getInstance().getUserIdFromToken(userToken).intValue();
        PageInfo<Task> pageInfo= taskService.findAllUserPublishTaskList(param.getPageNumber(),param.getPageSize(),userId);
        for(int i=0;i<pageInfo.getList().size();i++){
            Task item=pageInfo.getList().get(i);
            List<String> list=new ArrayList<>();
            if(item.getContent().contains("<img")){
                List<String> textList = StringUtils.cutStringByLineTag(item.getContent());
                for (int j = 0; j <textList.size()&&j<3 ; j++) {
                    String  text = textList.get(j);
                    if(text.contains("<img")){
                        list.add(StringUtils.getImgSrc(text));
                    }
                }
            }
            item.setImages(list);
        }
        return success(pageInfo);
    }


    @PostMapping(value = "/applyTask")
    @ApiOperation(value="申请任务")
    public synchronized AppResult applyTask(@RequestHeader("userToken") String userToken,@RequestBody ApplyTaskParam param){
        int userId = UserTokenManager.getInstance().getUserIdFromToken(userToken).intValue();
        Task task=  taskService.queryById(param.getTaskid());

        //首先判断该任务是否结束
        if(task.getState()==1){
            return fail(AppExecStatus.FAIL,"任务已结束或者关闭");
        }

        //先判断任务是否达到上限
        if(task.getWorkerNum()<=task.getWorkingNum()){
            return fail(AppExecStatus.FAIL,"申请人数已达到上限!");
        }

        //是否是自己在接收任务
        if(task.getUserid()==userId){
            return fail(AppExecStatus.FAIL,"自己不能申请自己发布的任务!");
        }

        UserTask userTask=userTaskService.hasApplyTask(userId,task.getId());
        //判断用户是否已经申请过该任务
        if(userTask!=null){
            return fail(AppExecStatus.FAIL,"您已经做过该任务了不能再做该任务!");
        }

        userTaskService.createUserTask(task,userId);
        return success("任务申请成功，请等待审核！");
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
}
