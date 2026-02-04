package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class HistoryApiTests extends LoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testGetHistory() throws Exception {
        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/order_history/101").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode bookNode : jsonNode) {
            Assertions.assertTrue(bookNode.has("transactionId"), "JSON should contain 'transactionId'");
            Assertions.assertTrue(bookNode.has("bookIsbn"), "JSON should contain 'bookIsbn'");
            Assertions.assertTrue(bookNode.has("customerId"), "JSON should contain 'customerId'");
            Assertions.assertTrue(bookNode.has("bookTitle"), "JSON should contain 'bookTitle'");
            Assertions.assertTrue(bookNode.has("bookAuthor"), "JSON should contain 'bookAuthor'");
            Assertions.assertTrue(bookNode.has("pricePerBook"), "JSON should contain 'pricePerBook'");

            Assertions.assertTrue(bookNode.has("total"), "JSON should contain 'total'");
            Assertions.assertTrue(bookNode.has("quantity"), "JSON should contain 'quantity'");

        }

    }
}
