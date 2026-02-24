package com.trading;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PortfolioService {

    private final ConcurrentHashMap<String,
            ConcurrentHashMap<String, AtomicInteger>> portfolio
            = new ConcurrentHashMap<>();

    public void update(Trade trade) {

        portfolio
                .computeIfAbsent(trade.getAccountId(),
                        k -> new ConcurrentHashMap<>())
                .computeIfAbsent(trade.getSymbol(),
                        k -> new AtomicInteger(0))
                .addAndGet(trade.getQuantity());
    }

    public void generateReport() {

        portfolio.forEach((account, positions) -> {

            int totalQty = positions.values()
                    .stream()
                    .mapToInt(AtomicInteger::get)
                    .sum();

            System.out.println("Account: " + account);
            System.out.println("Total Quantity: " + totalQty);

            positions.forEach((symbol, qty) ->
                    System.out.println("   " + symbol + " -> " + qty.get()));

            System.out.println("--------------------------------");
        });
    }
}