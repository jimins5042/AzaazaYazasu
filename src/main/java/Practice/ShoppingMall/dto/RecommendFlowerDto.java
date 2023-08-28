package Practice.ShoppingMall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecommendFlowerDto {

    private Integer RecommendId;
    private String RecommendImage;
    private String RecommendName;
    private String RecommendContent;

    public RecommendFlowerDto() {
    }

    public RecommendFlowerDto(String recommendImage, String recommendName, String recommendContent) {
        RecommendImage = recommendImage;
        RecommendName = recommendName;
        RecommendContent = recommendContent;
    }
}
