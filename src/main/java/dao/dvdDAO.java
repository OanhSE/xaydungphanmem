/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.DvdJpaController;
import entity.Dvd;
import entity.TieuDe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class dvdDAO {
    private EntityManagerFactory emf = null;
    private  DvdJpaController dvdJpaController ;
    TieuDeDAO tdDAO ;

    public dvdDAO() {
         emf = MyEntityManager.getInstance().getEmf();
         dvdJpaController = new DvdJpaController(emf);
         tdDAO = new TieuDeDAO();
    }
    public boolean addDvdByNameAndQuantity(TieuDe td,int sl){
        try {
            for (int i = 0; i < sl; i++) {
                Date date = new Date();
                Dvd d = new Dvd("DVD"+ date+i,0,td);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Dvd> getListDVD() {

        ArrayList<Dvd> list = new ArrayList<>();
        list = new ArrayList<Dvd>( dvdJpaController.findDvdEntities());
        return list;
    }
    
    public Dvd findDVDbyMa(String ma) {

        Dvd dvd = new Dvd();
        dvd = dvdJpaController.findDvd(ma);
        return dvd;
    }
    
    public boolean updateTD(int ttdvd, String iddvd) {
       Dvd d = dvdJpaController.findDvd(iddvd);
       d.setDvdTrangThai(ttdvd);
        try {
             dvdJpaController.edit(d);
             return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        return false;
    }
}
