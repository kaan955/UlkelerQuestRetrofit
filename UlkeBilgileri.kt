package com.ulkeler.titanium.kaanb.ulkeler

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class UlkeBilgileri {

    @Expose
    @SerializedName("cioc")
    var cioc: String? = null
    @Expose
    @SerializedName("flag")
    var flag: String? = null
    @Expose
    @SerializedName("translations")
    var translations: Translations? = null
    @Expose
    @SerializedName("languages")
    var languages: List<Languages>? = null
    @Expose
    @SerializedName("currencies")
    var currencies: List<Currencies>? = null
    @Expose
    @SerializedName("numericCode")
    var numericcode: String? = null
    @Expose
    @SerializedName("nativeName")
    var nativename: String? = null
    @Expose
    @SerializedName("borders")
    var borders: List<String>? = null
    @Expose
    @SerializedName("timezones")
    var timezones: List<String>? = null
    @Expose
    @SerializedName("gini")
    var gini: Double = 0.toDouble()
    @Expose
    @SerializedName("area")
    var area: Double = 0.toDouble()
    @Expose
    @SerializedName("demonym")
    var demonym: String? = null
    @Expose
    @SerializedName("latlng")
    var latlng: List<Double>? = null
    @Expose
    @SerializedName("population")
    var population: Int = 0
    @Expose
    @SerializedName("subregion")
    var subregion: String? = null
    @Expose
    @SerializedName("region")
    var region: String? = null
    @Expose
    @SerializedName("altSpellings")
    var altspellings: List<String>? = null
    @Expose
    @SerializedName("capital")
    var capital: String? = null
    @Expose
    @SerializedName("callingCodes")
    var callingcodes: List<String>? = null
    @Expose
    @SerializedName("alpha3Code")
    var alpha3code: String? = null
    @Expose
    @SerializedName("alpha2Code")
    var alpha2code: String? = null
    @Expose
    @SerializedName("topLevelDomain")
    var topleveldomain: List<String>? = null
    @Expose
    @SerializedName("name")
    var name: String? = null

    class Regionalblocs {
        @Expose
        @SerializedName("otherNames")
        var othernames: List<String>? = null
        @Expose
        @SerializedName("otherAcronyms")
        var otheracronyms: List<String>? = null
        @Expose
        @SerializedName("name")
        var name: String? = null
        @Expose
        @SerializedName("acronym")
        var acronym: String? = null
    }

    class Translations {
        @Expose
        @SerializedName("fa")
        var fa: String? = null
        @Expose
        @SerializedName("hr")
        var hr: String? = null
        @Expose
        @SerializedName("nl")
        var nl: String? = null
        @Expose
        @SerializedName("pt")
        var pt: String? = null
        @Expose
        @SerializedName("br")
        var br: String? = null
        @Expose
        @SerializedName("it")
        var it: String? = null
        @Expose
        @SerializedName("ja")
        var ja: String? = null
        @Expose
        @SerializedName("fr")
        var fr: String? = null
        @Expose
        @SerializedName("es")
        var es: String? = null
        @Expose
        @SerializedName("de")
        var de: String? = null
    }

    class Languages {
        @Expose
        @SerializedName("nativeName")
        var nativename: String? = null
        @Expose
        @SerializedName("name")
        var name: String? = null
        @Expose
        @SerializedName("iso639_2")
        var iso6392: String? = null
        @Expose
        @SerializedName("iso639_1")
        var iso6391: String? = null
    }

    class Currencies {
        @Expose
        @SerializedName("symbol")
        var symbol: String? = null
        @Expose
        @SerializedName("name")
        var name: String? = null
        @Expose
        @SerializedName("code")
        var code: String? = null
    }
}
