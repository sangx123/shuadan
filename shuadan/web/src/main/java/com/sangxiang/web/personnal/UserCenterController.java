package com.sangxiang.web.personnal;

import com.sangxiang.web.oauth2.ShiroUtils;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.base.rest.BaseResource;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.model.UserAlipayTixian;
import com.sangxiang.dao.service.TaskService;
import com.sangxiang.dao.service.UserAlipayTixianService;
import com.sangxiang.dao.service.UserTaskService;
import com.sangxiang.model.UserCenter.UserOverView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 个人中心
 */
@RestController
@Api(description="个人中心" )
public class UserCenterController extends BaseResource {
    @Autowired
    UserTaskService userTaskService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserAlipayTixianService userAlipayTixianService;

    /**
     *个人中心概览
     */
    @GetMapping(value = "/getUserOverView")
    @ApiOperation(value="个人中心概览")
    public ApiResult<UserOverView> getUserOverView() {
        UserOverView userOverView=new UserOverView();
        userOverView.setMoney(ShiroUtils.getUser().getMoney());

        List<Task> list1=userTaskService.getUserTask(ShiroUtils.getUserId(),0);
        int num1=list1==null?0:list1.size();
        userOverView.setGetTaskNum(num1);

        List<Task> list2=taskService.getShanhuTask(ShiroUtils.getUserId(),0);
        int num2=list2==null?0:list2.size();
        userOverView.setPubTaskNum(num2);

        List<UserAlipayTixian> list3=userAlipayTixianService.getTixianByUser(ShiroUtils.getUserId(),0);
        int num3=list3==null?0:list3.size();
        userOverView.setTixianTaskNum(num3);
        return 	success(userOverView);
    }

}
