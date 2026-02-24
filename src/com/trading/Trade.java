package com.trading;

public class Trade {

    private final String tradeId;
    private final String accountId;
    private final String symbol;
    private final int quantity;
    private final double price;

    public Trade(String tradeId, String accountId,
                 String symbol, int quantity, double price) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        this.tradeId = tradeId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTradeId() { return tradeId; }
    public String getAccountId() { return accountId; }
    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}