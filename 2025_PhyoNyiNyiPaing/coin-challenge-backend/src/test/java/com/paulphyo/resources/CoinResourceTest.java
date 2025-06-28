package com.paulphyo.resources;

import com.paulphyo.api.CoinRequest;
import com.paulphyo.core.service.CoinService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
@DisplayName("CoinResource Integration Test")
class CoinResourceTest {

    private static final CoinService COIN_SERVICE = mock(CoinService.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new CoinResource(COIN_SERVICE))
            .build();

    private CoinRequest validRequest;
    private List<Double> standardDenominations;

    @BeforeEach
    void setUp() {
        standardDenominations = Arrays.asList(0.01, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00);
        validRequest = new CoinRequest(1.67, Arrays.asList(1.0, 0.5, 0.1, 0.05, 0.01));
    }

    @AfterEach
    void tearDown() {
        reset(COIN_SERVICE);
    }

    @Test
    @DisplayName("Returns minimum coins when request is valid")
    void returnsMinimumCoins_whenRequestIsValid() {
        // Given
        double requestAmount = 1.67;
        List<Double> mockServiceResponse = Arrays.asList(0.01, 0.01, 0.05, 0.1, 0.5, 1.0);
        String expectedJsonResponse = "[0.01,0.01,0.05,0.1,0.5,1.0]";

        when(COIN_SERVICE.getMinimumCoinChange(requestAmount, validRequest.getCoinDenominations()))
                .thenReturn(mockServiceResponse);

        // When
        String jsonResponse = EXT.target("/coins/min")
                .request()
                .post(Entity.json(validRequest), String.class);

        // Then
        assertThat(jsonResponse).isEqualTo(expectedJsonResponse);
        verify(COIN_SERVICE).getMinimumCoinChange(requestAmount, validRequest.getCoinDenominations());
    }

    @Test
    @DisplayName("Returns Validation Error 422 when target amount is negative")
    void returnsValidationError_whenTargetAmountIsNegative() {
        validRequest.setTargetAmount(-1.0);

        Response response = EXT.target("/coins/min")
                .request()
                .post(Entity.json(validRequest));

        assertThat(response.getStatus()).isEqualTo(422);
        verifyNoInteractions(COIN_SERVICE);
    }

    @Test
    @DisplayName("Returns Validation Error 422 when coin denominations list is empty")
    void returnsValidationError_whenCoinDenominationsEmpty() {
        // Given
        validRequest.setCoinDenominations(List.of());

        // When
        Response response = EXT.target("/coins/min")
                .request()
                .post(Entity.json(validRequest));

        // Then
        assertThat(response.getStatus()).isEqualTo(422);
        verifyNoInteractions(COIN_SERVICE);
    }

    @Test
    @DisplayName("Returns empty list when target amount is zero")
    void returnsEmptyList_whenTargetAmountZero() {
        validRequest.setTargetAmount(0.0);

        when(COIN_SERVICE.getMinimumCoinChange(0.0, validRequest.getCoinDenominations()))
                .thenReturn(List.of());

        String jsonResponse = EXT.target("/coins/min")
                .request()
                .post(Entity.json(validRequest), String.class);

        assertThat(jsonResponse).isEqualTo("[]");
        verify(COIN_SERVICE).getMinimumCoinChange(0.0, validRequest.getCoinDenominations());
    }
}