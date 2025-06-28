package com.paulphyo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request model for coin change operations.
 * <p>
 * This class represents the incoming JSON payload for calculating the
 * minimum number of coins required to form a target amount using
 * provided coin denominations.
 * </p>
 *
 * Example JSON:
 * <pre>
 * {
 *   "targetAmount": 2.75,
 *   "coinDenominations": [1.0, 0.5, 0.2, 0.05]
 * }
 * </pre>
 */
public class CoinRequest {

    @Min(value = 0, message = "targetAmount must be >= 0")
    @Max(value = 10000, message = "targetAmount must be <= 10000")
    private double targetAmount;

    @NotNull(message = "coinDenominations cannot be null")
    @NotEmpty(message = "coinDenominations cannot be empty")
    private List<Double> coinDenominations;

    // Default constructor for Jackson
    public CoinRequest() {
    }

    // Constructor for unit tests
    public CoinRequest(double targetAmount, List<Double> coinDenominations) {
        this.targetAmount = targetAmount;
        this.coinDenominations = coinDenominations;
    }

    @JsonProperty
    public double getTargetAmount() {
        return targetAmount;
    }

    @JsonProperty
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    @JsonProperty
    public List<Double> getCoinDenominations() {
        return coinDenominations;
    }

    @JsonProperty
    public void setCoinDenominations(List<Double> coinDenominations) {
        this.coinDenominations = coinDenominations;
    }
}
