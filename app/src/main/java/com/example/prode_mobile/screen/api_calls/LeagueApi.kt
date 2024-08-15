package com.example.prode_mobile.screen.api_calls

class LeagueApi : ApiClient() {
    val service: LeagueService = retrofit.create(LeagueService::class.java)
}
