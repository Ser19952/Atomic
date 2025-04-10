package com.learding.concurrency.atomicity;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		Counter counter = new Counter();

		Runnable taskWriter1 = () -> {
			for (int i = 0; i < 1000; i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				counter.increment();
			}

		};

		Runnable taskWriter2 = () -> {
			for (int i = 0; i < 1000; i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				counter.increment();
			}

		};

		Runnable taskReader = () -> {
			int firstValue = counter.getCounter();
			boolean run = true;
			while (run) {
				if (firstValue != counter.getCounter()) {
			    	firstValue = counter.getCounter();
				}
				if (counter.getCounter() >= 500) {
					run = false;
				}
			}
		};

		Thread t1 = new Thread(taskWriter1);
		Thread t2 = new Thread(taskWriter2);
		Thread t3 = new Thread(taskReader);

		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

		System.out.printf("Final result: %s", counter.getCounter());

	}
}
