package kata.academy.notification.service.impl.dto;

import kata.academy.notification.dao.dto.NotificationResponseDtoDao;
import kata.academy.notification.service.dto.NotificationResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationResponseDtoServiceImpl implements NotificationResponseDtoService {

    private final NotificationResponseDtoDao notificationResponseDtoDao;
}
