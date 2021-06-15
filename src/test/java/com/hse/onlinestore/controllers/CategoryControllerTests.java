package com.hse.onlinestore.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.hse.onlinestore.TestUtils.CATEGORY_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.CATEGORY_NAME;
import static com.hse.onlinestore.TestUtils.createCategory;
import static org.hamcrest.core.Is.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getcategoriesTest() throws Exception {
        this.mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    void addCategoryTest() throws Exception {
        CategoryDto CategoryDto = createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION); ;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(CategoryDto);

        this.mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(CATEGORY_NAME));
    }

    @Test
    void getCategoryTest() throws Exception {
        this.mockMvc.perform(get("/api/categories/1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("CATEGORY1"));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        CategoryDto CategoryDto = createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION); ;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(CategoryDto);

        MvcResult result = this.mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();

        CategoryDto CategoryDtoResult = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        this.mockMvc.perform(delete("/api/categories/" + CategoryDtoResult.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCategoryTest() throws Exception {
        CategoryDto CategoryDto = createCategory(CATEGORY_NAME, CATEGORY_DESCRIPTION); ;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(CategoryDto);

        MvcResult result = this.mockMvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();

        CategoryDto CategoryDtoResult = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);

        CategoryDto.setName("Category name updated");
        String updatedJson = objectMapper.writeValueAsString(CategoryDto);

        this.mockMvc.perform(put("/api/categories/" + CategoryDtoResult.getId()).contentType(MediaType.APPLICATION_JSON)
                                     .content(updatedJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Category name updated"));
    }
}
