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
import entity.DatTruoc;
import java.util.ArrayList;
import java.util.Collection;
import entity.HoaDon;
import entity.KhachHang;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class KhachHangJpaController implements Serializable {

    public KhachHangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KhachHang khachHang) throws PreexistingEntityException, Exception {
        if (khachHang.getDatTruocCollection() == null) {
            khachHang.setDatTruocCollection(new ArrayList<DatTruoc>());
        }
        if (khachHang.getHoaDonCollection() == null) {
            khachHang.setHoaDonCollection(new ArrayList<HoaDon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DatTruoc> attachedDatTruocCollection = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionDatTruocToAttach : khachHang.getDatTruocCollection()) {
                datTruocCollectionDatTruocToAttach = em.getReference(datTruocCollectionDatTruocToAttach.getClass(), datTruocCollectionDatTruocToAttach.getDtMa());
                attachedDatTruocCollection.add(datTruocCollectionDatTruocToAttach);
            }
            khachHang.setDatTruocCollection(attachedDatTruocCollection);
            Collection<HoaDon> attachedHoaDonCollection = new ArrayList<HoaDon>();
            for (HoaDon hoaDonCollectionHoaDonToAttach : khachHang.getHoaDonCollection()) {
                hoaDonCollectionHoaDonToAttach = em.getReference(hoaDonCollectionHoaDonToAttach.getClass(), hoaDonCollectionHoaDonToAttach.getHdMa());
                attachedHoaDonCollection.add(hoaDonCollectionHoaDonToAttach);
            }
            khachHang.setHoaDonCollection(attachedHoaDonCollection);
            em.persist(khachHang);
            for (DatTruoc datTruocCollectionDatTruoc : khachHang.getDatTruocCollection()) {
                KhachHang oldKhMaOfDatTruocCollectionDatTruoc = datTruocCollectionDatTruoc.getKhMa();
                datTruocCollectionDatTruoc.setKhMa(khachHang);
                datTruocCollectionDatTruoc = em.merge(datTruocCollectionDatTruoc);
                if (oldKhMaOfDatTruocCollectionDatTruoc != null) {
                    oldKhMaOfDatTruocCollectionDatTruoc.getDatTruocCollection().remove(datTruocCollectionDatTruoc);
                    oldKhMaOfDatTruocCollectionDatTruoc = em.merge(oldKhMaOfDatTruocCollectionDatTruoc);
                }
            }
            for (HoaDon hoaDonCollectionHoaDon : khachHang.getHoaDonCollection()) {
                KhachHang oldKhMaOfHoaDonCollectionHoaDon = hoaDonCollectionHoaDon.getKhMa();
                hoaDonCollectionHoaDon.setKhMa(khachHang);
                hoaDonCollectionHoaDon = em.merge(hoaDonCollectionHoaDon);
                if (oldKhMaOfHoaDonCollectionHoaDon != null) {
                    oldKhMaOfHoaDonCollectionHoaDon.getHoaDonCollection().remove(hoaDonCollectionHoaDon);
                    oldKhMaOfHoaDonCollectionHoaDon = em.merge(oldKhMaOfHoaDonCollectionHoaDon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKhachHang(khachHang.getKhMa()) != null) {
                throw new PreexistingEntityException("KhachHang " + khachHang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KhachHang khachHang) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KhachHang persistentKhachHang = em.find(KhachHang.class, khachHang.getKhMa());
            Collection<DatTruoc> datTruocCollectionOld = persistentKhachHang.getDatTruocCollection();
            Collection<DatTruoc> datTruocCollectionNew = khachHang.getDatTruocCollection();
            Collection<HoaDon> hoaDonCollectionOld = persistentKhachHang.getHoaDonCollection();
            Collection<HoaDon> hoaDonCollectionNew = khachHang.getHoaDonCollection();
            List<String> illegalOrphanMessages = null;
            for (DatTruoc datTruocCollectionOldDatTruoc : datTruocCollectionOld) {
                if (!datTruocCollectionNew.contains(datTruocCollectionOldDatTruoc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DatTruoc " + datTruocCollectionOldDatTruoc + " since its khMa field is not nullable.");
                }
            }
            for (HoaDon hoaDonCollectionOldHoaDon : hoaDonCollectionOld) {
                if (!hoaDonCollectionNew.contains(hoaDonCollectionOldHoaDon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HoaDon " + hoaDonCollectionOldHoaDon + " since its khMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DatTruoc> attachedDatTruocCollectionNew = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionNewDatTruocToAttach : datTruocCollectionNew) {
                datTruocCollectionNewDatTruocToAttach = em.getReference(datTruocCollectionNewDatTruocToAttach.getClass(), datTruocCollectionNewDatTruocToAttach.getDtMa());
                attachedDatTruocCollectionNew.add(datTruocCollectionNewDatTruocToAttach);
            }
            datTruocCollectionNew = attachedDatTruocCollectionNew;
            khachHang.setDatTruocCollection(datTruocCollectionNew);
            Collection<HoaDon> attachedHoaDonCollectionNew = new ArrayList<HoaDon>();
            for (HoaDon hoaDonCollectionNewHoaDonToAttach : hoaDonCollectionNew) {
                hoaDonCollectionNewHoaDonToAttach = em.getReference(hoaDonCollectionNewHoaDonToAttach.getClass(), hoaDonCollectionNewHoaDonToAttach.getHdMa());
                attachedHoaDonCollectionNew.add(hoaDonCollectionNewHoaDonToAttach);
            }
            hoaDonCollectionNew = attachedHoaDonCollectionNew;
            khachHang.setHoaDonCollection(hoaDonCollectionNew);
            khachHang = em.merge(khachHang);
            for (DatTruoc datTruocCollectionNewDatTruoc : datTruocCollectionNew) {
                if (!datTruocCollectionOld.contains(datTruocCollectionNewDatTruoc)) {
                    KhachHang oldKhMaOfDatTruocCollectionNewDatTruoc = datTruocCollectionNewDatTruoc.getKhMa();
                    datTruocCollectionNewDatTruoc.setKhMa(khachHang);
                    datTruocCollectionNewDatTruoc = em.merge(datTruocCollectionNewDatTruoc);
                    if (oldKhMaOfDatTruocCollectionNewDatTruoc != null && !oldKhMaOfDatTruocCollectionNewDatTruoc.equals(khachHang)) {
                        oldKhMaOfDatTruocCollectionNewDatTruoc.getDatTruocCollection().remove(datTruocCollectionNewDatTruoc);
                        oldKhMaOfDatTruocCollectionNewDatTruoc = em.merge(oldKhMaOfDatTruocCollectionNewDatTruoc);
                    }
                }
            }
            for (HoaDon hoaDonCollectionNewHoaDon : hoaDonCollectionNew) {
                if (!hoaDonCollectionOld.contains(hoaDonCollectionNewHoaDon)) {
                    KhachHang oldKhMaOfHoaDonCollectionNewHoaDon = hoaDonCollectionNewHoaDon.getKhMa();
                    hoaDonCollectionNewHoaDon.setKhMa(khachHang);
                    hoaDonCollectionNewHoaDon = em.merge(hoaDonCollectionNewHoaDon);
                    if (oldKhMaOfHoaDonCollectionNewHoaDon != null && !oldKhMaOfHoaDonCollectionNewHoaDon.equals(khachHang)) {
                        oldKhMaOfHoaDonCollectionNewHoaDon.getHoaDonCollection().remove(hoaDonCollectionNewHoaDon);
                        oldKhMaOfHoaDonCollectionNewHoaDon = em.merge(oldKhMaOfHoaDonCollectionNewHoaDon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = khachHang.getKhMa();
                if (findKhachHang(id) == null) {
                    throw new NonexistentEntityException("The khachHang with id " + id + " no longer exists.");
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
            KhachHang khachHang;
            try {
                khachHang = em.getReference(KhachHang.class, id);
                khachHang.getKhMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The khachHang with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DatTruoc> datTruocCollectionOrphanCheck = khachHang.getDatTruocCollection();
            for (DatTruoc datTruocCollectionOrphanCheckDatTruoc : datTruocCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KhachHang (" + khachHang + ") cannot be destroyed since the DatTruoc " + datTruocCollectionOrphanCheckDatTruoc + " in its datTruocCollection field has a non-nullable khMa field.");
            }
            Collection<HoaDon> hoaDonCollectionOrphanCheck = khachHang.getHoaDonCollection();
            for (HoaDon hoaDonCollectionOrphanCheckHoaDon : hoaDonCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KhachHang (" + khachHang + ") cannot be destroyed since the HoaDon " + hoaDonCollectionOrphanCheckHoaDon + " in its hoaDonCollection field has a non-nullable khMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(khachHang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KhachHang> findKhachHangEntities() {
        return findKhachHangEntities(true, -1, -1);
    }

    public List<KhachHang> findKhachHangEntities(int maxResults, int firstResult) {
        return findKhachHangEntities(false, maxResults, firstResult);
    }

    private List<KhachHang> findKhachHangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KhachHang.class));
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

    public KhachHang findKhachHang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KhachHang.class, id);
        } finally {
            em.close();
        }
    }
    public List<KhachHang> findKhachHangbyName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("KhachHang.findByKhHoVaTen", KhachHang.class).setParameter("khHoVaTen", name).getResultList();
        } finally {
            em.close();
        }
        
    }

    public int getKhachHangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KhachHang> rt = cq.from(KhachHang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
