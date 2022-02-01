package com.abdulrahman.contactlistapp

import androidx.paging.*
import com.abdulrahman.contactlistapp.data.entities.Coordinates
import com.abdulrahman.contactlistapp.data.entities.Location
import com.abdulrahman.contactlistapp.data.entities.Picture
import com.abdulrahman.contactlistapp.data.local.UserDBEntity

class PagingSourceUtils<T : Any>(
    private val data: List<T>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    }
}

fun <T : Any> PagingData<T>.transformPagingDataToListOfData(): List<T> {
    val list = mutableListOf<T>()
    this.map { item ->
        list.add(item)
    }
    return list
}
val mockedUser = UserDBEntity(
    id = "10",
    gender = ",male",
    name = "ahmed",
    location = Location("Cairo", "Cairo", "102523", Coordinates(1.0, 6.6)),
    email = "a@test.com",
    dob = "20/02/2020",
    registered = "20/02/2020",
    phone = "+2015255523",
    picture = Picture("", "", ""),
)