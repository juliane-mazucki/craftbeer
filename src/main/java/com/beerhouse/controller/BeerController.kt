package com.beerhouse.controller

import com.beerhouse.controller.BeerConverter.Companion.convertToGet
import com.beerhouse.controller.BeerConverter.Companion.convertToGetAll
import com.beerhouse.controller.BeerConverter.Companion.convertToPatch
import com.beerhouse.controller.BeerConverter.Companion.convertToPost
import com.beerhouse.controller.BeerConverter.Companion.convertToPut
import com.beerhouse.service.BeerAlreadyExistsException
import com.beerhouse.service.BeerNotFoundException
import com.beerhouse.service.BeerService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [BeerApi.EndPoint.PATH])
class BeerController @Autowired constructor(private val service: BeerService) {

    companion object {

        private val log = KotlinLogging.logger { }
    }

    @RequestMapping
    fun getAll(): ResponseEntity<List<BeerApi.Out.Beer>> {

        val beers = service.findAll()

        return ResponseEntity.ok(convertToGetAll(beers))
    }

    @RequestMapping(path = ["/{id}"])
    fun get(@PathVariable("id") id: Int): ResponseEntity<BeerApi.Out.Beer> {

        return try {
            val beer = service.findById(id)
            ResponseEntity.ok(convertToGet(beer))
        } catch (e: BeerNotFoundException) {
            log.error { e.message }
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun post(@RequestBody beer: BeerApi.In.Beer): ResponseEntity<Void> {

        return try {
            val savedId = service.insert(convertToPost(beer))
            ResponseEntity.created(BeerApi.CreatedURI.of(savedId)).build()
        } catch (e: BeerAlreadyExistsException) {
            log.error { e.message }
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PutMapping(path = ["/{id}"])
    fun put(@PathVariable("id") id: Int, @RequestBody beer: BeerApi.In.Beer): ResponseEntity<Void> {

        return try {
            service.update(convertToPut(id, beer))

            ResponseEntity.noContent().build()
        } catch (e: BeerNotFoundException) {
            log.error { e.message }
            ResponseEntity.notFound().build()
        } catch (e: BeerAlreadyExistsException) {
            log.error { e.message }
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PatchMapping(path = ["/{id}"])
    fun patch(@PathVariable("id") id: Int, @RequestBody beer: BeerApi.In.PatchBeer): ResponseEntity<Void> {

        return try {
            val originalBeer = service.findById(id)
            service.update(convertToPatch(originalBeer, beer))

            ResponseEntity.noContent().build()
        } catch (e: BeerNotFoundException) {
            log.error { e.message }
            ResponseEntity.notFound().build()
        } catch (e: BeerAlreadyExistsException) {
            log.error { e.message }
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Void> {

        return try {
            service.deleteById(id)
            ResponseEntity.noContent().build()
        } catch (e: BeerNotFoundException) {
            log.error { e.message }
            ResponseEntity.notFound().build()
        }
    }

}