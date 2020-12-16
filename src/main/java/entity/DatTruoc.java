/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "DatTruoc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatTruoc.findAll", query = "SELECT d FROM DatTruoc d"),
    @NamedQuery(name = "DatTruoc.findByDtMa", query = "SELECT d FROM DatTruoc d WHERE d.dtMa = :dtMa"),
    @NamedQuery(name = "DatTruoc.findByDtSoLuong", query = "SELECT d FROM DatTruoc d WHERE d.dtSoLuong = :dtSoLuong")})
public class DatTruoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dt_Ma")
    private String dtMa;
    @Basic(optional = false)
    @Column(name = "dt_SoLuong")
    private int dtSoLuong;
    @JoinColumn(name = "dvd_Ma", referencedColumnName = "dvd_Ma")
    @ManyToOne(optional = false)
    private Dvd dvdMa;
    @JoinColumn(name = "kh_Ma", referencedColumnName = "kh_Ma")
    @ManyToOne(optional = false)
    private KhachHang khMa;

    public DatTruoc() {
    }

    public DatTruoc(String dtMa) {
        this.dtMa = dtMa;
    }

    public DatTruoc(String dtMa, int dtSoLuong) {
        this.dtMa = dtMa;
        this.dtSoLuong = dtSoLuong;
    }

    public String getDtMa() {
        return dtMa;
    }

    public void setDtMa(String dtMa) {
        this.dtMa = dtMa;
    }

    public int getDtSoLuong() {
        return dtSoLuong;
    }

    public void setDtSoLuong(int dtSoLuong) {
        this.dtSoLuong = dtSoLuong;
    }

    public Dvd getDvdMa() {
        return dvdMa;
    }

    public void setDvdMa(Dvd dvdMa) {
        this.dvdMa = dvdMa;
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
        hash += (dtMa != null ? dtMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatTruoc)) {
            return false;
        }
        DatTruoc other = (DatTruoc) object;
        if ((this.dtMa == null && other.dtMa != null) || (this.dtMa != null && !this.dtMa.equals(other.dtMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DatTruoc[ dtMa=" + dtMa + " ]";
    }
    
}
