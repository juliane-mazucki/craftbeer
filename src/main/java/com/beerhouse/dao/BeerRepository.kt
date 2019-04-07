package com.beerhouse.dao

import org.springframework.data.repository.CrudRepository

interface BeerRepository : CrudRepository<Beer, Int> {

    fun findByName(name: String): Beer?

}