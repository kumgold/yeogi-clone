package com.example.yeogi.feature.home.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FlightClass
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.SingleBed
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Umbrella
import com.example.yeogi.feature.home.data.local.ServiceCategory

class HomeRepository {
    private val dummyServiceCategory = listOf(
        ServiceCategory("호텔•리조트", Icons.Default.Business),
        ServiceCategory("모텔", Icons.Default.KingBed),
        ServiceCategory("펜션•풀빌라", Icons.Default.Umbrella),
        ServiceCategory("캠핑•글램핑", Icons.Default.Terrain),
        ServiceCategory("홈•빌라", Icons.Default.Home),
        ServiceCategory("게하•한옥", Icons.Default.SingleBed),
        ServiceCategory("레저티켓•공간", Icons.Default.LocalAtm),
        ServiceCategory("렌터카", Icons.Default.DirectionsCar),
        ServiceCategory("해외 숙소", Icons.Default.Kayaking),
        ServiceCategory("패키지 여행", Icons.Default.FlightClass),
        ServiceCategory("항공+숙소", Icons.Default.FlightLand),
        ServiceCategory("항공", Icons.Default.Flight),
    )

    fun getServiceCategories(): List<ServiceCategory> {
        return dummyServiceCategory
    }
}