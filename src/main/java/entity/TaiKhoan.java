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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vothi
 */
@Entity
@Table(name = "TaiKhoan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaiKhoan.findAll", query = "SELECT t FROM TaiKhoan t"),
    @NamedQuery(name = "TaiKhoan.findByTkTenTK", query = "SELECT t FROM TaiKhoan t WHERE t.tkTenTK = :tkTenTK"),
    @NamedQuery(name = "TaiKhoan.findByTkMatKhau", query = "SELECT t FROM TaiKhoan t WHERE t.tkMatKhau = :tkMatKhau")})
public class TaiKhoan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tk_TenTK")
    private String tkTenTK;
    @Basic(optional = false)
    @Column(name = "tk_MatKhau")
    private String tkMatKhau;

    public TaiKhoan() {
    }

    public TaiKhoan(String tkTenTK) {
        this.tkTenTK = tkTenTK;
    }

    public TaiKhoan(String tkTenTK, String tkMatKhau) {
        this.tkTenTK = tkTenTK;
        this.tkMatKhau = tkMatKhau;
    }

    public String getTkTenTK() {
        return tkTenTK;
    }

    public void setTkTenTK(String tkTenTK) {
        this.tkTenTK = tkTenTK;
    }

    public String getTkMatKhau() {
        return tkMatKhau;
    }

    public void setTkMatKhau(String tkMatKhau) {
        this.tkMatKhau = tkMatKhau;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkTenTK != null ? tkTenTK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaiKhoan)) {
            return false;
        }
        TaiKhoan other = (TaiKhoan) object;
        if ((this.tkTenTK == null && other.tkTenTK != null) || (this.tkTenTK != null && !this.tkTenTK.equals(other.tkTenTK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TaiKhoan[ tkTenTK=" + tkTenTK + " ]";
    }
    
}
