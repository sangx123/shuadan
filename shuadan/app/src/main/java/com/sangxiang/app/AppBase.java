package com.sangxiang.app;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangxiang.app.utils.ParamVo;
import com.sangxiang.exception.ApiException;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import com.xiaoleilu.hutool.util.StrUtil;

import java.math.BigDecimal;
import java.util.List;

public abstract class AppBase {
	
	protected final transient Log log = LogFactory.get(this.getClass());
	
//	protected ThreadLocal<Map<String, Object>> submitToken = new ThreadLocal<Map<String, Object>>()
//	{
//		public Map<String, Object> initialValue(){
//			Map<String,Object> conn = new HashMap<String,Object>(); 
//			conn.put("error", error);
//			return conn;
//		}
//	};
	protected String submitToken = null;
	
	private void setSubmitToken(String val)
	{
		submitToken = val;
	}
	
    /**
     * 〈一句话功能简述〉
     * 〈功能详细描述〉
     * @param   [参数1] [参数1说明]
     * @param   [参数2] [参数2说明]
     * @return  [返回类型说明]
     *
     * @exception/throws [异常类型] [异常说明]
     * @see   [类、类#方法、类#成员](可选)
     */
    protected void checkParam(String param, String desc) throws ApiException
    {
    	if(StrUtil.isEmpty(param))
		{
			throw new ApiException(desc);
		}
    }
    protected void checkParam(String param, String desc, Integer num) throws ApiException
    {
    	if(StrUtil.isEmpty(param))
		{
			throw new ApiException(desc);
		}
    	
    	if(param.length()>num)
    	{
    		throw new ApiException("字符长度不能超过[" + num + "]个字符！");
    	}
    	
    }
    
    protected void checkParam(List<?> param, String desc) throws ApiException
    {
    	if(null==param||param.size()<=0)
		{
			throw new ApiException(desc);
		}
    }
    
    protected void checkParam(Object param, String desc) throws ApiException
    {
    	if(null==param)
		{
			throw new ApiException(desc);
		}
    }
    @SuppressWarnings("rawtypes")
	protected void checkParam(ParamVo base, String methed)
    {
    	if(null==base)
    	{
    		throw new ApiException("解析入口参数错误：未接收到入口参数！");
    	}
    	
    	/*if(StrUtil.isNotEmpty(base.getReSubmitToken()))
		{
			if(!ReSubmitTokenManager.checkToken(base.getReSubmitToken()))
			{
				throw new ApiReSubmitException();
			}
			setSubmitToken(base.getReSubmitToken());
		}*/
    	
//    	if(null!=response)
//    	{
//    		response.setDateHeader("expires", System.currentTimeMillis() + 1000 * 60 * 60 * 24);
//    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try 
    	{
			log.info("方法名[" + methed + "]入口参数[" + mapper.writeValueAsString(base) + "]");
		} 
    	catch (Exception e) 
    	{
    		throw new ApiException(e.getMessage());
		}
    	
    	if(null==base.getData())
    	{
    		throw new ApiException("解析入口参数错误：data参数为空！");
    	}
    }
    
//    @Autowired(required=false)
//	private HttpServletResponse response;
//    
//    @Autowired(required=false)
//	protected HttpServletRequest request;

    protected Integer intVal(Object obj)
	{
		Integer res = -1;
		
		if(obj!=null)
		{
			
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = Integer.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	protected Integer intVal(Object obj, Integer def)
	{
		Integer res = def;
		
		if(obj!=null)
		{
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return def;
			}
			
			try
			{
				res = Integer.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected Long longVal(Object obj)
	{
		Long res = -1L;
		
		if(obj!=null)
		{
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = Long.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	protected Long longVal(Object obj, Long def)
	{
		Long res = def;
		
		if(obj!=null)
		{
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return def;
			}
			
			try
			{
				res = Long.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected String strVal(Object obj)
	{
		String res = "";
		
		if(obj!=null)
		{
			try
			{
				res = String.valueOf(obj);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	protected String strVal(Object obj, String def)
	{
		String res = def;
		
		if(obj!=null)
		{
			try
			{
				res = String.valueOf(obj);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected Double doubleVal(Object obj)
	{
		Double res = new Double(-1);
		
		if(obj!=null)
		{
			
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = Double.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	protected Double doubleVal(Object obj, Double def)
	{
		Double res = def;
		
		if(obj!=null)
		{
			
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return def;
			}
			
			try
			{
				res = Double.valueOf(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected Number numberlVal(Object obj)
	{
		BigDecimal res = null;
		
		if(obj!=null)
		{
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = new BigDecimal(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	
	protected BigDecimal bigDecimalVal(Object obj)
	{
		BigDecimal res = null;
		
		if(obj!=null)
		{
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = new BigDecimal(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	protected BigDecimal bigDecimalVal(Object obj, BigDecimal def)
	{
		BigDecimal res = def;
		
		if(obj!=null)
		{
			
			if(StrUtil.isEmpty(obj.toString())) 
			{
				return res;
			}
			
			try
			{
				res = new BigDecimal(obj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}
	public static void main(String[] s)
	{
		AppBase b = new AppBase(){
			
		};
		System.out.println(b.numberlVal("222.00"));
		
	}
	/**
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param   [obj] [要转换的值]
	 * @param   [defVal] [默认值]
	 * @return  [返回类型说明]
	 *
	 * @exception/throws [异常类型] [异常说明]
	 * @see   [类、类#方法、类#成员](可选)
	 */
	@SuppressWarnings("unchecked")
	protected <T> T valOf(Object obj, T def)
	{
		if(null != obj)
		{
			try
			{
				if(def instanceof Integer)
				{
					return (T)Integer.valueOf(obj.toString());
				}
				else if(def instanceof Long)
				{
					return (T)Long.valueOf(obj.toString());
				}
				else if(def instanceof String)
				{
					return (T)String.valueOf(obj.toString());
				}
				else if(def instanceof Double)
				{
					return (T)Double.valueOf(obj.toString());
				}
				else if(def instanceof BigDecimal)
				{
					return (T)new BigDecimal(obj.toString());
				}
				else
				{
					return null;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

}
