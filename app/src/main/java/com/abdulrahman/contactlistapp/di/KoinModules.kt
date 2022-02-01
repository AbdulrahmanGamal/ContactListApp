package com.abdulrahman.contactlistapp.di

import androidx.room.Room
import com.abdulrahman.contactlistapp.data.local.Db
import com.abdulrahman.contactlistapp.data.mapper.UserMapper
import com.abdulrahman.contactlistapp.data.repositories.UsersRemoteMediator
import com.abdulrahman.contactlistapp.data.repositories.UserRepository
import com.abdulrahman.contactlistapp.domain.GetUserByIdUseCase
import com.abdulrahman.contactlistapp.domain.GetUsersUseCase
import com.abdulrahman.contactlistapp.presentation.details.ContactsDetailsViewModel
import com.abdulrahman.contactlistapp.presentation.listscreen.ContactsListViewModel
import org.koin.dsl.module


val repositoryModule = module {
    factory { UsersRemoteMediator(get(), get(), get()) }
    single { UserRepository(get(), get()) }
}

val mappersModule = module {
    single { UserMapper() }
}

val useCasesModule = module {
    factory { GetUsersUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(get(), Db::class.java, Db.NAME).build()
    }
}
val viewModelsModule = module {
    factory { ContactsListViewModel(get()) }
    factory { ContactsDetailsViewModel(get()) }
}
