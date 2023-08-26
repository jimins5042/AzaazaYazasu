package Practice.ShoppingMall.controller;

import Practice.ShoppingMall.dto.Product;
import Practice.ShoppingMall.repository.ProductMapper;
import Practice.ShoppingMall.service.ChatService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
/*
    @PostMapping("/api/v1/chat-gpt")
    @ResponseBody
    public String test(@RequestBody String question){
        return chatService.getChatResponse(question);
        //\n\nAs an AI language model, I don't have feelings, but I'm functioning well. Thank you for asking. How can I assist you today?
    }
*/
    @GetMapping("/main")
    public String mainPage(Model model){
        log.info("= 메인 페이지 =");
        //model.addAttribute("products", productMapper.findAll());
        model.addAttribute("products", productMapper.pagenation());
        return "/main";
    }

    private List<String> botMessages = new ArrayList<>();

    @GetMapping("/chat")
    public String chat(Model model) {
       // log.info(""+botMessages.size());
        if(botMessages.size() == 0){
            botMessages.add("1번 질문");
        }else{
            log.info("size : " + botMessages.size() + " 채팅 : " + botMessages.get(botMessages.size()-1));
        }
        //botMessages.add("어떤 서비스를 찾으시나요?");
        model.addAttribute("botMessages", botMessages);

        return "/chat";
    }

    @PostMapping("/chat")
    @ResponseBody
    public String sendMessage(@RequestBody String userMessage) {
        // 챗봇 엔진 로직 대신 간단히 응답 메시지를 추가합니다.

        String botResponse = userMessage;
        botMessages.add(botResponse);
        botResponse = "답변 생성중입니다! 잠시만 기다려주세요... ";


        if(botMessages.size() == 2){
            botResponse = " 2번질문";
        }else if(botMessages.size() == 4){
            botResponse = " 3번질문";
        }else if(botMessages.size() == 6){
            botResponse = " 4번질문";
        }else if(botMessages.size() == 8){

            botResponse = chatService.getChatResponse(botMessages);
            log.info("챗 gpt : " +botResponse );
        }
        botMessages.add(botResponse);
        log.info("user message : " + userMessage + " size : " + botMessages.size());
        //return botResponse;
        return botResponse;
    }

    @GetMapping("/list")
    public String listPage(Model model){
        log.info("= 더보기 페이지 =");
        //model.addAttribute("products", productMapper.findAll());
        model.addAttribute("products", productMapper.findAll());
        return "/list";
    }


    @GetMapping("/product/{productId}")
    public String productPage(@PathVariable(name = "productId") Integer productId, Model model){
        log.info("= 상품 상세 페이지 =" + productId);
        Product product = productMapper.findById(productId);
        model.addAttribute("product", product);

        return "/productInfo";
    }

    @GetMapping("/product")
    public String productPage(){

        ArrayList<Product> list = productMapper.findAll();

        for(Product product : list){
            log.info(product.getProductName());
        }
        return "/main";
    }

    @GetMapping("/add")
    public String addProductPage(){
        log.info("= 상품추가 페이지 =");

        return "/addProduct";
    }

    @PostMapping("/add")
    public String addProductPage(@ModelAttribute Product product, RedirectAttributes redirectAttributes){
        log.info("= 상품추가 성공 =");
        productMapper.insertProduct(product);
        redirectAttributes.addAttribute("productId", productMapper.lastOne().getProductId());
        return "redirect:/product/{productId}";
    }



}
