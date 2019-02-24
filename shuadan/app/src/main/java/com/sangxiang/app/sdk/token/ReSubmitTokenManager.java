package com.sangxiang.app.sdk.token;

import com.sangxiang.exception.ApiException;
import com.sangxiang.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author  fujg
 * @see     [相关类/方法]（可选）
 * @version TODOVer 1.1
 * @Date	 2017 2017年8月14日 下午4:47:18
 *
 */
@Slf4j
public class ReSubmitTokenManager {
	
	private static ReSubmitTokenManager instance;
	
	private Map<String,Date> tokenCache = new ConcurrentHashMap<String,Date>();
	
	public synchronized static ReSubmitTokenManager getInstance()
	{
		if(null == instance)
		{
			instance = new ReSubmitTokenManager();
		}
		return instance;
	}
	
	private synchronized String _getToken()
	{
		remove();
		String key = UuidUtil.creatUUID();
		if(tokenCache.size()>300)
		{
			throw new ApiException("获取重复提交token失败！");
		}
		//log.info("获取到token=[" + key + "]");
		tokenCache.put(key, new Date());
		return key;
	}
	
	private synchronized Boolean _checkToken(String token)
	{
		boolean res = false;
		if(tokenCache.containsKey(token))
		{
			res = true;
			tokenCache.remove(token);
			//log.info("验证token[" + token + "]时，发现该token存在，验证成功！");
		}
		else
		{
			//log.info("验证token[" + token + "]时，发现该token不存在，验证失败！");
		}
		return res;
	}

	private synchronized void _rollBackToken(String token)
	{
		if(!tokenCache.containsKey(token))
		{
			tokenCache.put(token, new Date());
			//log.info("恢复token[" + token + "]成功！");
		}
	}
	
	private synchronized void remove()
	{
		if(tokenCache.size()>0)
		{
			List<String> kList = new ArrayList<String>();
			for(String key:tokenCache.keySet())
			{
				Date d = tokenCache.get(key);
				if((d.getTime() + 1000*60*10) < (System.currentTimeMillis()))
				{
					kList.add(key);
				}
			}
			for(String key:kList)
			{
				tokenCache.remove(key);
				//log.info("删除重复提交token[" + key + "]成功！");
			}
		}
	}

	private synchronized boolean validate(String token) {
		if(tokenCache.containsKey(token)) {
			//log.info("验证token[" + token + "]时，发现该token存在，验证成功！");
			return true;
		} else {
			//log.info("验证token[" + token + "]时，发现该token不存在，验证失败！");
			return false;
		}
	}

	private synchronized void clear(String token) {
		if(tokenCache.containsKey(token)){
			tokenCache.remove(token);
		}
	}

	public synchronized static boolean validateToken(String token) {
		return ReSubmitTokenManager.getInstance().validate(token);
	}

	public synchronized static void clearToken(String token) {
	    ReSubmitTokenManager.getInstance().clear(token);
    }
	
	public synchronized static String getToken()
	{
		return ReSubmitTokenManager.getInstance()._getToken();
	}
	
	public synchronized static void rollBackToken(String token)
	{
		ReSubmitTokenManager.getInstance()._rollBackToken(token);
	}
	
	public synchronized static boolean checkToken(String token)
	{
		 return ReSubmitTokenManager.getInstance()._checkToken(token);
	}
}
