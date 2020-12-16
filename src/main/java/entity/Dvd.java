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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "DVD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dvd.findAll", query = "SELECT d FROM Dvd d"),
    @NamedQuery(name = "Dvd.findByDvdMa", query = "SELECT d FROM Dvd d WHERE d.dvdMa = :dvdMa"),
    @NamedQuery(name = "Dvd.findByDvdTrangThai", query = "SELECT d FROM Dvd d WHERE d.dvdTrangThai = :dvdTrangThai"),
    @NamedQuery(name = "Dvd.findByDvdTrangThaiAndTieuDe", query = "SELECT d FROM Dvd d WHERE d.dvdTrangThai = :dvdTrangThai AND d.tdMa.tdTenTD = :tdTenTD")
})
public class Dvd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dvd_Ma")
    private String dvdMa;
    @Basic(optional = false)
    @Column(name = "dvd_TrangThai")
    private int dvdTrangThai;
    @JoinColumn(name = "td_Ma", referencedColumnName = "td_Ma")
    @ManyToOne(optional = false)
    private TieuDe tdMa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dvdMa")
    private Collection<DatTruoc> datTruocCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dvdMa")
    private Collection<ChiTietHoaDon> chiTietHoaDonCollection;

    public Dvd() {
    }

    public Dvd(String dvdMa) {
        this.dvdMa = dvdMa;
    }

    public Dvd(String dvdMa, int dvdTrangThai) {
        this.dvdMa = dvdMa;
        this.dvdTrangThai = dvdTrangThai;
    }

    public Dvd(String dvdMa, int dvdTrangThai, TieuDe tdMa) {
        this.dvdMa = dvdMa;
        this.dvdTrangThai = dvdTrangThai;
        this.tdMa = tdMa;
    }
    

    public String getDvdMa() {
        return dvdMa;
    }

    public void setDvdMa(String dvdMa) {
        this.dvdMa = dvdMa;
    }

    public int getDvdTrangThai() {
        return dvdTrangThai;
    }

    public void setDvdTrangThai(int dvdTrangThai) {
        this.dvdTrangThai = dvdTrangThai;
    }

    public TieuDe getTdMa() {
        return tdMa;
    }

    public void setTdMa(TieuDe tdMa) {
        this.tdMa = tdMa;
    }

    @XmlTransient
    public Collection<DatTruoc> getDatTruocCollection() {
        return datTruocCollection;
    }

    public void setDatTruocCollection(Collection<DatTruoc> datTruocCollection) {
        this.datTruocCollection = datTruocCollection;
    }

    @XmlTransient
    public Collection<ChiTietHoaDon> getChiTietHoaDonCollection() {
        return chiTietHoaDonCollection;
    }

    public void setChiTietHoaDonCollection(Collection<ChiTietHoaDon> chiTietHoaDonCollection) {
        this.chiTietHoaDonCollection = chiTietHoaDonCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dvdMa != null ? dvdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dvd)) {
            return false;
        }
        Dvd other = (Dvd) object;
        if ((this.dvdMa == null && other.dvdMa != null) || (this.dvdMa != null && !this.dvdMa.equals(other.dvdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dvd[ dvdMa=" + dvdMa + " ]";
    }
    
}
