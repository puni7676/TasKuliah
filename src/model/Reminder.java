package model;

public class Reminder {

    // overloading
    public void tampilReminder() {

        System.out.println(
                "Reminder aktif"
        );

    }

    public void tampilReminder(String pesan) {

        System.out.println(
                "Reminder : " + pesan
        );

    }
}