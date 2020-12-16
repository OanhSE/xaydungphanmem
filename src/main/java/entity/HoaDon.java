/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "HoaDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HoaDon.findAll", query = "SELECT h FROM HoaDon h"),
    @NamedQuery(name = "HoaDon.findByHdMa", query = "SELECT h FROM HoaDon h WHERE h.hdMa = :hdMa"),
    @NamedQuery(name = "HoaDon.findByHdSoLuong", query = "SELECT h FROM HoaDon h WHERE h.hdSoLuong = :hdSoLuong"),
    @NamedQuery(name = "HoaDon.findByHdNgayThue", query = "SELECT h FROM HoaDon h WHERE h.hdNgayThue = :hdNgayThue"),
    @NamedQuery(name = "HoaDon.findByHdNgayTra", query = "SELECT h FROM HoaDon h WHERE h.hdNgayTra = :hdNgayTra"),
    @NamedQuery(name = "HoaDon.findByHdTinhTrang", query = "SELECT h FROM HoaDon h WHERE h.hdTinhTrang = :hdTinhTrang")})
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "hd_Ma")
    private String hdMa;
    @Basic(optional = false)
    @Column(name = "hd_SoLuong")
    private int hdSoLuong;
    @Basic(optional = false)
    @Column(name = "hd_NgayThue")
    @Temporal(TemporalType.DATE)
    private Date hdNgayThue;
    @Basic(optional = false)
    @Column(name = "hd_NgayTra")
    @Temporal(TemporalType.DATE)
    private Date hdNgayTra;
    @Basic(optional = false)
    @Column(name = "hd_TinhTrang")
    private boolean hdTinhTrang;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hdMa")
    private Collection<ChiTietHoaDon> chiTietHoaDonCollection;
    @JoinColumn(name = "kh_Ma", referencedColumnName = "kh_Ma")
    @ManyToOne(optional = false)
    private KhachHang khMa;

    public HoaDon() {
    }

    public HoaDon(String hdMa) {
        this.hdMa = hdMa;
    }

    public HoaDon(String hdMa, int hdSoLuong, Date hdNgayThue, Date hdNgayTra, boolean hdTinhTrang) {
        this.hdMa = hdMa;
        this.hdSoLuong = hdSoLuong;
        this.hdNgayThue = hdNgayThue;
        this.hdNgayTra = hdNgayTra;
        this.hdTinhTrang = hdTinhTrang;
    }

    public HoaDon(String hdMa, KhachHang khMa, int hdSoLuong, Date hdNgayThue, Date hdNgayTra, boolean hdTinhTrang) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getHdMa() {
        return hdMa;
    }

    public void setHdMa(String hdMa) {
        this.hdMa = hdMa;
    }

    public int getHdSoLuong() {
        return hdSoLuong;
    }

    public void setHdSoLuong(int hdSoLuong) {
        this.hdSoLuong = hdSoLuong;
    }

    public Date getHdNgayThue() {
        return hdNgayThue;
    }

    public void setHdNgayThue(Date hdNgayThue) {
        this.hdNgayThue = hdNgayThue;
    }

    public Date getHdNgayTra() {
        return hdNgayTra;
    }

    public void setHdNgayTra(Date hdNgayTra) {
        this.hdNgayTra = hdNgayTra;
    }

    public boolean getHdTinhTrang() {
        return hdTinhTrang;
    }

    public void setHdTinhTrang(boolean hdTinhTrang) {
        this.hdTinhTrang = hdTinhTrang;
    }

    @XmlTransient
    public Collection<ChiTietHoaDon> getChiTietHoaDonCollection() {
        return chiTietHoaDonCollection;
    }

    public void setChiTietHoaDonCollection(Collection<ChiTietHoaDon> chiTietHoaDonCollection) {
        this.chiTietHoaDonCollection = chiTietHoaDonCollection;
    }

    public KhachHang getKhMa() {
        return khMa;
    }

    public void setKhMa(KhachHang khMa) {
        this.khMa = khMa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hdMa != null ? hdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoaDon)) {
            return false;
        }
        HoaDon other = (HoaDon) object;
        if ((this.hdMa == null && other.hdMa != null) || (this.hdMa != null && !this.hdMa.equals(other.hdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HoaDon[ hdMa=" + hdMa + " ]";
    }
    
}
