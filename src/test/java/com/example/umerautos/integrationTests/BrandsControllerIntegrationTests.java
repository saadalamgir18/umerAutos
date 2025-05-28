package com.example.umerautos.integrationTests;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BrandsControllerIntegrationTests extends BaseIntegrationTest {


    private BrandsRequestDTO brands;





    @Test
    public void createBrandsTest() throws Exception {
        BrandsRequestDTO brandsResponseDTO = BrandsRequestDTO.builder().name("panther").build();


        ResponseEntity<BrandsResponseDTO> response = restTemplate.postForEntity(
                baseUrl + "/brands",
                brandsResponseDTO,
                BrandsResponseDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        BrandsResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("panther", responseBody.getName());

    }

    @Test
    public void getAllBrandsTest() throws Exception {

        ResponseEntity<BrandsResponseDTO[]> response = restTemplate.getForEntity(baseUrl + "/brands", BrandsResponseDTO[].class);

        BrandsResponseDTO[] body =  response.getBody();
        assertEquals(1, body.length);
        assertNotEquals(null, body);
        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

}
