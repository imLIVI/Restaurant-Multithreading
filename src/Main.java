import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class Main {

    public static final int NUM_OF_GUESTS = 5;
    public static final int NUM_OF_WAITERS = 5;
    public static final int COOKING_TIME = 500;
    public static final int WAIT_A_DISHES = 600;

    public static void main(String[] args) {
        List<Guest> guests = new ArrayList<>();
        List<Waiter> waiters = new ArrayList<>();
        Lock lock = new ReentrantLock();
        Condition newClient = lock.newCondition();
        Condition waitWaiter = lock.newCondition();
        Condition waitDish = lock.newCondition();

        //Kitchen
        for (int i = 0; i < NUM_OF_WAITERS; i++) {
            int finalI = i;
            new Thread(() -> {
                lock.lock();
                // Официант приходит на работу и обслуживает гостя
                System.out.println("Официант " + finalI + " на работе!");
                waiters.add(new Waiter());
                // Сообщаем, что свободный официант появился
                waitWaiter.signal();
                if (guests.size() == 0) {
                    System.out.println("Гостей пока нет...");
                    try {
                        newClient.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (waiters.size() != 0) {
                    System.out.println("Официант " + finalI + " взял заказ");
                    try {
                        Thread.sleep(COOKING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitDish.signal();
                    System.out.println("Официант " + finalI + " несет заказ");
                }
                waiters.remove(0);
                lock.unlock();
            }).start();
        }

        //Guests
        for (int i = 0; i < NUM_OF_GUESTS; i++) {
            int finalI = i;
            new Thread(() -> {
                lock.lock();
                guests.add(new Guest());
                System.out.println("Посетитель " + finalI + " в ресторане");
                newClient.signal();
                //Если нет свободных официантов, ожидание
                while (waiters.size() == 0) {
                    System.out.println("Нет свободных официантов. Надо подождать...");
                    try {
                        waitWaiter.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(WAIT_A_DISHES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    waitDish.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Посетитель " + finalI + " вышел из ресторана");
                guests.remove(0);
                lock.unlock();
            }).start();
        }
    }
}
