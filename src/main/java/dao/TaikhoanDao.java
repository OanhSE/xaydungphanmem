/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controller.TaiKhoanJpaController;
import entity.TaiKhoan;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class TaikhoanDao {
     private EntityManagerFactory emf = null;
     private TaiKhoanJpaController taiKhoanJpaController;

    public TaikhoanDao() {
        emf = MyEntityManager.getInstance().getEmf();
        taiKhoanJpaController = new TaiKhoanJpaController(emf);
    }
    public boolean createAccount(TaiKhoan tk){
        try {
            taiKhoanJpaController.create(tk);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public TaiKhoan findAccountByUsername(String username){
        try {
            return  taiKhoanJpaController.findTaiKhoan(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int checkLogin(String username,String password){
        TaiKhoan t = new TaiKhoan();
        try {
            t = taiKhoanJpaController.findTaiKhoan(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(t!=null){
            if(t.getTkMatKhau().equalsIgnoreCase(password))
            {
                return 1;
            }
            else {
                return 0;
            }
        }
        return -1;
    }
    
}
