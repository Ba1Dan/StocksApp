package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.entity.FavouriteEntity

interface FavouriteRepository {

    suspend fun getStocks(): List<FavouriteEntity>

    suspend fun deleteStock(stock: FavouriteEntity)

    suspend fun insertStock(stock: FavouriteEntity)

    suspend fun getNames(): List<String>
}