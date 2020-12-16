/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.DatTruoc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Dvd;
import entity.KhachHang;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class DatTruocJpaController implements Serializable {

    public DatTruocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatTruoc datTruoc) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd dvdMa = datTruoc.getDvdMa();
            if (dvdMa != null) {
                dvdMa = em.getReference(dvdMa.getClass(), dvdMa.getDvdMa());
                datTruoc.setDvdMa(dvdMa);
            }
            KhachHang khMa = datTruoc.getKhMa();
            if (khMa != null) {
                khMa = em.getReference(khMa.getClass(), khMa.getKhMa());
                datTruoc.setKhMa(khMa);
            }
            em.persist(datTruoc);
            if (dvdMa != null) {
                dvdMa.getDatTruocCollection().add(datTruoc);
                dvdMa = em.merge(dvdMa);
            }
            if (khMa != null) {
                khMa.getDatTruocCollection().add(datTruoc);
                khMa = em.merge(khMa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDatTruoc(datTruoc.getDtMa()) != null) {
                throw new PreexistingEntityException("DatTruoc " + datTruoc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatTruoc datTruoc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatTruoc persistentDatTruoc = em.find(DatTruoc.class, datTruoc.getDtMa());
            Dvd dvdMaOld = persistentDatTruoc.getDvdMa();
            Dvd dvdMaNew = datTruoc.getDvdMa();
            KhachHang khMaOld = persistentDatTruoc.getKhMa();
            KhachHang khMaNew = datTruoc.getKhMa();
            if (dvdMaNew != null) {
                dvdMaNew = em.getReference(dvdMaNew.getClass(), dvdMaNew.getDvdMa());
                datTruoc.setDvdMa(dvdMaNew);
            }
            if (khMaNew != null) {
                khMaNew = em.getReference(khMaNew.getClass(), khMaNew.getKhMa());
                datTruoc.setKhMa(khMaNew);
            }
            datTruoc = em.merge(datTruoc);
            if (dvdMaOld != null && !dvdMaOld.equals(dvdMaNew)) {
                dvdMaOld.getDatTruocCollection().remove(datTruoc);
                dvdMaOld = em.merge(dvdMaOld);
            }
            if (dvdMaNew != null && !dvdMaNew.equals(dvdMaOld)) {
                dvdMaNew.getDatTruocCollection().add(datTruoc);
                dvdMaNew = em.merge(dvdMaNew);
            }
            if (khMaOld != null && !khMaOld.equals(khMaNew)) {
                khMaOld.getDatTruocCollection().remove(datTruoc);
                khMaOld = em.merge(khMaOld);
            }
            if (khMaNew != null && !khMaNew.equals(khMaOld)) {
                khMaNew.getDatTruocCollection().add(datTruoc);
                khMaNew = em.merge(khMaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = datTruoc.getDtMa();
                if (findDatTruoc(id) == null) {
                    throw new NonexistentEntityException("The datTruoc with id " + id + " no longer exists.");
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
            DatTruoc datTruoc;
            try {
                datTruoc = em.getReference(DatTruoc.class, id);
                datTruoc.getDtMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datTruoc with id " + id + " no longer exists.", enfe);
            }
            Dvd dvdMa = datTruoc.getDvdMa();
            if (dvdMa != null) {
                dvdMa.getDatTruocCollection().remove(datTruoc);
                dvdMa = em.merge(dvdMa);
            }
            KhachHang khMa = datTruoc.getKhMa();
            if (khMa != null) {
                khMa.getDatTruocCollection().remove(datTruoc);
                khMa = em.merge(khMa);
            }
            em.remove(datTruoc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatTruoc> findDatTruocEntities() {
        return findDatTruocEntities(true, -1, -1);
    }

    public List<DatTruoc> findDatTruocEntities(int maxResults, int firstResult) {
        return findDatTruocEntities(false, maxResults, firstResult);
    }

    private List<DatTruoc> findDatTruocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatTruoc.class));
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

    public DatTruoc findDatTruoc(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatTruoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatTruocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatTruoc> rt = cq.from(DatTruoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
