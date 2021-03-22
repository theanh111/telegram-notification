package com.printway.repository;

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
        String sqlQuery = "SELECT COUNT(1) as totalLabeled " +
                "FROM (" +
                "SELECT distinct tblneworders.order_id " +
                "FROM tblneworders " +
                "WHERE tblneworders.status_production=6 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered) " +
                "AND tblneworders.order_id " +
                "IN ( " +
                "SELECT tblnewtracking.order_id " +
                "FROM tblnewtracking " +
                "WHERE labeled=1 " +
                ")) " +
                "TMP " +
                "WHERE 1=1 ";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalLabeled = query.getResultList();
        em.close();
        return totalLabeled;
    }

    public List<Integer> countShipped(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(1) as totalShipped " +
                "FROM (" +
                "SELECT distinct tblneworders.order_id " +
                "FROM tblneworders " +
                "WHERE tblneworders.status_production=6 " +
                "AND tblneworders.dtime_entered " +
                "LIKE (:dateEntered) " +
                "AND tblneworders.order_id " +
                "IN ( " +
                "SELECT tblnewtracking.order_id " +
                "FROM tblnewtracking " +
                "WHERE shipped=1 " +
                ")) " +
                "TMP " +
                "WHERE 1=1 ";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalShipped = query.getResultList();
        em.close();
        return totalShipped;
    }
}
