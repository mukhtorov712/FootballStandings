package uz.pdp.footballappmvvm.models.custom

import uz.pdp.footballappmvvm.database.entity.MatchEntity

data class TourData(
    val tourNumber: Int,
    val matchList: List<MatchEntity>
)
