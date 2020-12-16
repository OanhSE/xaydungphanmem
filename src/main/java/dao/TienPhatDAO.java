/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import controller.TienPhatJpaController;
import entity.TienPhat;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TienPhatDAO {
    private EntityManagerFactory emf = null;
    private TienPhatJpaController tienPhatJpaController;

    public TienPhatDAO() {
        emf = MyEntityManager.getInstance().getEmf();
        tienPhatJpaController =  new TienPhatJpaController(emf);
    }
    
    
   
    
    public TienPhat findTDbyMa(String maTP) {
        try {
            return tienPhatJpaController.findTienPhat(maTP);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<TienPhat> getListTienPhat() {

        ArrayList<TienPhat> list = new ArrayList<>();
        list = new ArrayList<TienPhat> (tienPhatJpaController.findTienPhatEntities());
        return list;
    }
}
