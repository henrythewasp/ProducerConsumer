package com.inovem.solr;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

class Consumer implements Runnable {

	private BlockingQueue<Doc> queue;
	private ConcurrentHashMap<String, Integer> counts;
	private HashMap<String, Integer> localCounts;

	public Consumer(BlockingQueue<Doc> queue,
					ConcurrentHashMap<String, Integer> counts) {
		this.queue = queue;
		this.counts = counts;
		localCounts = new HashMap<String, Integer>();
	}

	public void run() {
		try {
			while(true) {
				Doc doc = queue.take();
				if (doc.isPoisonPill()) { 
					break; 
				}

				// Process Doc and update localCounts

			}
			mergeCounts();
		} catch (Exception e) { e.printStackTrace(); }	
	}

	private void mergeCounts() {
		for (Map.Entry<String, Integer> e: localCounts.entrySet()) {
			String k = e.getKey();
			Integer count = e.getValue();

			while(true) {
				Integer currentCount = counts.get(k);
				if (currentCount == null) {
					if (counts.putIfAbsent(k, count) == null) { 
						break; 
					}
					
				} else if (counts.replace(k, currentCount, currentCount + count)) { 
					break; 
				}
			}
		}
	}
}
