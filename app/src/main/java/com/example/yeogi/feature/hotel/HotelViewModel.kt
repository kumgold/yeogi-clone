package com.example.yeogi.feature.hotel

import androidx.compose.runtime.remember
import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import com.example.yeogi.feature.hotel.data.HotelCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HotelUiState(
    val categories: List<HotelCategory> = emptyList(),
    val regions: Map<String, List<String>> = emptyMap(),
    val popularHotels: List<Accommodation> = emptyList(),
    val premiumHotels: List<Accommodation> = emptyList()
)

class HotelViewModel : SharedViewModel() {
    private val _uiState = MutableStateFlow(HotelUiState())
    val uiState: StateFlow<HotelUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = listOf(
                HotelCategory("전체", "https://images.unsplash.com/photo-1566073771259-6a8506099945"),
                HotelCategory("5성급", "https://images.unsplash.com/photo-1542314831-068cd1dbb5eb"),
                HotelCategory("리조트", "https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9"),
                HotelCategory("가성비", "https://images.unsplash.com/photo-1590490359854-dfba5d78b13d"),
                HotelCategory("호캉스", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b"),
                HotelCategory("반려견", "https://images.unsplash.com/photo-1558788353-f76d92427f16"),
                HotelCategory("신규", "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4")
            )
            val regions = mapOf(
                "서울" to listOf("서울 전체", "강남/역삼/삼성", "서초/교대", "신사/청담", "송파/잠실", "여의도/영등포", "홍대/합정/마포", "종로/을지로", "명동/이태원", "동대문", "성수/건대"),
                "경기" to listOf("경기 전체", "수원", "용인", "성남/분당", "고양/일산", "화성/동탄", "가평/양평", "파주", "의정부", "안양/과천"),
                "인천" to listOf("인천 전체", "영종도/인천공항", "송도", "강화도", "을왕리", "월미도"),
                "강원" to listOf("강원 전체", "강릉", "속초", "양양", "춘천/홍천", "평창", "정선", "동해/삼척"),
                "부산" to listOf("부산 전체", "해운대", "광안리", "서면/부산역", "기장", "남포동/송도"),
                "제주" to listOf("제주 전체", "제주시", "서귀포시", "애월/한림", "성산/우도", "표선/남원"),
                "여수" to listOf("여수 전체", "돌산읍", "화양면", "시내중심"),
                "경주" to listOf("경주 전체", "보문단지", "황리단길/시내", "불국사", "양남/감포"),
                "전주" to listOf("전주 전체", "한옥마을 주변", "시내중심", "덕진공원")
            )

            _uiState.update { currentState ->
                currentState.copy(
                    categories = categories,
                    regions = regions,
                    popularHotels = getAccommodations().shuffled().subList(0, 4),
                    premiumHotels = getAccommodations().shuffled().subList(0, 4)
                )
            }
        }
    }
}