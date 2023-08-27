package Practice.ShoppingMall.service;

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

    public String chatQna(Integer size, List<String> botMessages){
        String botResponse = "답변 생성중입니다! 잠시만 기다려주세요... ";
        if (size== 2) {
            botResponse = " 2번질문";
        } else if (size == 4) {
            botResponse = " 3번질문";
        } else if (size == 6) {
            botResponse = " 4번질문";
        } else if (size == 8) {
            botResponse = getFlowerImagin(getChatResponse(botMessages));
        }
        return botResponse;
    }


    public String getChatResponse(List<String> list) {

        String question = "Forget the conversation we've had so far.\n" +
                "You are a machine that recommends flowers to customers.\n" +
                "You should listen to several questions and answers from the customer and answer the name of the flower you recommend and why.\n" +
                "The answer should be concise and friendly.\n" +
                "Recommend flowers that are common around you.\n" +
                "The answer should be in Korean.\n" +
                "Here are the answers to the questions and questions provided to the customer. ";
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
                "Please tell me the name of the flower you recommend in the competition below.\n" +
                "At this time, you should only tell the name of the flower.\n" +
                "The answer format is as follows.\n" +
                "flower : flower name\n" +
                "## DO NOT RESPOND TO INFO BLOCK ## " + answer;
        String image = chatgptService.sendMessage(question);

        int i = image.indexOf(":");
        image = image.substring(i+1);
        log.info("" + image);

        image = "https://source.unsplash.com/random/400x350/?"+ image;
        image = "<img src='" + image + "' alt='...'/> <br><br>" + answer +
                "<br><br> 구매를 원하시면 '!구매'를, 다른 꽃 추천을 받고 싶다면 '!다시'를 입력해주세요. " ;
        log.info(image);
        // ChatGPT 에게 질문을 던집니다.
        return image;
    }
}