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
@Table(name = "TieuDe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TieuDe.findAll", query = "SELECT t FROM TieuDe t"),
    @NamedQuery(name = "TieuDe.findByTdMa", query = "SELECT t FROM TieuDe t WHERE t.tdMa = :tdMa"),
    @NamedQuery(name = "TieuDe.findByTdTenTD", query = "SELECT t FROM TieuDe t WHERE t.tdTenTD = :tdTenTD"),
    @NamedQuery(name = "TieuDe.findByTdNhaSX", query = "SELECT t FROM TieuDe t WHERE t.tdNhaSX = :tdNhaSX"),
    @NamedQuery(name = "TieuDe.findByTdNgay", query = "SELECT t FROM TieuDe t WHERE t.tdNgay = :tdNgay"),
    @NamedQuery(name = "TieuDe.findByTdImages", query = "SELECT t FROM TieuDe t WHERE t.tdImages = :tdImages"),
    @NamedQuery(name = "TieuDe.findByTdTinhTrang", query = "SELECT t FROM TieuDe t WHERE t.tdTinhTrang = :tdTinhTrang")})
public class TieuDe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "td_Ma")
    private String tdMa;
    @Basic(optional = false)
    @Column(name = "td_TenTD")
    private String tdTenTD;
    @Basic(optional = false)
    @Column(name = "td_NhaSX")
    private String tdNhaSX;
    @Basic(optional = false)
    @Column(name = "td_Ngay")
    @Temporal(TemporalType.DATE)
    private Date tdNgay;
    @Basic(optional = false)
    @Column(name = "td_Images")
    private String tdImages;
    @Basic(optional = false)
    @Column(name = "td_TinhTrang")
    private boolean tdTinhTrang;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdMa")
    private Collection<Dvd> dvdCollection;
    @JoinColumn(name = "loai_Ma", referencedColumnName = "loai_Ma")
    @ManyToOne(optional = false)
    private Loai loaiMa;

    public TieuDe() {
    }

    public TieuDe(String tdMa) {
        this.tdMa = tdMa;
    }

    public TieuDe(String tdMa, String tdTenTD, String tdNhaSX,Loai loaiMa, Date tdNgay, String tdImages, boolean tdTinhTrang) {
        this.tdMa = tdMa;
        this.tdTenTD = tdTenTD;
        this.tdNhaSX = tdNhaSX;
        this.loaiMa = loaiMa;
        this.tdNgay = tdNgay;
        this.tdImages = tdImages;
        this.tdTinhTrang = tdTinhTrang;
    }

   

    public String getTdMa() {
        return tdMa;
    }

    public void setTdMa(String tdMa) {
        this.tdMa = tdMa;
    }

    public String getTdTenTD() {
        return tdTenTD;
    }

    public void setTdTenTD(String tdTenTD) {
        this.tdTenTD = tdTenTD;
    }

    public String getTdNhaSX() {
        return tdNhaSX;
    }

    public void setTdNhaSX(String tdNhaSX) {
        this.tdNhaSX = tdNhaSX;
    }

    public Date getTdNgay() {
        return tdNgay;
    }

    public void setTdNgay(Date tdNgay) {
        this.tdNgay = tdNgay;
    }

    public String getTdImages() {
        return tdImages;
    }

    public void setTdImages(String tdImages) {
        this.tdImages = tdImages;
    }

    public boolean getTdTinhTrang() {
        return tdTinhTrang;
    }

    public void setTdTinhTrang(boolean tdTinhTrang) {
        this.tdTinhTrang = tdTinhTrang;
    }

    @XmlTransient
    public Collection<Dvd> getDvdCollection() {
        return dvdCollection;
    }

    public void setDvdCollection(Collection<Dvd> dvdCollection) {
        this.dvdCollection = dvdCollection;
    }

    public Loai getLoaiMa() {
        return loaiMa;
    }

    public void setLoaiMa(Loai loaiMa) {
        this.loaiMa = loaiMa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdMa != null ? tdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TieuDe)) {
            return false;
        }
        TieuDe other = (TieuDe) object;
        if ((this.tdMa == null && other.tdMa != null) || (this.tdMa != null && !this.tdMa.equals(other.tdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TieuDe[ tdMa=" + tdMa + " ]";
    }
    
}
