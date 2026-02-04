package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public abstract class LoginTest {
    @Autowired
    private MockMvc mockMvc;
    protected MockHttpSession session;

    @BeforeEach
    @Test
    public void testCartWithRealLogin() throws Exception {
        MvcResult result = mockMvc.perform(get("/login"))
                .andReturn();
        MockHttpServletRequest request = result.getRequest();
        String csrfToken = (String) request.getAttribute("_csrf");
        this.session = (MockHttpSession) request.getSession(true);

        // now perform login POST. this can fail based on values in the database
        //next iteration a different test database should be used for this
        mockMvc.perform(post("/login")
                        .param("email", "m@gmail.com")
                        .param("password", "123")
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

}
