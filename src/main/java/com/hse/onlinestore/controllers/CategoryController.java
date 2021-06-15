package com.hse.onlinestore.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.hse.onlinestore.dtos.CategoryDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/categories")
@Tag(name = "Categories", description = "Categories API")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"Categories"}, summary = "Gets All Categories", description = "Returns All Categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public List<CategoryDto> getCategories() {
        return categoryService.listCategories();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {
            "Categories"}, summary = "Gets Category By Id", description = "Returns A Single Category given the Id of the Category")
    @Parameter(name = "categoryId", description = "Category Id of the required Category", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    public ResponseEntity<CategoryDto> getCategory(@NotNull @PathVariable(name = "categoryId") int categoryId) {
        try {
            return new ResponseEntity<>(categoryService.getCategory(categoryId), HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = {
            "Categories"}, summary = "Add Category", description = "Creates a new Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto category) {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {
            "Categories"}, summary = "Updates Category By Id", description = "Updates Single Category given the Id of the Category")
    @Parameter(name = "categoryId", description = "Category Id of the required Category", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<CategoryDto> updateCategory(@NotNull @PathVariable(name = "categoryId") int categoryId,
                                                      @Valid @RequestBody CategoryDto category) {
        try {
            return new ResponseEntity<>(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = {
            "Categories"}, summary = "Deletes Category By Id", description = "Deletes a Category given the Id of the Category")
    @Parameter(name = "categoryId", description = "Category Id of the required Category", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    })
    public ResponseEntity deleteCategory(@NotNull @PathVariable(name = "categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
