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
import entity.ChiTietHoaDon;
import entity.TienPhat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class TienPhatJpaController implements Serializable {

    public TienPhatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TienPhat tienPhat) throws PreexistingEntityException, Exception {
        if (tienPhat.getChiTietHoaDonCollection() == null) {
            tienPhat.setChiTietHoaDonCollection(new ArrayList<ChiTietHoaDon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollection = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDonToAttach : tienPhat.getChiTietHoaDonCollection()) {
                chiTietHoaDonCollectionChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollection.add(chiTietHoaDonCollectionChiTietHoaDonToAttach);
            }
            tienPhat.setChiTietHoaDonCollection(attachedChiTietHoaDonCollection);
            em.persist(tienPhat);
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDon : tienPhat.getChiTietHoaDonCollection()) {
                TienPhat oldPtMaOfChiTietHoaDonCollectionChiTietHoaDon = chiTietHoaDonCollectionChiTietHoaDon.getPtMa();
                chiTietHoaDonCollectionChiTietHoaDon.setPtMa(tienPhat);
                chiTietHoaDonCollectionChiTietHoaDon = em.merge(chiTietHoaDonCollectionChiTietHoaDon);
                if (oldPtMaOfChiTietHoaDonCollectionChiTietHoaDon != null) {
                    oldPtMaOfChiTietHoaDonCollectionChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionChiTietHoaDon);
                    oldPtMaOfChiTietHoaDonCollectionChiTietHoaDon = em.merge(oldPtMaOfChiTietHoaDonCollectionChiTietHoaDon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTienPhat(tienPhat.getPtMa()) != null) {
                throw new PreexistingEntityException("TienPhat " + tienPhat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TienPhat tienPhat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TienPhat persistentTienPhat = em.find(TienPhat.class, tienPhat.getPtMa());
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOld = persistentTienPhat.getChiTietHoaDonCollection();
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionNew = tienPhat.getChiTietHoaDonCollection();
            List<String> illegalOrphanMessages = null;
            for (ChiTietHoaDon chiTietHoaDonCollectionOldChiTietHoaDon : chiTietHoaDonCollectionOld) {
                if (!chiTietHoaDonCollectionNew.contains(chiTietHoaDonCollectionOldChiTietHoaDon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ChiTietHoaDon " + chiTietHoaDonCollectionOldChiTietHoaDon + " since its ptMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollectionNew = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDonToAttach : chiTietHoaDonCollectionNew) {
                chiTietHoaDonCollectionNewChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollectionNew.add(chiTietHoaDonCollectionNewChiTietHoaDonToAttach);
            }
            chiTietHoaDonCollectionNew = attachedChiTietHoaDonCollectionNew;
            tienPhat.setChiTietHoaDonCollection(chiTietHoaDonCollectionNew);
            tienPhat = em.merge(tienPhat);
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDon : chiTietHoaDonCollectionNew) {
                if (!chiTietHoaDonCollectionOld.contains(chiTietHoaDonCollectionNewChiTietHoaDon)) {
                    TienPhat oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon = chiTietHoaDonCollectionNewChiTietHoaDon.getPtMa();
                    chiTietHoaDonCollectionNewChiTietHoaDon.setPtMa(tienPhat);
                    chiTietHoaDonCollectionNewChiTietHoaDon = em.merge(chiTietHoaDonCollectionNewChiTietHoaDon);
                    if (oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon != null && !oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon.equals(tienPhat)) {
                        oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionNewChiTietHoaDon);
                        oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon = em.merge(oldPtMaOfChiTietHoaDonCollectionNewChiTietHoaDon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tienPhat.getPtMa();
                if (findTienPhat(id) == null) {
                    throw new NonexistentEntityException("The tienPhat with id " + id + " no longer exists.");
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
            TienPhat tienPhat;
            try {
                tienPhat = em.getReference(TienPhat.class, id);
                tienPhat.getPtMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tienPhat with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOrphanCheck = tienPhat.getChiTietHoaDonCollection();
            for (ChiTietHoaDon chiTietHoaDonCollectionOrphanCheckChiTietHoaDon : chiTietHoaDonCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TienPhat (" + tienPhat + ") cannot be destroyed since the ChiTietHoaDon " + chiTietHoaDonCollectionOrphanCheckChiTietHoaDon + " in its chiTietHoaDonCollection field has a non-nullable ptMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tienPhat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TienPhat> findTienPhatEntities() {
        return findTienPhatEntities(true, -1, -1);
    }

    public List<TienPhat> findTienPhatEntities(int maxResults, int firstResult) {
        return findTienPhatEntities(false, maxResults, firstResult);
    }

    private List<TienPhat> findTienPhatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TienPhat.class));
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

    public TienPhat findTienPhat(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TienPhat.class, id);
        } finally {
            em.close();
        }
    }

    public int getTienPhatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TienPhat> rt = cq.from(TienPhat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
