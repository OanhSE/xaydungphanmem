/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import controller.ChiTietHoaDonJpaController;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class cthdDAO {
     private EntityManagerFactory emf = null;
     private ChiTietHoaDonJpaController chiTietHoaDonJpaController;

    public cthdDAO() {
        emf = MyEntityManager.getInstance().getEmf();
        chiTietHoaDonJpaController = new ChiTietHoaDonJpaController(emf);
    }
     

    public ArrayList<ChiTietHoaDon> getListCTHD() {

        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        try {
            list = new ArrayList<ChiTietHoaDon>(chiTietHoaDonJpaController.findChiTietHoaDonEntities());
            return list;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean createCTHD(ChiTietHoaDon cthd) {
        try {
            chiTietHoaDonJpaController.create(cthd);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
