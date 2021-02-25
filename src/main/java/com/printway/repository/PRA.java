package com.printway.repository;

import com.printway.entity.amz.TblProductAmazon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PRA {
    @Autowired
    private EntityManagerFactory emf;
    public List<TblProductAmazon> getProductAmazon(String userId)
    {
        EntityManager em = emf.createEntityManager();
        //em.getTransaction().begin( );
        String sqlQuery = "select t1.id, t1.sku, t1.product_name, t3.brand_name,t2.pro_description,t2.amazon_bullet_print,t3.currency,t1.store_sale_price,t1.store_media,t4.cat_name from tblproduct_post_store t1 join tblproducts t2 on t1.product_type_id = t2.id join tblamazon_brand t3 on t2.amazon_brand_id = t3.id join tblcategories t4 on t2.amazon_category_id = t4.id where t1.user_id_post = :userId and t1.store_name = 'amazon'";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<TblProductAmazon> list =(List<TblProductAmazon>)query.getResultList();

        em.close();

        return list;

    }

}
