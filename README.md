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
<summary>전체적인 흐름</summary>
<div markdown="1">
 
 ![전체적흐름](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/2faeab50-1c1a-4551-9da9-c6363ce1e381)

 
 * 해당 프로젝트의 전체적인 기능들의 흐름
 * GeocodingApi와 openWeatherMap api를 통해 위치 기반 날씨 정보 제공
 * 사용자의 Profile을 기반으로 코디 추천기능 제공
 * 다른 사용자들의 좋아요 즉, likes 수가 최소 10개 이상인 코디 추천
 * 날씨를 기반으로 여러 사용자들의 코디를 토대로 코디 추천
 * 사용자가 원하는 Style 지정시 해당 Style 코디들만 추천
 * 사용자가 등록한 코디와 옷에 BookMark 기능을 추가하여 선호하는 옷들을 사용자에게 선별 제공

</div>
</details>

<details>
<summary>회원가입 및 로그인</summary>
<div markdown="1">
 
 ![회원가입및로그인](https://github.com/19-21-40/KLtime_FrontEnd/assets/99861250/032deaa9-d901-4c3d-b2d6-19558df9b1a7)


* 회원가입 진행시 이메일 인증을 받으며 가입 성공시 로그인 페이지로 이동

***
 ![프로필설정](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/28b0b4a5-5186-4280-a5af-b5d5a7cb7c8c)


* 회원가입 이후 프로필 설정
***
 ![로그인성공코디O](https://github.com/19-21-40/KLtime_FrontEnd/assets/99861250/62112528-68a5-4b5d-aaec-9c9ab62dd258)


* 코디가 있을 때 로그인 예시
* 로그인 성공시 메인 페이지로 이동
* 메인 페이지에서는 사용자의 정보에 있는 지역의 날씨와 기본적인 코디 추천
  * GeocodingApi와 openWeatherMap api를 통해 위치 기반 날씨 정보 제공
* 내 코디가 있을 시 날씨를 기반으로 코디 추천
***
 ![로그인실패및코디X](https://github.com/19-21-40/KLtime_FrontEnd/assets/99861250/335f203e-9e45-4d86-862b-d396a9e77770)


* 코디가 없을 때 로그인 예시
* 로그인 실패시 콘솔에 에러표시
* 메인 페이지에서는 사용자의 정보에 있는 지역의 날씨와 기본적인 코디 추천 동일
  * GeocodingApi와 openWeatherMap api를 통해 위치 기반 날씨 정보 제공
* 내 코디가 없다면 메시지 출력

</div>
</details>

<details>
<summary>옷 및 코디 등록</summary>
<div markdown="1">

 ![옷추가](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/b8956934-23af-4638-b067-4bf9716b0dbf)


* 본인 소유의 옷을 추가할 수 있습니다.
***
 ![내옷장](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/0c5af5b2-3b28-431a-8d46-94296e55b12c)


* 사용자가 추가한 옷들을 살펴볼 수 있습니다.
* 카테고리별 조회가 가능합니다.
*** 
 ![코디추가](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/226957ca-fb61-49c7-a8dd-dcb4390a9298)


* 본인의 코디를 추가할 수 있습니다.
***
 ![내코디](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/e8da7681-99eb-4c61-b9fd-e98398f952bd)

* 사용자가 추가한 코디들을 살펴볼 수 있습니다.
* 스타일별 조회가 가능합니다.

</div>
</details>

<details>
<summary>데일리룩 기능</summary>
<div markdown="1">

![데일리룩둘러보기](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/f6d91439-ac47-4863-8d28-e79e84a4494d)


* 사용자들이 추가한 코디가 데일리룩 게시판에서 조회됩니다.
* 각 사용자는 다른 사용자들의 코디에 좋아요를 누를 수 있습니다.
* 좋아요 2개 이상이 된다면 추천 코디 목록에 들어갈 수 있습니다.

</div>
</details>

<details>
<summary>코디 추천 기능</summary>
<div markdown="1">

![코디추천남자](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/16902e8b-619f-427e-8c69-618c05554360)


* 남성 유저
* 일반 코디 추천의 경우 날씨와 기온에 따른 추천기능
* 스타일별 추천 기능
  * 다른 사용자들의 좋아요 즉, likes 수가 최소 2개 이상인 코디 추천
  * 사용자가 원하는 Style 지정시 해당 Style 코디들만 추천
  * 사용자의 Profile이 추천시 사용되기 때문에 사용자에 맞춤형 서비스 제공 가능
***
![코디추천여자](https://github.com/jaemin-shin02/StyleCast_BackEnd/assets/99861250/3d8e6b0b-2efe-4299-9c35-f957270ab9a0)

* 여성 유저
* 일반 코디 추천의 경우 날씨와 기온에 따른 추천기능
* 스타일별 추천 기능
  * 다른 사용자들의 좋아요 즉, likes 수가 최소 2개 이상인 코디 추천
  * 사용자가 원하는 Style 지정시 해당 Style 코디들만 추천
  * 사용자의 Profile이 추천시 사용되기 때문에 사용자에 맞춤형 서비스 제공 가능

</div>
</details>

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


