package com.example.wijaya_pc.eplmatchschedule.presenter

import com.example.wijaya_pc.eplmatchschedule.api.ApiRepository
import com.example.wijaya_pc.eplmatchschedule.api.TheSportDBApi
import com.example.wijaya_pc.eplmatchschedule.model.MatchResponse
import com.example.wijaya_pc.eplmatchschedule.model.TeamResponse
import com.example.wijaya_pc.eplmatchschedule.view.DetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(
    private val view: DetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    fun getMatchDetail(matchID: String?) {
        view.showLoading()

        async(UI) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getMatchDetail(matchID)),
                    MatchResponse::class.java
                )
            }
            view.getMatch(data.await().events[0])
            view.hideLoading()
        }

    }

    fun getTeamDetail(teamID: String?, homeTeam: Boolean) {
        view.showLoading()

        async(UI) {
            val data = bg {
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeam(teamID)),
                    TeamResponse::class.java
                )
            }
            view.getTeam(data.await().teams[0], homeTeam)
            view.hideLoading()
        }
    }
}