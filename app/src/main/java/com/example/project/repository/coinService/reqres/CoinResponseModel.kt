package com.example.project.repository.coinService.reqres

import com.google.gson.annotations.SerializedName


data class Coin(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("is_new") var isNew: Boolean? = null,
    @SerializedName("is_active") var isActive: Boolean? = null,
    @SerializedName("type") var type: String? = null
)

data class CoinDetail(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("is_new") var isNew: Boolean? = null,
    @SerializedName("is_active") var isActive: Boolean? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("tags") var tags: ArrayList<Tags> = arrayListOf(),
    @SerializedName("team") var team: ArrayList<Team> = arrayListOf(),
    @SerializedName("description") var description: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("open_source") var openSource: Boolean? = null,
    @SerializedName("started_at") var startedAt: String? = null,
    @SerializedName("development_status") var developmentStatus: String? = null,
    @SerializedName("hardware_wallet") var hardwareWallet: Boolean? = null,
    @SerializedName("proof_type") var proofType: String? = null,
    @SerializedName("org_structure") var orgStructure: String? = null,
    @SerializedName("hash_algorithm") var hashAlgorithm: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("links_extended") var linksExtended: ArrayList<LinksExtended> = arrayListOf(),
    @SerializedName("whitepaper") var whitepaper: Whitepaper? = Whitepaper(),
    @SerializedName("first_data_at") var firstDataAt: String? = null,
    @SerializedName("last_data_at") var lastDataAt: String? = null

)

data class Tags(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("coin_counter") var coinCounter: Long? = null,
    @SerializedName("ico_counter") var icoCounter: Long? = null

)

data class Team(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("position") var position: String? = null

)

data class Links(

    @SerializedName("explorer") var explorer: ArrayList<String> = arrayListOf(),
    @SerializedName("facebook") var facebook: ArrayList<String> = arrayListOf(),
    @SerializedName("reddit") var reddit: ArrayList<String> = arrayListOf(),
    @SerializedName("source_code") var sourceCode: ArrayList<String> = arrayListOf(),
    @SerializedName("website") var website: ArrayList<String> = arrayListOf(),
    @SerializedName("youtube") var youtube: ArrayList<String> = arrayListOf()

)

data class LinksExtended(

    @SerializedName("url") var url: String? = null,
    @SerializedName("type") var type: String? = null

)

data class Whitepaper(

    @SerializedName("link") var link: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null

)

data class PriceModel(

    @SerializedName("time_open") var timeOpen: String? = null,
    @SerializedName("time_close") var timeClose: String? = null,
    @SerializedName("open") var open: Double? = null,
    @SerializedName("high") var high: Double? = null,
    @SerializedName("low") var low: Double? = null,
    @SerializedName("close") var close: Double? = null,
    @SerializedName("volume") var volume: Long? = null,
    @SerializedName("market_cap") var marketCap: Long? = null

)