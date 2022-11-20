package kata.academy.eurekanotificationservice.outer;

import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationRestControllerTestIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/AfterTest.sql")
    void getNotificationPage_SuccessfulTest() throws Exception {
        Long recipientId = 1L;
        mockMvc.perform(get("/api/v1/notifications")
                .header("userId", recipientId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Is.is(2)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_ViewedTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_ViewedTest/AfterTest.sql")
    void getNotificationPage_ViewedTest() throws Exception {
        Long recipientId = 1L;
        mockMvc.perform(get("/api/v1/notifications?isViewed=true")
                .header("userId", recipientId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Is.is(1)));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/AfterTest.sql")
    void viewAllNotifications_SuccessfulTest() throws Exception {
        Long recipientId = 1L;
        mockMvc.perform(put("/api/v1/notifications/")
                .header("userId", recipientId.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        assertFalse(entityManager.createQuery(
                """
                        SELECT COUNT(n.id) > 0
                        FROM Notification n
                        WHERE n.recipientId = :recipientId
                        AND n.isViewed = false
                        """, Boolean.class)
            .setParameter("recipientId", recipientId)
            .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/AfterTest.sql")
    void viewNotification_SuccessfulTest() throws Exception {
        Long notificationId = 1L;
        Long userId = 1L;
        boolean isViewed = true;
        mockMvc.perform(put("/api/v1/notifications/{notificationId}", notificationId)
            .header("userId", userId.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        assertTrue(entityManager.createQuery(
            """
                    SELECT COUNT(n.id) = 1
                    FROM Notification n
                    WHERE n.id = :notificationId
                    AND n.isViewed = :isViewed
                    """, Boolean.class)
            .setParameter("notificationId", notificationId)
            .setParameter("isViewed", isViewed)
            .getSingleResult());
    }
}