package homework3;

public class WorkerThread extends Thread {
    private final CustomThreadPool pool;

    public WorkerThread(CustomThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            Runnable task;
            synchronized (pool.getTaskQueue()) {
                while (pool.getTaskQueue().isEmpty()) {
                    if (pool.isShutdown()) {
                        return;
                    }
                    try {
                        pool.getTaskQueue().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                task = pool.getTaskQueue().poll();
            }
            try {
                task.run();
            } catch (RuntimeException e) {
                System.err.println("Task execution failed: " + e.getMessage());
            }
        }
    }
}
