package com.beerhouse.dao

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Beer(@Id @GeneratedValue val id: Int = -1, val name: String = "",
                val ingredients: String = "",
                val alcoholContent: String = "",
                val price: Long = -1,
                val category: String = "")