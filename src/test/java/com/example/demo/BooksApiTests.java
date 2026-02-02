package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BooksApiTests extends LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFilteredBooksNoParams() throws Exception{
        // Perform the GET request and get the result
        MvcResult result = mockMvc.perform(get("/api/books/filtered?").session(this.session))
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
    public void testFilterPriceRange() throws Exception{
        MvcResult result = mockMvc.perform(get("/api/books/filtered?priceRange=200-300").session(this.session))

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
            Assertions.assertTrue(bookNode.has("description"), "JSON should contain 'description'");
            Assertions.assertTrue(bookNode.has("stock"), "JSON should contain 'stock'");
            Assertions.assertTrue(bookNode.has("price"), "JSON should contain 'price'");
            Assertions.assertTrue(bookNode.get("price").asDouble() >= 200 && bookNode.get("price").asDouble() <= 300);
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
    public void InvalidRange() throws Exception{
        //check random word
        MvcResult result = mockMvc.perform(get("/api/books/filtered?priceRange=fiction").session(this.session))
                .andExpect(status().isBadRequest())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);

        Assertions.assertTrue(jsonNode.has("error"), "Missing error key");
        //check one number
        result = mockMvc.perform(get("/api/books/filtered?priceRange=100").session(this.session))
                .andExpect(status().isBadRequest())
                .andReturn();
        jsonResponse = result.getResponse().getContentAsString();
        mapper = new ObjectMapper();
        jsonNode = mapper.readTree(jsonResponse);

        Assertions.assertTrue(jsonNode.has("error"), "Missing error key");
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

    }


}
