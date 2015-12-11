package com.finalcut.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

	private static int NUM_CONSUMERS = 3;

	public static void main(String[] args) throws Exception {

		// Work queue. Produce adds to it.  Consumers remove from it.
		ArrayBlockingQueue<Doc> queue = new ArrayBlockingQueue<Doc>(100);

		// Counts map.  Keys are "indexed", "failed".
		ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<String, Integer>();
	
		// Create threadpool for consumers	
		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < NUM_CONSUMERS; ++i) {
			executor.execute(new Consumer(queue, counts));
		}

		Thread producer = new Thread(new Producer(queue));

		long start = System.currentTimeMillis();
		producer.start();
		producer.join();

		for (int i = 0; i < NUM_CONSUMERS; ++i) {
			queue.put(new PoisonPill());
		}

		executor.shutdown();
		executor.awaitTermination(10L, TimeUnit.MINUTES);

		long end = System.currentTimeMillis();

		System.out.println("Elapsed time: " + (end - start) + "ms");

		for (Map.Entry<String, Integer> e: counts.entrySet()) {
			System.out.println(e);
		}
	}
}
