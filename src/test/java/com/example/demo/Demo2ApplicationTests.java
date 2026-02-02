package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
//@WithMockUser(username="admin",roles={"USER","ADMIN"})

class Demo2ApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    @Test
    public void testCartWithRealLogin() throws Exception {
        MvcResult result = mockMvc.perform(get("/login"))
                .andReturn();

        MockHttpServletRequest request = result.getRequest();
        String csrfToken = (String) request.getAttribute("_csrf");
        this.session = (MockHttpSession) request.getSession(true);

        // now perform login POST
        mockMvc.perform(post("/login")
                        .param("email", "m@gmail.com")
                        .param("password", "123")
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testGetBooks() throws Exception {

        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/books").session(this.session))

                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode bookNode : jsonNode) {

            Assertions.assertTrue(bookNode.has("isbn"), "JSON should contain 'isbn'");
            Assertions.assertTrue(bookNode.has("title"), "JSON should contain 'title'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("category"), "JSON should contain 'category'");
            Assertions.assertTrue(bookNode.has("description"), "JSON should contain 'description'");
            Assertions.assertTrue(bookNode.has("stock"), "JSON should contain 'stock'");
            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
        }
    }

    @Test
    public void testGetBooksFilterAuthor() throws Exception {

        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/books/filtered?author=jose%rizal").session(this.session))

                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode bookNode : jsonNode) {

            Assertions.assertTrue(bookNode.has("isbn"), "JSON should contain 'isbn'");
            Assertions.assertTrue(bookNode.has("title"), "JSON should contain 'title'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            assertEquals("jose rizal",bookNode.get("author").asText().toLowerCase());
            Assertions.assertTrue(bookNode.has("category"), "JSON should contain 'category'");
            Assertions.assertTrue(bookNode.has("description"), "JSON should contain 'description'");
            Assertions.assertTrue(bookNode.has("stock"), "JSON should contain 'stock'");
            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
        }
    }

    @Test
    public void testGetBooksFilterCategory() throws Exception {

        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/books/filtered?category=fiction").session(this.session))

                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode bookNode : jsonNode) {

            Assertions.assertTrue(bookNode.has("isbn"), "JSON should contain 'isbn'");
            Assertions.assertTrue(bookNode.has("title"), "JSON should contain 'title'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("category"), "JSON should contain 'category'");
            assertEquals("fiction",bookNode.get("category").asText().toLowerCase());

            Assertions.assertTrue(bookNode.has("description"), "JSON should contain 'description'");
            Assertions.assertTrue(bookNode.has("stock"), "JSON should contain 'stock'");
            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
        }
    }

    @Test
    public void testGetBooksFilterCategoryNotExist() throws Exception {

        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/books/filtered?category=biography").session(this.session))

                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");

        Assertions.assertTrue(jsonNode.isEmpty());
        /*for (JsonNode bookNode : jsonNode) {

            Assertions.assertTrue(bookNode.has("isbn"), "JSON should contain 'isbn'");
            Assertions.assertTrue(bookNode.has("title"), "JSON should contain 'title'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("author"), "JSON should contain 'author'");
            Assertions.assertTrue(bookNode.has("category"), "JSON should contain 'category'");
            assertEquals("fiction",bookNode.get("category").asText().toLowerCase());

            Assertions.assertTrue(bookNode.has("description"), "JSON should contain 'description'");
            Assertions.assertTrue(bookNode.has("stock"), "JSON should contain 'stock'");
            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
        }*/
    }
    @Test
    public void testGetHistory() throws Exception {
        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/order_history/100").session(this.session))
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
    @Test
    public void testGetShoppingCart() throws Exception {
        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/shopping_cart/100").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        for (JsonNode bookNode : jsonNode) {
            Assertions.assertTrue(bookNode.has("shoppingId"), "JSON should contain 'shoppingId'");
            Assertions.assertTrue(bookNode.has("bookIsbn"), "JSON should contain 'bookIsbn'");
            Assertions.assertTrue(bookNode.has("bookTitle"), "JSON should contain 'bookTitle'");
            Assertions.assertTrue(bookNode.has("bookAuthor"), "JSON should contain 'bookAuthor'");
            Assertions.assertTrue(bookNode.has("category"), "JSON should contain 'category'");

            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
            Assertions.assertTrue(bookNode.has("quantity"), "JSON should contain 'quantity'");

        }

    }

}
