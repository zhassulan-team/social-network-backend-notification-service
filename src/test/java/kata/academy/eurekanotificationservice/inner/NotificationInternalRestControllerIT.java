package kata.academy.eurekanotificationservice.inner;
import com.fasterxml.jackson.databind.ObjectMapper;
import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class NotificationInternalRestControllerIT extends SpringSimpleContextTest {



    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/inner/NotificationInternalRestController/addNotification_SuccessfulTest/AfterTest.sql")
  public void addNotification_SuccessfulTest() throws Exception {

            String text = "Hello World";
            Long recipientId = 1L;
            mockMvc.perform(post("/api/internal/v1/notifications")
                             .content(text)
                            .param("recipientId", String.valueOf(recipientId))
                            .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
            assertTrue(entityManager.createQuery(
                            """
                                    SELECT COUNT(n.id) > 0
                                    FROM Notification n
                                    WHERE n.recipientId = :recipientId
                                    AND n.text = :text
                                    """, Boolean.class)
                    .setParameter("recipientId", recipientId)
                    .setParameter("text", text)
                    .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/inner/NotificationInternalRestController/addNotificationsByMap_SuccessfulTest/AfterTest.sql")
    public void addNotificationsByMap_SuccessfulTest() throws Exception {
        Long recipientId =1L;
        String text = "Hello World!";
        Map<Long, String> notificationMap = new HashMap<>();
        notificationMap.put(recipientId, text);


        mockMvc.perform(post("/api/internal/v1/notifications/map")
                        .content(new ObjectMapper().writeValueAsString(notificationMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(n.id) > 0
                                FROM Notification n
                                WHERE n.recipientId = :recipientId
                                AND n.text = :text
                                """, Boolean.class)
                .setParameter("recipientId", recipientId)
                .setParameter("text", text)
                .getSingleResult());
    }






}
