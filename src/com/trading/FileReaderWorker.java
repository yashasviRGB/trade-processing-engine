package com.trading;

import java.io.BufferedReader;
import java.util.concurrent.BlockingQueue;

public class FileReaderWorker implements Runnable {

    private final BufferedReader reader;
    private final BlockingQueue<Trade> queue;

    public FileReaderWorker(BufferedReader reader,
                            BlockingQueue<Trade> queue) {
        this.reader = reader;
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {

                String line;

                synchronized (reader) {
                    line = reader.readLine();
                }

                if (line == null) {
                    break;
                }

                String[] parts = line.split(",");

                try {
                    Trade trade = new Trade(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            Double.parseDouble(parts[4].trim())
                    );

                    queue.put(trade);

                } catch (Exception e) {
                    System.out.println("Invalid trade skipped: " + line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}