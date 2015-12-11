package com.inovem.solr;

import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
	private BlockingQueue<Doc> queue;

	public Producer(BlockingQueue<Doc> queue) {
		this.queue = queue;
	}

	public void run() {
		
	}
}
