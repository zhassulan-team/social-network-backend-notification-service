package kata.academy.eurekanotificationservice.outer;

import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import kata.academy.eurekanotificationservice.model.entity.Notification;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificationRestControllerIT extends SpringSimpleContextTest {
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/After.sql")
    public void getNotificationPage_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        Notification[] sortedByTextDesc4 = {
                new Notification(1L, recipientId, "Explain Everything Whiteboard and LMSs",
                        LocalDateTime.of(2012, 6, 18, 12, 34, 9), false),
                new Notification(3L, recipientId, "Did you feel it?",
                        LocalDateTime.of(2012, 8, 11, 2, 34, 9), false),
                new Notification(5L, recipientId, "Could you tell me why you applied for this job?",
                        LocalDateTime.of(2012, 8, 18, 10, 34, 9), false),
                new Notification(4L, recipientId, "Black and white show!",
                        LocalDateTime.of(2012, 7, 18, 10, 34, 9), false)
        };
        Notification[] sortedByTimeAsc3 = {
                new Notification(3L, recipientId, "Did you feel it?",
                        LocalDateTime.of(2012, 8, 11, 2, 34, 9), false),
                new Notification(6L, recipientId,"Did you feel it?",
                        LocalDateTime.of(2012, 8, 11, 2, 34, 9), true),
                new Notification(5L, recipientId, "Could you tell me why you applied for this job?",
                        LocalDateTime.of(2012, 8, 18, 10, 34, 9), false)
        };

        Notification[] sortedByTimeDesc = {
                new Notification(7L, recipientId, "Could you tell me why you applied for this job?",
                        LocalDateTime.of(2012, 8, 18, 10, 34, 9), true),
                new Notification(6L, recipientId, "Did you feel it?",
                        LocalDateTime.of(2012, 8, 11, 2, 34, 9), true)
        };

        mockMvc.perform(get("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .param("isViewed", "false")
                        .param("page", "0")
                        .param("size", "4")
                        .param("sort", "text,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].id", Is.is(sortedByTextDesc4[0].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].recipientId", Is.is(sortedByTextDesc4[0].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].text", Is.is(sortedByTextDesc4[0].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].time", Is.is(sortedByTextDesc4[0].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].isViewed", Is.is(sortedByTextDesc4[0].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].id", Is.is(sortedByTextDesc4[1].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].recipientId", Is.is(sortedByTextDesc4[1].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].text", Is.is(sortedByTextDesc4[1].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].time", Is.is(sortedByTextDesc4[1].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].isViewed", Is.is(sortedByTextDesc4[1].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].id", Is.is(sortedByTextDesc4[2].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].recipientId", Is.is(sortedByTextDesc4[2].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].text", Is.is(sortedByTextDesc4[2].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].time", Is.is(sortedByTextDesc4[2].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].isViewed", Is.is(sortedByTextDesc4[2].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].id", Is.is(sortedByTextDesc4[3].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].recipientId", Is.is(sortedByTextDesc4[3].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].text", Is.is(sortedByTextDesc4[3].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].time", Is.is(sortedByTextDesc4[3].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[3].isViewed", Is.is(sortedByTextDesc4[3].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.sorted", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber", Is.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize", Is.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(5)));

        mockMvc.perform(get("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .param("page", "1")
                        .param("size", "3")
                        .param("sort", "time,asc")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].id", Is.is(sortedByTimeAsc3[0].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].recipientId", Is.is(sortedByTimeAsc3[0].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].text", Is.is(sortedByTimeAsc3[0].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].time", Is.is(sortedByTimeAsc3[0].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].isViewed", Is.is(sortedByTimeAsc3[0].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].id", Is.is(sortedByTimeAsc3[1].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].recipientId", Is.is(sortedByTimeAsc3[1].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].text", Is.is(sortedByTimeAsc3[1].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].time", Is.is(sortedByTimeAsc3[1].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].isViewed", Is.is(sortedByTimeAsc3[1].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].id", Is.is(sortedByTimeAsc3[2].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].recipientId", Is.is(sortedByTimeAsc3[2].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].text", Is.is(sortedByTimeAsc3[2].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].time", Is.is(sortedByTimeAsc3[2].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].isViewed", Is.is(sortedByTimeAsc3[2].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.sorted", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(7)));

        mockMvc.perform(get("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .param("isViewed", "true")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "time,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].id", Is.is(sortedByTimeDesc[0].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].recipientId", Is.is(sortedByTimeDesc[0].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].text", Is.is(sortedByTimeDesc[0].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].time", Is.is(sortedByTimeDesc[0].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].isViewed", Is.is(sortedByTimeDesc[0].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].id", Is.is(sortedByTimeDesc[1].getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].recipientId", Is.is(sortedByTimeDesc[1].getRecipientId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].text", Is.is(sortedByTimeDesc[1].getText())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].time", Is.is(sortedByTimeDesc[1].getTime().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].isViewed", Is.is(sortedByTimeDesc[1].getIsViewed())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.sorted", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber", Is.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", Is.is(2)));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/After.sql")
    public void viewAllNotifications_SuccessfulTest() throws Exception {
        Long recipientId = 2L;
        mockMvc.perform(put("/api/v1/notifications")
                        .param("userId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));
        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(n) = 2
                                FROM Notification n
                                WHERE n.recipientId = :recipientId
                                """, Boolean.class)
                .setParameter("recipientId", recipientId)
                .getSingleResult());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewNotification_SuccessfulTest/After.sql")
    public void viewNotification_SuccessfulTest() throws Exception {
        Long notificationId = 1L;
        Long recipientId = 2L;
        Boolean isViewed = true;
        LocalDateTime time = LocalDateTime.of(2012, 6, 18, 10, 34, 9);
        String text = "You have new message";
        mockMvc.perform(put("/api/v1/notifications/{notificationId}", notificationId)
                        .param("userId", recipientId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is(200)));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(n.id) > 0
                                FROM Notification n
                                WHERE n.recipientId = :recipientId
                                AND n.text = :text
                                AND n.time = :time
                                AND n.isViewed = :isViewed
                                """, Boolean.class)
                .setParameter("recipientId", recipientId)
                .setParameter("text", text)
                .setParameter("time", time)
                .setParameter("isViewed", isViewed)
                .getSingleResult());
    }

    @Test
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
