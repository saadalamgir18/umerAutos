package com.example.umerautos.controllersTest;

import com.example.umerautos.controllers.BrandsController;
import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.services.BrandsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class BrandControllerTest {

    @InjectMocks
    private BrandsController brandsController;

    @Mock
    private BrandsService brandsService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllBrands_Success(){
        List<BrandsResponseDTO> mockBrands = List.of(BrandsResponseDTO.builder()
                        .name("lefan")
                .build());

        when(brandsService.findAll()).thenReturn(mockBrands);

        ResponseEntity<?> response = brandsController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindBrandsById_Success(){
        UUID brandId = UUID.randomUUID();

        BrandsResponseDTO mockBrand = BrandsResponseDTO.builder().build();

        mockBrand.setId(brandId);

        when(brandsService.findOne(brandId)).thenReturn(mockBrand);

        ResponseEntity<Object> response = brandsController.findOne(brandId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();


        assertTrue((Boolean) body.get("isSuccess"));
        assertEquals("success", body.get("message"));

        // Access your DTO
        BrandsResponseDTO dto = (BrandsResponseDTO) body.get("data");

        assertEquals(brandId, dto.getId());

    }

    @Test
    public void testFindBrandsById_Fail(){
        UUID brandId = UUID.randomUUID();

        BrandsResponseDTO mockBrand = BrandsResponseDTO.builder().build();

        mockBrand.setId(brandId);

        when(brandsService.findOne(brandId)).thenReturn(null);

            ResponseEntity<Object> response = brandsController.findOne(brandId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();


        assertFalse((Boolean) body.get("isSuccess"));

        assertNotEquals("fail", body.get("message"));

        // Access your DTO
        BrandsResponseDTO dto = (BrandsResponseDTO) body.get("data");

        assertNull(dto);

    }

    @Test
    public void testSaveOneBrand_Success(){

        BrandsRequestDTO mockRequest = BrandsRequestDTO.builder()
                .name("lefan")
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
