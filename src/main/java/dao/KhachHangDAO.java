/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.KhachHangJpaController;
import java.sql.Connection;
import entity.KhachHang;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author pt
 */
public class KhachHangDAO {

    private EntityManagerFactory emf = null;
    private KhachHangJpaController khachHangJpaController;

    public KhachHangDAO() {
        emf = MyEntityManager.getInstance().getEmf();
        khachHangJpaController = new KhachHangJpaController(emf);
    }
    

    public boolean updateKH(KhachHang kh) {
        try {
            khachHangJpaController.create(kh);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
                
        
    }

    public ArrayList<KhachHang> listKH() {

        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            list = new ArrayList<KhachHang>( khachHangJpaController.findKhachHangEntities());
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createKH(KhachHang kh) {
        try {
            khachHangJpaController.create(kh);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
                
    }

    public ArrayList<KhachHang> findKHByTen(String tenKH) {
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            list = new ArrayList<KhachHang>( khachHangJpaController.findKhachHangbyName(tenKH));
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public KhachHang findKHByMa(String maKH) {
        try {
         return   khachHangJpaController.findKhachHang(maKH);
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
       
    }

    public void changeTT(String ma, boolean tinhTrang) {
       KhachHang kh = khachHangJpaController.findKhachHang(ma);
       if(tinhTrang){
           kh.setKhTinhTrang(false);
       }
       else{
           kh.setKhTinhTrang(true);
       }
    }
}
