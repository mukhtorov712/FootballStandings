package uz.pdp.footballappmvvm.models.standings

data class Standing(
    val group: Any,
    val stage: String,
    val table: List<Table>,
    val type: String
)