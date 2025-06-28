package com.paulphyo.core.service;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.*;

public class CoinService {

    private static final Set<Double> ALLOWED_DENOMINATIONS = Set.of(
            0.01, 0.05, 0.10, 0.20, 0.50,
            1.00, 2.00, 5.00, 10.00, 50.00,
            100.00, 1000.00
    );

    private static final double MIN_AMOUNT = 0.0;
    private static final double MAX_AMOUNT = 10000.0;

    public List<Double> getMinimumCoinChange(double targetAmount, List<Double> denominations) {
        validateTargetAmount(targetAmount);
        validateCoinDenominations(denominations);
        return computeGreedyChange(targetAmount, denominations);
    }

    private void validateTargetAmount(double amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new WebApplicationException(
                    String.format("Target amount must be between %.2f and %.2f", MIN_AMOUNT, MAX_AMOUNT),
                    Response.Status.BAD_REQUEST
            );
        }
    }

    private void validateCoinDenominations(List<Double> denominations) {
        if (denominations == null || denominations.isEmpty()) {
            throw new WebApplicationException("Coin denominations cannot be null or empty",
                    Response.Status.BAD_REQUEST);
        }

        for (Double denomination : denominations) {
            if (denomination == null) {
                throw new WebApplicationException("Denomination cannot be null",
                        Response.Status.BAD_REQUEST);
            }
            if (!ALLOWED_DENOMINATIONS.contains(denomination)) {
                throw new WebApplicationException(
                        String.format("Invalid coin denomination: %.2f", denomination),
                        Response.Status.BAD_REQUEST
                );
            }
        }
    }

    private List<Double> computeGreedyChange(double amount, List<Double> denominations) {
        amount = Math.round(amount * 100.0) / 100.0;
        List<Double> result = new ArrayList<>();

        denominations.sort(Comparator.reverseOrder());

        for (double coin : denominations) {
            int count = (int) (amount / coin);
            for (int i = 0; i < count; i++) {
                result.add(coin);
            }
            amount = Math.round((amount - coin * count) * 100.0) / 100.0;
        }

        if (amount != 0.0) {
            throw new WebApplicationException(
                    String.format("Cannot make exact change for %.2f", amount),
                    Response.Status.BAD_REQUEST
            );
        }

        Collections.sort(result);
        return result;
    }
}
