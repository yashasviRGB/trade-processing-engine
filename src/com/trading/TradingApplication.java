package com.trading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.*;

public class TradingApplication {

    private static final int READER_THREADS = 5;
    private static final int WORKER_THREADS = 15;

    public static void main(String[] args) throws Exception {

        BlockingQueue<Trade> queue =
                new LinkedBlockingQueue<>(5000);

        PortfolioService portfolio = new PortfolioService();

        BufferedReader reader =
                new BufferedReader(new FileReader("trades.csv"));

        Thread[] readers = new Thread[READER_THREADS];

        for (int i = 0; i < READER_THREADS; i++) {
            readers[i] =
                    new Thread(new FileReaderWorker(reader, queue));
            readers[i].start();
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(WORKER_THREADS);

        for (int i = 0; i < WORKER_THREADS; i++) {
            executor.submit(
                    new TradeProcessor(queue, portfolio)
            );
        }

        for (Thread t : readers) {
            t.join();
        }

        for (int i = 0; i < WORKER_THREADS; i++) {
            queue.put(new Trade("POISON", "", "", 1, 0));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        reader.close();

        portfolio.generateReport();
    }
}