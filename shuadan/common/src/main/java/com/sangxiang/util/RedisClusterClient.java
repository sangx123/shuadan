//package com.sangxiang.util;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Lists;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//import redis.clients.util.JedisClusterCRC16;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//public class RedisClusterClient {
//    private static final Logger logger = Logger.getLogger(RedisClusterClient.class);
//
//    @Autowired
//    private JedisCluster jedisCluster;
//
//    public String set(String key, Object value, int expire) {
//        String jsonObject = JSON.toJSONString(value);
//        logger.info("放入redis 缓存key："+key+". velue:"+jsonObject);
//        return jedisCluster.setex(key, expire, jsonObject);
//    }
//
//    public String buffer(String key, Object value) {
//        String jsonObj = JSON.toJSONString(value);
//        return jedisCluster.set(key, jsonObj);
//    }
//
//    public String get(String key) {
//        return jedisCluster.get(key);
//    }
//
//    public <T> T getObject(String key, Class<T> clazz) {
//        String jsonObject = jedisCluster.get(key);
//        return StringUtil.isBlank(jsonObject) ? null : JSON.parseObject(jsonObject, clazz);
//    }
//
//    public  void delete(String key) {
//        if (!StringUtil.isBlank(key)) {
//            jedisCluster.del(key);
//            logger.info("删除redis 缓存key："+key);
//        }
//    }
//
//    public Boolean existsKey(String key) {
//        return jedisCluster.exists(key);
//    }
//
//    public void deleteRedisKeyStartWith(String redisKeyStartWith) {
//        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
//
//        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
//            Jedis jedis = entry.getValue().getResource();
//            // 判断非从节点(因为若主从复制，从节点会跟随主节点的变化而变化)
//            if (!jedis.info("replication").contains("role:slave")) {
//                Set<String> keys = jedis.keys(redisKeyStartWith + "*");
//                if (keys.size() > 0) {
//                    Map<Integer, List<String>> map = new HashMap<>();
//                    for (String key : keys) {
//                        // cluster模式执行多key操作的时候，这些key必须在同一个slot上，不然会报:JedisDataException:
//                        // CROSSSLOT Keys in request don't hash to the same slot
//                        int slot = JedisClusterCRC16.getSlot(key);
//                        // 按slot将key分组，相同slot的key一起提交
//                        if (map.containsKey(slot)) {
//                            map.get(slot).add(key);
//                        } else {
//                            map.put(slot, Lists.newArrayList(key));
//                        }
//                    }
//                    for (Map.Entry<Integer, List<String>> integerListEntry : map.entrySet()) {
//                        jedis.del(integerListEntry.getValue().toArray(new String[integerListEntry.getValue().size()]));
//                    }
//                }
//            }
//        }
//    }
//
//}
