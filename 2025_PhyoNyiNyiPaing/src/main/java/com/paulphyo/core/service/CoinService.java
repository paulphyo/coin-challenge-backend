package com.paulphyo.core.service;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.*;

/**
 * Service class that provides logic for computing the minimum number of coins
 * needed to make up a target amount, using a greedy algorithm.
 */
public class CoinService {

    // Allowed coin denominations in dollars
    private static final Set<Double> ALLOWED_DENOMINATIONS = Set.of(
            0.01, 0.05, 0.10, 0.20, 0.50,
            1.00, 2.00, 5.00, 10.00, 50.00,
            100.00, 1000.00
    );

    // Valid input range for the target amount
    private static final double MIN_AMOUNT = 0.0;
    private static final double MAX_AMOUNT = 10000.0;

    /**
     * Computes the minimum number of coins needed to form the target amount.
     *
     * @param targetAmount   The amount to be made up, in dollars
     * @param denominations  A list of coin denominations available
     * @return A list of coin values (sorted in ascending order) that make up the amount
     * @throws WebApplicationException if the amount or denominations are invalid,
     *         or if exact change cannot be calculated
     */
    public List<Double> getMinimumCoinChange(double targetAmount, List<Double> denominations) {
        validateCoinDenominations(denominations);
        return computeGreedyChange(targetAmount, denominations);
    }

    /**
     * Validates that the given coin denominations are allowed and non-null.
     *
     * @param denominations The list of denominations to validate
     * @throws WebApplicationException if the list is null, empty, or contains invalid values
     */
    private void validateCoinDenominations(List<Double> denominations) {
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

    /**
     * Greedy algorithm to compute the minimum number of coins needed.
     * It tries the largest coins first and works its way down.
     *
     * @param amount The amount to make change for (rounded to 2 decimal places)
     * @param denominations The list of valid denominations
     * @return A list of coins used (sorted in ascending order)
     * @throws WebApplicationException if exact change cannot be made
     */
    private List<Double> computeGreedyChange(double amount, List<Double> denominations) {
        // Round to 2 decimal places to avoid floating-point errors
        amount = Math.round(amount * 100.0) / 100.0;
        List<Double> result = new ArrayList<>();

        // Sort denominations descending for greedy selection
        denominations.sort(Comparator.reverseOrder());

        for (double coin : denominations) {
            int count = (int) (amount / coin);
            for (int i = 0; i < count; i++) {
                result.add(coin);
            }
            amount = Math.round((amount - coin * count) * 100.0) / 100.0;
        }

        // If amount left is not zero, exact change is not possible
        if (amount != 0.0) {
            throw new WebApplicationException(
                    String.format("Cannot make exact change for %.2f", amount),
                    Response.Status.BAD_REQUEST
            );
        }

        // Sort result in ascending order before returning
        Collections.sort(result);
        return result;
    }
}
