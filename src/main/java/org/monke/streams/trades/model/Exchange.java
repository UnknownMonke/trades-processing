package org.monke.streams.trades.model;

public record Exchange(ExchangeCode symbol, String name, Currency currency) {}
