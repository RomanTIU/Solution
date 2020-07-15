package com.petproject.store.model;

import com.petproject.store.services.StorePerformanceService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
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


    public void trade(Queue<Buyer> buyers) throws InterruptedException {
        servedBuyers.set(0);
        performanceService.startServeBuyers();
//        Callable<Boolean> help = ()->{
//            synchronized (buyers){
//                sellers.stream().forEach(seller -> seller.serveTheBuyer(buyers.poll()));
//                servedBuyers.incrementAndGet();
//            }
//      return true;  };
        ExecutorService service = Executors.newFixedThreadPool(100); //Число допустимых тредов в 1 пуле

        for (int i = 0; i < 100; i++) {                                        //Число созданых тредов
            service.submit(()->{
                    sellers.stream().forEach(seller -> seller.serveTheBuyer(buyers.poll()));
                    servedBuyers.incrementAndGet();
                                    return true;  });
        }
        service.shutdown();
        service.awaitTermination(1,TimeUnit.SECONDS);                   // Время сколько ждёт 1 пул до завершения в секундах



//

        log.info(performanceService.checkPerformance(servedBuyers.get()));
    }


}
