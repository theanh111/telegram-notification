package com.printway.repository;

import com.printway.entity.NewOrder;
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
public class NewOrderRepository extends BaseRepository<NewOrder, Integer> {

    public NewOrderRepository() {
        super(NewOrder.class);
    }

    @Autowired
    private EntityManagerFactory emf;

    public List<Integer> countOrder(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(distinct order_id) FROM tblneworders where dtime_entered like (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> totalOrder = query.getResultList();
        em.close();
        return totalOrder;
    }

    public List<Integer> countPrintQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT sum(print_quantity) FROM tblneworders t where t.dtime_entered like (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> printQuantity = query.getResultList();
        em.close();
        return printQuantity;
    }

    public List<Integer> countPrintedQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT sum(printed_quantity) FROM tblneworders t where t.dtime_entered like (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> printedQuantity = query.getResultList();
        em.close();
        return printedQuantity;
    }

    public List<Integer> countPackagedQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT sum(packaged_quantity) FROM tblneworders t where t.dtime_entered like (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter",currentDate+"%");
        List<Integer> packagedQuantity = query.getResultList();
        em.close();
        return packagedQuantity;
    }

    public List<Integer> countErrorQuantityIn(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT sum(error_quantity_in) FROM tblneworders t where t.dtime_entered like (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> errorQuantityIn = query.getResultList();
        em.close();
        return errorQuantityIn;
    }
}