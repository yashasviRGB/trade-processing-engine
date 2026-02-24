package com.trading;

import java.util.concurrent.BlockingQueue;

public class TradeProcessor implements Runnable {

    private final BlockingQueue<Trade> queue;
    private final PortfolioService portfolio;

    public TradeProcessor(BlockingQueue<Trade> queue,
                          PortfolioService portfolio) {
        this.queue = queue;
        this.portfolio = portfolio;
    }

    @Override
    public void run() {

        try {
            while (true) {

                Trade trade = queue.take();

                if ("POISON".equals(trade.getTradeId())) {
                    break;
                }

                portfolio.update(trade);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}