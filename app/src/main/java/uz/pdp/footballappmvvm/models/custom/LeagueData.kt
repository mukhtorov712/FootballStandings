package uz.pdp.footballappmvvm.models.custom

import uz.pdp.footballappmvvm.database.entity.TeamEntity
import java.io.Serializable

data class LeagueData(
    val id: Int,
    val name: String,
    val country: String,
    val code: String,
    val imageId: Int,
    var standingList: List<TeamEntity>? = null
) : Serializable