package com.sangxiang.app.controller.taobao;

import com.github.pagehelper.PageInfo;
import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.AppExecStatus;
import com.sangxiang.app.controller.login.UserLoginInfo;
import com.sangxiang.app.controller.login.UserLoginParam;
import com.sangxiang.app.sdk.token.UserTokenManager;
import com.sangxiang.app.utils.AppResult;
import com.sangxiang.base.rest.ApiResult;
import com.sangxiang.dao.model.SysUser;
import com.sangxiang.dao.model.TaobaoTask;
import com.sangxiang.dao.model.Task;
import com.sangxiang.dao.service.SysUserService;
import com.sangxiang.dao.service.TaobaoTaskService;
import com.sangxiang.model.Login.HomeTaskParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/taobao")
public class TaobaoController extends AppBaseController {
    @Autowired
    TaobaoTaskService taobaoTaskService;

    @Autowired
    SysUserService sysUserService;

    /**
     * 发布淘宝任务
     */
    @PostMapping("/publishTaoBaoTask")
    public AppResult publishTaoBaoTask(@RequestHeader("userToken") String userToken, @RequestBody TaobaoTask model) {
        int userId = UserTokenManager.getInstance().getUserIdFromToken(userToken).intValue();
        checkParam(userId, "没有此用户");
        //先查询用户的余额是否足够，不够的话提醒其去充值
        SysUser userModel=sysUserService.queryUser(userId);

        model.setUserid(userId);
        model.setCreateTime(new Date());
        model.setStatus(true);
        model.setTotalPrice((model.getShangpinjiage()+calYongjin(model))*model.getShuadanshuliang());
        if(model.getTotalPrice()>userModel.getMoney()){
            return fail(AppExecStatus.FAIL, "用户余额不足!,需要的总金额为："+model.getTotalPrice());
        }
        taobaoTaskService.createTaobaoTask(model,userModel);
        //String key="task"+task.getId();
        //redisTemplate.opsForValue().set(key,task.getWorkerNum());
        return success("任务创建成功");
    }

    @PostMapping(value = "/getTaoBaoHomeTask")
    @ApiOperation(value="淘宝任务大厅")
    public AppResult<PageInfo<TaobaoTask>> getTaoBaoHomeTask(@RequestBody HomeTaskParam param){
        PageInfo<TaobaoTask> taobaoTaskList= taobaoTaskService.findPage(param.getPageNumber(),param.getPageSize(),param.getState());
        return success(taobaoTaskList);
    }


    private Float calYongjin(TaobaoTask model){
        Float yongjin=5.0f;
        if(model.getJialiao()){
            yongjin+=0.5f;
        }
        if(model.getTingliushichang()){
            yongjin+=0.5f;
        }
        if(model.getLiulanqita()){
            yongjin+=0.5f;
        }
        if(model.getDaituhaoping()){
            yongjin+=1f;
        }
        if(model.getHuobisanjia()){
            yongjin+=1f;
        }
        if(model.getZhenshiqianshou()){
            yongjin+=1f;
        }
        return yongjin;

    }
}
