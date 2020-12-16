/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import controller.HoaDonJpaController;
import entity.HoaDon;
import entity.KhachHang;
import entity.TieuDe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class HoaDonDAO {

   private EntityManagerFactory emf = null;
   private HoaDonJpaController hoaDonJpaController;

    public HoaDonDAO() {
        emf = MyEntityManager.getInstance().getEmf();
        hoaDonJpaController = new HoaDonJpaController(emf);
    }
   

    public ArrayList<HoaDon> getListHoaDon() {

        ArrayList<HoaDon> list = new ArrayList<>();
        try {
            list = new ArrayList<HoaDon>( hoaDonJpaController.findHoaDonEntities());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public HoaDon findHoadonByMa(String maHD) {
        try {
          return hoaDonJpaController.findHoaDon(maHD);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean createHoaDon(HoaDon hd) {
        try {
            hoaDonJpaController.create(hd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
