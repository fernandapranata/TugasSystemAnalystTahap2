/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tugassystemanalysttahap2;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
class Admin {
    private List<String> catatanAktivitas;

    public Admin() {
        catatanAktivitas = new ArrayList<>();
    }

    public Buku menambahkanBukuBaru(String judul, String pengarang, String isbn) {
        return new Buku(judul, pengarang, isbn);
    }

    public void mengelolaInformasiAnggota(AnggotaPerpustakaan anggota) {
        System.out.println("Informasi Anggota:");
        System.out.println("Nama: " + anggota.getNama());
        System.out.println("Nomor Anggota: " + anggota.getNomorAnggota());
        System.out.println("Alamat: " + anggota.getAlamat());
    }

    public void merekamPeminjaman(TransaksiPeminjaman transaksiPeminjaman) {
        System.out.println("Transaksi Peminjaman Direkam:");
        System.out.println("Buku: " + transaksiPeminjaman.getBuku().getJudul());
        System.out.println("Tanggal Peminjaman: " + transaksiPeminjaman.getTanggalPeminjaman());
        System.out.println("Durasi: " + transaksiPeminjaman.getDurasi() + " hari");
        catatAktivitas("Peminjaman direkam untuk buku " + transaksiPeminjaman.getBuku().getJudul());
    }

    public void membuatLaporanAktivitas() {
        System.out.println("Laporan Aktivitas Dibuat pada: " + new Date());
        System.out.println("Catatan Aktivitas:");
        for (String aktivitas : catatanAktivitas) {
            System.out.println("- " + aktivitas);
        }
    }

    public void mengelolaDanMengirimNotifikasi(Notifikasi notifikasi, AnggotaPerpustakaan anggota) {
        System.out.println("Notifikasi Dikirim:");
        System.out.println("Pesan: " + notifikasi.getPesan());
        System.out.println("Tanggal: " + notifikasi.getTanggal());
        anggota.menerimaNotifikasi(notifikasi);
        catatAktivitas("Notifikasi dikirim kepada " + anggota.getNama() + ": " + notifikasi.getPesan());
    }

    public void catatAktivitas(String aktivitas) {
        catatanAktivitas.add(aktivitas);
    }
}

class Notifikasi {
    private String pesan;
    private Date tanggal;

    public Notifikasi(String pesan, Date tanggal) {
        this.pesan = pesan;
        this.tanggal = tanggal;
    }

    public String getPesan() {
        return pesan;
    }

    public Date getTanggal() {
        return tanggal;
    }
}

class TransaksiPeminjaman {
    private Buku buku;
    private Date tanggalPeminjaman;
    private int durasi;

    public TransaksiPeminjaman(Buku buku, Date tanggalPeminjaman, int durasi) {
        this.buku = buku;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.durasi = durasi;
    }

    public Buku getBuku() {
        return buku;
    }

    public Date getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public int getDurasi() {
        return durasi;
    }
}

class TransaksiPengembalian {
    private TransaksiPeminjaman transaksiPeminjaman;
    private Date tanggalPengembalian;

    public TransaksiPengembalian(TransaksiPeminjaman transaksiPeminjaman, Date tanggalPengembalian) {
        this.transaksiPeminjaman = transaksiPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public TransaksiPeminjaman getTransaksiPeminjaman() {
        return transaksiPeminjaman;
    }

    public Date getTanggalPengembalian() {
        return tanggalPengembalian;
    }
}

class AnggotaPerpustakaan {
    private String nama;
    private String nomorAnggota;
    private String alamat;
    private List<TransaksiPeminjaman> sejarahPeminjaman;

    public AnggotaPerpustakaan(String nama, String nomorAnggota, String alamat) {
        this.nama = nama;
        this.nomorAnggota = nomorAnggota;
        this.alamat = alamat;
        this.sejarahPeminjaman = new ArrayList<>();
    }

    public String getNama() {
        return nama;
    }

    public String getNomorAnggota() {
        return nomorAnggota;
    }

    public String getAlamat() {
        return alamat;
    }

    public List<TransaksiPeminjaman> getSejarahPeminjaman() {
        return sejarahPeminjaman;
    }

    public void lihatDaftarBukuTersedia(List<Buku> daftarBuku) {
        System.out.println("Daftar Buku Tersedia:");
        for (Buku buku : daftarBuku) {
            if (buku.isTersedia()) {
                System.out.println("Judul: " + buku.getJudul() +
                                   ", Pengarang: " + buku.getPengarang() +
                                   ", ISBN: " + buku.getISBN());
            }
        }
    }

