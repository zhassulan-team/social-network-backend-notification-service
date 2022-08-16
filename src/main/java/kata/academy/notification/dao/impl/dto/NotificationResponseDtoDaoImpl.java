package kata.academy.notification.dao.impl.dto;

import kata.academy.notification.dao.dto.NotificationResponseDtoDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class NotificationResponseDtoDaoImpl implements NotificationResponseDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
}
