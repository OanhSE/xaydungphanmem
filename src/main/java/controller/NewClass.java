///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package controller;
//
//import com.sun.org.apache.bcel.internal.generic.LoadClass;
//import dao.MyEntityManager;
//import entity.Loai;
//import entity.TieuDe;
//import java.util.Date;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
///**
// *
// * @author vothi
// */
//public class NewClass {
//    public static void main(String[] args) throws Exception {
//        EntityManagerFactory emf = null;
//        emf = MyEntityManager.getInstance().getEmf();
//        
//        System.out.println("dao.NewClass.main()");
//        TieuDeJpaController tieuDeJpaController = new TieuDeJpaController(emf);
//        LoaiJpaController loaiJpaController = new LoaiJpaController(emf);
//        Loai loai = new Loai("1", "Game", 1000, 1000);
//        TieuDe td = new TieuDe("1", "ABC", "ABC",new Loai("1"), new Date(), "abc", true);
//        loaiJpaController.create(loai);
//        tieuDeJpaController.create(td);
//    }
//    
//}
