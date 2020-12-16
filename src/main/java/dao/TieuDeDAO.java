/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.DvdJpaController;
import controller.LoaiJpaController;
import controller.TieuDeJpaController;
import entity.Dvd;
import java.sql.Connection;
import entity.KhachHang;
import entity.Loai;
import entity.TieuDe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pt
 */
public class TieuDeDAO {
    private EntityManagerFactory emf = null;
    TieuDeJpaController tieuDeJpaController;
    LoaiJpaController loaiJpaController;
    public TieuDeDAO() {
        emf = MyEntityManager.getInstance().getEmf();
        tieuDeJpaController = new TieuDeJpaController(emf);
        loaiJpaController = new LoaiJpaController(emf);
    }

   

    public boolean updateTD(TieuDe td) {

        try {
            tieuDeJpaController.edit(td);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<TieuDe> getListTD() {
        
        try {
             ArrayList<TieuDe> list ;
            list =new ArrayList<>( tieuDeJpaController.findTieuDeEntities());
           return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Loai findLoaiTD(String maloai) {
        
        try {
            return loaiJpaController.findLoai(maloai);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TieuDe findLoaiTD_maTD(String maTD) {
        try {
            return tieuDeJpaController.findTieuDe(maTD);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createTD(TieuDe td) {
        try {
            tieuDeJpaController.create(td);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<TieuDe> findTDByTen(String tenTD) {
        ArrayList<TieuDe> list = new ArrayList<>();
               list =new ArrayList<TieuDe>( tieuDeJpaController.findTieuDebyName(tenTD));
        return list;
    }

    public void changeTT(String ma, boolean tinhTrang) {
        TieuDe td = new TieuDe();
        td = tieuDeJpaController.findTieuDe(ma);
        if(tinhTrang){
            td.setTdTinhTrang(false);    
        }else{
            td.setTdTinhTrang(true);
        }
        try {
            tieuDeJpaController.edit(td);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    public Date chuyenStringThanhDate(String ngay) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = formatter.parse(ngay);
            // System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public int sLDiaRanhTheoTieuDe(String maTD) {
        List<Dvd> l = null;
        try {
             l = tieuDeJpaController.findByDvdTrangThaiAndTieuDe(true, maTD);
             return l.size();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
