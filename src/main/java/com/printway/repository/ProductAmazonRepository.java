package com.printway.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.printway.entity.amz.ProductAmazon;
import com.printway.entity.amz.ProductCustomize;
import com.printway.entity.amz.Variant;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProductAmazonRepository {
    @Autowired
    private EntityManagerFactory emf;

    public String findNewestOrderDate() throws ParseException {
        EntityManager em = emf.createEntityManager();
        String sqlQuery = "SELECT max(date_order) FROM tblorders where ord_store_infomation like '%Amazon%'";
        Query query = em.createNativeQuery(sqlQuery);
        List<Timestamp> stringList = query.getResultList();
        Timestamp data = stringList.get(0);
        String date = data.toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTest = formatter.parse(date);
        String dateFormat = formatter.format(dateTest);
        em.close();
        return dateFormat;
    }

    public List<ProductAmazon> getProductsAmazon(String userId) throws JsonProcessingException {
        EntityManager em = emf.createEntityManager();
        //em.getTransaction().begin( );
        String sqlQuery = "select t1.id, t1.sku, t1.product_name, t3.brand_name,t2.pro_description," +
                "t2.amazon_bullet_print,t3.currency,t1.store_sale_price,t1.store_media,t4.cat_name, " +
                "t1.store_variant, t1.amazon_auto_custom from tblproduct_post_store t1 join tblproducts t2 on t1.product_type_id = t2.id " +
                "join tblamazon_brand t3 on t2.amazon_brand_id = t3.id join tblcategories t4 on t2.amazon_category_id = t4.id " +
                "where t1.user_id_post = :userId and t1.store_name = 'amazon' and t1.online_store_status = 'Wait' ";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();
        em.close();
        List<ProductAmazon> list = convertProductAmazon(resultList);
        return list;
    }

    public ProductCustomize getCustomTemplate(String sku){
        EntityManager em = emf.createEntityManager();
        //em.getTransaction().begin( );
        String sqlQuery = "select \n" +
                "t1.id,\n" +
                "    t1.sku,\n" +
                "    t4.amazon_custom\n" +
                "    from tblproduct_post_store t1 \n" +
                "      join tblproducts t2 on t1.product_type_id = t2.id\n" +
                "      join tblcategories t4 on t2.amazon_category_id = t4.id\n" +
                "    where t1.sku = :sku";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("sku", sku);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();
        em.close();
        ProductCustomize productCustomize = convertProductCustomize(resultList);
        return productCustomize;
    }

    private List<ProductAmazon> convertProductAmazon(List<Object[]> stringList) throws JsonProcessingException {
        List<ProductAmazon> productAmazonList = new ArrayList<>();
        for (Object[] content : stringList) {
            ProductAmazon productAmazon = new ProductAmazon();
            productAmazon.setId(Integer.valueOf((Integer) content[0]));
            productAmazon.setSku((String) content[1]);
            productAmazon.setTitle((String) content[2]);
            productAmazon.setBrand((String) content[3]);
            productAmazon.setDescription((String) content[4]);
            productAmazon.setBulletPoint(stringParser(String.valueOf(content[5])));
            productAmazon.setCurrency((String) content[6]);
            productAmazon.setPrice(String.valueOf(content[7]));
            productAmazon.setImageLocation(stringParser(String.valueOf(content[8])));
            productAmazon.setCategory((String) content[9]);
            productAmazon.setVariantList(variantParser((String) content[10]));
            productAmazon.setAutoCustom(Integer.valueOf((Integer) content[11]));
            productAmazonList.add(productAmazon);
        }
        return productAmazonList;
    }

    private ProductCustomize convertProductCustomize(List<Object[]> stringList){
        ProductCustomize productCustomize = new ProductCustomize();
        Object[] data = stringList.get(0);
        productCustomize.setId((Integer) data[0]);
        productCustomize.setSku((String) data[1]);
        productCustomize.setCustom((String) data[2]);
        return productCustomize;
    }

    private List<String> stringParser(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    private List<Variant> variantParser(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Variant> variantList = mapper.readValue(json, new TypeReference<List<Variant>>() {
        });
        return variantList;
    }

}
