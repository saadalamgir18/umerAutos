package com.example.umerautos.controllersTest;

import com.example.umerautos.controllers.BrandsController;
import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.services.BrandsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

    @InjectMocks
    private BrandsController brandsController;

    @Mock
    private BrandsService brandsService;



    @ParameterizedTest
    @ValueSource(strings = {"lefan", "panther", "service"})
    @DisplayName("findAllBrands_Success")
    public void testFindAllBrands_Success(String str){
        List<BrandsResponseDTO> mockBrands = List.of(BrandsResponseDTO.builder()
                        .name(str)
                .build());

        when(brandsService.findAll()).thenReturn(mockBrands);

        ResponseEntity<?> response = brandsController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("findAllBrandsById_Success")
    public void testFindBrandsById_Success(){
        UUID brandId = UUID.randomUUID();

        BrandsResponseDTO mockBrand = BrandsResponseDTO.builder().build();

        mockBrand.setId(brandId);

        when(brandsService.findOne(brandId)).thenReturn(mockBrand);

        ResponseEntity<Object> response = brandsController.findOne(brandId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();


        assertNotNull(body);
        assertTrue((Boolean) body.get("isSuccess"));
        assertEquals("success", body.get("message"));

        // Access your DTO
        BrandsResponseDTO dto = (BrandsResponseDTO) body.get("data");

        assertEquals(brandId, dto.getId());

    }

    @Test
    @DisplayName("FindBrandsById_Fail")
    public void testFindBrandsById_Fail(){
        UUID brandId = UUID.randomUUID();

        BrandsResponseDTO mockBrand = BrandsResponseDTO.builder().build();

        mockBrand.setId(brandId);

        when(brandsService.findOne(brandId)).thenReturn(null);

            ResponseEntity<Object> response = brandsController.findOne(brandId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();


        assertNotNull(body);

        assertFalse((Boolean) body.get("isSuccess"));

        assertNotEquals("fail", body.get("message"));

        // Access your DTO
        BrandsResponseDTO dto = (BrandsResponseDTO) body.get("data");

        assertNull(dto);

    }

    @ParameterizedTest
    @ValueSource(strings = {"lefan", "panther", "service"})
    @DisplayName("SaveOneBrand_Success")
    public void testSaveOneBrand_Success(String str){

        BrandsRequestDTO mockRequest = BrandsRequestDTO.builder()
                .name(str)
                .build();

        BrandsResponseDTO responseDTO = BrandsResponseDTO
                .builder()
                .name(mockRequest.getName())
                .build();

        when(brandsService.createOne(mockRequest)).thenReturn(responseDTO);

        ResponseEntity<?> response = brandsController.saveOne(mockRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }
}
