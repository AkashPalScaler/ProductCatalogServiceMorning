package com.scaler.productcatalogservice.mappers;

import com.scaler.productcatalogservice.dtos.ProductDto;
import com.scaler.productcatalogservice.models.Category;
import com.scaler.productcatalogservice.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductMapperTest {
    //toDto
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    @DisplayName("ProductMapper.toDto copies the fields and category to dto successfully")
    public void toDto_product_copiesFieldsAndCatgeoryToDtoSuccessfully() {
        //Arrange

        Category category = new Category();
        category.setId(1L);
        category.setName("Apple");
        Product product = new Product();
        product.setId(1L);
        product.setName("Macbook Pro");
        product.setDescription("14 inch Macbook Pro with nano display");
        product.setCategory(category);

        // Act
        ProductDto productDto = productMapper.toDto(product);

        // Assert
        assertThat(productDto.getId()).isEqualTo(1L);
        assertThat(productDto.getName()).isEqualTo("Macbook Pro");
        assertThat(productDto.getDescription()).isEqualTo("14 inch Macbook Pro with nano display");
        assertThat(productDto.getCategory().getName()).isEqualTo("Apple");
    }

    @Test
    @DisplayName("ProductMapper.toDto with product having no category set should copy null category")
    public void toDto_productHavingNoCategorySet_ShouldCopyNullCategory() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Macbook Pro");

        ProductDto productDto = productMapper.toDto(product);
        assertThat(productDto.getId()).isEqualTo(1L);
        assertThat(productDto.getName()).isEqualTo("Macbook Pro");
        assertThat(productDto.getDescription()).isNull();
    }
}
