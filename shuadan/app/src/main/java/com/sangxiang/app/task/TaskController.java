package com.sangxiang.app.task;

import com.github.pagehelper.PageInfo;
import com.sangxiang.app.oauth2.ShiroUtils;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.base.rest.BaseResource;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.service.TaskService;
import com.sangxiang.dao.service.UserTaskService;
import com.sangxiang.model.ApplyTaskParam;
import com.sangxiang.model.Login.HomeTaskParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

@RestController
@Api(description="任务" )
public class TaskController extends BaseResource {
    @Autowired
    TaskService taskService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    UserTaskService userTaskService;

    @PostMapping(value = "/createTask")
    @ApiOperation(value="用户登录")
    public ApiResult createTask(@RequestBody Task task){
        task.setUserid(ShiroUtils.getUserId());
        task.setCreateTime(new Date());
        task.setState(0);
        task.setTotalPrice((task.getGoodsPrice()+task.getWorkerPrice())*task.getWorkerNum());
        taskService.createTask(task);
        String key="task"+task.getId();
        redisTemplate.opsForValue().set(key,task.getWorkerNum());
        return success("创建成功");
    }

    @PostMapping(value = "/getHomeTask")
    @ApiOperation(value="任务大厅任务")
    public ApiResult<PageInfo<Task>> getHomeTask(@RequestBody HomeTaskParam param){
        PageInfo<Task> pageInfo= taskService.findPage(param.getPageNumber(),param.getPageSize(),param.getState());
        return success(pageInfo);
    }

    @PostMapping(value = "/applyTask")
    @ApiOperation(value="申请任务")
    public synchronized ApiResult applyTask(@RequestBody ApplyTaskParam param){
        Task model=  taskService.queryById(param.getTaskid());
        //先判断这个人是否已经申请过
        if(model.getWorkerNum()==0){
            return success("申请人数已达到上限!");
        }
        userTaskService.createUserTask(model,ShiroUtils.getUser());
        return success("申请成功！");
    }

    @PostMapping(value = "/getUserTask")
    @ApiOperation(value="获取任务列表")
    public  ApiResult<PageInfo<Task>> getUserTask(@RequestBody HomeTaskParam param){
        //先判断这个人是否已经申请过
        return success (userTaskService.getUserTask(ShiroUtils.getUserId(),0,param.getPageNumber(),param.getPageSize()));
    }

    @GetMapping(value = "/test")
    @ApiOperation(value="测试")
    public ApiResult test(){
        //httpClient工厂
        final SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        //开50个线程模拟并发秒杀下单
        for (int i = 0; i < 50; i++) {
            //购买人姓名
            final String consumerName = "consumer" + i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ClientHttpRequest request = null;
                    try {
                        String takeOrderUrl = "http://127.0.0.1:8081/applyTask";
                        URI uri = new URI(takeOrderUrl);
                        request = httpRequestFactory.createRequest(uri, HttpMethod.GET);
                        InputStream body = request.execute().getBody();
                        BufferedReader br = new BufferedReader(new InputStreamReader(body));
                        String line = "";
                        String result = "";
                        while ((line = br.readLine()) != null) {
                            result += line;//获得页面内容或返回内容
                        }
                        System.out.println(consumerName+":"+result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return success("结束");
    }
}
