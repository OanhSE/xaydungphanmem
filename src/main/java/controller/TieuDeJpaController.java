/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Loai;
import entity.Dvd;
import entity.TieuDe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class TieuDeJpaController implements Serializable {

    public TieuDeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TieuDe tieuDe) throws PreexistingEntityException, Exception {
        if (tieuDe.getDvdCollection() == null) {
            tieuDe.setDvdCollection(new ArrayList<Dvd>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loai loaiMa = tieuDe.getLoaiMa();
            if (loaiMa != null) {
                loaiMa = em.getReference(loaiMa.getClass(), loaiMa.getLoaiMa());
                tieuDe.setLoaiMa(loaiMa);
            }
            Collection<Dvd> attachedDvdCollection = new ArrayList<Dvd>();
            for (Dvd dvdCollectionDvdToAttach : tieuDe.getDvdCollection()) {
                dvdCollectionDvdToAttach = em.getReference(dvdCollectionDvdToAttach.getClass(), dvdCollectionDvdToAttach.getDvdMa());
                attachedDvdCollection.add(dvdCollectionDvdToAttach);
            }
            tieuDe.setDvdCollection(attachedDvdCollection);
            em.persist(tieuDe);
            if (loaiMa != null) {
                loaiMa.getTieuDeCollection().add(tieuDe);
                loaiMa = em.merge(loaiMa);
            }
            for (Dvd dvdCollectionDvd : tieuDe.getDvdCollection()) {
                TieuDe oldTdMaOfDvdCollectionDvd = dvdCollectionDvd.getTdMa();
                dvdCollectionDvd.setTdMa(tieuDe);
                dvdCollectionDvd = em.merge(dvdCollectionDvd);
                if (oldTdMaOfDvdCollectionDvd != null) {
                    oldTdMaOfDvdCollectionDvd.getDvdCollection().remove(dvdCollectionDvd);
                    oldTdMaOfDvdCollectionDvd = em.merge(oldTdMaOfDvdCollectionDvd);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTieuDe(tieuDe.getTdMa()) != null) {
                throw new PreexistingEntityException("TieuDe " + tieuDe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TieuDe tieuDe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TieuDe persistentTieuDe = em.find(TieuDe.class, tieuDe.getTdMa());
            Loai loaiMaOld = persistentTieuDe.getLoaiMa();
            Loai loaiMaNew = tieuDe.getLoaiMa();
            Collection<Dvd> dvdCollectionOld = persistentTieuDe.getDvdCollection();
            Collection<Dvd> dvdCollectionNew = tieuDe.getDvdCollection();
            List<String> illegalOrphanMessages = null;
            for (Dvd dvdCollectionOldDvd : dvdCollectionOld) {
                if (!dvdCollectionNew.contains(dvdCollectionOldDvd)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dvd " + dvdCollectionOldDvd + " since its tdMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (loaiMaNew != null) {
                loaiMaNew = em.getReference(loaiMaNew.getClass(), loaiMaNew.getLoaiMa());
                tieuDe.setLoaiMa(loaiMaNew);
            }
            Collection<Dvd> attachedDvdCollectionNew = new ArrayList<Dvd>();
            for (Dvd dvdCollectionNewDvdToAttach : dvdCollectionNew) {
                dvdCollectionNewDvdToAttach = em.getReference(dvdCollectionNewDvdToAttach.getClass(), dvdCollectionNewDvdToAttach.getDvdMa());
                attachedDvdCollectionNew.add(dvdCollectionNewDvdToAttach);
            }
            dvdCollectionNew = attachedDvdCollectionNew;
            tieuDe.setDvdCollection(dvdCollectionNew);
            tieuDe = em.merge(tieuDe);
            if (loaiMaOld != null && !loaiMaOld.equals(loaiMaNew)) {
                loaiMaOld.getTieuDeCollection().remove(tieuDe);
                loaiMaOld = em.merge(loaiMaOld);
            }
            if (loaiMaNew != null && !loaiMaNew.equals(loaiMaOld)) {
                loaiMaNew.getTieuDeCollection().add(tieuDe);
                loaiMaNew = em.merge(loaiMaNew);
            }
            for (Dvd dvdCollectionNewDvd : dvdCollectionNew) {
                if (!dvdCollectionOld.contains(dvdCollectionNewDvd)) {
                    TieuDe oldTdMaOfDvdCollectionNewDvd = dvdCollectionNewDvd.getTdMa();
                    dvdCollectionNewDvd.setTdMa(tieuDe);
                    dvdCollectionNewDvd = em.merge(dvdCollectionNewDvd);
                    if (oldTdMaOfDvdCollectionNewDvd != null && !oldTdMaOfDvdCollectionNewDvd.equals(tieuDe)) {
                        oldTdMaOfDvdCollectionNewDvd.getDvdCollection().remove(dvdCollectionNewDvd);
                        oldTdMaOfDvdCollectionNewDvd = em.merge(oldTdMaOfDvdCollectionNewDvd);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tieuDe.getTdMa();
                if (findTieuDe(id) == null) {
                    throw new NonexistentEntityException("The tieuDe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TieuDe tieuDe;
            try {
                tieuDe = em.getReference(TieuDe.class, id);
                tieuDe.getTdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tieuDe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Dvd> dvdCollectionOrphanCheck = tieuDe.getDvdCollection();
            for (Dvd dvdCollectionOrphanCheckDvd : dvdCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TieuDe (" + tieuDe + ") cannot be destroyed since the Dvd " + dvdCollectionOrphanCheckDvd + " in its dvdCollection field has a non-nullable tdMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Loai loaiMa = tieuDe.getLoaiMa();
            if (loaiMa != null) {
                loaiMa.getTieuDeCollection().remove(tieuDe);
                loaiMa = em.merge(loaiMa);
            }
            em.remove(tieuDe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TieuDe> findTieuDeEntities() {
        return findTieuDeEntities(true, -1, -1);
    }

    public List<TieuDe> findTieuDeEntities(int maxResults, int firstResult) {
        return findTieuDeEntities(false, maxResults, firstResult);
    }

    private List<TieuDe> findTieuDeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TieuDe.class));
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

    public TieuDe findTieuDe(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TieuDe.class, id);
        } finally {
            em.close();
        }
    }
     public List<TieuDe> findTieuDebyName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("TieuDe.findByTdTenTD", TieuDe.class).setParameter("tdTenTD", name).getResultList();
        } finally {
            em.close();
        }
    }

    public int getTieuDeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TieuDe> rt = cq.from(TieuDe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Dvd> findByDvdTrangThaiAndTieuDe(boolean tt, String td){
         EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Dvd d WHERE d.dvdTrangThai = " + tt + " AND d.tdMa.tdTenTD = " + td).getResultList();
        } finally {
            em.close();
        }
    }
    
}
