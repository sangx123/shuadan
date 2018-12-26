package com.sangxiang.app.task;

import com.github.pagehelper.PageInfo;
import com.sangxiang.app.oauth2.ShiroUtils;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.base.rest.BaseResource;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.service.TaskService;
import com.sangxiang.model.HomeTaskParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Api(description="任务" )
public class TaskController extends BaseResource {
    @Autowired
    TaskService taskService;

    @PostMapping(value = "/createTask")
    @ApiOperation(value="用户登录")
    public ApiResult createTask(@RequestBody Task task){
        task.setUserid(ShiroUtils.getUserId());
        task.setCreateTime(new Date());
        task.setState(0);
        task.setTotalPrice((task.getGoodsPrice()+task.getWorkerPrice())*task.getWorkerNum());
        taskService.createTask(task);
        return success("创建成功");
    }

    @PostMapping(value = "/getHomeTask")
    @ApiOperation(value="任务大厅任务")
    public ApiResult<PageInfo<Task>> getHomeTask(@RequestBody HomeTaskParam param){
        PageInfo<Task> pageInfo= taskService.findPage(param.getPageNumber(),param.getPageSize(),param.getState());
        return success(pageInfo);
    }
}
