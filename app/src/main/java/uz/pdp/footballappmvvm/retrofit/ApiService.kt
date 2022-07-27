package uz.pdp.footballappmvvm.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.pdp.footballappmvvm.models.match.MatchData
import uz.pdp.footballappmvvm.models.scorer.ScorerData
import uz.pdp.footballappmvvm.models.standings.StandingsData

interface ApiService {
    @GET("competitions/{Id}/standings")
    suspend fun getStandingsById(
        @Path("Id") id: Int
    ): Response<StandingsData>

    @GET("competitions/{Id}/matches")
    suspend fun getMatchById(
        @Path("Id") id: Int,
        @Query("status") status:String = "FINISHED"
    ): Response<MatchData>

    @GET("competitions/{Id}/scorers")
    suspend fun getScorer(
        @Path("Id") competitionId: Int,
        @Query("limit") limit: Int = 15
    ): Response<ScorerData>
}