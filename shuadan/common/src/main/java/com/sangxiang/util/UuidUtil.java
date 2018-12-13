package com.sangxiang.util;

import java.util.UUID;

public class UuidUtil {
	
	public static String creatUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
	
	public static void main(String[] s)
	{
		System.out.println(UuidUtil.creatUUID());
	}

}
