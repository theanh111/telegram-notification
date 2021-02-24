package com.printway.repository;

import com.printway.entity.amz.TblProductAmazon;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TblProductAmazonRepository extends BaseRepository<TblProductAmazon, Long> {
    public TblProductAmazonRepository(){
        super(TblProductAmazonRepository.class);
    }
    public List<TblProductAmazon> getProductsAmazon(String userId){
        String query = "select pp.id, pp.sku, pp.product_name," +
                "ab.brand_name, pt.pro_description, pt.amazon_bullet_print" +
                "ab.currency, pp.store_sale_price, pp.store_media" +
                "from tblproduct_post_store pp " +
                "inner join tblproducts pt on pp.url_file_design = pt.prod_url_design_printer" +
                "inner join tblamazon_brand ab on pt.amazon_brand_id = ab.id" +
                "where pp.user_id_post = :userId and pp.store_name = amazon ";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return query(query, true, params);
    }
}
