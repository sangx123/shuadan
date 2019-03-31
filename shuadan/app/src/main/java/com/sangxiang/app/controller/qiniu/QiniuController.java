package com.sangxiang.app.controller.qiniu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.sangxiang.app.AppBaseController;
import com.sangxiang.app.utils.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/qiniu")
public class QiniuController extends AppBaseController {
	@Autowired
	private QiniuService qiniuService;
    @GetMapping("/commonToken")
    public String getCommonToken() {
        return qiniuService.getCommonToken();
    }

    @GetMapping ("/appCommonToken")
    public Map<String, String> getAppCommonToken() {
        Map<String, String> map = Maps.newHashMap();
        map.put("token", qiniuService.getCommonToken());
        return map;
    }
    @PostMapping("/appCommonToken")
    public AppResult getAppCommonToken2() {
        Map<String, String> map = Maps.newHashMap();
        map.put("token", qiniuService.getCommonToken());
        return success(map);
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(HttpServletRequest request) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("errno", 0);
        map.put("data", ImmutableList.of(qiniuService.upload(request)));
        return map;
    }
}
