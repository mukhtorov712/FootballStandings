package uz.pdp.footballappmvvm.models.standings

data class StandingsData(
    val competition: Competition,
    val season: Season,
    val standings: List<Standing>
)