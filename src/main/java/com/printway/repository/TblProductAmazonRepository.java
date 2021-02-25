package com.printway.repository;

import com.printway.entity.amz.TblProductAmazon;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TblProductAmazonRepository extends BaseRepository<TblProductAmazon, Long> {
    public TblProductAmazonRepository(){
        super(TblProductAmazon.class);
    }
    public List<TblProductAmazon> getProductsAmazon(String userId){
        String query = "select t1.id, t1.sku, t1.product_name, t3.brand_name,t2.pro_description,t2.amazon_bullet_print,t3.currency,t1.store_sale_price,t1.store_media,t4.cat_name from tblproduct_post_store t1 join tblproducts t2 on t1.product_type_id = t2.id join tblamazon_brand t3 on t2.amazon_brand_id = t3.id join tblcategories t4 on t2.amazon_category_id = t4.id where t1.user_id_post = :userId and t1.store_name = 'amazon'";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return query(query, true, params);
    }

    public static void main(String[] args) {
        String query = "select " +
                "t1.id," +
                " t1.sku," +
                " t1.product_name," +
                " t3.brand_name," +
                "    t2.pro_description," +
                "    t2.amazon_bullet_print," +
                "    t3.currency," +
                "    t1.store_sale_price," +
                "    t1.store_media," +
                "    t4.cat_name" +
                "    from tblproduct_post_store t1" +
                "      join tblproducts t2 on t1.product_type_id = t2.id" +
                "      join tblamazon_brand t3 on t2.amazon_brand_id = t3.id" +
                "      join tblcategories t4 on t2.amazon_category_id = t4.id" +
                "    where t1.user_id_post = :userId and t1.store_name = 'amazon';";

        System.out.println(query.replaceAll("  ",""));
    }

}
