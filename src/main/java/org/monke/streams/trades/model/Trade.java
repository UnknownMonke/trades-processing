package org.monke.streams.trades.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Trade(
    String transactionId,
    Long transactionDate,
    String userId,
    Side side,
    Integer quantity,
    Double priceMultiplier,
    String ticker,
    Exchange exchange,
    Map<String, Double> exchangeRates) {

    public enum Side {
        BUY,
        SELL
    }

    /**
     * <p>Returns the exchange rate between 2 currencies.</p>
     * <p>Currencies must exist in the map.</p>
     * <p>Rate name is derived from currencies.</p>
     * <ul>
     *     <li>Ex : "EUR_USD" = from EUR to USD.</li>
     * </ul>
     */
    public Double getExchangeRate(Currency from, Currency to) {
        String key = from + "_" + to;
        String invertedKey = to + "_" + from;

        if (from == null || to == null || (!exchangeRates.containsKey(key) && !exchangeRates.containsKey(invertedKey))) {
            throw new RuntimeException(String.format("Currency unknown : from = %s / to = %s", from, to));
        }

        if (exchangeRates.containsKey(key)) {
            return exchangeRates.get(key);
        }
        return exchangeRates.get(invertedKey);
    }
}
