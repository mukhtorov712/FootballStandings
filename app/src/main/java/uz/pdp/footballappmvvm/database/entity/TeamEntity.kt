package uz.pdp.footballappmvvm.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val standingId: Int?,
    val position: Int,
    val name: String,
    val league: String,
    val country: String,
    val crestUrl: String,
    val draw: Int,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val won: Int
)
