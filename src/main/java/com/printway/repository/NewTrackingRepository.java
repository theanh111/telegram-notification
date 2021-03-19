package com.printway.repository;

import com.printway.entity.NewTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class NewTrackingRepository  {
    @Autowired
    private EntityManagerFactory emf;

    public NewTrackingRepository() {
    }

    public List<Integer> countErrorTracking() {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(multran_log) FROM tblnewtracking WHERE LENGTH(multran_log)>0 AND shipping_country = 'US' AND (LENGTH(partner_tracking_number) is null)";
        Query query = em.createNativeQuery(sqlQuery);
        List<Integer> totalErorrTracking = query.getResultList();
        em.close();
        return totalErorrTracking;
    }

    public List<Integer> countLabeled(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(distinct tblneworders.order_id) FROM tblnewtracking INNER JOIN tblneworders ON tblneworders.order_id=tblnewtracking.order_id WHERE tblnewtracking.labeled = 1 and tblneworders.dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalLabeled = query.getResultList();
        em.close();
        return totalLabeled;
    }

    public List<Integer> countUnlabeled(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(distinct tblneworders.order_id) FROM tblnewtracking INNER JOIN tblneworders ON tblneworders.order_id=tblnewtracking.order_id WHERE tblnewtracking.labeled = 0 and tblneworders.dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalNotLabeled = query.getResultList();
        em.close();
        return totalNotLabeled;
    }

    public List<Integer> countShipped(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(distinct tblneworders.order_id) FROM tblnewtracking INNER JOIN tblneworders ON tblneworders.order_id=tblnewtracking.order_id WHERE tblnewtracking.shipped = 1 and tblneworders.dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalShipped = query.getResultList();
        em.close();
        return totalShipped;
    }

    public List<Integer> countUnshipped(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(distinct tblneworders.order_id) FROM tblnewtracking INNER JOIN tblneworders ON tblneworders.order_id=tblnewtracking.order_id WHERE tblnewtracking.shipped = 0 and tblneworders.dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalUnshipped = query.getResultList();
        em.close();
        return totalUnshipped;
    }
}
