package com.hse.onlinestore.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.hse.onlinestore.dtos.ProductDto;
import com.hse.onlinestore.exceptions.CategoryNotFoundException;
import com.hse.onlinestore.exceptions.NotSupportedCurrencyException;
import com.hse.onlinestore.exceptions.ProductNotFoundException;
import com.hse.onlinestore.services.ProductService;
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
@RequestMapping(path = "/api/products")
@Tag(name = "Products", description = "Products API")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {"Products"}, summary = "Gets All Products", description = "Returns All Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public List<ProductDto> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {
            "Products"}, summary = "Gets Product By Id", description = "Returns A Single Product given the Id of the product")
    @Parameter(name = "productId", description = "Product Id of the required Product", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    public ResponseEntity<ProductDto> getProduct(@NotNull @PathVariable(name = "productId") long productId) {
        try {
            return new ResponseEntity<>(productService.findProduct(productId), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = {
            "Products"}, summary = "Add Product", description = "Creates new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto product) {
        try {
            return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity("Category Not Found", HttpStatus.BAD_REQUEST);
        } catch (NotSupportedCurrencyException e) {
            return new ResponseEntity("Currency Not Supported", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = {
            "Products"}, summary = "Updates Product By Id", description = "Updates Single Product given the Id of the product")
    @Parameter(name = "productId", description = "Product Id of the required product", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<ProductDto> updateProduct(@NotNull @PathVariable(name = "productId") long productId,
                                                    @Valid @RequestBody ProductDto product) {
        try {
            return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity("Product Not Found", HttpStatus.NOT_FOUND);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity("Category Not Found", HttpStatus.BAD_REQUEST);
        } catch (NotSupportedCurrencyException e) {
            return new ResponseEntity("Currency Not Supported", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = {
            "Products"}, summary = "Deletes Product By Id", description = "Deletes a Product given the Id of the product")
    @Parameter(name = "productId", description = "Product Id of the required product", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    })
    public ResponseEntity deleteProduct(@NotNull @PathVariable(name = "productId") long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
