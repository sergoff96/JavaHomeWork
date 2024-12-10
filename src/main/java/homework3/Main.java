package homework3;

public class Main {
    public static void main(String[] args) {
        CustomThreadPool threadPool = new CustomThreadPool(4);

        for (int i = 0; i < 10; i++) {
            int taskNumber = i;
            threadPool.execute(() -> {
                System.out.println("Executing task " + taskNumber + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination();

        System.out.println("All tasks completed.");
    }
}

