package com.petproject.store.model;

import com.petproject.store.services.StorePerformanceService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Stall {


    Logger log;
    List<Seller> sellers;
    AtomicInteger servedBuyers = new AtomicInteger(0);
    StorePerformanceService performanceService = new StorePerformanceService();

    public Stall(Logger log, List<Seller> sellers) {
        this.log = log;
        this.sellers = sellers;
    }


    public void trade(Queue<Buyer> buyers) {
        Thread job = new Thread(new Runnable() {
            @Override
            public void run() {
                while (buyers.size() > 0)
                for (Seller s : sellers){
                    s.serveTheBuyer(buyers.poll());
                    servedBuyers.incrementAndGet();
                }
            }
        });
        servedBuyers.set(0);
        performanceService.startServeBuyers();
        sellers.get(0).serveTheBuyer(buyers.poll());

        job.start();

        log.info(performanceService.checkPerformance(servedBuyers.get()));
    }


}
