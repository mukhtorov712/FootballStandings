package uz.pdp.footballappmvvm.models.match

data class Matche(
    val awayTeam: AwayTeam,
    val homeTeam: HomeTeam,
    val id: Int,
    val matchday: Int,
    val score: Score,
    val season: Season,
    val status: String,
    val utcDate: String
)