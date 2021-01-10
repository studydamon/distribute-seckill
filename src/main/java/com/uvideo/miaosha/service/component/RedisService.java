package com.uvideo.miaosha.service.component;


import com.uvideo.miaosha.expection.RedisCacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    // =================================== Lua脚本执行 ===================================

    public String execute(String script, String key) {
        if (script == null || script == "") {
            throw new NullPointerException();
        } else {
            RedisScript<String> defaultRedisScript = new DefaultRedisScript<>(script);
            try {
                return (String) redisTemplate.execute(defaultRedisScript, Arrays.asList(key));
            } catch (RedisCacheException ex) {
                printLogStream("eval", script, null, ex);
            }
        }
        return null;
    }

    // ===================================  hash操作 =====================================

    public String hGet(String key, String field) {
        if (key == null || field == null) {
            throw new NullPointerException();
        } else {
            try {
                return (String) redisTemplate.opsForHash().get(key, field);
            } catch (RedisCacheException ex) {
                printLogStream("hget", key, field, ex);
            }
        }
        return null;
    }

    public void hSet(String key, String field, String value) {
        if (key == null || field == null) {
            List<Integer> integers = new ArrayList<>();
            integers.stream().min()
            throw new NullPointerException();
        } else {
            try {
                redisTemplate.opsForHash().put(key, field, value);
            } catch (RedisCacheException ex) {
                printLogStream("hset", key, field, ex);
            }
        }
    }

    // ===================================  值操作 ========================================

    public String getAndSet(String key, String value) {
        if (key == null) {
            throw new NullPointerException();
        } else {
            try {
                return (String) redisTemplate.opsForValue().getAndSet(key, value);
            } catch (RedisCacheException ex) {
                printLogStream("getset", key, value, ex);
            }
        }
        return null;
    }

    public void set(String key, String value) {
        if (key == null) {
            throw new NullPointerException();
        } else {
            try {
                redisTemplate.opsForValue().set(key, value);
            } catch (RedisCacheException ex) {
                printLogStream("set", key, value, ex);
            }
        }
    }

    public String get(String key) {
        if (key == null) {
            throw new NullPointerException();
        } else {
            try {
                return (String) redisTemplate.opsForValue().get(key);
            } catch (RedisCacheException ex) {
                printLogStream("get", key, null, ex);
            }
        }
        return null;
    }

    public void set(String key, String value, long timeout) {
        if (key == null || value == null) {
            throw new NullPointerException();
        } else if (timeout <= 0) {
            throw new IllegalArgumentException();
        } else {
            try {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } catch (RedisCacheException ex) {
                printLogStream("set expire", key, value, ex);
            }
        }
    }

    // ===========================   列表操作 ================================

    public void lpush(String key, String value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        try {
            long count = redisTemplate.opsForList().leftPush(key, value);
        } catch (RedisCacheException ex) {
            printLogStream("lpush", key, value, ex);
        }
    }

    public String lpop(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        try {
            return (String) redisTemplate.opsForList().rightPop(key);
        } catch (RedisCacheException ex) {
            printLogStream("lpop", key, null, ex);
        }
        return null;
    }

    public List<String> keysOfList(String key) {
        if (key == null) {
            return new ArrayList<>();
        }
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (RedisCacheException ex) {
            printLogStream("range(key,0,-1)", key, null, ex);
        }
        return null;
    }

    public List<String> range(String key, int start, int end) {
        Assert.isTrue(key != null && key != "");
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (RedisCacheException ex) {
            printLogStream("range(key,start,end)", key, null, ex);
        }
        return new ArrayList<>();
    }

    // ===========================   日志信息打印 ==============================

    private void printLogStream(String command, String key, String value, Exception ex) {
        log.error("redis缓存命令[{}]出现异常key:[{}],value:[{}],异常信息[{}]", command, key, value, ex);
    }
}
