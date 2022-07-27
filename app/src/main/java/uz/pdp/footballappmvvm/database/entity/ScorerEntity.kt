package uz.pdp.footballappmvvm.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScorerEntity(
    @PrimaryKey
    val id: Int,
    val competitionId: Int,
    val teamId: Int,
    val name: String,
    val teamName: String,
    val goals: Int

)