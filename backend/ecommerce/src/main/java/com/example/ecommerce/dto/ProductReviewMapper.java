package com.example.ecommerce.dto;


import com.example.ecommerce.entity.ProductReview;

//@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
//    @Mapping(source = "user.username", target = "username")
    ProductReviewDto toDto(ProductReview productReview);
}
