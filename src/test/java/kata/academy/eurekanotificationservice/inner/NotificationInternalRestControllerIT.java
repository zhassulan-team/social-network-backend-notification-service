package kata.academy.eurekanotificationservice.inner;

import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationInternalRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/inner/NotificationInternalRestController/addNotification_SuccessfulTest/After.sql")
    public void addNotification_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        String text = "You have new message";
        mockMvc.perform(post("/api/internal/v1/notifications")
                        .param("text", text)
                        .param("recipientId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(n.id) > 0
                                FROM Notification n
                                WHERE n.recipientId = :recipientId
                                AND n.isViewed = :isViewed
                                AND n.text = :text
                                """, Boolean.class)
                .setParameter("recipientId", recipientId)
                .setParameter("isViewed", false)
                .setParameter("text", text)
                .getSingleResult());
    }
}
