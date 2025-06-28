package com.paulphyo.api;

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
    private float targetAmount;
    private List<Double> coinDenominations;

    public float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public List<Double> getCoinDenominations() {
        return coinDenominations;
    }

    public void setCoinDenominations(List<Double> coinDenominations) {
        this.coinDenominations = coinDenominations;
    }
}
