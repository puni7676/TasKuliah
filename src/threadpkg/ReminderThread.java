package threadpkg;

public class ReminderThread extends Thread {

    @Override
    public void run() {

        while(true) {

            try {

                System.out.println("Reminder tugas berjalan...");
                Thread.sleep(5000);

            } catch(Exception e) {

                System.out.println(e.getMessage());

            }

        }

    }

}