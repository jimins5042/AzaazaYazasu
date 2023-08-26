package Practice.ShoppingMall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Product {
    private Integer productId;
    private String productName;
    private String productInfo;

    public Product() {
    }
    public Product(Integer productId, String productName, String productInfo) {
        this.productId = productId;
        this.productName = productName;
        this.productInfo = productInfo;
    }
}
