package com.petproject.store.model;

public class Seller {

    public void serveTheBuyer(Buyer buyer) {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread());
            buyer.isServed = true;
        }
    }

}
