package com.printway.repository;

import com.printway.entity.NewTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Repository
public class NewTrackingRepository extends BaseRepository<NewTracking, Integer> {

    public NewTrackingRepository() {
        super(NewTracking.class);
    }

    @Autowired
    private EntityManagerFactory emf;

//    public List<Integer> countOrder(String currentDate) throws ParseException {
//        EntityManager em = emf.createEntityManager();
//        String sqlQuery = "SELECT count(order_id) FROM tblnewtracking t where t.dtime_entered like (:dateEnter)";
//        Query query = em.createNativeQuery(sqlQuery);
//        query.setParameter("dateEnter", currentDate+"%");
//        List<Integer> totalOrder = query.getResultList();
//        em.close();
//        return totalOrder;
//    }

    public List<Integer> countErrorTracking() throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(multran_log) FROM tblnewtracking WHERE LENGTH(multran_log)>0 AND shipping_country = 'US' AND (LENGTH(partner_tracking_number) is null)";
        Query query = em.createNativeQuery(sqlQuery);
        List<Integer> totalErorrTracking = query.getResultList();
        em.close();
        return totalErorrTracking;
    }


    public List<Integer> countLabeled(String currentDate) throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(order_id) FROM tblnewtracking where labeled = 1 and dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalLabeled = query.getResultList();
        em.close();
        return totalLabeled;
    }

    public List<Integer> countUnlabeled(String currentDate) throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(order_id) FROM tblnewtracking where labeled = 0 and dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalNotLabeled = query.getResultList();
        em.close();
        return totalNotLabeled;
    }

    public List<Integer> countShipped(String currentDate) throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(order_id) FROM tblnewtracking where shipped = 1 and dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalShipped = query.getResultList();
        em.close();
        return totalShipped;
    }

    public List<Integer> countUnshipped(String currentDate) throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT count(order_id) FROM tblnewtracking where shipped = 0 and dtime_entered like (:dateEntered)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEntered", currentDate + "%");
        List<Integer> totalUnshipped = query.getResultList();
        em.close();
        return totalUnshipped;
    }

}
