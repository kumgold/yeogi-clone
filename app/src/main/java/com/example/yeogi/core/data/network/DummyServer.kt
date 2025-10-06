package com.example.yeogi.core.data.network

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.model.Facility
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.core.model.Review
import com.example.yeogi.feature.hotel.data.Region
import java.time.LocalDate

object DummyServer {
    val regions = mapOf(
        "서울" to listOf(
            Region("서울 전체", 37.5665, 126.9780),
            Region("강남/역삼/삼성", 37.5088, 127.0489),
            Region("서초/교대", 37.4979, 127.0074),
            Region("신사/청담", 37.5240, 127.0394),
            Region("송파/잠실", 37.5145, 127.1058),
            Region("여의도/영등포", 37.5218, 126.9242),
            Region("홍대/합정/마포", 37.5567, 126.9237),
            Region("종로/을지로", 37.5700, 126.9850),
            Region("명동/이태원", 37.5598, 126.9862),
            Region("동대문", 37.5714, 127.0095),
            Region("성수/건대", 37.5445, 127.0559)
    ),
    "경기" to listOf(
        Region("경기 전체", 37.2636, 127.0286),
        Region("수원", 37.2636, 127.0286),
        Region("용인", 37.2411, 127.1775),
        Region("성남/분당", 37.3800, 127.1189),
        Region("고양/일산", 37.6570, 126.7637),
        Region("화성/동탄", 37.2062, 127.0721),
        Region("가평/양평", 37.6332, 127.5100),
        Region("파주", 37.7618, 126.7785),
        Region("의정부", 37.7381, 127.0337),
        Region("안양/과천", 37.3943, 126.9568)
    ),
    "인천" to listOf(
        Region("인천 전체", 37.4563, 126.7052),
        Region("영종도/인천공항", 37.4602, 126.4407),
        Region("송도", 37.3842, 126.6551),
        Region("강화도", 37.7478, 126.4957),
        Region("을왕리", 37.4477, 126.3772),
        Region("월미도", 37.4754, 126.5978)
    ),
    "강원" to listOf(
        Region("강원 전체", 37.7519, 128.8761),
        Region("강릉", 37.7638, 128.8991),
        Region("속초", 38.2043, 128.5912),
        Region("양양", 38.0754, 128.6206),
        Region("춘천/홍천", 37.8813, 127.7299),
        Region("평창", 37.3707, 128.3919),
        Region("정선", 37.3822, 128.6608),
        Region("동해/삼척", 37.5212, 129.1123)
    ),
    "부산" to listOf(
        Region("부산 전체", 35.1796, 129.0756),
        Region("해운대", 35.1608, 129.1639),
        Region("광안리", 35.1534, 129.1189),
        Region("서면/부산역", 35.1578, 129.0593),
        Region("기장", 35.2445, 129.2138),
        Region("남포동/송도", 35.0756, 129.0169)
    ),
    "제주" to listOf(
        Region("제주 전체", 33.4996, 126.5312),
        Region("제주시", 33.5104, 126.5216),
        Region("서귀포시", 33.2541, 126.5601),
        Region("애월/한림", 33.4578, 126.3112),
        Region("성산/우도", 33.4585, 126.9423),
        Region("표선/남원", 33.3255, 126.8329)
    ),
    "여수" to listOf(
        Region("여수 전체", 34.7604, 127.6622),
        Region("돌산읍", 34.7001, 127.7337),
        Region("화양면", 34.6974, 127.5957),
        Region("시내중심", 34.7437, 127.7397)
    ),
    "경주" to listOf(
        Region("경주 전체", 35.8562, 129.2247),
        Region("보문단지", 35.8437, 129.2870),
        Region("황리단길/시내", 35.8351, 129.2130),
        Region("불국사", 35.7903, 129.3321),
        Region("양남/감포", 35.7336, 129.4719)
    ),
    "전주" to listOf(
        Region("전주 전체", 35.8242, 127.1480),
        Region("한옥마을 주변", 35.8148, 127.1517),
        Region("시내중심", 35.8200, 127.1437),
        Region("덕진공원", 35.8480, 127.1217)
        )
    )

