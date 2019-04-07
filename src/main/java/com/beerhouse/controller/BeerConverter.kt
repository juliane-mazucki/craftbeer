package com.beerhouse.controller

import com.beerhouse.dao.Beer
import java.util.Optional

class BeerConverter {

    companion object {

        fun convertToPatch(originalBeer: Beer, beer: BeerApi.In.PatchBeer): Beer {
            return Beer(originalBeer.id,
                Optional.ofNullable(beer.name).orElse(originalBeer.name),
                Optional.ofNullable(beer.ingredients).orElse(originalBeer.ingredients),
                Optional.ofNullable(beer.alcoholContent).orElse(originalBeer.alcoholContent),
                Optional.ofNullable(beer.price).orElse(originalBeer.price),
                Optional.ofNullable(beer.category).orElse(originalBeer.category))
        }

        fun convertToPut(id: Int, beer: BeerApi.In.Beer): Beer {
            return Beer(id, beer.name, beer.ingredients, beer.alcoholContent, beer.price, beer.category)
        }

        fun convertToPost(beer: BeerApi.In.Beer): Beer {
            return Beer(name = beer.name,
                ingredients = beer.ingredients,
                alcoholContent = beer.alcoholContent,
                price = beer.price,
                category = beer.category)
        }

        fun convertToGetAll(beers: List<Beer>): List<BeerApi.Out.Beer> {
            return beers.map {
                convertToGet(it)
            }.sortedBy { it.name }
        }

        fun convertToGet(beer: Beer): BeerApi.Out.Beer {
            return BeerApi.Out.Beer(beer.id,
                beer.name,
                beer.ingredients,
                beer.alcoholContent,
                beer.price,
                beer.category)
        }
    }
}