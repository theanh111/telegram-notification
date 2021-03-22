package com.printway.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public List<Integer> findNewestOrder(String oneHourBack, String currentDate) throws JsonProcessingException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT COUNT(DISTINCT order_id) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered >= :dateOneHourBack AND t.dtime_entered <= :dateCurrent";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateCurrent", currentDate+"%");
        query.setParameter("dateOneHourBack", oneHourBack+"%");
        List<Integer> totalNewestOrder = query.getResultList();
        em.close();
        return totalNewestOrder;
    }

//    public List<NewOrder> findNewestOrder(Date currentDate, Date oneHourBack) throws JsonProcessingException {
//        EntityManager em = emf.createEntityManager();
//        String sqlQuery = "SELECT count(distinct order_id) FROM orderdesk.tblneworders WHERE dtime_entered <= :dateCurrent and dtime_entered >= :dateOneHourBack";
//        Query query = em.createNativeQuery(sqlQuery);
//        query.setParameter("dateCurrent", currentDate);
//        query.setParameter("dateOneHourBack", oneHourBack);
//        List<Object[]> totalNewestOrder = query.getResultList();
//        em.close();
//        List<NewOrder> list = convertNewOrder(totalNewestOrder);
//        return list;
//    }

//    private List<NewOrder> convertNewOrder(List<Object[]> newOrderList) throws JsonProcessingException {
//        List<NewOrder> newOrderArrayList = new ArrayList<>();
//        for (Object[] content : newOrderList) {
//            NewOrder newOrder = new NewOrder();
//            newOrder.setId(Integer.valueOf((Integer) content[0]));
//            newOrder.setOrderId((String) content[1]);
//            newOrder.setBarCode((String) content[2]);
//            newOrder.setShippingName((String) content[3]);
//            newOrder.setShippingAddress1((String) content[4]);
//            newOrder.setShippingAddress2((String.valueOf(content[5])));
//            newOrder.setShippingCity((String) content[6]);
//            newOrder.setShippingZip(String.valueOf(content[7]));
//            newOrder.setShippingProvince((String.valueOf(content[8])));
//            newOrder.setShippingCountry((String) content[9]);
//            newOrder.setShippingPhone(((String) content[10]));
//            newOrder.setLineItemQuantity(Integer.valueOf((Integer) content[11]));
//            newOrder.setLineItemName(String.valueOf(content[12]));
//            newOrder.setProductType(String.valueOf(content[13]));
//            newOrder.setVariant(String.valueOf(content[14]));
//            newOrder.setPrintQuantity(Integer.valueOf((Integer) content[15]));
//            newOrder.setPrintedQuantity(Integer.valueOf((Integer) content[16]));
//            newOrder.setPackagedQuantity(Integer.valueOf((Integer) content[17]));
//            newOrder.setErrorQuantityIn(Integer.valueOf((Integer) content[18]));
//            newOrder.setErrorQuantityOut(Integer.valueOf((Integer) content[19]));
//            newOrder.setSku(String.valueOf(content[20]));
//            newOrder.setUrlMockup(String.valueOf(content[21]));
//            newOrder.setPartnerId(String.valueOf(content[22]));
//            newOrder.setNoted(String.valueOf(content[23]));
//            newOrder.setStatusProduction(Integer.valueOf((Integer) content[24]));
//            newOrder.setDateReceived((java.util.Date) (content[25]));
//            newOrder.setDtimeEntered((java.util.Date) (content[26]));
//            newOrder.setWhoEntered(String.valueOf(content[27]));
//            newOrder.setStatusOnStracking(Integer.valueOf((Integer) content[28]));
//            newOrder.setTotalpackaged(null);
//            newOrder.setStatusDownloadFileDesign(Integer.valueOf((Integer) content[30]));
//            newOrder.setResendReason(null);
//            newOrder.setResendDtimeEntered(null);
//            newOrder.setResendWhoEntered(null);
//            newOrder.setMaterial(String.valueOf(content[34]));
//            newOrderArrayList.add(newOrder);
//        }
//        return newOrderArrayList;
//    }


    // New query
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
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> totalOrder = query.getResultList();
        em.close();
        return totalOrder;
    }

//    // Old query
//    public List<Integer> countOrder(String currentDate) {
//        EntityManager em = emf.createEntityManager();
//        String sqlQuery = "SELECT COUNT(DISTINCT order_id) " +
//                "FROM tblneworders t " +
//                "WHERE t.dtime_entered " +
//                "LIKE (:dateEnter)";
//        Query query = em.createNativeQuery(sqlQuery);
//        query.setParameter("dateEnter", currentDate+"%");
//        List<Integer> totalOrder = query.getResultList();
//        em.close();
//        return totalOrder;
//    }

    public List<Integer> countPrintQuantity(String currentDate) {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT SUM(print_quantity) " +
                "FROM tblneworders t " +
                "WHERE t.dtime_entered " +
                "LIKE (:dateEnter)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("dateEnter", currentDate+"%");
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
        query.setParameter("dateEnter", currentDate+"%");
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
        query.setParameter("dateEnter",currentDate+"%");
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
        query.setParameter("dateEnter", currentDate+"%");
        List<Integer> errorQuantityIn = query.getResultList();
        em.close();
        return errorQuantityIn;
    }
}