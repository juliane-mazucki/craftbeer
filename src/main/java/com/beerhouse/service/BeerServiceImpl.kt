package com.beerhouse.service

import com.beerhouse.dao.Beer
import com.beerhouse.dao.BeerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BeerServiceImpl @Autowired constructor(private val repository: BeerRepository) : BeerService {

    override fun findAll(): List<Beer> {
        return repository.findAll().toList()
    }

    override fun findById(id: Int): Beer {
        val beer = repository.findById(id)
        return beer.orElseThrow { BeerNotFoundException(id) }
    }

    override fun insert(beer: Beer): Int {

        val existentBeer = repository.findByName(beer.name)

        if (existentBeer != null) {
            throw BeerAlreadyExistsException(beer.name)
        }

        return repository.save(beer).id
    }

    override fun update(beer: Beer) {

        if (!repository.existsById(beer.id)) {
            throw BeerNotFoundException(beer.id)
        }

        val existentBeer = repository.findByName(beer.name)

        if (existentBeer != null && existentBeer.id != beer.id) {
            throw BeerAlreadyExistsException(beer.name)
        }

        repository.save(beer)
    }

    override fun deleteById(id: Int) {

        if (!repository.existsById(id)) {
            throw BeerNotFoundException(id)
        }

        repository.deleteById(id)
    }

}