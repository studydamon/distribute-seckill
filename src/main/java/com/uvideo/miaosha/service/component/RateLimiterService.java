package com.uvideo.miaosha.service.component;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private RateLimiter rateLimiter = RateLimiter.create(1);

    public boolean tryAcquireCurrentAvailable(){
        return rateLimiter.tryAcquire();
    }
}
