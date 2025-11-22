package org.monke.streams.trades.model;

/**
 * Represents the current share value for a given ticker in the given currency.
 */
public record ShareValue(String ticker, Double price, Currency currency) {}