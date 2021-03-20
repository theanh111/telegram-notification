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
        String sqlQuery = "SELECT COUNT(multran_log) " +
                "FROM tblnewtracking nt " +
                "WHERE LENGTH(multran_log)>0 " +
                "AND nt.shipping_country = 'US' " +
                "AND (LENGTH(nt.partner_tracking_number) IS NULL) " +
                "AND nt.shipping_city NOT IN ('APO', 'AE', 'FPO','PR', 'VI', 'GU') " +
                "AND (LENGTH(nt.dtime_last_changed) IS NULL)";
        Query query = em.createNativeQuery(sqlQuery);
        List<Integer> totalErorrTracking = query.getResultList();
        em.close();
        return totalErorrTracking;
    }

    public List<Integer> countLabeled(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT tblneworders.order_id) " +
                "FROM tblnewtracking " +
                "INNER JOIN tblneworders " +
                "ON tblneworders.order_id=tblnewtracking.order_id " +
                "WHERE tblnewtracking.labeled = 1 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalLabeled = query.getResultList();
        em.close();
        return totalLabeled;
    }

    public List<Integer> countUnlabeled(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT tblneworders.order_id) " +
                "FROM tblnewtracking " +
                "INNER JOIN tblneworders " +
                "ON tblneworders.order_id=tblnewtracking.order_id " +
                "WHERE tblnewtracking.labeled = 0 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalNotLabeled = query.getResultList();
        em.close();
        return totalNotLabeled;
    }

    public List<Integer> countShipped(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT tblneworders.order_id) " +
                "FROM tblnewtracking " +
                "INNER JOIN tblneworders " +
                "ON tblneworders.order_id = tblnewtracking.order_id " +
                "WHERE tblnewtracking.shipped = 1 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalShipped = query.getResultList();
        em.close();
        return totalShipped;
    }

    public List<Integer> countUnshipped(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT tblneworders.order_id) " +
                "FROM tblnewtracking " +
                "INNER JOIN tblneworders " +
                "ON tblneworders.order_id=tblnewtracking.order_id " +
                "WHERE tblnewtracking.shipped = 0 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalUnshipped = query.getResultList();
        em.close();
        return totalUnshipped;
    }
}
