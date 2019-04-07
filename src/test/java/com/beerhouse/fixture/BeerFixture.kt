package com.beerhouse.fixture

import com.beerhouse.dao.Beer
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import java.security.SecureRandom

fun aBeer(): Beer {
    return Beer(id = SecureRandom().nextInt(),
        name = randomAlphanumeric(10),
        ingredients = randomAlphanumeric(10),
        alcoholContent = randomAlphanumeric(10),
        price = SecureRandom().nextLong(),
        category = randomAlphanumeric(10))
}

fun aBeer(id: Int): Beer {
    return Beer(id = id,
        name = randomAlphanumeric(10),
        ingredients = randomAlphanumeric(10),
        alcoholContent = randomAlphanumeric(10),
        price = SecureRandom().nextLong(),
        category = randomAlphanumeric(10))
}