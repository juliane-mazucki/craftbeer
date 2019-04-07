package com.beerhouse.service

import com.beerhouse.dao.Beer

interface BeerService {

    fun findAll(): List<Beer>

    fun findById(id: Int): Beer

    fun insert(beer: Beer): Int

    fun update(beer: Beer)

    fun deleteById(id: Int)

}