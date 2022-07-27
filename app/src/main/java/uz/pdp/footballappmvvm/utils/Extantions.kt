package uz.pdp.footballappmvvm.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import uz.pdp.footballappmvvm.R
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*


fun ImageView.setImage(url: String) {
    Picasso.get().load(url).into(this)
}

fun ImageView.setCountyImage(code: String) {
    val url = "https://flagcdn.com/80x60/$code.png"
    Picasso.get().load(url).into(this)
}

fun ImageView.setLeagueImage(id: Int) {
    val url = "https://a.espncdn.com/i/leaguelogos/soccer/500-dark/$id.png"
    Picasso.get().load(url).into(this)
}

fun ImageView.setTeamImage(id: Int) {
    val url = "https://crests.football-data.org/$id.png"
    Picasso.get().load(url).into(this)
}

fun ImageView.loadSvg(url: String?) {
    GlideToVectorYou
        .init()
        .with(this.context)
        .load(Uri.parse(url), this)
}

fun ImageView.loadTeamSvg(id: Int) {
    val url = "https://crests.football-data.org/$id.svg"
    GlideToVectorYou
        .init()
        .with(this.context)
        .load(Uri.parse(url), this)
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

@SuppressLint("SimpleDateFormat")
fun getDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormat = SimpleDateFormat("dd.MM.")
    val date1: Date = inputFormat.parse(date)
    return outputFormat.format(date1)
}

fun navOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.enter)
        .setExitAnim(R.anim.exit)
        .setPopEnterAnim(R.anim.pop_enter)
        .setPopExitAnim(R.anim.pop_exit).build()
}