package kata.academy.eurekanotificationservice.outer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kata.academy.eurekanotificationservice.SpringSimpleContextTest;
import kata.academy.eurekanotificationservice.entity.Notification;
import kata.academy.eurekanotificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = BEFORE_CLASS)
public class NotificationRestControllerTestIT extends SpringSimpleContextTest {
    @Autowired
    NotificationRepository repository;

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_SuccessfulTest/AfterTest.sql")
    public void getNotificationPage_SuccessfulTest() throws Exception {
        Long recipientId = 1L;
        int defaultPageNumber = 0;
        int defaultPageSize = 20;
        boolean defaultSorted = false;
        int numberOfElements = 20;
        int totalElements = 25;
        int totalPages = (totalElements / defaultPageSize) + 1;

        List<Notification> expectedResult = entityManager.createQuery(
                                                                    """
                                                                    SELECT n
                                                                    FROM Notification n
                                                                    ORDER BY n.id
                                                                    """, Notification.class)
                                                            .setMaxResults(20)
                                                            .getResultList();

        MvcResult result = mockMvc.perform(get("/api/v1/notifications")
                .header("userId", recipientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.sort.sorted").value(defaultSorted))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(defaultPageNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(defaultPageSize))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(totalPages))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(numberOfElements))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(totalElements))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JsonNode responsePageContent = mapper.readTree(result.getResponse().getContentAsString()).get("content");
        List<Notification> actual = mapper.readValue(responsePageContent.traverse(), new TypeReference<>() {});
        actual.sort(Comparator.comparing(Notification::getId));

        for (Notification n : expectedResult) {
            int actualIndexOf = expectedResult.indexOf(n);
            assertEquals(n.getId(), actual.get(actualIndexOf).getId());
            assertEquals(n.getRecipientId(), actual.get(actualIndexOf).getRecipientId());
            assertEquals(n.getText(), actual.get(actualIndexOf).getText());
            assertEquals(n.getCreatedDate(), actual.get(actualIndexOf).getCreatedDate());
            assertEquals(n.getIsViewed(), actual.get(actualIndexOf).getIsViewed());
        }
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_ViewedTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/getNotificationPage_ViewedTest/AfterTest.sql")
    public void getNotificationPage_ViewedTest() throws Exception {
        Long recipientId = 1L;
        int expectedTotalElements = 1;
        int expectedId = 2;
        String expectedText = "notification_text_2";
        String expectedCreatedDate = "2022-01-31T00:00:00";
        boolean expectedIsViewed = true;

        mockMvc.perform(get("/api/v1/notifications")
                .param("isViewed", "true")
                .header("userId", recipientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expectedTotalElements))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expectedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].recipientId").value(recipientId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].text").value(expectedText))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].createdDate").value(expectedCreatedDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].isViewed").value(expectedIsViewed));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/NotificationRestController/viewAllNotifications_SuccessfulTest/AfterTest.sql")
    public void viewAllNotifications_SuccessfulTest() throws Exception {
        Long recipientId = 1L;
        mockMvc.perform(put("/api/v1/notifications/")
                .header("userId", recipientId)
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
    public void viewNotification_SuccessfulTest() throws Exception {
        Long notificationId = 1L;
        Long userId = 1L;
        boolean isViewed = true;
        mockMvc.perform(put("/api/v1/notifications/{notificationId}", notificationId)
            .header("userId", userId)
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
