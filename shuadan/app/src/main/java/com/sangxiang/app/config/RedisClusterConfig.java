package com.sangxiang.app.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisClusterConfig {
    //private static final Logger logger = Logger.getLogger(RedisClusterConfig.class);

    @Value("${spring.redis.cluster.nodes}")
    private String redisNodes;

    @Value("${spring.redis.cluster.maxIdle:150}")
    private int redisMaxIdle = 150;

    @Value("${spring.redis.cluster.timeout:5000}")
    private int redisTimeout = 5000;

    @Value("${spring.redis.cluster.maxTotal:300}")
    private int redisMaxTotal = 300;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisCluster jedisCluster() {
        Set<HostAndPort> nds = new HashSet<>();
        Arrays.asList(redisNodes.split(",")).forEach(s->{
            String[] hp = s.split(":");
            nds.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        });

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaxTotal);
        jedisPoolConfig.setMaxIdle(redisMaxIdle);
        //logger.info("Redis连接主机:" + nds);
        if (StringUtils.isBlank(password)) {
            password = null;
        }
        return new JedisCluster(nds, redisTimeout, 1000, 1, password, jedisPoolConfig);
    }
}
