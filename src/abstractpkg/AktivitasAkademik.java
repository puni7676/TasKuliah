package abstractpkg;

public abstract class AktivitasAkademik {

    protected String nama;
    protected String tanggal;

    public AktivitasAkademik(String nama, String tanggal) {
        this.nama = nama;
        this.tanggal = tanggal;
    }

    public abstract void tampilInfo();

}