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
@Table(name = "ChiTietHoaDon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChiTietHoaDon.findAll", query = "SELECT c FROM ChiTietHoaDon c"),
    @NamedQuery(name = "ChiTietHoaDon.findByCthdMa", query = "SELECT c FROM ChiTietHoaDon c WHERE c.cthdMa = :cthdMa")})
public class ChiTietHoaDon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cthd_Ma")
    private String cthdMa;
    @JoinColumn(name = "dvd_Ma", referencedColumnName = "dvd_Ma")
    @ManyToOne(optional = false)
    private Dvd dvdMa;
    @JoinColumn(name = "hd_Ma", referencedColumnName = "hd_Ma")
    @ManyToOne(optional = false)
    private HoaDon hdMa;
    @JoinColumn(name = "pt_Ma", referencedColumnName = "pt_Ma")
    @ManyToOne(optional = false)
    private TienPhat ptMa;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String cthdMa) {
        this.cthdMa = cthdMa;
    }

    public ChiTietHoaDon(String string, HoaDon hd, Dvd findDVDbyMa, TienPhat tienPhat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCthdMa() {
        return cthdMa;
    }

    public void setCthdMa(String cthdMa) {
        this.cthdMa = cthdMa;
    }

    public Dvd getDvdMa() {
        return dvdMa;
    }

    public void setDvdMa(Dvd dvdMa) {
        this.dvdMa = dvdMa;
    }

    public HoaDon getHdMa() {
        return hdMa;
    }

    public void setHdMa(HoaDon hdMa) {
        this.hdMa = hdMa;
    }

    public TienPhat getPtMa() {
        return ptMa;
    }

    public void setPtMa(TienPhat ptMa) {
        this.ptMa = ptMa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cthdMa != null ? cthdMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChiTietHoaDon)) {
            return false;
        }
        ChiTietHoaDon other = (ChiTietHoaDon) object;
        if ((this.cthdMa == null && other.cthdMa != null) || (this.cthdMa != null && !this.cthdMa.equals(other.cthdMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ChiTietHoaDon[ cthdMa=" + cthdMa + " ]";
    }
    
}
