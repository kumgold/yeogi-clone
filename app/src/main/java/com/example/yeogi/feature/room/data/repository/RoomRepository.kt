package com.example.yeogi.feature.room.data.repository

import com.example.yeogi.feature.room.data.remote.Room

class RoomRepository {
    private val dummyRoomList = listOf(
        Room(
            id = 1,
            accommodationId = 101,
            name = "스탠다드 더블룸",
            price = 120000,
            originalPrice = 150000,
            discountRate = "20%",
            imageUrl = "https://picsum.photos/seed/picsum/400/300",
            capacity = "기준 2인 / 최대 2인",
            features = listOf("시티뷰", "무료 Wi-Fi", "넷플릭스 이용가능"),
            bookingStatus = "예약가능"
        ),
        Room(
            id = 2,
            accommodationId = 101,
            name = "디럭스 오션뷰 트윈",
            price = 185000,
            originalPrice = 200000,
            discountRate = "7.5%",
            imageUrl = "https://picsum.photos/seed/fast/400/300",
            capacity = "기준 2인 / 최대 3인",
            features = listOf("오션뷰", "욕조", "테라스", "무료 Wi-Fi"),
            bookingStatus = "마감임박"
        ),
        Room(
            id = 3,
            accommodationId = 101,
            name = "프리미어 스위트",
            price = 290000,
            originalPrice = 350000,
            discountRate = "17%",
            imageUrl = "https://picsum.photos/seed/hotel/400/300",
            capacity = "기준 2인 / 최대 4인",
            features = listOf("오션뷰", "거실 분리형", "조식 포함", "넷플릭스 이용가능"),
            bookingStatus = "예약가능"
        ),
        Room(
            id = 4,
            accommodationId = 102,
            name = "패밀리 온돌룸",
            price = 210000,
            originalPrice = 210000,
            discountRate = "0%",
            imageUrl = "https://picsum.photos/seed/room/400/300",
            capacity = "기준 4인 / 최대 6인",
            features = listOf("마운틴뷰", "취사 가능", "무료 Wi-Fi"),
            bookingStatus = "예약가능"
        ),
        Room(
            id = 5,
            accommodationId = 102,
            name = "가든뷰 싱글룸",
            price = 99000,
            originalPrice = 110000,
            discountRate = "10%",
            imageUrl = "https://picsum.photos/seed/view/400/300",
            capacity = "기준 1인 / 최대 1인",
            features = listOf("가든뷰", "1인 조식 포함"),
            bookingStatus = "마감임박"
        )
    )

    fun getRoomList(accommodationId: Int): List<Room> {
        /**
         * accommodation id로 해당 숙소의 객실을 불러온다.
         */
        return dummyRoomList
    }
}