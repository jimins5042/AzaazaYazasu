package Practice.ShoppingMall.controller;

import Practice.ShoppingMall.dto.Product;
import Practice.ShoppingMall.dto.RecommendFlowerDto;
import Practice.ShoppingMall.repository.ProductMapper;
import Practice.ShoppingMall.service.ChatService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductMapper productMapper;
    private final ChatService chatService;
    private final ChatgptService chatgptService;

    @Value("${kakao.api-key}")
    private String kakaoApikey;

    @GetMapping("/main")
    public String mainPage(Model model) {
        log.info("= 메인 페이지 =");
        model.addAttribute("products", productMapper.pagenation());
        return "/main";
    }

    private List<String> botMessages = new ArrayList<>();

    @GetMapping("/chat")
    public String chat(Model model) {
        // log.info(""+botMessages.size());

        botMessages.clear();
        if (botMessages.size() == 0) {
            botMessages.add("성별과 연령대를 알려주세요.");
        }
        model.addAttribute("kakaoApikey", kakaoApikey);

        return "/chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public String sendMessage(@RequestBody String userMessage, Model model) {

        botMessages.add(userMessage);

        String botResponse = chatService.chatQna(botMessages.size(), botMessages);

        botMessages.add(botResponse);
        log.info("user message : " + userMessage + " size : " + botMessages.size());

        return botResponse;
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        log.info("= 더보기 페이지 =");
        //model.addAttribute("products", productMapper.findAll());
        model.addAttribute("products", productMapper.findAll());
        return "/list";
    }


    @GetMapping("/product/{productId}")
    public String productPage(@PathVariable(name = "productId") Integer productId, Model model) {
        log.info("= 상품 상세 페이지 =" + productId);
        Product product = productMapper.findById(productId);
        model.addAttribute("product", product);

        return "/productInfo";
    }

    @GetMapping("/product")
    public String productPage() {

        ArrayList<Product> list = productMapper.findAll();

        for (Product product : list) {
            log.info(product.getProductName());
        }
        return "/main";
    }

    @GetMapping("/add")
    public String addProductPage() {
        log.info("= 상품추가 페이지 =");

        return "/addProduct";
    }

    @PostMapping("/add")
    public String addProductPage(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        log.info("= 상품추가 성공 =");
        productMapper.insertProduct(product);
        redirectAttributes.addAttribute("productId", productMapper.lastOne().getProductId());
        return "redirect:/product/{productId}";
    }

    @GetMapping("/purchase")
    public String purchaseFlower(Model model){
        log.info("= 상품구매 페이지 이동 =");

        RecommendFlowerDto purchase = productMapper.findPurchaseRecord();

        model.addAttribute("purchase", purchase);
        return "/purchase";
    }

    @GetMapping("/shareSns")
    public String shareSns(Model model){
        log.info("= 상품구매 페이지 이동 =");

        RecommendFlowerDto purchase = productMapper.findPurchaseRecord();
        purchase.setApiKey(kakaoApikey);
        model.addAttribute("purchase", purchase);
        return "/shareSns";
    }

    @GetMapping("/test")
    public String test() {
        log.info("= 상품추가 페이지 =");

        return "/test";
    }


}
