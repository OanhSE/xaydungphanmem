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
import entity.KhachHang;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class HoaDonJpaController implements Serializable {

    public HoaDonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HoaDon hoaDon) throws PreexistingEntityException, Exception {
        if (hoaDon.getChiTietHoaDonCollection() == null) {
            hoaDon.setChiTietHoaDonCollection(new ArrayList<ChiTietHoaDon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KhachHang khMa = hoaDon.getKhMa();
            if (khMa != null) {
                khMa = em.getReference(khMa.getClass(), khMa.getKhMa());
                hoaDon.setKhMa(khMa);
            }
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollection = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDonToAttach : hoaDon.getChiTietHoaDonCollection()) {
                chiTietHoaDonCollectionChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollection.add(chiTietHoaDonCollectionChiTietHoaDonToAttach);
            }
            hoaDon.setChiTietHoaDonCollection(attachedChiTietHoaDonCollection);
            em.persist(hoaDon);
            if (khMa != null) {
                khMa.getHoaDonCollection().add(hoaDon);
                khMa = em.merge(khMa);
            }
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDon : hoaDon.getChiTietHoaDonCollection()) {
                HoaDon oldHdMaOfChiTietHoaDonCollectionChiTietHoaDon = chiTietHoaDonCollectionChiTietHoaDon.getHdMa();
                chiTietHoaDonCollectionChiTietHoaDon.setHdMa(hoaDon);
                chiTietHoaDonCollectionChiTietHoaDon = em.merge(chiTietHoaDonCollectionChiTietHoaDon);
                if (oldHdMaOfChiTietHoaDonCollectionChiTietHoaDon != null) {
                    oldHdMaOfChiTietHoaDonCollectionChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionChiTietHoaDon);
                    oldHdMaOfChiTietHoaDonCollectionChiTietHoaDon = em.merge(oldHdMaOfChiTietHoaDonCollectionChiTietHoaDon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHoaDon(hoaDon.getHdMa()) != null) {
                throw new PreexistingEntityException("HoaDon " + hoaDon + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HoaDon hoaDon) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HoaDon persistentHoaDon = em.find(HoaDon.class, hoaDon.getHdMa());
            KhachHang khMaOld = persistentHoaDon.getKhMa();
            KhachHang khMaNew = hoaDon.getKhMa();
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOld = persistentHoaDon.getChiTietHoaDonCollection();
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionNew = hoaDon.getChiTietHoaDonCollection();
            List<String> illegalOrphanMessages = null;
            for (ChiTietHoaDon chiTietHoaDonCollectionOldChiTietHoaDon : chiTietHoaDonCollectionOld) {
                if (!chiTietHoaDonCollectionNew.contains(chiTietHoaDonCollectionOldChiTietHoaDon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ChiTietHoaDon " + chiTietHoaDonCollectionOldChiTietHoaDon + " since its hdMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (khMaNew != null) {
                khMaNew = em.getReference(khMaNew.getClass(), khMaNew.getKhMa());
                hoaDon.setKhMa(khMaNew);
            }
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollectionNew = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDonToAttach : chiTietHoaDonCollectionNew) {
                chiTietHoaDonCollectionNewChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollectionNew.add(chiTietHoaDonCollectionNewChiTietHoaDonToAttach);
            }
            chiTietHoaDonCollectionNew = attachedChiTietHoaDonCollectionNew;
            hoaDon.setChiTietHoaDonCollection(chiTietHoaDonCollectionNew);
            hoaDon = em.merge(hoaDon);
            if (khMaOld != null && !khMaOld.equals(khMaNew)) {
                khMaOld.getHoaDonCollection().remove(hoaDon);
                khMaOld = em.merge(khMaOld);
            }
            if (khMaNew != null && !khMaNew.equals(khMaOld)) {
                khMaNew.getHoaDonCollection().add(hoaDon);
                khMaNew = em.merge(khMaNew);
            }
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDon : chiTietHoaDonCollectionNew) {
                if (!chiTietHoaDonCollectionOld.contains(chiTietHoaDonCollectionNewChiTietHoaDon)) {
                    HoaDon oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon = chiTietHoaDonCollectionNewChiTietHoaDon.getHdMa();
                    chiTietHoaDonCollectionNewChiTietHoaDon.setHdMa(hoaDon);
                    chiTietHoaDonCollectionNewChiTietHoaDon = em.merge(chiTietHoaDonCollectionNewChiTietHoaDon);
                    if (oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon != null && !oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon.equals(hoaDon)) {
                        oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionNewChiTietHoaDon);
                        oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon = em.merge(oldHdMaOfChiTietHoaDonCollectionNewChiTietHoaDon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = hoaDon.getHdMa();
                if (findHoaDon(id) == null) {
                    throw new NonexistentEntityException("The hoaDon with id " + id + " no longer exists.");
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
            HoaDon hoaDon;
            try {
                hoaDon = em.getReference(HoaDon.class, id);
                hoaDon.getHdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hoaDon with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOrphanCheck = hoaDon.getChiTietHoaDonCollection();
            for (ChiTietHoaDon chiTietHoaDonCollectionOrphanCheckChiTietHoaDon : chiTietHoaDonCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HoaDon (" + hoaDon + ") cannot be destroyed since the ChiTietHoaDon " + chiTietHoaDonCollectionOrphanCheckChiTietHoaDon + " in its chiTietHoaDonCollection field has a non-nullable hdMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KhachHang khMa = hoaDon.getKhMa();
            if (khMa != null) {
                khMa.getHoaDonCollection().remove(hoaDon);
                khMa = em.merge(khMa);
            }
            em.remove(hoaDon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HoaDon> findHoaDonEntities() {
        return findHoaDonEntities(true, -1, -1);
    }

    public List<HoaDon> findHoaDonEntities(int maxResults, int firstResult) {
        return findHoaDonEntities(false, maxResults, firstResult);
    }

    private List<HoaDon> findHoaDonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HoaDon.class));
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

    public HoaDon findHoaDon(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HoaDon.class, id);
        } finally {
            em.close();
        }
    }

    public int getHoaDonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HoaDon> rt = cq.from(HoaDon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
