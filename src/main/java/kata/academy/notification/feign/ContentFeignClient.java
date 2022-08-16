package kata.academy.notification.feign;

import kata.academy.notification.feign.fallback.ContentFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "content", fallbackFactory = ContentFallbackFactory.class)
public interface ContentFeignClient {

    @GetMapping("/api/inner/v1/profiles")
    List<String> getProfileUsernamesByProfileIds(@RequestParam List<Long> profileIds);
}
