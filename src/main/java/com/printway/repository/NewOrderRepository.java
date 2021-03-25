package com.printway.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class NewOrderRepository {
    @Autowired
    private EntityManagerFactory emf;

    public NewOrderRepository() {
    }

    public List<Integer> findNewestOrder(String oneHourBack, String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT order_id) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered >= :dateOneHourBack AND t.dtime_entered <= :dateCurrent";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateCurrent", currentDate + "%");
        query.setParameter("dateOneHourBack", oneHourBack + "%");
        List<Integer> totalNewestOrder = query.getResultList();
        em.close();
        return totalNewestOrder;
    }

    public List<Integer> countOrder(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(1) as totalOrders " +
                "FROM (" +
                "SELECT distinct tblneworders.order_id " +
                "FROM tblneworders " +
                "WHERE tblneworders.dtime_entered " +
                "LIKE (:dateEnter) " +
                "AND tblneworders.order_id " +
                "IN ( " +
                "SELECT tblnewtracking.order_id " +
                "FROM tblnewtracking " +
                ")) " +
                "TMP " +
                "WHERE 1=1 ";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate + "%");
        List<Integer> totalOrder = query.getResultList();
        em.close();
        return totalOrder;
    }

    public List<Integer> countPrintQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT SUM(print_quantity) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered " +
                "LIKE (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate + "%");
        List<Integer> printQuantity = query.getResultList();
        em.close();
        return printQuantity;
    }

    public List<Integer> countPrintedQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT SUM(printed_quantity) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered " +
                "LIKE (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate + "%");
        List<Integer> printedQuantity = query.getResultList();
        em.close();
        return printedQuantity;
    }

    public List<Integer> countPackagedQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT SUM(packaged_quantity) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered " +
                "LIKE (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate + "%");
        List<Integer> packagedQuantity = query.getResultList();
        em.close();
        return packagedQuantity;
    }

    public List<Integer> countErrorQuantityIn(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT SUM(error_quantity_in) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered " +
                "LIKE (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate + "%");
        List<Integer> errorQuantityIn = query.getResultList();
        em.close();
        return errorQuantityIn;
    }
}