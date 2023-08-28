package Practice.ShoppingMall.repository;

import Practice.ShoppingMall.dto.PageProduct;
import Practice.ShoppingMall.dto.Product;
import Practice.ShoppingMall.dto.RecommendFlowerDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {

    //WHERE name = #{name}
    @Select("SELECT * FROM product WHERE product_id = #{productId}")
    Product findById(Integer productId);

    @Select("SELECT * FROM product ORDER BY product_id DESC limit 1")
    Product lastOne();

    @Select("SELECT * FROM product")
    ArrayList<Product> findAll();

    @Select("SELECT COUNT(*) FROM product")
    Integer countProduct();

    @Select("select * from product ORDER BY product_id DESC limit 4")
    List<Product> pagenation();


    @Insert("INSERT INTO product(product_name, product_info) VALUES(#{productName}, #{productInfo})")
    void insertProduct(Product product);


    @Insert("INSERT INTO recommend(recommend_Image, recommend_Name, recommend_Content) " +
            "VALUES(#{recommendImage}, #{recommendName}, #{recommendContent})")
    void insertRecommendFlower(RecommendFlowerDto recommendFlowerDto);

    @Select("SELECT * FROM recommend ORDER BY recommend_Id DESC limit 1")
    RecommendFlowerDto findPurchaseRecord();

}
