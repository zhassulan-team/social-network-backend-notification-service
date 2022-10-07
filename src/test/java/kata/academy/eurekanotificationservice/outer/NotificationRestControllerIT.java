package kata.academy.eurekanotificationservice.outer;

import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import kata.academy.eurekanotificationservice.model.dto.NotificationPersistRequestDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationRestControllerIT extends SpringSimpleContextTest {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/After.sql")
    public void getNotificationPage_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        NotificationPersistRequestDto dto = new NotificationPersistRequestDto("You have new message",
                LocalDateTime.of(2012,6,18,10,34,9),false);
        mockMvc.perform(get("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].recipientId", Is.is(recipientId.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].text", Is.is(dto.text())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].time", Is.is(dto.time().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].isViewed", Is.is(dto.isViewed())));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/After.sql")
    public void viewAllNotifications_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        mockMvc.perform(put("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/After.sql")
    public void viewNotification_SuccessfulTest() throws Exception {
        Long notificationId = 1L;
        Long recipientId = 2L;
        mockMvc.perform(put("/api/v1/notifications/{notificationId}", notificationId)
                        .param("userId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_NotificationDoesNotExists/After.sql")
    public void viewNotification_NotificationDoesNotExists() throws Exception {
        Long notificationId = 1L;
        Long recipientId = 2L;
        mockMvc.perform(put("/api/v1/notifications/{notificationId}", notificationId)
                        .param("userId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is(
                        String.format("Уведомление с notificationId %d и userId %d нет в базе данных", notificationId, recipientId))
                ));

    }
}
