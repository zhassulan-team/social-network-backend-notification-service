package kata.academy.notification.feign.fallback;

import kata.academy.notification.feign.ContentFeignClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ContentFallbackFactory implements FallbackFactory<ContentFeignClient> {

    @Override
    public ContentFeignClient create(Throwable cause) {
        return new ContentFallback(cause);
    }
}
