package com.ama.agencybooks;

import com.ama.agencybooks.model.Book;
import com.ama.agencybooks.repository.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static com.ama.agencybooks.model.SecurityLevel.TOP_SECRET;
import static com.ama.agencybooks.model.SecurityLevel.UNCLASSIFIED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnclassifiedAuthorizationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    BookRepository bookRepo;
    Long bookIdUnclassified;

   @BeforeAll
    public void setUp(){
       addUnclassifiedBook();
    }

    @AfterAll
    public void tearDown(){
        bookRepo.deleteById(bookIdUnclassified);
    }

    @Test
    @WithMockUser(roles = "UNCLASSIFIED")
    public void testUnclassified_correct_role() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/unclassified"))
                .andExpect(status().isOk())
                .andReturn();
        List<Book> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        Assertions.assertEquals(Optional.empty(), actual.stream().filter(n -> n.getClassification().equals(TOP_SECRET)).findAny());
        Assertions.assertNotNull(actual.stream().filter(n -> n.getClassification().equals(UNCLASSIFIED)).findAny());
    }

    @Test
    @WithMockUser(roles = "WRONG")
    public void testUnclassified_role_mistake() throws Exception {
        mockMvc.perform(get("/books/unclassified"))
                .andExpect(status().isForbidden());
    }

    public void addUnclassifiedBook() {
        Book book = new Book();
        book.setClassification(UNCLASSIFIED);
        book.setAuthor("Albert Einstein");
        book.setTitle("Relativity");
        bookIdUnclassified = bookRepo.save(book).getId();
    }
}
