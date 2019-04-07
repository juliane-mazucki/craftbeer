package com.beerhouse.service

import com.beerhouse.dao.Beer
import com.beerhouse.dao.BeerRepository
import com.beerhouse.fixture.aBeer
import com.google.common.collect.ImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import java.security.SecureRandom
import java.util.Optional.of

class BeerServiceTest {

    private lateinit var repository: BeerRepository
    private lateinit var service: BeerService

    @Before
    fun setUp() {
        repository = mock(BeerRepository::class.java)
        service = BeerServiceImpl(repository)
    }

    @Test
    fun `when find all beers and there are no beers it should return empty`() {
        val beers = service.findAll()
        assertThat(beers).isEmpty()
    }

    @Test
    fun `when find all beers there are beers it should return all of them`() {
        val beer1 = aBeer()
        val beer2 = aBeer()
        val expected = ImmutableList.of(beer1, beer2)
        `when`(repository.findAll()).thenReturn(expected)

        val beers = service.findAll()
        assertThat(beers).hasSize(expected.size).containsAll(expected)
    }

    @Test(expected = BeerNotFoundException::class)
    fun `when find beer by id and there is no beer with the informed id it should throw BeerNotFoundException`() {
        service.findById(SecureRandom().nextInt())
    }

    @Test
    fun `when find beer by id and there is a beer with the informed id it should be returned`() {
        val expected = aBeer()
        `when`(repository.findById(expected.id)).thenReturn(of(expected))

        val beer = service.findById(expected.id)
        assertThat(beer).isEqualTo(expected)
    }

    @Test(expected = BeerAlreadyExistsException::class)
    fun `when insert a new beer and already exists a beer with the informed name it should throw BeerAlreadyExistsException`() {
        `when`(repository.findByName(anyString())).thenReturn(aBeer())

        service.insert(aBeer())
    }

    @Test
    fun `when insert a new beer and there is no beer with the informed name it should insert and return the generated id`() {
        `when`(repository.findByName(anyString())).thenReturn(null)
        val expected = aBeer()
        `when`(repository.save(any(Beer::class.java))).thenReturn(expected)

        val generatedId = service.insert(aBeer())
        assertThat(generatedId).isEqualTo(expected.id)
    }

    @Test(expected = BeerNotFoundException::class)
    fun `when update a beer and there is no beer with the informed id it should throw BeerNotFoundException`() {
        `when`(repository.existsById(anyInt())).thenReturn(false)

        service.update(aBeer())
    }

    @Test(expected = BeerAlreadyExistsException::class)
    fun `when update a beer and already exists a beer with the informed name and different id it should throw BeerAlreadyExistsException`() {
        `when`(repository.existsById(anyInt())).thenReturn(true)
        val existentBeer = aBeer()
        `when`(repository.findByName(anyString())).thenReturn(existentBeer)

        service.update(aBeer(existentBeer.id + 1))
    }

    @Test
    fun `when update a beer and already exists a beer with the informed name and same id it should update`() {
        `when`(repository.existsById(anyInt())).thenReturn(true)
        val existentBeer = aBeer()
        `when`(repository.findByName(anyString())).thenReturn(existentBeer)

        service.update(aBeer(existentBeer.id))
    }

    @Test
    fun `when update a beer and there is no beer with with the informed name it should update`() {
        `when`(repository.existsById(anyInt())).thenReturn(true)
        `when`(repository.findByName(anyString())).thenReturn(null)

        service.update(aBeer())
    }

    @Test(expected = BeerNotFoundException::class)
    fun `when delete and there is no beer with the informed id it should throw BeerNotFoundException`() {
        `when`(repository.existsById(anyInt())).thenReturn(false)

        service.deleteById(SecureRandom().nextInt())
    }

    @Test
    fun `when delete and there is a beer with the informed id it should delete`() {
        `when`(repository.existsById(anyInt())).thenReturn(true)

        service.deleteById(SecureRandom().nextInt())
    }
}