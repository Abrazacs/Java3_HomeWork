import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class Road extends Stage {
    private CyclicBarrier barrier;
    private ReentrantLock lock;

    public Road(int length, boolean isItFinalStage, CyclicBarrier barrier, ReentrantLock lock) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.isItFinalStage = isItFinalStage;
        this.barrier = barrier;
        this.lock = lock;
    }

    @Override
    public void go(Car c) {
        try {
            if(!isItFinalStage) {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            }else {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
                if (lock.tryLock()){
                    System.out.println("Winner is " + c.getName());
                    lock.lock();
                }
                barrier.await();
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