    public List<Buku> melakukanPencarianBuku(List<Buku> daftarBuku, String kriteria) {
        List<Buku> hasilPencarian = new ArrayList<>();
        for (Buku buku : daftarBuku) {
            if (buku.getJudul().contains(kriteria) ||
                buku.getPengarang().contains(kriteria) ||
                buku.getISBN().contains(kriteria)) {
                hasilPencarian.add(buku);
            }
        }
        return hasilPencarian;
    }

    public TransaksiPeminjaman meminjamBuku(Buku buku, int durasi) {
        TransaksiPeminjaman transaksi = new TransaksiPeminjaman(buku, new Date(), durasi);
        sejarahPeminjaman.add(transaksi);
        buku.setStatusKetersediaan(false); // Menandai buku tidak tersedia setelah dipinjam
        return transaksi;
    }

    public TransaksiPengembalian mengembalikanBuku(TransaksiPeminjaman transaksiPeminjaman) {
        TransaksiPengembalian transaksiPengembalian = new TransaksiPengembalian(transaksiPeminjaman, new Date());
        transaksiPeminjaman.getBuku().setStatusKetersediaan(true); // Menandai buku tersedia setelah dikembalikan
        return transaksiPengembalian;
    }

    public List<TransaksiPeminjaman> melihatRiwayatPeminjaman() {
        return sejarahPeminjaman;
    }

    public void menerimaNotifikasi(Notifikasi notifikasi) {
        System.out.println("Notifikasi: " + notifikasi.getPesan() +
                           " Tanggal: " + notifikasi.getTanggal());
    }
}


class Buku {
    private String judul;
    private String pengarang;
    private String isbn;
    private boolean statusKetersediaan;

    public Buku(String judul, String pengarang, String isbn) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.isbn = isbn;
        this.statusKetersediaan = true;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getISBN() {
        return isbn;
    }

    public boolean isTersedia() {
        return statusKetersediaan;
    }

    public void setStatusKetersediaan(boolean status) {
        this.statusKetersediaan = status;
    }
}

public class TugasSystemAnalystTahap2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Membuat objek AnggotaPerpustakaan dan Admin
        AnggotaPerpustakaan anggota = new AnggotaPerpustakaan("Alexander Wijaya", "001", "Jl. Kopi No. 18, Rajabasa, Bandar Lampung");
        Admin admin = new Admin();

        // Informasi Anggota
        admin.mengelolaInformasiAnggota(anggota);
        System.out.println("");

        // Menambahkan buku baru
        Buku buku1 = admin.menambahkanBukuBaru("Dilan 1990", "Pidi Baiq", "9786027870864");
        Buku buku2 = admin.menambahkanBukuBaru("Ancika 1995", "Pidi Baiq", "9786026716897");
        
        // Daftar Buku Tersedia
        anggota.lihatDaftarBukuTersedia(List.of(buku1, buku2));

        // Pencarian buku
        String kriteriaPencarian = "Dilan";
        List<Buku> hasilPencarian = anggota.melakukanPencarianBuku(List.of(buku1, buku2), kriteriaPencarian);

        // Hasil Pencarian
        System.out.println("\nHasil Pencarian untuk '" + kriteriaPencarian + "':");
        for (Buku buku : hasilPencarian) {
            System.out.println("Judul: " + buku.getJudul() +
                               ", Pengarang: " + buku.getPengarang() +
                               ", ISBN: " + buku.getISBN());
        }

        // Peminjaman buku
        TransaksiPeminjaman transaksi = anggota.meminjamBuku(buku1, 7);

        // Riwayat Peminjaman
        List<TransaksiPeminjaman> riwayatPeminjaman = anggota.melihatRiwayatPeminjaman();
        System.out.println("\nRiwayat Peminjaman:");
        for (TransaksiPeminjaman t : riwayatPeminjaman) {
            System.out.println("Buku: " + t.getBuku().getJudul() +
                               ", Tanggal Peminjaman: " + t.getTanggalPeminjaman() +
                               ", Durasi: " + t.getDurasi() + " hari");
        }
        System.out.println("");

        // Pengembalian buku
        TransaksiPengembalian transaksiPengembalian = anggota.mengembalikanBuku(transaksi);

        // Notifikasi
        Notifikasi notifikasi = new Notifikasi("Batas waktu peminjaman buku akan berakhir. Silahkan Kembalikan buku!", new Date());
        
        // Memanggil fungsi-fungsi Admin
        admin.merekamPeminjaman(transaksi);
        System.out.println("");
        admin.membuatLaporanAktivitas();
        System.out.println("");
        admin.mengelolaDanMengirimNotifikasi(notifikasi, anggota);
        System.out.println("");
    }
}