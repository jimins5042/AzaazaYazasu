package Practice.ShoppingMall.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService{

    private final ChatgptService chatgptService;

    public String getChatResponse(List<String> list) {
        String question = "You are a machine that recommends flowers to customers.\n" +
                "You should listen to several questions and answers from the customer and answer the name of the flower you recommend and why.\n" +
                "The answer should be concise and friendly.\n" +
                "The answer should be in Korean.\n" +
                "Here are the answers to the questions and questions provided to the customer. ";
        String prompt = "";
        for(int i = 0; i < 7; i = i+2){
            prompt += "질문 : " + list.get(i) + " 대답 : " + list.get(i + 1);
        }


        question += prompt;

        // ChatGPT 에게 질문을 던집니다.
        return chatgptService.sendMessage(question);
    }}