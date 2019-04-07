package com.beerhouse.service

class BeerNotFoundException(id: Int) : IllegalStateException("Beer with id $id not exists.")

class BeerAlreadyExistsException(name: String) : IllegalStateException("Beer with name $name already exists.")