    val accommodations = listOf(
        // == 서울 ==
        Accommodation(
            id = 1,
            name = "호텔 신라 서울",
            rating = 4.6,
            reviewCount = 6800,
            price = 450000,
            imageUrl = "https://picsum.photos/seed/1/400",
            category = "호텔",
            address = "서울 중구 동호로 249",
            isSpecialPrice = true,
            coordinateY = 37.5551,
            coordinateX = 127.0093,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.BusinessCenter, "비즈니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.RoomService, "룸서비스")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "어번 아일랜드(야외 수영장)는 유료 시설이며, 특정 패키지 이용 시에만 이용 가능합니다.",
            usageInfo = "• 모든 객실은 금연입니다.\n• 반려동물 동반 입실은 불가합니다.\n• 미성년자는 보호자 동반 없이 이용할 수 없습니다.",
            reviews = listOf(
                Review("김신라", 5.0, "역시 최고의 호텔입니다. 서비스, 룸 컨디션 모두 만족스러웠어요.", "2025.09.01"),
                Review("이투숙", 4.0, "수영장이 정말 좋았지만, 주말이라 사람이 너무 많았네요.", "2025.08.28")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = true,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 2,
            name = "롯데호텔 서울",
            rating = 4.5,
            reviewCount = 7500,
            price = 380000,
            imageUrl = "https://picsum.photos/seed/2/400",
            category = "호텔",
            address = "서울 중구 을지로 30",
            isSpecialPrice = false,
            coordinateY = 37.5658,
            coordinateX = 126.9804,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.BusinessCenter, "비즈니스"),
                Facility(Icons.Default.LocalBar, "바"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "피트니스 센터 및 수영장은 매월 마지막 주 월요일 휴무입니다.",
            usageInfo = "• 객실 내 흡연 시, 20만원의 추가 요금이 부과됩니다.\n• 주차는 투숙 기간 동안 무료로 제공됩니다.",
            reviews = listOf(
                Review("박롯데", 5.0, "위치가 너무 좋아서 비즈니스 출장객에게 최고입니다. 룸서비스도 훌륭해요.", "2025.09.05"),
                Review("최서울", 4.0, "오래된 호텔이라 약간의 세월감은 있지만, 관리가 잘 되어있습니다.", "2025.08.25")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = false,
            hasOceanView = false,
            hasCityView = true,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 3,
            name = "파크 하얏트 서울",
            rating = 4.6,
            reviewCount = 2400,
            price = 550000,
            imageUrl = "https://picsum.photos/seed/3/400",
            category = "호텔",
            address = "서울 강남구 테헤란로 606",
            isSpecialPrice = true,
            coordinateY = 37.5085,
            coordinateX = 127.0632,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.RoomService, "룸서비스"),
                Facility(Icons.Default.LocalBar, "바")
            ),
            checkInTime = "15:00", checkOutTime = "12:00",
            notice = "도심 뷰가 아름다운 인피니티 풀이 호텔의 자랑입니다. 수영모 착용은 필수입니다.",
            usageInfo = "• 주차는 발렛 파킹 서비스만 제공되며, 유료입니다.\n• 애완동물 동반 입실은 불가합니다.",
            reviews = listOf(
                Review("강남스타일", 5.0, "뷰가 정말 최고입니다. 기념일에 방문했는데 잊지 못할 경험이었어요.", "2025.09.03")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = true,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 4,
            name = "포시즌스 호텔 서울",
            rating = 4.7,
            reviewCount = 3300,
            price = 600000,
            imageUrl = "https://picsum.photos/seed/4/400",
            category = "호텔",
            address = "서울 종로구 새문안로 97",
            isSpecialPrice = true,
            coordinateY = 37.5710,
            coordinateX = 126.9760,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.LocalBar, "바")
            ),
            checkInTime = "15:00", checkOutTime = "12:00",
            notice = "광화문에 위치하여 접근성이 매우 뛰어납니다. 일부 객실에서는 경복궁 뷰를 감상할 수 있습니다.",
            usageInfo = "• 객실 내 미니바는 유료입니다.\n• 모든 실내 공간은 금연입니다.",
            reviews = listOf(
                Review("이종로", 5.0, "가족 여행으로 방문했는데 키즈 라운지가 있어서 아이가 정말 좋아했어요.", "2025.08.22")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = false,
            hasOceanView = false,
            hasCityView = true,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 5,
            name = "시그니엘 서울",
            rating = 4.7,
            reviewCount = 3400,
            price = 700000,
            imageUrl = "https://picsum.photos/seed/5/400",
            category = "호텔",
            address = "서울 송파구 올림픽로 300",
            isSpecialPrice = false,
            coordinateY = 37.5125,
            coordinateX = 127.1031,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.RoomService, "룸서비스"),
                Facility(Icons.Default.LocalBar, "바")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "롯데월드타워 76-101층에 위치하여 환상적인 서울의 스카이라인을 조망할 수 있습니다.",
            usageInfo = "• 투숙객 전용 라운지 '살롱 드 시그니엘'을 무료로 이용할 수 있습니다.\n• 주차는 롯데월드몰 주차장을 이용합니다.",
            reviews = listOf(
                Review("김송파", 5.0, "프로포즈를 위해 예약했는데, 야경이 정말 압도적이었습니다. 최고의 하루였어요.", "2025.09.01")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = true,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 6,
            name = "가평 하늘정원 펜션",
            rating = 4.8,
            reviewCount = 1250,
            price = 180000,
            imageUrl = "https://picsum.photos/seed/6/400",
            category = "펜션",
            address = "경기 가평군 북면",
            isSpecialPrice = true,
            coordinateY = 37.8222,
            coordinateX = 127.5222,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.OutdoorGrill, "바베큐"),
                Facility(Icons.Default.Kitchen, "주방"),
                Facility(Icons.Default.Pool, "개별수영장"),
                Facility(Icons.Default.Deck, "테라스"),
                Facility(Icons.Default.SmokeFree, "금연객실"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "바베큐 이용 시 2만원의 추가 요금이 발생합니다. (숯, 그릴 제공)",
            usageInfo = "• 늦은 시간 고성방가는 다른 손님들을 위해 자제 부탁드립니다.\n• 애완동물 입실은 불가합니다.",
            reviews = listOf(
                Review("김가평", 5.0, "사장님도 친절하시고 방도 깨끗해서 잘 쉬다 갑니다!", "2025.08.15"),
                Review("최커플", 5.0, "개별 수영장이 있어서 프라이빗하고 너무 좋았어요. 또 오고 싶어요.", "2025.08.11")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = false,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = true,
            isAvailable = true
        ),
        // == 부산 ==
        Accommodation(
            id = 11,
            name = "파크 하얏트 부산",
            rating = 4.6,
            reviewCount = 4200,
            price = 500000,
            imageUrl = "https://picsum.photos/seed/11/400",
            category = "호텔",
            address = "부산 해운대구 마린시티1로 51",
            isSpecialPrice = true,
            coordinateY = 35.1565,
            coordinateX = 129.1419,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.LocalBar, "바"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "광안대교 뷰 객실은 조기 마감될 수 있습니다. 실내 수영장에서 멋진 뷰를 즐겨보세요.",
            usageInfo = "• 객실 내에서는 금연입니다.\n• 주차는 투숙객에 한해 무료로 제공됩니다.",
            reviews = listOf(Review("이부산", 5.0, "광안대교 뷰가 정말 환상적입니다. 인생샷 건졌어요!", "2025.09.02")),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 12,
            name = "시그니엘 부산",
            rating = 4.7,
            reviewCount = 3600,
            price = 650000,
            imageUrl = "https://picsum.photos/seed/12/400",
            category = "호텔",
            address = "부산 해운대구 달맞이길 30",
            isSpecialPrice = false,
            coordinateY = 35.1614,
            coordinateX = 129.1764,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "인피니티풀"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.RoomService, "룸서비스")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "해운대 해변이 내려다보이는 인피니티 풀이 유명합니다. 날씨에 따라 운영이 제한될 수 있습니다.",
            usageInfo = "• 투숙객은 사우나(건식, 습식)를 무료로 이용할 수 있습니다.\n• 전 객실 금연입니다.",
            reviews = listOf(
                Review("박해운대", 5.0, "인피니티 풀 하나만으로도 올 가치가 충분합니다. 뷰가 정말 최고예요.", "2025.08.18")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 13,
            name = "힐튼 부산",
            rating = 4.6,
            reviewCount = 7300,
            price = 480000,
            imageUrl = "https://picsum.photos/seed/13/400",
            category = "리조트",
            address = "부산 기장군 기장읍 기장해안로 268-32",
            isSpecialPrice = true,
            coordinateY = 35.1918,
            coordinateX = 129.2223,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "인피니티풀"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Book, "서점"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.Water, "워터하우스")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "리조트 내 '이터널 저니' 서점은 투숙객이 아니어도 이용 가능합니다. 워터하우스는 유료 시설입니다.",
            usageInfo = "• 모든 객실에 프라이빗 발코니가 있습니다.\n• 반려동물 동반은 불가합니다.",
            reviews = listOf(
                Review("김기장", 5.0, "가족 여행으로 완벽한 곳입니다. 아이들이 놀 거리가 많아서 좋았어요.", "2025.09.04")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        // == 제주 ==
        Accommodation(
            id = 15,
            name = "호텔 신라 제주",
            rating = 4.6,
            reviewCount = 8500,
            price = 500000,
            imageUrl = "https://picsum.photos/seed/15/400",
            category = "호텔",
            address = "제주 서귀포시 중문관광로72번길 75",
            isSpecialPrice = false,
            coordinateY = 33.2487,
            coordinateX = 126.4069,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "야외수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.Yard, "정원")
            ),
            checkInTime = "14:00", checkOutTime = "11:00",
            notice = "성인풀과 패밀리풀이 분리되어 있어 쾌적한 이용이 가능합니다. G.A.O. 프로그램을 통해 다양한 체험을 즐겨보세요.",
            usageInfo = "• 공항 셔틀버스를 운행합니다. (사전 예약 필수)\n• 모든 객실은 금연입니다.",
            reviews = listOf(
                Review("박서귀포", 5.0, "오래되었지만 관리가 정말 잘 되어있고, 직원들의 서비스가 최고 수준입니다. 정원이 아름다워요.", "2025.08.30")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 16,
            name = "롯데호텔 제주",
            rating = 4.5,
            reviewCount = 9200,
            price = 420000,
            imageUrl = "https://picsum.photos/seed/16/400",
            category = "호텔",
            address = "제주 서귀포시 중문관광로72번길 35",
            isSpecialPrice = true,
            coordinateY = 33.2464,
            coordinateX = 126.4101,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "야외수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Casino, "카지노"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.Yard, "정원")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "야외 수영장 '해온'은 사계절 온수풀로 운영됩니다. 화산 분수쇼는 저녁 8시 30분에 시작됩니다.",
            usageInfo = "• 헬로키티 캐릭터 룸이 있어 아이들에게 인기가 많습니다.\n• 반려동물 동반은 불가합니다.",
            reviews = listOf(
                Review("최중문", 5.0, "아이들과 함께 가기 최고의 호텔입니다. 수영장에서 하루 종일 놀았어요.", "2025.08.25")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 17,
            name = "제주 돌담집",
            rating = 4.9,
            reviewCount = 850,
            price = 250000,
            imageUrl = "https://picsum.photos/seed/17/400",
            category = "펜션",
            address = "제주 제주시 구좌읍",
            isSpecialPrice = false,
            coordinateY = 33.5187,
            coordinateX = 126.8392,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Kitchen, "주방"),
                Facility(Icons.Default.OutdoorGrill, "바베큐"),
                Facility(Icons.Default.Bathtub, "욕조"),
                Facility(Icons.Default.SmokeFree, "금연객실"),
                Facility(Icons.Default.Yard, "정원"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "16:00", checkOutTime = "11:00",
            notice = "제주 전통 돌담집을 개조한 독채 펜션입니다. 벌레가 나올 수 있습니다.",
            usageInfo = "• 조용한 마을에 위치해 있어 밤 10시 이후 소음 발생을 자제해주세요.\n• 바베큐는 지정된 장소에서만 이용 가능합니다.",
            reviews = listOf(Review("박제주", 5.0, "고즈넉하고 정말 좋았어요. 힐링 그 자체였습니다.", "2025.08.20")),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = false,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        // == 강원 ==
        Accommodation(
            id = 20,
            name = "씨마크 호텔 강릉",
            rating = 4.6,
            reviewCount = 2800,
            price = 600000,
            imageUrl = "https://picsum.photos/seed/20/400",
            category = "호텔",
            address = "강원 강릉시 해안로406번길 2",
            isSpecialPrice = false,
            coordinateY = 37.7911,
            coordinateX = 128.9221,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "인피니티풀"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Spa, "스파"),
                Facility(Icons.Default.RoomService, "룸서비스"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "12:00",
            notice = "경포 해변에 위치하여 멋진 오션 뷰를 자랑합니다. 인피니티 풀 '비치 온 더 클라우드'가 유명합니다.",
            usageInfo = "• 투숙객은 사우나를 무료로 이용할 수 있습니다.\n• 전 객실 금연입니다.",
            reviews = listOf(
                Review("김강릉", 5.0, "국내 최고의 호텔 중 하나라고 생각합니다. 뷰, 시설, 서비스 모두 완벽했어요.", "2025.09.08")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 21,
            name = "스카이베이 호텔 경포",
            rating = 4.3,
            reviewCount = 11000,
            price = 250000,
            imageUrl = "https://picsum.photos/seed/21/400",
            category = "호텔",
            address = "강원 강릉시 해안로 476",
            isSpecialPrice = true,
            coordinateY = 37.8010,
            coordinateX = 128.8993,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "인피니티풀"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Store, "편의점"),
                Facility(Icons.Default.AcUnit, "에어컨"),
                Facility(Icons.Default.RoomService, "룸서비스")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "경포 호수와 경포 해변 사이에 위치하여 양쪽 뷰를 모두 즐길 수 있습니다. 20층 인피니티 풀이 상징적입니다.",
            usageInfo = "• 인피니티 풀은 유료 시설입니다.\n• 주차 공간이 협소할 수 있으니 참고 바랍니다.",
            reviews = listOf(Review("이경포", 4.0, "가성비 좋은 인피니티 풀 호텔입니다. 뷰는 정말 좋았어요.", "2025.08.15")),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = false,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 22,
            name = "켄싱턴호텔 설악",
            rating = 4.4,
            reviewCount = 3800,
            price = 200000,
            imageUrl = "https://picsum.photos/seed/22/400",
            category = "호텔",
            address = "강원 속초시 설악산로 998",
            isSpecialPrice = false,
            coordinateY = 38.1915,
            coordinateX = 128.4878,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Book, "라이브러리"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.Yard, "정원"),
                Facility(Icons.Default.AcUnit, "에어컨"),
                Facility(Icons.Default.Luggage, "짐보관")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "설악산 국립공원 입구에 위치하여 등산객에게 최적의 숙소입니다. 영국 테마의 인테리어가 특징입니다.",
            usageInfo = "• 객실에서 설악산의 아름다운 풍경을 감상할 수 있습니다.\n• 반려동물 동반 입실은 불가합니다.",
            reviews = listOf(
                Review("박설악", 5.0, "설악산 등반하고 묵었는데 위치가 정말 최고였습니다. 고풍스러운 분위기도 좋았어요.", "2025.09.07")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = true,
            isAvailable = true
        ),
        // == 경주 ==
        Accommodation(
            id = 23,
            name = "라한셀렉트 경주",
            rating = 4.5,
            reviewCount = 4900,
            price = 300000,
            imageUrl = "https://picsum.photos/seed/23/400",
            category = "호텔",
            address = "경북 경주시 보문로 338",
            isSpecialPrice = true,
            coordinateY = 35.8398,
            coordinateX = 129.2818,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "야외수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.Book, "북카페"),
                Facility(Icons.Default.FamilyRestroom, "키즈 라운지"),
                Facility(Icons.Default.Store, "편의점")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "보문호수 바로 앞에 위치하고 있습니다. 리모델링하여 시설이 매우 깨끗하고 현대적입니다.",
            usageInfo = "• 투숙객은 수영장을 무료로 이용할 수 있습니다.\n• 전 객실 금연입니다.",
            reviews = listOf(
                Review("김경주", 5.0, "리모델링해서 정말 좋아졌네요. 아이들과 함께 호캉스하기 최고의 장소입니다.", "2025.08.29")
            ),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 24,
            name = "황리단길 한옥 스테이",
            rating = 4.8,
            reviewCount = 980,
            price = 190000,
            imageUrl = "https://picsum.photos/seed/24/400",
            category = "게스트하우스",
            address = "경북 경주시 포석로 1080",
            isSpecialPrice = false,
            coordinateY = 35.8353,
            coordinateX = 129.2124,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.FreeBreakfast, "조식제공"),
                Facility(Icons.Default.Kitchen, "공용주방"),
                Facility(Icons.Default.Luggage, "짐보관"),
                Facility(Icons.Default.SmokeFree, "금연객실"),
                Facility(Icons.Default.AcUnit, "에어컨"),
                Facility(Icons.Default.Yard, "마당"),
                Facility(Icons.Default.LocalLaundryService, "세탁기")
            ),
            checkInTime = "16:00", checkOutTime = "11:00",
            notice = "황리단길 중심에 위치하여 관광하기에 매우 편리합니다. 간단한 조식이 무료로 제공됩니다.",
            usageInfo = "• 객실 내 취식은 금지되어 있습니다. (공용 주방 이용)\n• 한옥 특성상 방음이 취약할 수 있습니다.",
            reviews = listOf(Review("최경주", 5.0, "위치도 좋고 한옥의 정취를 느낄 수 있어서 정말 좋았습니다.", "2025.07.30")),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = false,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        // == 여수 ==
        Accommodation(
            id = 25,
            name = "소노캄 여수",
            rating = 4.5,
            reviewCount = 5100,
            price = 320000,
            imageUrl = "https://picsum.photos/seed/25/400",
            category = "리조트",
            address = "전남 여수시 오동도로 111",
            isSpecialPrice = false,
            coordinateY = 34.7391,
            coordinateX = 127.7505,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.HotTub, "사우나"),
                Facility(Icons.Default.Store, "편의점"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "여수 엑스포역과 가까워 KTX 이용객에게 편리합니다. 오동도와 아쿠아플라넷이 가깝습니다.",
            usageInfo = "• 전 객실 오션 뷰입니다.\n• 반려동물 동반 입실은 불가합니다.",
            reviews = listOf(Review("이여수", 5.0, "위치가 정말 환상적입니다. 룸에서 보는 바다 뷰가 최고였어요.", "2025.09.06")),
            // 필터칩 관련 속성 추가
            hasCoupon = false,
            isBreakfastIncluded = true,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        ),
        Accommodation(
            id = 26,
            name = "여수 베네치아 호텔&리조트",
            rating = 4.4,
            reviewCount = 4300,
            price = 220000,
            imageUrl = "https://picsum.photos/seed/26/400",
            category = "호텔",
            address = "전남 여수시 오동도로 61-13",
            isSpecialPrice = true,
            coordinateY = 34.7431,
            coordinateX = 127.7523,
            facilities = listOf(
                Facility(Icons.Default.Wifi, "무료 Wifi"),
                Facility(Icons.Default.LocalParking, "주차가능"),
                Facility(Icons.Default.Restaurant, "레스토랑"),
                Facility(Icons.Default.Pool, "루프탑수영장"),
                Facility(Icons.Default.FitnessCenter, "피트니스"),
                Facility(Icons.Default.FamilyRestroom, "키즈카페"),
                Facility(Icons.Default.Store, "편의점"),
                Facility(Icons.Default.AcUnit, "에어컨")
            ),
            checkInTime = "15:00", checkOutTime = "11:00",
            notice = "루프탑 수영장은 온수풀로 운영되며, 유료 시설입니다. 여수 해상 케이블카 탑승장과 가깝습니다.",
            usageInfo = "• 전 객실 금연입니다.\n• 주차장이 협소할 수 있어 만차 시 외부 주차장을 이용해야 할 수 있습니다.",
            reviews = listOf(Review("박관광", 4.0, "가성비가 좋은 호텔입니다. 관광지 접근성이 뛰어나요.", "2025.08.24")),
            // 필터칩 관련 속성 추가
            hasCoupon = true,
            isBreakfastIncluded = false,
            hasOceanView = true,
            hasCityView = false,
            hasMountainView = false,
            isAvailable = true
        )
    )

    val domesticRecentSearch = mutableListOf(
        RecentSearch(
            id = 1,
            keyword = "강릉 호텔",
            guest = 2,
            startDate = LocalDate.of(2025, 8, 1),
            endDate = LocalDate.of(2025, 8, 2)
        ),
        RecentSearch(
            id = 2,
            keyword = "서울 호텔",
            guest = 2,
            startDate = LocalDate.of(2025, 10, 9),
            endDate = LocalDate.of(2025, 10, 11)
        ),
        RecentSearch(
            id = 3,
            keyword = "부산 호텔",
            guest = 2,
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 6)
        ),
    )

    val overseasRecentSearch = mutableListOf(
        RecentSearch(
            id = 4,
            keyword = "도쿄, 일본",
            guest = 2,
            startDate = LocalDate.of(2025, 8, 1),
            endDate = LocalDate.of(2025, 8, 2)
        ),
        RecentSearch(
            id = 5,
            keyword = "방콕, 태국",
            guest = 2,
            startDate = LocalDate.of(2025, 10, 9),
            endDate = LocalDate.of(2025, 10, 11)
        ),
        RecentSearch(
            id = 6,
            keyword = "로스엔젤레스, 미국",
            guest = 2,
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 6)
        ),
    )
}