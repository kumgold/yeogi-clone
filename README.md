# 여기어때 클론 프로젝트

## 📖 프로젝트 소개
실제로 자주 사용하는 여행 서비스 ‘여기어때’를 클론 코딩한 프로젝트입니다.
단순히 UI만 구현한 것이 아니라, Dummy 데이터를 실제 서비스 구조에 맞게 설계하여 실제 서비스와 유사한 데이터 흐름을 구현했습니다. 
이를 통해 단순한 화면 구현을 넘어, 서비스 전반의 작동 방식을 이해하고 개발자로서의 역량을 확장하는 데에 목적을 두었습니다.

또한, 규모가 크고 다양한 기능을 가진 서비스인 만큼, 복잡한 데이터 구조와 화면 흐름을 학습하기에 적합하다고 판단하여 ‘여기어때’를 클론 프로젝트 대상으로 선택했습니다. 
앞으로는 현재 더미 데이터를 Firebase로 이전하여 실제 환경과 더욱 가까운 구현을 목표로 하고 있습니다.

## 🛠 기술 스택
Language : Kotlin <br>
View : Compose <br>
Server : Firebase <br>
Library : Hilt <br>

## ✨ 주요 기능

## 🏞️ 화면
<p>
  <img src="./images/yeogi-1.gif" width="24%">
  <img src="./images/yeogi-2.gif" width="24%">
  <img src="./images/yeogi-3.gif" width="24%">
  <img src="./images/yeogi-4.gif" width="24%">
</p>
<p>
  <img src="./images/yeogi-4.png" width="24%">
  <img src="./images/yeogi-5.png" width="24%">
  <img src="./images/yeogi-6.png" width="24%">
</p>

## 프로젝트 구조
```markdown
yeogi-clone/
    ├── core/
    │   ├── data/...
    │   ├── model/...
    │   ├── util/...
    │   └── presentation/...
    ├── main/
    │   └── MainScreen.kt
    ├── feature/
    │   ├── home/
    │   │   ├── data/
    │   │   │   ├── local/...
    │   │   │   └── repository/...
    │   │   ├── HomeScreen.kt
    │   │   └── HomeViewModel.kt
    │   ├── hotel/
    │   │   ├── data/
    │   │   │   └── HotelCategory.kt
    │   │   ├── HotelScreen.kt
    │   │   └── HotelViewModel.kt
    │   ├── accommodation/
    │   │   ├── AccommodationScreen.kt
    │   │   └── AccommodationViewModel.kt
    │   ├── payment/
    │   │   ├── data/
    │   │   │   └── PaymentDetail.kt
    │   │   ├── PaymentScreen.kt
    │   │   └── PaymentViewModel.kt
    │   │ ...
    ├── navigation/
    │   │   ├── graph/
    │   │   │   └── HomeGraph.kt
    │   │   ├── Navigation.kt
    │   │   └── NavItem.kt
    ├── shared/
    │   ├── ui/
    │   │   └── RecentHistorySection.kt
    │   │   ...
    └── MainActivity.kt
```

## 👀 개발 과정에서 발생한 이슈

## 🎯 개발 계획
- Dummy 데이터를 Firebase에 옮겨서 네트워크 연결 구현 예정
