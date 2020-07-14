package com.petproject.store.model;

import com.petproject.store.services.StorePerformanceService;

import java.util.List;
import java.util.Queue;
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
        servedBuyers.set(0);
        performanceService.startServeBuyers();
        sellers.stream().forEach(seller -> {
            seller.serveTheBuyer(buyers.poll());
            servedBuyers.incrementAndGet();
        });
        log.info(performanceService.checkPerformance(servedBuyers.get()));
    }

}
