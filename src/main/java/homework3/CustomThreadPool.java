package homework3;

import java.util.LinkedList;
import java.util.Queue;

public class CustomThreadPool {
    private final WorkerThread[] threads;
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private boolean isShutdown = false;

    public CustomThreadPool(int capacity) {
        threads = new WorkerThread[capacity];
        for (int i = 0; i < capacity; i++) {
            threads[i] = new WorkerThread(this);
            threads[i].start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Thread pool is shutdown. No new tasks are accepted.");
        }
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    public synchronized void shutdown() {
        isShutdown = true;
        synchronized (taskQueue) {
            taskQueue.notifyAll();
        }
    }

    public void awaitTermination() {
        for (WorkerThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    Queue<Runnable> getTaskQueue() {
        return taskQueue;
    }

    boolean isShutdown() {
        return isShutdown;
    }
}