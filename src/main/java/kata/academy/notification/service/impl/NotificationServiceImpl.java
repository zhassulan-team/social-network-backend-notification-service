package kata.academy.notification.service.impl;

import kata.academy.notification.dao.NotificationDao;
import kata.academy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;
}
