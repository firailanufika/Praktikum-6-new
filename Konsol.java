package absen;

import java.util.Scanner;

public class Konsol {
    Absen absen;

    public Konsol() {
        this.absen = new Absen();

        Scanner in = new Scanner(System.in);
        while (true) {

            System.out.println("Nama Lengkap:");
            String namaLengkap = in.nextLine();
            if (namaLengkap.isEmpty()) {
                System.out.println("Error: Tidak boleh kosong!");
                continue;
            }
            if (absen.cekDuplikat(namaLengkap)) {
                System.out.println("Error: Nama " + namaLengkap + " sudah absen!");
                continue;
            }

            System.out.println("Asal Kota:");
            String asalKota = in.nextLine();
            if (asalKota.isEmpty()) {
                System.out.println("Error: Tidak boleh kosong!");
                continue;
            }
            if (!absen.cekAsalKota(asalKota)) {
                System.out.println("Warning: Asal kota harus dari Jawa Timur");
                continue;
            }

            System.out.println("Nomor Ponsel:");
            String nomorPonsel = in.nextLine();
            if (!absen.cekNomorPonsel(nomorPonsel)) {
                System.out.println("Error: Format nomor ponsel salah!");
                continue;
            }

            System.out.println("Email:");
            String email = in.nextLine();
            if (!absen.cekEmail(email)) {
                System.out.println("Error: Format email salah!");
                continue;
            }

            absen.listPengunjung.add(new Pengunjung(namaLengkap, asalKota, nomorPonsel, email));

            cetakDaftarPengunjung();
        }
    }

    /**
     * Mencetak semua daftar pengunjung dalam bentuk tabel pada konsol.
     * Contoh:
     * +------------------+---------------+-------------------+
     * | NAMA LENGKAP     | ASAL KOTA     | NOMOR PONSEL      |
     * +------------------+---------------+-------------------+
     * | Budi Hartono     | Malang        | 1234512345        |
     * | Iwan Sutrisno    | Banyuwangi    | 23456723456       |
     * +------------------+---------------+-------------------+
     */
    public void cetakDaftarPengunjung() {
        String garis = "+------------------+---------------+-------------------+----------------------+%n";
        String format = "| %-16s | %-13s | %-17s | %-20s |%n";
        System.out.format(garis);
        System.out.format(format, "NAMA LENGKAP", "ASAL KOTA", "NOMOR PONSEL", "EMAIL");
        System.out.format(garis);
        for (Pengunjung p : absen.listPengunjung) {
            System.out.format(format, p.namaLengkap, p.asalKota, p.nomorPonsel, p.email);
        }
        System.out.format(garis);
    }
}
