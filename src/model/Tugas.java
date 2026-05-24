package model;

public class Tugas {

    private int id;
    private String namaTugas;
    private String mataKuliah;
    private String deadline;
    private String status;

    public Tugas() {
    }

    public Tugas(
            int id,
            String namaTugas,
            String mataKuliah,
            String deadline,
            String status
    ) {

        this.id = id;
        this.namaTugas = namaTugas;
        this.mataKuliah = mataKuliah;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTugas() {
        return namaTugas;
    }

    public void setNamaTugas(String namaTugas) {
        this.namaTugas = namaTugas;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}