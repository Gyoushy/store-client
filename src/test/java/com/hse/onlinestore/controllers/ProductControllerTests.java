package com.hse.onlinestore.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hse.onlinestore.dtos.CurrencyDto;
import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.hse.onlinestore.TestUtils.PRODUCT_DESCRIPTION;
import static com.hse.onlinestore.TestUtils.PRODUCT_NAME;
import static com.hse.onlinestore.TestUtils.createProduct;
import static org.hamcrest.Matchers.hasSize;
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
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductsTest() throws Exception {
        this.mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)));
    }

    @Test
    void addProductTest() throws Exception {
        ProductDto productDto = createProduct(CurrencyDto.EUR, PRODUCT_NAME, PRODUCT_DESCRIPTION,
                                              BigDecimal.valueOf(50), 3, 5, false, 14, BigDecimal.valueOf(5));
        productDto.setCategoryId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDto);

        this.mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
                .andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME));
    }

    @Test
    void getProductTest() throws Exception {
        this.mockMvc.perform(get("/api/products/2").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("PRODUCT_NAME"));
    }

    @Test
    void deleteProductTest() throws Exception {
        ProductDto productDto = createProduct(CurrencyDto.EUR, PRODUCT_NAME, PRODUCT_DESCRIPTION,
                                              BigDecimal.valueOf(50), 3, 5, false, 14, BigDecimal.valueOf(5));
        productDto.setCategoryId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDto);

        MvcResult result = this.mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();

        ProductDto productDtoResult = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        this.mockMvc.perform(delete("/api/products/" + productDtoResult.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateProductTest() throws Exception {
        ProductDto productDto = createProduct(CurrencyDto.EUR, PRODUCT_NAME, PRODUCT_DESCRIPTION,
                                              BigDecimal.valueOf(50), 3, 5, false, 14, BigDecimal.valueOf(5));
        productDto.setCategoryId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDto);

        MvcResult result = this.mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();

        ProductDto productDtoResult = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDto.class);

        productDto.setName("Product name updated");
        String updatedJson = objectMapper.writeValueAsString(productDto);

        this.mockMvc.perform(put("/api/products/" + productDtoResult.getId()).contentType(MediaType.APPLICATION_JSON)
                                     .content(updatedJson))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Product name updated"));
    }
}
