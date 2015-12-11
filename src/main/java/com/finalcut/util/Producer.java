package com.finalcut.util;

import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
	private BlockingQueue<Doc> queue;

	public Producer(BlockingQueue<Doc> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			for (int i = 0; i < 1000; ++i) {
				StringDoc doc = new StringDoc("Number-" + i);
				queue.put(doc);
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
}
