package org.example;

import com.stoyanr.evictor.map.ConcurrentHashMapWithTimedEviction;
import com.stoyanr.evictor.scheduler.DelayedTaskEvictionScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreeMarkerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeMarkerDemoApplication.class, args);

        // 测试evictor缓存定时驱逐组件 -> https://github.com/MagicFollower/Evictor
        ConcurrentHashMapWithTimedEviction<Object, Object> mapWithTimedEviction =
                new ConcurrentHashMapWithTimedEviction<>(256, new DelayedTaskEvictionScheduler<>());
        mapWithTimedEviction.put("A", "1");
        mapWithTimedEviction.put("A", "2");
        mapWithTimedEviction.put("B", "1");
        mapWithTimedEviction.putIfAbsent("B", "2");
        System.out.println("mapWithTimedEviction = " + mapWithTimedEviction);
    }
}