package uz.pdp.footballappmvvm.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MatchEntity(
    @PrimaryKey
    val id: Int,
    val leagueId:Int,
    val matchDay: Int,
    val status: String,
    val date: String,
    val homeName: String,
    val awayName: String,
    val homeId: Int,
    val awayId: Int,
    val homeScore: Int,
    val awayScore: Int
)
