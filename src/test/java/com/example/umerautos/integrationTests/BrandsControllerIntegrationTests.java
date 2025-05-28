package com.example.umerautos.integrationTests;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import java.util.UUID;

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

        ResponseEntity<BrandsResponseDTO> getByIdResponse = restTemplate.getForEntity(baseUrl + "/brands/" + responseBody.getId(), BrandsResponseDTO.class);

        BrandsResponseDTO bodyById =  getByIdResponse.getBody();

        assertNotNull(bodyById);

        assertEquals(HttpStatus.OK, getByIdResponse.getStatusCode());


    }

    @Test
    public void getAllBrandsTest() throws Exception {

        ResponseEntity<BrandsResponseDTO[]> response = restTemplate.getForEntity(baseUrl + "/brands", BrandsResponseDTO[].class);

        BrandsResponseDTO[] body =  response.getBody();
        assertEquals(1, body.length);
        assertNotEquals(null, body);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void getBrandByIdTest() throws Exception {

        UUID nonExistingId = UUID.randomUUID();

        try {
            restTemplate.getForEntity(baseUrl + "/brands/" + nonExistingId, BrandsResponseDTO.class);
            fail("Expected HttpClientErrorException.NotFound to be thrown");
        } catch (HttpClientErrorException.NotFound ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }

    }

    @Test
    public void updateBrandTest(){
        BrandsRequestDTO createRequest = BrandsRequestDTO.builder().name("Panther").build();
        ResponseEntity<BrandsResponseDTO> createResponse = restTemplate.postForEntity(
                baseUrl + "/brands", createRequest, BrandsResponseDTO.class
        );
        UUID brandId = createResponse.getBody().getId();

        // Step 2: Update the brand using PUT
        BrandsRequestDTO updateRequest = BrandsRequestDTO.builder().name("Panther Updated").build();
        restTemplate.put(baseUrl + "/brands/" + brandId, updateRequest);

        // Step 3: Fetch and verify the update
        ResponseEntity<BrandsResponseDTO> updatedResponse = restTemplate.getForEntity(
                baseUrl + "/brands/" + brandId, BrandsResponseDTO.class
        );

        assertEquals("Panther Updated", updatedResponse.getBody().getName());
    }

}
