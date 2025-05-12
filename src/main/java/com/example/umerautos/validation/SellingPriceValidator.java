package com.example.umerautos.validation;

import com.example.umerautos.dto.ProductsRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SellingPriceValidator implements ConstraintValidator<ValidSellingPrice, ProductsRequestDTO> {

    @Override
    public boolean isValid(ProductsRequestDTO product, ConstraintValidatorContext context) {
        if (product == null) {
            return true; // Let @NotNull handle null checks
        }

        if (product.getSellingPrice() <= product.getPurchasePrice()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Selling price must be greater than purchase price")
                    .addPropertyNode("sellingPrice")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
