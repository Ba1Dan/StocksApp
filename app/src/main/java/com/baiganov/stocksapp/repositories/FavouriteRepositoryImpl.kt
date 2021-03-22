package com.baiganov.stocksapp.repositories

import com.baiganov.stocksapp.data.entity.FavouriteEntity
import com.baiganov.stocksapp.db.FavouriteStockDao

class FavouriteRepositoryImpl(private val favouriteStockDao: FavouriteStockDao) : FavouriteRepository {

    override suspend fun getStocks(): List<FavouriteEntity> {
        return favouriteStockDao.getStocks()
    }



    override suspend fun deleteStock(stock: FavouriteEntity) {
        favouriteStockDao.delete(stock)
    }

    override suspend fun insertStock(stock: FavouriteEntity) {
        favouriteStockDao.insert(stock)
    }

    override suspend fun getNames(): List<String> {
        return favouriteStockDao.getNames()
    }
}