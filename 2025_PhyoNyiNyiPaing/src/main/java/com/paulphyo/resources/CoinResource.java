package com.paulphyo.resources;

import com.paulphyo.api.CoinRequest;
import com.paulphyo.core.service.CoinService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/coins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinResource {

    private final CoinService coinChangeService;

    public CoinResource() {
        this.coinChangeService = new CoinService();
    }

    public CoinResource(CoinService coinChangeService) {
        this.coinChangeService = coinChangeService;
    }

    @POST
    @Path("/min")
    public List<Double> getMinimumCoins(CoinRequest request) {
        validateRequest(request);

        return coinChangeService.getMinimumCoinChange(
                request.getTargetAmount(),
                request.getCoinDenominations()
        );
    }

    private void validateRequest(CoinRequest request) {
        if (request == null) {
            throw new WebApplicationException(
                    "Request cannot be null",
                    Response.Status.BAD_REQUEST
            );
        }
    }
}
