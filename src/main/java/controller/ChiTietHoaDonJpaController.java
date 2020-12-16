/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.ChiTietHoaDon;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Dvd;
import entity.HoaDon;
import entity.TienPhat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class ChiTietHoaDonJpaController implements Serializable {

    public ChiTietHoaDonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChiTietHoaDon chiTietHoaDon) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvdMa = chiTietHoaDon.getDvdMa();
            if (dvdMa != null) {
                dvdMa = em.getReference(dvdMa.getClass(), dvdMa.getDvdMa());
                chiTietHoaDon.setDvdMa(dvdMa);
            }
            HoaDon hdMa = chiTietHoaDon.getHdMa();
            if (hdMa != null) {
                hdMa = em.getReference(hdMa.getClass(), hdMa.getHdMa());
                chiTietHoaDon.setHdMa(hdMa);
            }
            TienPhat ptMa = chiTietHoaDon.getPtMa();
            if (ptMa != null) {
                ptMa = em.getReference(ptMa.getClass(), ptMa.getPtMa());
                chiTietHoaDon.setPtMa(ptMa);
            }
            em.persist(chiTietHoaDon);
            if (dvdMa != null) {
                dvdMa.getChiTietHoaDonCollection().add(chiTietHoaDon);
                dvdMa = em.merge(dvdMa);
            }
            if (hdMa != null) {
                hdMa.getChiTietHoaDonCollection().add(chiTietHoaDon);
                hdMa = em.merge(hdMa);
            }
            if (ptMa != null) {
                ptMa.getChiTietHoaDonCollection().add(chiTietHoaDon);
                ptMa = em.merge(ptMa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findChiTietHoaDon(chiTietHoaDon.getCthdMa()) != null) {
                throw new PreexistingEntityException("ChiTietHoaDon " + chiTietHoaDon + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChiTietHoaDon chiTietHoaDon) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChiTietHoaDon persistentChiTietHoaDon = em.find(ChiTietHoaDon.class, chiTietHoaDon.getCthdMa());
            Dvd dvdMaOld = persistentChiTietHoaDon.getDvdMa();
            Dvd dvdMaNew = chiTietHoaDon.getDvdMa();
            HoaDon hdMaOld = persistentChiTietHoaDon.getHdMa();
            HoaDon hdMaNew = chiTietHoaDon.getHdMa();
            TienPhat ptMaOld = persistentChiTietHoaDon.getPtMa();
            TienPhat ptMaNew = chiTietHoaDon.getPtMa();
            if (dvdMaNew != null) {
                dvdMaNew = em.getReference(dvdMaNew.getClass(), dvdMaNew.getDvdMa());
                chiTietHoaDon.setDvdMa(dvdMaNew);
            }
            if (hdMaNew != null) {
                hdMaNew = em.getReference(hdMaNew.getClass(), hdMaNew.getHdMa());
                chiTietHoaDon.setHdMa(hdMaNew);
            }
            if (ptMaNew != null) {
                ptMaNew = em.getReference(ptMaNew.getClass(), ptMaNew.getPtMa());
                chiTietHoaDon.setPtMa(ptMaNew);
            }
            chiTietHoaDon = em.merge(chiTietHoaDon);
            if (dvdMaOld != null && !dvdMaOld.equals(dvdMaNew)) {
                dvdMaOld.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                dvdMaOld = em.merge(dvdMaOld);
            }
            if (dvdMaNew != null && !dvdMaNew.equals(dvdMaOld)) {
                dvdMaNew.getChiTietHoaDonCollection().add(chiTietHoaDon);
                dvdMaNew = em.merge(dvdMaNew);
            }
            if (hdMaOld != null && !hdMaOld.equals(hdMaNew)) {
                hdMaOld.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                hdMaOld = em.merge(hdMaOld);
            }
            if (hdMaNew != null && !hdMaNew.equals(hdMaOld)) {
                hdMaNew.getChiTietHoaDonCollection().add(chiTietHoaDon);
                hdMaNew = em.merge(hdMaNew);
            }
            if (ptMaOld != null && !ptMaOld.equals(ptMaNew)) {
                ptMaOld.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                ptMaOld = em.merge(ptMaOld);
            }
            if (ptMaNew != null && !ptMaNew.equals(ptMaOld)) {
                ptMaNew.getChiTietHoaDonCollection().add(chiTietHoaDon);
                ptMaNew = em.merge(ptMaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = chiTietHoaDon.getCthdMa();
                if (findChiTietHoaDon(id) == null) {
                    throw new NonexistentEntityException("The chiTietHoaDon with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChiTietHoaDon chiTietHoaDon;
            try {
                chiTietHoaDon = em.getReference(ChiTietHoaDon.class, id);
                chiTietHoaDon.getCthdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chiTietHoaDon with id " + id + " no longer exists.", enfe);
            }
            Dvd dvdMa = chiTietHoaDon.getDvdMa();
            if (dvdMa != null) {
                dvdMa.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                dvdMa = em.merge(dvdMa);
            }
            HoaDon hdMa = chiTietHoaDon.getHdMa();
            if (hdMa != null) {
                hdMa.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                hdMa = em.merge(hdMa);
            }
            TienPhat ptMa = chiTietHoaDon.getPtMa();
            if (ptMa != null) {
                ptMa.getChiTietHoaDonCollection().remove(chiTietHoaDon);
                ptMa = em.merge(ptMa);
            }
            em.remove(chiTietHoaDon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChiTietHoaDon> findChiTietHoaDonEntities() {
        return findChiTietHoaDonEntities(true, -1, -1);
    }

    public List<ChiTietHoaDon> findChiTietHoaDonEntities(int maxResults, int firstResult) {
        return findChiTietHoaDonEntities(false, maxResults, firstResult);
    }

    private List<ChiTietHoaDon> findChiTietHoaDonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChiTietHoaDon.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ChiTietHoaDon findChiTietHoaDon(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChiTietHoaDon.class, id);
        } finally {
            em.close();
        }
    }

    public int getChiTietHoaDonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChiTietHoaDon> rt = cq.from(ChiTietHoaDon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
