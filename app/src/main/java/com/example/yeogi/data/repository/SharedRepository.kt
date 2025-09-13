package com.example.yeogi.data.repository

import com.example.yeogi.data.Accommodation
import com.example.yeogi.data.RecentSearch
import com.example.yeogi.data.ServiceCategory
import com.example.yeogi.data.dummyAccommodations
import com.example.yeogi.data.dummyDomesticRecentSearch
import com.example.yeogi.data.dummyOverseasRecentSearch
import com.example.yeogi.data.dummyServiceCategory

class SharedRepository {
    fun getServiceCategory(): List<ServiceCategory> {
        return dummyServiceCategory
    }

    fun getAccommodations(): List<Accommodation> {
        return dummyAccommodations
    }

    fun getDomesticRecentSearch(): List<RecentSearch> {
        return dummyDomesticRecentSearch
    }

    fun getOverseasRecentSearch(): List<RecentSearch> {
        return dummyOverseasRecentSearch
    }
}