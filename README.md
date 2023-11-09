# StyleCast_BackEnd

## 📖 프로젝트 소개

**구현 계기**

* 실무에서 사용되는 기술들을 공부하며, 직접 프로젝트에 적용해 더 효율적으로 체화시키기 위해 StyleCast 프로젝트를 시작했습니다.
* 여러 주제들을 생각하던 중 기존에 사용하고 있던 Weather Fit이라는 앱의 기능인 날씨에 기반한 간단한 옷 추천에 아쉬움을 느꼈습니다.
* 날씨를 기반으로 간단한 옷 추천에서 나아가 사용자에게 조금 더 특화된 날씨 기반 옷 추천 기능을 담고 있는 앱이 있으면 좋겠다고 생각해 주제를 정하게 되었습니다. 
 
**구현 목표**

* 가장 기반이 되는 날씨 정보 제공
* 사용자 별로 직접 옷과 코디를 저장(업로드)하고, 날씨에 맞춘 코디 추천
* 날씨 기반 추천만이 아닌 사용자가 입력한 Profile과 추가적으로 입력하는 조건에 맞춘 코디 추천
* 코디를 여러 사용자들과 함께 공유할 수 있도록 하며, 다른 사용자의 코디에 좋아요 기능 추가 -> 추천 수를 코디 추천에 사용

**기대 효과**
* 오늘 뭐입지?에 대한 고민 해소
* 다양한 룩들을 둘러보고 싶을 때 사용자의 체형과 비슷한 사람들의 코디 및 원하는 스타일에 따른 룩들을 보며 참고할 수 있음

**아쉬운 점**
* 프로젝트의 목적은 백엔드 실무에서 사용되는 기술들을 공부하고, 체화시키는 것이였기 때문에 프론트 개발이 완벽하지 않았던 점
* 추후에 프론트까지 신경써서 제작하여 "오늘 뭐입지?"라는 고민을 해결해 줄 수 있는 앱을 정식적으로 개발, 배포할 계획 

## 🔎 기능 소개

<details>
<summary>회원가입 및 Klas 연동하기</summary>
<div markdown="1">
 
 ![test-min](https://user-images.githubusercontent.com/83166141/212456924-869de349-82d3-430f-b595-19d3d6b6dcc8.gif)
 
 ![회원가입및로그인](https://github.com/19-21-40/KLtime_FrontEnd/assets/99861250/032deaa9-d901-4c3d-b2d6-19558df9b1a7)

* ㅇㅇ

</div>
</details>

* GeocodingApi와 openWeatherMap api를 통해 위치 기반 날씨 정보 제공
* 사용자의 Profile을 기반으로 코디 추천기능 제공
  * 다른 사용자들의 좋아요 즉, likes 수가 최소 10개 이상인 코디 추천
  * 날씨를 기반으로 여러 사용자들의 코디를 토대로 코디 추천
  * 사용자가 원하는 Style 지정시 해당 Style 코디들만 추천
  * 다른 사용자의 선호 Style이 겹치는 경우 해당 사용자의 코디들 추천
* 사용자가 등록한 코디와 옷에 BookMark 기능을 추가하여 선호하는 옷들을 사용자에게 선별 제공

## 📃 기술 스택
### Database
* H2 Database

### Framework
* Spring Boot
* Hibernate

### Add..
* Spring Data JPA
* QueryDsl
* Spring Security
* JWT

## ⚙️ 시스템 아키텍처
![stylecast drawio](https://github.com/19-21-40/KLtime_BackEnd/assets/99861250/74a94069-136d-4dde-9ddc-b45ab7f5dc0f)

## 📁 DB 구조


## 🗂️ Entitiy 구조
<img width="821" alt="스크린샷 2023-10-15 오후 7 40 55" src="https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/b5b239f8-d4ea-4693-9c17-80652e74c1c4">


