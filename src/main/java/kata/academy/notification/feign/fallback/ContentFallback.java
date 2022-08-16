package kata.academy.notification.feign.fallback;

import kata.academy.notification.exception.FeignRequestException;
import kata.academy.notification.feign.ContentFeignClient;

import java.util.List;

record ContentFallback(Throwable cause) implements ContentFeignClient {

    @Override
    public List<String> getProfileUsernamesByProfileIds(List<Long> profileIds) {
        throw new FeignRequestException("Сервис временно недоступен. Причина -> %s"
                .formatted(cause.getMessage()), cause);
    }
}
