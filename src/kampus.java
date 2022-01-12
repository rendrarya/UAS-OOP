import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class kampus {
    private int id;
    private String Nama = null;
    private String NIM = null;
    private String Alamat = null;
    private String Kode = null;
    public Object conn;

    public kampus(int inputId, String inputNama, String inputNIM, String inputAlamat, String inputKode) {
        this.id = inputId;
        this.Nama = inputNama;
        this.NIM = inputNIM;
        this.Alamat = inputAlamat;
        this.Kode = inputKode;
    }

    public int getId(){
        return id;
    }

    public String getNama(){
        return Nama;
    }

    public String getNIM(){
        return NIM;
    }

    public String getAlamat(){
        return Alamat;
    }

    public String getKode(){
        return Kode;
    }

    public void setId(String text) {
    }


    public void setNama() {
    }


    public void setNama(String text) {
    }


    public void setNama(double parseDouble) {
    }


    public void setNim(String text) {
    }


    public void setAlamat(String text) {
    }


    public void setKode(String text) {
    }
}