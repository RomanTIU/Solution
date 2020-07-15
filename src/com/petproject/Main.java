package com.petproject;

import com.petproject.store.Store;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Store store = new Store();
        store.openStore();
    }
}
