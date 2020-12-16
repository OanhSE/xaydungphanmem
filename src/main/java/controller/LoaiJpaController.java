/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Loai;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class LoaiJpaController implements Serializable {

    public LoaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Loai loai) throws PreexistingEntityException, Exception {
        if (loai.getTieuDeCollection() == null) {
            loai.setTieuDeCollection(new ArrayList<TieuDe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TieuDe> attachedTieuDeCollection = new ArrayList<TieuDe>();
            for (TieuDe tieuDeCollectionTieuDeToAttach : loai.getTieuDeCollection()) {
                tieuDeCollectionTieuDeToAttach = em.getReference(tieuDeCollectionTieuDeToAttach.getClass(), tieuDeCollectionTieuDeToAttach.getTdMa());
                attachedTieuDeCollection.add(tieuDeCollectionTieuDeToAttach);
            }
            loai.setTieuDeCollection(attachedTieuDeCollection);
            em.persist(loai);
            for (TieuDe tieuDeCollectionTieuDe : loai.getTieuDeCollection()) {
                Loai oldLoaiMaOfTieuDeCollectionTieuDe = tieuDeCollectionTieuDe.getLoaiMa();
                tieuDeCollectionTieuDe.setLoaiMa(loai);
                tieuDeCollectionTieuDe = em.merge(tieuDeCollectionTieuDe);
                if (oldLoaiMaOfTieuDeCollectionTieuDe != null) {
                    oldLoaiMaOfTieuDeCollectionTieuDe.getTieuDeCollection().remove(tieuDeCollectionTieuDe);
                    oldLoaiMaOfTieuDeCollectionTieuDe = em.merge(oldLoaiMaOfTieuDeCollectionTieuDe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLoai(loai.getLoaiMa()) != null) {
                throw new PreexistingEntityException("Loai " + loai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Loai loai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Loai persistentLoai = em.find(Loai.class, loai.getLoaiMa());
            Collection<TieuDe> tieuDeCollectionOld = persistentLoai.getTieuDeCollection();
            Collection<TieuDe> tieuDeCollectionNew = loai.getTieuDeCollection();
            List<String> illegalOrphanMessages = null;
            for (TieuDe tieuDeCollectionOldTieuDe : tieuDeCollectionOld) {
                if (!tieuDeCollectionNew.contains(tieuDeCollectionOldTieuDe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TieuDe " + tieuDeCollectionOldTieuDe + " since its loaiMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TieuDe> attachedTieuDeCollectionNew = new ArrayList<TieuDe>();
            for (TieuDe tieuDeCollectionNewTieuDeToAttach : tieuDeCollectionNew) {
                tieuDeCollectionNewTieuDeToAttach = em.getReference(tieuDeCollectionNewTieuDeToAttach.getClass(), tieuDeCollectionNewTieuDeToAttach.getTdMa());
                attachedTieuDeCollectionNew.add(tieuDeCollectionNewTieuDeToAttach);
            }
            tieuDeCollectionNew = attachedTieuDeCollectionNew;
            loai.setTieuDeCollection(tieuDeCollectionNew);
            loai = em.merge(loai);
            for (TieuDe tieuDeCollectionNewTieuDe : tieuDeCollectionNew) {
                if (!tieuDeCollectionOld.contains(tieuDeCollectionNewTieuDe)) {
                    Loai oldLoaiMaOfTieuDeCollectionNewTieuDe = tieuDeCollectionNewTieuDe.getLoaiMa();
                    tieuDeCollectionNewTieuDe.setLoaiMa(loai);
                    tieuDeCollectionNewTieuDe = em.merge(tieuDeCollectionNewTieuDe);
                    if (oldLoaiMaOfTieuDeCollectionNewTieuDe != null && !oldLoaiMaOfTieuDeCollectionNewTieuDe.equals(loai)) {
                        oldLoaiMaOfTieuDeCollectionNewTieuDe.getTieuDeCollection().remove(tieuDeCollectionNewTieuDe);
                        oldLoaiMaOfTieuDeCollectionNewTieuDe = em.merge(oldLoaiMaOfTieuDeCollectionNewTieuDe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = loai.getLoaiMa();
                if (findLoai(id) == null) {
                    throw new NonexistentEntityException("The loai with id " + id + " no longer exists.");
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
            Loai loai;
            try {
                loai = em.getReference(Loai.class, id);
                loai.getLoaiMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The loai with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TieuDe> tieuDeCollectionOrphanCheck = loai.getTieuDeCollection();
            for (TieuDe tieuDeCollectionOrphanCheckTieuDe : tieuDeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Loai (" + loai + ") cannot be destroyed since the TieuDe " + tieuDeCollectionOrphanCheckTieuDe + " in its tieuDeCollection field has a non-nullable loaiMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(loai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Loai> findLoaiEntities() {
        return findLoaiEntities(true, -1, -1);
    }

    public List<Loai> findLoaiEntities(int maxResults, int firstResult) {
        return findLoaiEntities(false, maxResults, firstResult);
    }

    private List<Loai> findLoaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Loai.class));
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

    public Loai findLoai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Loai.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Loai> rt = cq.from(Loai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
