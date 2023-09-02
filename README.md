작성중...

# 개요

4회 오아시스 해커톤에 참가한 아자아자야자수 팀의 백엔드 개발 보고서.

프로젝트 목표
- 화훼 소비를 늘리기 위한 체험과 공유, 지속적 소비 형성을 테마로 하는 <br>
  소상공인과 소비자를 연결하는 플랫폼 제작

구현한 서비스
- 공유 및 커뮤니티 기능
- ai를 이용한 추천 서비스

개발 요약
- 스프링부트와 Chat gpt를 사용한 Rest api 구현.
- SNS 공유 api를 통해 추천 결과를 다른 사람들과 공유하여 서비스 이용자의 지속적인 사용과 확장을 유도.
- 부트스트랩을 사용한 반응형 웹 디자인을 활용하여 웹, 모바일 환경 모두 같은 디자인을 적용하여 <br>
  웹, 모바일 환경 모두 동일한 경험이 가능.

# 프로젝트 설명

## 문제 인식

![image](https://github.com/timetable5987/AzaazaYazasu/assets/28335699/f67f5e65-69f7-45e3-92cb-b62e4823ae8f)

이렇게 꽃의 가격이 상승한 이유는 크게 두가지이다.

1. 화훼농가의 주 사용연료인  유류비, 전기요금 상승으로 인해 원가가 인상 되었으며 온도조절이  꽃 생산량이 크게 감소하였다.
2. 대형 중도매업체에 많은 경매권이 부여되는 경매 유통 시스템 덕분에 영세 중도매업체들은 남은 물량을 가지고 경쟁해야 하며,<br>
   이는 도매가의 상승으로 이어지게 되었다.

이러한 문제 해결을 위해 체험과 공유, 지속적 소비 형성을 테마로 하는 플랫폼을 개발하고자 하였다.

## 프로젝트 목표

![image](https://github.com/timetable5987/AzaazaYazasu/assets/28335699/dba54ec4-2672-4472-aaee-a9c819244650)

1. 꽃 가격 상승으로 수요 및 매출이 감소하면서 제품 판매만으로는 생존이 어려워진 소상공인에게 차별화된 클래스 운영 기회를 제공함으로써 부수적인 수입을 도모한다.
2. 소비자에겐 저렴한 가격으로 꽃을 구매함과 동시에 클래스를 통해 지식 획득과 취미 형성을 함께 할 수 있는 기회를 제공한다.
3. ai를 통한 맞춤형 꽃 추천 서비스를 통해 개인 니즈를 충족시키고, 커뮤니티를 통해 공유함으로써 일시적 경험이 아닌 지속적인 소비를 돕는 공간을 제공한다.

## 기대효과

![image](https://github.com/timetable5987/AzaazaYazasu/assets/28335699/78b660ab-c809-4490-88a2-2d67c10fa37b)

- 경제적측면에선 피어나 서비스를 통한 부수적 수입 경로 확보로 꽃집 소상공인들에게 경제적 도움을 제공할 수 있다.
- 소비자에게 저렴한 가격으로 꽃을 구매하고 클래스까지 수강할 수 있는 체험의 기회를 제공할 수 있다.
  
- 사회적 측면에선 다양한 컨텐츠를 통해 지속적 화훼소비를 할 수 있게끔 구성하였기 때문에 화훼 산업의 활성화를 도울 수 있으며<br>
  향후 추가 될 내가 만든 꽃 선물하기 서비스를 통해 사회적 공헌 활동도 실천할 수 있다.
- 메인 컨텐츠인 오프라인 화훼클래스는 수많은 꽃집과 도매시장이라는 자산을 활용하고 있기 때문에 <br>호남지역의 부족한 관광 및 체험에 대한 문제해결에도 기여할 수 있을 것으로 예상된다.


# 구현 상세

## Chat Gpt api를 사용한 사용자 개개인에 맞춘 꽃 추천 기능

![](https://velog.velcdn.com/images/2jooin1207/post/67749cd2-a68b-4403-a77d-0304c5549f39/image.PNG)


![](https://velog.velcdn.com/images/2jooin1207/post/46f891e7-08cd-459c-b5f0-0cb16a4b9e3a/image.PNG)


구현 방식 및 특징
- open ai 에서 제공하는 GPT-3.5 모델 중 하나인 text-davinci-003을 사용.
- text-davinci-003의 특징으로 텍스트의 의도를 이해하거나 특정 청중을 위한 텍스트 요약 및 창의적인 콘텐츠 생성에 특화되어 있음.
- 역할(Role)을 지정하여 답변 방식과 내용의 방향성을 고정하였음. <br>
  -> 서비스 이용자가 질문과 아무런 상관이 없는 응답을 하더라도 챗봇은 꽃과 관련된 답변을 반환함.

  ex) 명령 프롬포트를 사용한 방향성 고정
  ~~~
  public String getFlowerImagin(String answer) {
        String question =
                /*
                    앞의 대화는 잊고, 현제 제공하는 문장에서 추천하는 꽃의 이름을 말해라.
                    이때 대답 형식은 다음과 같다.
                    flower : 꽃 이름 (꽃의 영어 이름)
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
  ~~~


## Ajax 통신을 사용한 챗봇 구현(부트스트랩을 사용한 기능 테스트)

구현 방식 및 특징
- Chat gpt api를 사용한 챗봇의 답변은 Ajax통신을 사용하여 비동기 방식을 통해 바로 받아온다.
- 부트스트랩을 사용한 반응형 웹 디자인을 활용하여 웹, 모바일 환경 모두 같은 디자인을 적용 가능.
- 단, 이 브런치 코드의 경우 어디까지나 안드로이드 앱의 백엔드 rest api이므로, 기능 테스트 용도로만 사용하였음.



##  SNS 공유 api를 사용한 추천결과 공유

  ![](https://velog.velcdn.com/images/2jooin1207/post/d042907c-0c50-4cea-a57f-a0214fc7018e/image.PNG)


구현 방식 및 특징
- 카카오톡, 인스타 공유 api를 통해 서비스 홍보 및 확장을 유도.
- 동적으로 메시지를 구성하여 결과를 공유한다.

  ex) ProductController.java에서 model.attribute로 추천 결과를 <br>
  shareSns.html의 Kakao.Share.createCustomButton에 넘기면 카카오톡 메시지 탬플릿의 내용이 변경된다.
  
  ProductController.java
  ~~~
    @PostMapping("/chat")
    @ResponseBody
    public String sendMessage(@RequestBody String userMessage) {

        botMessages.add(userMessage);

        String botResponse = chatService.chatQna(botMessages.size(), botMessages);

        botMessages.add(botResponse);
        log.info("user message : " + userMessage + " size : " + botMessages.size());

        return botResponse;
    }
  ~~~

  shareSns.html
  ~~~
  Kakao.Share.createCustomButton({
    container: '#kakaotalk-sharing-btn',
    templateId: 97832,
    templateArgs: {
      image : info.recommendImage,
      title: info.recommendName,
      desc: info.recommendContent
    },
  });
  ~~~
