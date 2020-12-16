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
@Table(name = "TienPhat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TienPhat.findAll", query = "SELECT t FROM TienPhat t"),
    @NamedQuery(name = "TienPhat.findByPtMa", query = "SELECT t FROM TienPhat t WHERE t.ptMa = :ptMa"),
    @NamedQuery(name = "TienPhat.findByPtSoNgay", query = "SELECT t FROM TienPhat t WHERE t.ptSoNgay = :ptSoNgay"),
    @NamedQuery(name = "TienPhat.findByPtTienPhat", query = "SELECT t FROM TienPhat t WHERE t.ptTienPhat = :ptTienPhat")})
public class TienPhat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pt_Ma")
    private String ptMa;
    @Basic(optional = false)
    @Column(name = "pt_SoNgay")
    private int ptSoNgay;
    @Basic(optional = false)
    @Column(name = "pt_TienPhat")
    private double ptTienPhat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptMa")
    private Collection<ChiTietHoaDon> chiTietHoaDonCollection;

    public TienPhat() {
    }

    public TienPhat(String ptMa) {
        this.ptMa = ptMa;
    }

    public TienPhat(String ptMa, int ptSoNgay, double ptTienPhat) {
        this.ptMa = ptMa;
        this.ptSoNgay = ptSoNgay;
        this.ptTienPhat = ptTienPhat;
    }

    public String getPtMa() {
        return ptMa;
    }

    public void setPtMa(String ptMa) {
        this.ptMa = ptMa;
    }

    public int getPtSoNgay() {
        return ptSoNgay;
    }

    public void setPtSoNgay(int ptSoNgay) {
        this.ptSoNgay = ptSoNgay;
    }

    public double getPtTienPhat() {
        return ptTienPhat;
    }

    public void setPtTienPhat(double ptTienPhat) {
        this.ptTienPhat = ptTienPhat;
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
        hash += (ptMa != null ? ptMa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TienPhat)) {
            return false;
        }
        TienPhat other = (TienPhat) object;
        if ((this.ptMa == null && other.ptMa != null) || (this.ptMa != null && !this.ptMa.equals(other.ptMa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TienPhat[ ptMa=" + ptMa + " ]";
    }
    
}
