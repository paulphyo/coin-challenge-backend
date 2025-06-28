package com.paulphyo.resources;

import com.paulphyo.api.CoinRequest;
import com.paulphyo.core.service.CoinService;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST resource that provides endpoints for coin change calculations.
 */
@Path("/coins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinResource {

    private final CoinService coinChangeService;

    /**
     * Default constructor initializes a new instance of CoinService.
     */
    public CoinResource() {
        this.coinChangeService = new CoinService();
    }

    /**
     * Constructor for dependency injection (unit tests)
     *
     * @param coinChangeService the CoinService instance to use
     */
    public CoinResource(CoinService coinChangeService) {
        this.coinChangeService = coinChangeService;
    }

    /**
     * Computes the minimum number of coins needed to make up the target amount.
     *
     * @param request the coin change request containing the amount and denominations
     * @return a list of coin values that make up the amount using the minimum number of coins
     * @throws WebApplicationException if the request is invalid or exact change cannot be made
     */
    @POST
    @Path("/min")
    public List<Double> getMinimumCoins(@Valid CoinRequest request) {
        return coinChangeService.getMinimumCoinChange(
                request.getTargetAmount(),
                request.getCoinDenominations()
        );
    }
}
