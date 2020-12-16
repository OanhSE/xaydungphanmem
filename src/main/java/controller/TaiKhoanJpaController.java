/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.TaiKhoan;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author vothi
 */
public class TaiKhoanJpaController implements Serializable {

    public TaiKhoanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TaiKhoan taiKhoan) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(taiKhoan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTaiKhoan(taiKhoan.getTkTenTK()) != null) {
                throw new PreexistingEntityException("TaiKhoan " + taiKhoan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TaiKhoan taiKhoan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            taiKhoan = em.merge(taiKhoan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = taiKhoan.getTkTenTK();
                if (findTaiKhoan(id) == null) {
                    throw new NonexistentEntityException("The taiKhoan with id " + id + " no longer exists.");
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
            TaiKhoan taiKhoan;
            try {
                taiKhoan = em.getReference(TaiKhoan.class, id);
                taiKhoan.getTkTenTK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The taiKhoan with id " + id + " no longer exists.", enfe);
            }
            em.remove(taiKhoan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TaiKhoan> findTaiKhoanEntities() {
        return findTaiKhoanEntities(true, -1, -1);
    }

    public List<TaiKhoan> findTaiKhoanEntities(int maxResults, int firstResult) {
        return findTaiKhoanEntities(false, maxResults, firstResult);
    }

    private List<TaiKhoan> findTaiKhoanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TaiKhoan.class));
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

    public TaiKhoan findTaiKhoan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TaiKhoan.class, id);
        } finally {
            em.close();
        }
    }
    

    public int getTaiKhoanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TaiKhoan> rt = cq.from(TaiKhoan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
