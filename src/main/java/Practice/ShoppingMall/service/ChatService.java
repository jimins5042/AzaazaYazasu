package Practice.ShoppingMall.service;

import Practice.ShoppingMall.dto.RecommendFlowerDto;
import Practice.ShoppingMall.repository.ProductMapper;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatgptService chatgptService;
    private final ProductService service;

    public String chatQna(Integer size, List<String> botMessages) {
        String botResponse = "답변 생성중입니다! 잠시만 기다려주세요... ";
        if (size == 2) {
            botResponse = " 고객님의 퍼스널 컬러를 알려주세요. ";
        } else if (size == 4) {
            botResponse = " 고객님의 MBTI는 무엇인가요?";
        } else if (size == 6) {
            botResponse = " 특별히 원하는 꽃이 있을까요?";
        } else if (size == 8) {
            botResponse = getFlowerImagin(getChatResponse(botMessages));
        }
        return botResponse;
    }


    public String getChatResponse(List<String> list) {

        String question = "Forget the conversation we've had so far.\n" +
/*
                "You are a machine that recommends flowers to customers.\n" +
                "Listen to the customer's questions and answers and answer the name of the flower you recommend and why.\n" +
                "You must answer concisely and kindly within 100 characters.\n" +
                "Please recommend a type of flower that can be easily obtained around you.\n" +
                "The answer should be in Korean.\n" +

 */
                "Listen to the conversation below and recommend a flower that suits the customer.\n" +
                "The following conditions must be observed.\n" +
                "1. The answer must be short, concise and friendly.\n" +
                "2. The answer must be in Korean.\n" +
                "3. It must be a common flower around you." +
                "4. The answer must be related to flowers." +
        "Here are answers to the questions and questions provided to the customer.\n";

        String prompt = "";
        for (int i = 0; i < 7; i = i + 2) {
            prompt += "질문 : " + list.get(i) + " 대답 : " + list.get(i + 1);
        }
        question += prompt;

        // ChatGPT 에게 질문을 던집니다.
        return chatgptService.sendMessage(question);

    }

    public String getFlowerImagin(String answer) {
        String question =
                /*
                    앞의 대화는 잊고, 현제 제공하는 문장에서 추천하는 꽃의 이름을 말해라.
                    이때 대답 형식은 다음과 같다.
                    flower : 꽃 이름
                 */
                "Forget the conversation we've had so far.\n" +
                        "Tell me the name of the flower you recommend in the next conversation.\n" +
                        "The conditions are as follows.\n" +
                        "1. Just say the name of the flower.\n" +
                        "2. The answer format must be as follows.\n" +
                        "flower : flower name in korean (flower name in English)\n" +
                        "3. The name of the flower answered in English and the name of the flower answered in Korean must be the same."

                        + answer;
        String image = chatgptService.sendMessage(question);

        int i = image.indexOf(":");
        image = image.substring(i + 2);

        //image = testImage(image);
        String imageUrl = "https://source.unsplash.com/random/400x350/?" + image;
        log.info("" + image + " , " + imageUrl);

        RecommendFlowerDto recommend = new RecommendFlowerDto(imageUrl, image, answer);
        service.saveRecommend(recommend);

        image = "<img src='" + imageUrl + "' alt='...'/> <br><br>" + answer +
                "<br><br> 구매를 원하시면 '!구매'를, \n다른 꽃 추천을 받고 싶다면 '!다시'를,\n" +
                "진단 결과를 공유하고 싶다면 '!공유'를 입력해주세요. ";
        return image;
    }

    public String testImage(String answer) {
        String imageUrl = chatgptService.imageGenerate(answer);

        return imageUrl;
    }
}