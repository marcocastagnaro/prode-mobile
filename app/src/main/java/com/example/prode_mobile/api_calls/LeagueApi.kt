package com.example.prode_mobile.api_calls

class LeagueApi : ApiClient() {
    val service: LeagueService = retrofit.create(LeagueService::class.java)
}
