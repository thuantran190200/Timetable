package Database;

public class Userdata {
    String hoten, tendangnhap, email, sodienthoai, matkhau;

    public Userdata() {
    }

    public Userdata(String hoten, String tendangnhap, String email, String sodienthoai, String matkhau) {
        this.hoten = hoten;
        this.tendangnhap = tendangnhap;
        this.email = email;
        this.sodienthoai = sodienthoai;
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}

