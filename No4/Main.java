class Member {
    private int kd_member;
    private String nama;
    private String alamat;

    public Member(int kd_member, String nama, String alamat) {
        this.kd_member = kd_member;
        this.nama = nama;
        this.alamat = alamat;
    }

    public void displayInfo() {
        System.out.println("Kode Member: " + kd_member);
        System.out.println("Nama: " + nama);
        System.out.println("Alamat: " + alamat);
    }
}

class Dosen extends Member {
    private String kd_dosen;

    public Dosen(int kd_member, String nama, String alamat, String kd_dosen) {
        super(kd_member, nama, alamat);
        this.kd_dosen = kd_dosen;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Kode Dosen: " + kd_dosen);
    }
}

class Mahasiswa extends Member {
    private int nim;

    public Mahasiswa(int kd_member, String nama, String alamat, int nim) {
        super(kd_member, nama, alamat);
        this.nim = nim;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("NIM: " + nim);
    }
}

public class Main {
    public static void main(String[] args) {
        Member member1 = new Member(1, "John Doe", "Jl. ABC No. 123");
        member1.displayInfo();
        System.out.println();

        Dosen dosen1 = new Dosen(2, "Jane Smith", "Jl. XYZ No. 456", "D001");
        dosen1.displayInfo();
        System.out.println();

        Mahasiswa mahasiswa1 = new Mahasiswa(3, "Alice Johnson", "Jl. DEF No. 789", 12345);
        mahasiswa1.displayInfo();
    }
}





