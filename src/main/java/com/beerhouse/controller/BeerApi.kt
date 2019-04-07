package com.beerhouse.controller

import java.net.URI

interface BeerApi {

    object EndPoint {
        const val PATH = "/beers"
    }

    object CreatedURI {

        @JvmStatic
        fun of(id: Int): URI {
            return URI.create(EndPoint.PATH + "/" + id)
        }

    }

    interface In {

        data class Beer(val name: String,
                        val ingredients: String,
                        val alcoholContent: String,
                        val price: Long,
                        val category: String)

        data class PatchBeer(val name: String?,
                             val ingredients: String?,
                             val alcoholContent: String?,
                             val price: Long?,
                             val category: String?)

    }

    interface Out {

        data class Beer(val id: Int,
                        val name: String,
                        val ingredients: String,
                        val alcoholContent: String,
                        val price: Long,
                        val category: String)

    }
}