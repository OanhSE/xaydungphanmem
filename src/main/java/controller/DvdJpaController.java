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
import entity.TieuDe;
import entity.DatTruoc;
import java.util.ArrayList;
import java.util.Collection;
import entity.ChiTietHoaDon;
import entity.Dvd;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vothi
 */
public class DvdJpaController implements Serializable {

    public DvdJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dvd dvd) throws PreexistingEntityException, Exception {
        if (dvd.getDatTruocCollection() == null) {
            dvd.setDatTruocCollection(new ArrayList<DatTruoc>());
        }
        if (dvd.getChiTietHoaDonCollection() == null) {
            dvd.setChiTietHoaDonCollection(new ArrayList<ChiTietHoaDon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TieuDe tdMa = dvd.getTdMa();
            if (tdMa != null) {
                tdMa = em.getReference(tdMa.getClass(), tdMa.getTdMa());
                dvd.setTdMa(tdMa);
            }
            Collection<DatTruoc> attachedDatTruocCollection = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionDatTruocToAttach : dvd.getDatTruocCollection()) {
                datTruocCollectionDatTruocToAttach = em.getReference(datTruocCollectionDatTruocToAttach.getClass(), datTruocCollectionDatTruocToAttach.getDtMa());
                attachedDatTruocCollection.add(datTruocCollectionDatTruocToAttach);
            }
            dvd.setDatTruocCollection(attachedDatTruocCollection);
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollection = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDonToAttach : dvd.getChiTietHoaDonCollection()) {
                chiTietHoaDonCollectionChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollection.add(chiTietHoaDonCollectionChiTietHoaDonToAttach);
            }
            dvd.setChiTietHoaDonCollection(attachedChiTietHoaDonCollection);
            em.persist(dvd);
            if (tdMa != null) {
                tdMa.getDvdCollection().add(dvd);
                tdMa = em.merge(tdMa);
            }
            for (DatTruoc datTruocCollectionDatTruoc : dvd.getDatTruocCollection()) {
                Dvd oldDvdMaOfDatTruocCollectionDatTruoc = datTruocCollectionDatTruoc.getDvdMa();
                datTruocCollectionDatTruoc.setDvdMa(dvd);
                datTruocCollectionDatTruoc = em.merge(datTruocCollectionDatTruoc);
                if (oldDvdMaOfDatTruocCollectionDatTruoc != null) {
                    oldDvdMaOfDatTruocCollectionDatTruoc.getDatTruocCollection().remove(datTruocCollectionDatTruoc);
                    oldDvdMaOfDatTruocCollectionDatTruoc = em.merge(oldDvdMaOfDatTruocCollectionDatTruoc);
                }
            }
            for (ChiTietHoaDon chiTietHoaDonCollectionChiTietHoaDon : dvd.getChiTietHoaDonCollection()) {
                Dvd oldDvdMaOfChiTietHoaDonCollectionChiTietHoaDon = chiTietHoaDonCollectionChiTietHoaDon.getDvdMa();
                chiTietHoaDonCollectionChiTietHoaDon.setDvdMa(dvd);
                chiTietHoaDonCollectionChiTietHoaDon = em.merge(chiTietHoaDonCollectionChiTietHoaDon);
                if (oldDvdMaOfChiTietHoaDonCollectionChiTietHoaDon != null) {
                    oldDvdMaOfChiTietHoaDonCollectionChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionChiTietHoaDon);
                    oldDvdMaOfChiTietHoaDonCollectionChiTietHoaDon = em.merge(oldDvdMaOfChiTietHoaDonCollectionChiTietHoaDon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDvd(dvd.getDvdMa()) != null) {
                throw new PreexistingEntityException("Dvd " + dvd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dvd dvd) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dvd persistentDvd = em.find(Dvd.class, dvd.getDvdMa());
            TieuDe tdMaOld = persistentDvd.getTdMa();
            TieuDe tdMaNew = dvd.getTdMa();
            Collection<DatTruoc> datTruocCollectionOld = persistentDvd.getDatTruocCollection();
            Collection<DatTruoc> datTruocCollectionNew = dvd.getDatTruocCollection();
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOld = persistentDvd.getChiTietHoaDonCollection();
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionNew = dvd.getChiTietHoaDonCollection();
            List<String> illegalOrphanMessages = null;
            for (DatTruoc datTruocCollectionOldDatTruoc : datTruocCollectionOld) {
                if (!datTruocCollectionNew.contains(datTruocCollectionOldDatTruoc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DatTruoc " + datTruocCollectionOldDatTruoc + " since its dvdMa field is not nullable.");
                }
            }
            for (ChiTietHoaDon chiTietHoaDonCollectionOldChiTietHoaDon : chiTietHoaDonCollectionOld) {
                if (!chiTietHoaDonCollectionNew.contains(chiTietHoaDonCollectionOldChiTietHoaDon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ChiTietHoaDon " + chiTietHoaDonCollectionOldChiTietHoaDon + " since its dvdMa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tdMaNew != null) {
                tdMaNew = em.getReference(tdMaNew.getClass(), tdMaNew.getTdMa());
                dvd.setTdMa(tdMaNew);
            }
            Collection<DatTruoc> attachedDatTruocCollectionNew = new ArrayList<DatTruoc>();
            for (DatTruoc datTruocCollectionNewDatTruocToAttach : datTruocCollectionNew) {
                datTruocCollectionNewDatTruocToAttach = em.getReference(datTruocCollectionNewDatTruocToAttach.getClass(), datTruocCollectionNewDatTruocToAttach.getDtMa());
                attachedDatTruocCollectionNew.add(datTruocCollectionNewDatTruocToAttach);
            }
            datTruocCollectionNew = attachedDatTruocCollectionNew;
            dvd.setDatTruocCollection(datTruocCollectionNew);
            Collection<ChiTietHoaDon> attachedChiTietHoaDonCollectionNew = new ArrayList<ChiTietHoaDon>();
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDonToAttach : chiTietHoaDonCollectionNew) {
                chiTietHoaDonCollectionNewChiTietHoaDonToAttach = em.getReference(chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getClass(), chiTietHoaDonCollectionNewChiTietHoaDonToAttach.getCthdMa());
                attachedChiTietHoaDonCollectionNew.add(chiTietHoaDonCollectionNewChiTietHoaDonToAttach);
            }
            chiTietHoaDonCollectionNew = attachedChiTietHoaDonCollectionNew;
            dvd.setChiTietHoaDonCollection(chiTietHoaDonCollectionNew);
            dvd = em.merge(dvd);
            if (tdMaOld != null && !tdMaOld.equals(tdMaNew)) {
                tdMaOld.getDvdCollection().remove(dvd);
                tdMaOld = em.merge(tdMaOld);
            }
            if (tdMaNew != null && !tdMaNew.equals(tdMaOld)) {
                tdMaNew.getDvdCollection().add(dvd);
                tdMaNew = em.merge(tdMaNew);
            }
            for (DatTruoc datTruocCollectionNewDatTruoc : datTruocCollectionNew) {
                if (!datTruocCollectionOld.contains(datTruocCollectionNewDatTruoc)) {
                    Dvd oldDvdMaOfDatTruocCollectionNewDatTruoc = datTruocCollectionNewDatTruoc.getDvdMa();
                    datTruocCollectionNewDatTruoc.setDvdMa(dvd);
                    datTruocCollectionNewDatTruoc = em.merge(datTruocCollectionNewDatTruoc);
                    if (oldDvdMaOfDatTruocCollectionNewDatTruoc != null && !oldDvdMaOfDatTruocCollectionNewDatTruoc.equals(dvd)) {
                        oldDvdMaOfDatTruocCollectionNewDatTruoc.getDatTruocCollection().remove(datTruocCollectionNewDatTruoc);
                        oldDvdMaOfDatTruocCollectionNewDatTruoc = em.merge(oldDvdMaOfDatTruocCollectionNewDatTruoc);
                    }
                }
            }
            for (ChiTietHoaDon chiTietHoaDonCollectionNewChiTietHoaDon : chiTietHoaDonCollectionNew) {
                if (!chiTietHoaDonCollectionOld.contains(chiTietHoaDonCollectionNewChiTietHoaDon)) {
                    Dvd oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon = chiTietHoaDonCollectionNewChiTietHoaDon.getDvdMa();
                    chiTietHoaDonCollectionNewChiTietHoaDon.setDvdMa(dvd);
                    chiTietHoaDonCollectionNewChiTietHoaDon = em.merge(chiTietHoaDonCollectionNewChiTietHoaDon);
                    if (oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon != null && !oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon.equals(dvd)) {
                        oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon.getChiTietHoaDonCollection().remove(chiTietHoaDonCollectionNewChiTietHoaDon);
                        oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon = em.merge(oldDvdMaOfChiTietHoaDonCollectionNewChiTietHoaDon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dvd.getDvdMa();
                if (findDvd(id) == null) {
                    throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.");
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
            Dvd dvd;
            try {
                dvd = em.getReference(Dvd.class, id);
                dvd.getDvdMa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dvd with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DatTruoc> datTruocCollectionOrphanCheck = dvd.getDatTruocCollection();
            for (DatTruoc datTruocCollectionOrphanCheckDatTruoc : datTruocCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dvd (" + dvd + ") cannot be destroyed since the DatTruoc " + datTruocCollectionOrphanCheckDatTruoc + " in its datTruocCollection field has a non-nullable dvdMa field.");
            }
            Collection<ChiTietHoaDon> chiTietHoaDonCollectionOrphanCheck = dvd.getChiTietHoaDonCollection();
            for (ChiTietHoaDon chiTietHoaDonCollectionOrphanCheckChiTietHoaDon : chiTietHoaDonCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dvd (" + dvd + ") cannot be destroyed since the ChiTietHoaDon " + chiTietHoaDonCollectionOrphanCheckChiTietHoaDon + " in its chiTietHoaDonCollection field has a non-nullable dvdMa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TieuDe tdMa = dvd.getTdMa();
            if (tdMa != null) {
                tdMa.getDvdCollection().remove(dvd);
                tdMa = em.merge(tdMa);
            }
            em.remove(dvd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dvd> findDvdEntities() {
        return findDvdEntities(true, -1, -1);
    }

    public List<Dvd> findDvdEntities(int maxResults, int firstResult) {
        return findDvdEntities(false, maxResults, firstResult);
    }

    private List<Dvd> findDvdEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dvd.class));
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

    public Dvd findDvd(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dvd.class, id);
        } finally {
            em.close();
        }
    }

    public int getDvdCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dvd> rt = cq.from(Dvd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
