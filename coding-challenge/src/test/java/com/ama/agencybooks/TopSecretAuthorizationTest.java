package com.ama.agencybooks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TopSecretAuthorizationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "TOP_SECRET")
    public void testTopSecret_correct_role() throws Exception {
        mockMvc.perform(get("/books/topSecret"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "WRONG")
    public void testTopSecret_role_mistake() throws Exception {
        mockMvc.perform(get("/books/top_secret"))
                .andExpect(status().isForbidden());
    }
}
