/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "Loai")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loai.findAll", query = "SELECT l FROM Loai l"),
    @NamedQuery(name = "Loai.findByLoaiMa", query = "SELECT l FROM Loai l WHERE l.loaiMa = :loaiMa"),
    @NamedQuery(name = "Loai.findByLoaiTenLoai", query = "SELECT l FROM Loai l WHERE l.loaiTenLoai = :loaiTenLoai"),
    @NamedQuery(name = "Loai.findByLoaiGia", query = "SELECT l FROM Loai l WHERE l.loaiGia = :loaiGia"),
    @NamedQuery(name = "Loai.findByLoaiGiaTreNgay", query = "SELECT l FROM Loai l WHERE l.loaiGiaTreNgay = :loaiGiaTreNgay")})
public class Loai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "loai_Ma")
    private String loaiMa;
    @Basic(optional = false)
    @Column(name = "loai_TenLoai")
    private String loaiTenLoai;
    @Basic(optional = false)
    @Column(name = "loai_Gia")
    private double loaiGia;
    @Basic(optional = false)
    @Column(name = "loai_GiaTreNgay")
    private double loaiGiaTreNgay;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loaiMa")
    private Collection<TieuDe> tieuDeCollection;

    public Loai() {
    }

    public Loai(String loaiMa) {
        this.loaiMa = loaiMa;
    }

    public Loai(String loaiMa, String loaiTenLoai, double loaiGia, double loaiGiaTreNgay) {
        this.loaiMa = loaiMa;
        this.loaiTenLoai = loaiTenLoai;
        this.loaiGia = loaiGia;
        this.loaiGiaTreNgay = loaiGiaTreNgay;
    }

    public String getLoaiMa() {
        return loaiMa;
    }

    public void setLoaiMa(String loaiMa) {
        this.loaiMa = loaiMa;
    }

    public String getLoaiTenLoai() {
        return loaiTenLoai;
    }

    public void setLoaiTenLoai(String loaiTenLoai) {
        this.loaiTenLoai = loaiTenLoai;
    }

    public double getLoaiGia() {
        return loaiGia;
    }

    public void setLoaiGia(double loaiGia) {
        this.loaiGia = loaiGia;
    }

    public double getLoaiGiaTreNgay() {
        return loaiGiaTreNgay;
    }

    public void setLoaiGiaTreNgay(double loaiGiaTreNgay) {
        this.loaiGiaTreNgay = loaiGiaTreNgay;
    }

    @XmlTransient
    public Collection<TieuDe> getTieuDeCollection() {
        return tieuDeCollection;
    }

    public void setTieuDeCollection(Collection<TieuDe> tieuDeCollection) {
        this.tieuDeCollection = tieuDeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loaiMa != null ? loaiMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loai)) {
            return false;
        }
        Loai other = (Loai) object;
        if ((this.loaiMa == null && other.loaiMa != null) || (this.loaiMa != null && !this.loaiMa.equals(other.loaiMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Loai[ loaiMa=" + loaiMa + " ]";
    }
    
}
