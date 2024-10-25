package com.sopra.spring.jpa.h2.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sopra.spring.jpa.h2.dto.TutorialSaveDTO;
import com.sopra.spring.jpa.h2.dto.TutorialUpdateDTO;
import com.sopra.spring.jpa.h2.tools.TestTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TutorialControllerTest {

    private static String SERVICE_URI = "/api/tutorials/";
    @Autowired
    protected WebApplicationContext context;
    protected MockMvc mockMvc;

    @BeforeEach
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Order(1)
    void testGetTutorial_whenNoDatas() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(2)
    void testGetTutorialPublished_whenNoDatas() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "published").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(3)
    void testSaveTutorial_whenNoDatas() throws Exception {
        TutorialSaveDTO tutorialSaveDTO = TestTools.getTutorialSaveDTOs();
        String jsonRequestContent = new ObjectMapper().writeValueAsString(tutorialSaveDTO);
        this.mockMvc.perform(post(SERVICE_URI).contentType(MediaType.APPLICATION_JSON).content(jsonRequestContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("description").value("Task One : analyse feature"))
                .andExpect(jsonPath("published").value(false))
        ;
    }

    @Test
    @Order(4)
    void testGetTutorialPublished_whenNoDatasPublished() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "published").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void testUpdateTutorial_whenIdIsNotFound() throws Exception {
        TutorialSaveDTO tutorialSaveDTO = TestTools.getTutorialSaveDTOs();
        String jsonRequestContent = new ObjectMapper().writeValueAsString(tutorialSaveDTO);
        this.mockMvc.perform(put(SERVICE_URI + "2").contentType(MediaType.APPLICATION_JSON).content(jsonRequestContent))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void testUpdateTutorial() throws Exception {
        TutorialUpdateDTO tutorialUpdateDTO = TestTools.getTutorialUpdateDto();
        tutorialUpdateDTO.setDescription("Task One : analyse feature and write SFG");
        tutorialUpdateDTO.setPublished(true);
        String jsonRequestContent = new ObjectMapper().writeValueAsString(tutorialUpdateDTO);
        this.mockMvc.perform(put(SERVICE_URI + "1").contentType(MediaType.APPLICATION_JSON).content(jsonRequestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("published").value(true))
        ;
    }

    @Test
    @Order(7)
    void testGetTutorial() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("published").value(true))
        ;
    }

    @Test
    @Order(8)
    void testGetTutorialPublished() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "published").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("$[0].description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("$[0].published").value(true))
        ;
    }

    @Test
    @Order(9)
    void testGetTutorials() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("$[0].description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("$[0].published").value(true))
        ;
    }

    @Test
    @Order(10)
    void testSaveSecondItem() throws Exception {
        TutorialSaveDTO tutorialSaveDTO = TutorialSaveDTO.builder().title("Task 2 - model").description("Task Two : modeling feature and architecture").build();
        String jsonRequestContent = new ObjectMapper().writeValueAsString(tutorialSaveDTO);
        this.mockMvc.perform(post(SERVICE_URI).contentType(MediaType.APPLICATION_JSON).content(jsonRequestContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(2L))
                .andExpect(jsonPath("title").value("Task 2 - model"))
                .andExpect(jsonPath("description").value("Task Two : modeling feature and architecture"))
                .andExpect(jsonPath("published").value(false))
        ;

    }

    @Test
    @Order(11)
    void testGetTutorialsBeforeDelete() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("$[0].description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("$[0].published").value(true))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Task 2 - model"))
                .andExpect(jsonPath("$[1].description").value("Task Two : modeling feature and architecture"))
                .andExpect(jsonPath("$[1].published").value(false))
        ;
    }

    @Test
    @Order(12)
    void testDeleteSecondItem() throws Exception {
        this.mockMvc.perform(delete(SERVICE_URI + "2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(13)
    void testGetSecondItem() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI + "2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(14)
    void testGetTutorialsAfterDelete() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("$[0].description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("$[0].published").value(true))
        ;
    }

    @Test
    @Order(15)
    void testSaveThirdItem() throws Exception {
        TutorialSaveDTO tutorialSaveDTO = TutorialSaveDTO.builder().title("Task 3 - dataset").description("Task Three : dataset for developers and functional tests").build();
        String jsonRequestContent = new ObjectMapper().writeValueAsString(tutorialSaveDTO);
        this.mockMvc.perform(post(SERVICE_URI).contentType(MediaType.APPLICATION_JSON).content(jsonRequestContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(3L))
                .andExpect(jsonPath("title").value("Task 3 - dataset"))
                .andExpect(jsonPath("description").value("Task Three : dataset for developers and functional tests"))
                .andExpect(jsonPath("published").value(false))
        ;

    }

    @Test
    @Order(16)
    void testGetTutorialsBeforeDeleteAll() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Task 1 - Analyse"))
                .andExpect(jsonPath("$[0].description").value("Task One : analyse feature and write SFG"))
                .andExpect(jsonPath("$[0].published").value(true))
                .andExpect(jsonPath("$[1].id").value(3L))
                .andExpect(jsonPath("$[1].title").value("Task 3 - dataset"))
                .andExpect(jsonPath("$[1].description").value("Task Three : dataset for developers and functional tests"))
                .andExpect(jsonPath("$[1].published").value(false))
        ;
    }

    @Test
    @Order(17)
    void testDeleteAll() throws Exception {
        this.mockMvc.perform(delete(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(18)
    void testGetTutorialAfterDeleteAll() throws Exception {
        this.mockMvc.perform(get(SERVICE_URI).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}