package kata.academy.notification.dao.impl;

import kata.academy.notification.dao.NotificationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class NotificationDaoImpl implements NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;
}
