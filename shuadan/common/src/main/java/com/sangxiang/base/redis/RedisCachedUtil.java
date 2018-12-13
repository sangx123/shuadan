//package com.sangxiang.base.redis;
//
//
//import com.alibaba.fastjson.JSON;
//import com.emucoo.common.util.StringUtil;
//import org.apache.log4j.Logger;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//
///**
// * redis缓存工具类
// * @author lixiaofei
// *
// */
//public class RedisCachedUtil {
//
//	private static final Logger LOGGER = Logger.getLogger(RedisCachedUtil.class);
//
//	/**
//	 * 有效期的后缀
//	 */
//	public static final String EXPIRE_SUFFIXN = "_expire";
//
//	private static JedisCluster jedisCluster;
//
//	static
//    {
//        try
//        {
//            String hostAndPorts = PropertiesUtil.getValue("redis.cluster.hostAndPorts");
//           // String hostAndPorts = "192.168.199.109:7000,192.168.199.110:7001,192.168.199.111:7002,192.168.199.109:7003,192.168.199.110:7004,192.168.199.111:7005";
//            Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//            String[] hostAndPortArray = hostAndPorts.split(",");
//            for (String hostAndPort : hostAndPortArray)
//            {
//                String[] str = hostAndPort.split(":");
//                jedisClusterNodes.add(new HostAndPort(str[0], Integer.parseInt(str[1])));
//            }
//            //RedisConfig redisConfig = BeanHelper.getInstance(RedisConfig.class);
//
//            // pool中最大持有对象数
//            //int maxTotal = 300;//
//            int maxTotal = Integer.valueOf(PropertiesUtil.getValue("redis.cluster.maxTotal", "300"));
//            // pool中最大空闲对象数
//            //int maxIdle = 150;//
//            int maxIdle =Integer.valueOf(PropertiesUtil.getValue("redis.cluster.maxIdle", "150"));
//            // 获取redis对象的timeout时间
//            //int timeout = 5000;//
//            int timeout =  Integer.valueOf(PropertiesUtil.getValue("redis.cluster.timeout", "5000"));
//
//            // 配置jedisPool
//            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//            jedisPoolConfig.setMaxTotal(maxTotal);
//            jedisPoolConfig.setMaxIdle(maxIdle);
//
//            jedisCluster = new JedisCluster(jedisClusterNodes, timeout, jedisPoolConfig);
//            LOGGER.info("Redis连接主机:" + jedisClusterNodes);
//        }
//        catch(Exception e) {
//        	LOGGER.error(e.toString());
//        }
//
//    }
//
//    public static JedisCluster getJedisCluster() {
//        return jedisCluster;
//    }
//
//    /*public static String set(String key, Object value) {
//		String jsonObject = JSON.toJSONString(value);
//        return jedisCluster.set(key, jsonObject);
//	}*/
//
//    /**
//	 * 设置缓存
//	 * @param key 缓存的键
//	 * @param value 缓存的值
//	 * @param expire 超时时间
//	 * @return
//	 */
//	public static String set(String key, Object value, int expire) {
//		String jsonObject = JSON.toJSONString(value);
//        return jedisCluster.setex(key, expire, jsonObject);
//	}
//
//	 /**
//     *
//     * 获取redis里的json对象，转换为对象
//     *
//     * @param key
//     * @return Object
//     * @since p2p_cloud_v1.0
//     */
//    public static <T> T getObject(String key, Class<T> clazz) {
//        String jsonObject = jedisCluster.get(key);
//        if (StringUtil.isBlank(jsonObject)) {
//            return null;
//        }
//        return JSON.parseObject(jsonObject, clazz);
//    }
//
//    /**
//     * 删除指定的key对应的缓存
//     * @param key
//     */
//    public static void delete(String key){
//    	if(StringUtil.isBlank(key)){
//    		return;
//    	}
//    	jedisCluster.del(key);
//    }
//
//    public static void main(String[] args){
//
//      /*  Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//
//
//        jedisClusterNodes.add(new HostAndPort("192.168.199.109", 7000));
//        jedisClusterNodes.add(new HostAndPort("192.168.199.110", 7001));
//        jedisClusterNodes.add(new HostAndPort("192.168.199.111", 7002));
//        jedisClusterNodes.add(new HostAndPort("192.168.199.109", 7003));
//        jedisClusterNodes.add(new HostAndPort("192.168.199.110", 7004));
//        jedisClusterNodes.add(new HostAndPort("192.168.199.111", 7005));
//
//        // pool中最大持有对象数
//        int maxTotal = 3000;//Integer.parseInt(properties.getProperty("redis.cluster.maxTotal"));
//        // pool中最大空闲对象数
//        int maxIdle = 5;//Integer.parseInt(properties.getProperty("redis.cluster.maxIdle"));
//        // 获取redis对象的timeout时间
//        int timeout = 5000;//Integer.parseInt(properties.getProperty("redis.cluster.timeout"));
//
//        // 配置jedisPool
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//
//        jedisCluster = new JedisCluster(jedisClusterNodes, timeout, jedisPoolConfig);*/
//        try {
//
//        	//System.out.println("设置缓存 " + DateUtil.dateStr4(DateUtil.getNow()));
//        	set("test", "测试啊", 10);
//        //	System.out.println("设置缓存ok " + DateUtil.dateStr4(DateUtil.getNow()));
//        	String strGetValue = getObject("test", String.class);
//        	//System.out.println("读取缓存开始 " + DateUtil.dateStr4(DateUtil.getNow()));
//        //	System.out.println("值："+strGetValue);
//
//        	//System.out.println("删除缓存 key:test");
//        	delete("test");
//
//        	//System.out.println("读取缓存开始2 " + DateUtil.dateStr4(DateUtil.getNow()));
//        	strGetValue = getObject("test", String.class);
//
//        	System.out.println("值2："+strGetValue);
//
//        	//设置HashMap
//        	Map<String, Integer> mapData = new HashMap<String, Integer>();
//        	mapData.put("key1", 123);
//        	mapData.put("key2", 456);
//        	System.out.println("设置HashMap");
//        	set("hashMap", mapData, 60);
//        	Object objMap = getObject("hashMap", Object.class);
//        	System.out.println("读取HashMap" + objMap);
//
//        	for(int i = 0; i < 10; i++){
//        		long lCount = jedisCluster.incr("testIncr");
//        		System.out.println("读取增量的值" + lCount);
//        	}
//        	jedisCluster.hset("key", "key1", "测试批量匹配key1");
//        	jedisCluster.hset("key", "key2", "测试批量匹配key2");
//        	jedisCluster.hset("key", "key3", "测试批量匹配key3");
//        	Set<String> keyArray = jedisCluster.hkeys("key");
//        	jedisCluster.hdel("key", "key1");
//        	System.out.println("和key相匹配的值" + keyArray.toString() + ", filed的值" + jedisCluster.hget("key", "key1"));
//
//		} catch (Exception e) {
//			//System.out.println("出错了"+ e.getMessage() + DateUtil.dateStr4(DateUtil.getNow()));
//		}
//
//    }
//}
