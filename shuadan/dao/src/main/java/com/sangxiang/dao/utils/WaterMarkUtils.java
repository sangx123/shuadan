package com.sangxiang.dao.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by tombaby on 2018/3/30.
 */
public class WaterMarkUtils {
    private static final String ACCESS_TOKEN = "";
    private static final String SECRECT_KEY = "";
    private static final String BUCKET = "";

    private static final String WATER_STYLE_TMPLT = "?imageView2/0/q/75|watermark/2/text/%s/font/5a6L5L2T/fontsize/280/fill/IzRCNEI0Qg==/dissolve/100/gravity/NorthWest/dx/10/dy/10|imageslim";

/*    @Autowired
    private GaodeGeoMap gaodeGeoMap;*/

    /**
     * 根据原始图片的url和打水印操作的样式url参数串，生成该图片的带打水印操作的url地址，使用该地址看图片，水印会显示在图片上。
     * 注意：这个地址是原图片的图像操作api的restful url地址串，每调用一次做一次打水印操作，在七牛上并没有对带水印的结果图片进行持久化。
     * @param picUrl
     * @param coordinate
     * @param shootTime
     * @return
     */
    public static String genPicUrlWithWaterMark(String picUrl, String coordinate, String shootTime) {

        if(StringUtils.isBlank(picUrl)) {
            return null;
        }
        StringBuffer result = new StringBuffer(picUrl);
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(coordinate)) {
            GaodeGeoMap gaodeGeoMap =new GaodeGeoMap();
            String location = gaodeGeoMap.locateGeoCoordinates(coordinate);
            if(StringUtils.isNoneBlank(location)){
                sb.append(location + " ");
            }
        }
        if(StringUtils.isNotBlank(shootTime)) {
            sb.append(shootTime);
        }
        String mark = sb.toString();
        if(StringUtils.isNotBlank(mark)){
            try {
                String encodeMark = new String(Base64.encodeBase64(mark.getBytes("UTF-8")));
                String ops = String.format(WATER_STYLE_TMPLT, encodeMark).replace("+","_").replace("/","_");
                result.append(ops);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }


}
