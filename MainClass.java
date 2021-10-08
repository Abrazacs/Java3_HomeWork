import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    public static final int CARS_COUNT = 4;
    static int flag =0;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(CARS_COUNT, ()-> {
            if(flag==0){
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
                flag=1;
            }
            else {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            }
        });
        ReentrantLock lock = new ReentrantLock();

        Semaphore semaphore = new Semaphore(CARS_COUNT/2,true);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60,false, barrier, lock), new Tunnel(semaphore), new Road(40,true, barrier, lock));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),barrier);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
    };
}

