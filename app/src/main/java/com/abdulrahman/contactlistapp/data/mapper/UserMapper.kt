package com.abdulrahman.contactlistapp.data.mapper

import com.abdulrahman.contactlistapp.core.BaseMapper
import com.abdulrahman.contactlistapp.data.entities.User
import com.abdulrahman.contactlistapp.data.local.UserDBEntity

class UserMapper : BaseMapper<User, UserDBEntity>() {
    override fun transformTo(source: User): UserDBEntity {
        return UserDBEntity(
            gender = source.gender,
            name = source.name.fullName(),
            location = source.location,
            email = source.email,
            dob = source.dob.date,
            registered = source.registered.date,
            phone = source.phone,
            picture = source.picture,
            id = source.login.uuid,
        )
    }

}