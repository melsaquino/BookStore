package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AddToCartTestsAndCheckout extends LoginTest{
    @Autowired
    private MockMvc mockMvc;

    @Test
    @BeforeEach
    public void addToCartTest() throws Exception{
        //used to get what the current quantity in shopping cart is
        MvcResult BeforeAddresult = mockMvc.perform(get("/api/shopping_cart/100").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponseBefore = BeforeAddresult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeBefore = mapper.readTree(jsonResponseBefore);
        JsonNode bookBefore = findByKeyValue(jsonNodeBefore, "bookIsbn", "1000000003");
        //assertNotNull(bookBefore, "Book to be added not found");
        var oldQuantity = 0;
        if (bookBefore !=null)
            oldQuantity =bookBefore.path("quantity").asInt();
        //add to cart post
        MvcResult result = mockMvc.perform(post("/add_cart/100/1000000003")
                        .session(this.session).with(csrf()))
                .andExpect(redirectedUrl("/shopping_cart/100"))
                .andReturn();
        MvcResult resultAfter = mockMvc.perform(get("/api/shopping_cart/100").session(this.session))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = resultAfter.getResponse().getContentAsString();
        ObjectMapper newMapper = new ObjectMapper();
        JsonNode jsonNode = newMapper.readTree(jsonResponse);
        Assertions.assertTrue(jsonNode.isArray(), "Expected JSON array");
        JsonNode book = findByKeyValue(jsonNode, "bookIsbn", "1000000003");

        assertNotNull(book, "Book not found");
        assertEquals( oldQuantity+1, book.path("quantity").asInt(),"Quantity was not added");
    }
    @Test
    public void addUnknowBookToCartTest() throws Exception{
        //used to get what the current quantity in shopping cart is

        MvcResult result = mockMvc.perform(post("/add_cart/100/1006600003")
                        .session(this.session).with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    public void addBookToCartByNotLoggedInUser() throws Exception{
        //used to get what the current quantity in shopping cart is

        MvcResult result = mockMvc.perform(post("/add_cart/101/1000000003")
                        .session(this.session).with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void checkoutAllApiTest() throws Exception{
        MvcResult postResult = mockMvc.perform(post("/checkout/100/all").session(this.session))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult cartAfter = mockMvc.perform(get("/api/shopping_cart/100").session(this.session))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = cartAfter.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonResponse);

        Assertions.assertTrue(jsonNode.isEmpty());

    }

    @Test
    public void CheckoutNotLoggedInUser() throws Exception{
        MvcResult result = mockMvc.perform(post("/checkout/101/all")
                        .session(this.session).with(csrf()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private JsonNode findByKeyValue(JsonNode arrayNode, String key, String expectedValue) {
        for (JsonNode node : arrayNode) {
            if (expectedValue.equals(node.path(key).asText())) {
                return node;
            }
        }
        return null;
    }


}
