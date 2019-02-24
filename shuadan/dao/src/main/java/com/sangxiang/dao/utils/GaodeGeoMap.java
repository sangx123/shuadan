package com.sangxiang.dao.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tombaby on 2018/3/30.
 */
@Component
public class GaodeGeoMap {
    private final Logger logger = LoggerFactory.getLogger(GaodeGeoMap.class);

    private static final String SECRET_KEY = "5d377810ae041e65dc6041ffef682631";

/*    @Autowired
    private RestTemplate restTemplate;*/

    /**
     * 根据经纬度坐标得到具体位置信息： 经度，纬度 -》 城市，区，位置
     * @param location
     * @return
     */
    public  String locateGeoCoordinates(String location){

        if(StringUtils.isBlank(location)){
            logger.info("传入字符串为空    location = "+location + " locationDes =" );
            return "";
        }
        String url = "http://restapi.amap.com/v3/geocode/regeo?"
                + "location=" + location
                + "&key=" + GaodeGeoMap.SECRET_KEY
                + "&radius=10&extensions=base";
        RestTemplate rstTemplate=new RestTemplate();
        String result = rstTemplate.getForObject(url, String.class);
        String locationDes =  getLocalInfoFromJson(result);

        logger.info("location = "+location + " locationDes =" + locationDes);
        return locationDes;

    }

    private String getLocalInfoFromJson(String locationInfo) {
        JSONObject jsonObject =  JSONObject.parseObject(locationInfo);
        if(!"1".equals(jsonObject.getString("status"))) {
            return "未知地理位置";
        }
        JSONObject regeocode =jsonObject.getJSONObject("regeocode");
        //String formattedAddress = regeocode.getString("formatted_address");
        JSONObject addressComponent = regeocode.getJSONObject("addressComponent");
        //String country =  addressComponent.getString("country");
        String province =  addressComponent.getString("province");
        String city =  addressComponent.getString("city");
        String district =  addressComponent.getString("district");
        String township =  addressComponent.getString("township");

        if("[]".equals(city)) {
            city = province;
        }
        if("[]".equals(township)) {
            return "未知地址";
        }
        return city + " " + district + " " + township;
    }
}
