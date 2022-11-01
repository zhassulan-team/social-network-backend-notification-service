package kata.academy.eurekanotificationservice.inner;

import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationInternalRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/inner/NotificationInternalRestController/addNotification_SuccessfulTest/After.sql")
    public void addNotification_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        String text = "You have new message";
        mockMvc.perform(post("/api/internal/v1/notifications")
                        .param("recipientId", recipientId.toString())
                        .content(objectMapper.writeValueAsString(text))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(n.id) > 0
                                FROM Notification n
                                WHERE n.recipientId = :recipientId
                                """, Boolean.class)
                .setParameter("recipientId", recipientId)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/inner/NotificationInternalRestController/addNotification_SuccessfulTest/After.sql")
    public void addNotificationsByMap_SuccessfulTest() throws Exception {
        Map<Long, String> notificationMap = new HashMap<>();
        notificationMap.put(1L, "Happy birthday!");
        mockMvc.perform(post("/api/internal/v1/notifications/map")
                        .content(objectMapper.writeValueAsString(notificationMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
