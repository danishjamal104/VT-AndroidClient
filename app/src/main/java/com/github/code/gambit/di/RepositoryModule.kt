package com.github.code.gambit.di

import com.cloudinary.android.MediaManager
import com.github.code.gambit.data.mapper.aws.UserAttributeMapper
import com.github.code.gambit.data.remote.services.auth.AuthService
import com.github.code.gambit.data.remote.services.auth.AuthServiceImpl
import com.github.code.gambit.data.remote.services.image.ImageService
import com.github.code.gambit.data.remote.services.image.ImageServiceImpl
import com.github.code.gambit.repositories.AuthRepository
import com.github.code.gambit.utility.sharedpreference.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserAttributeMapper(): UserAttributeMapper {
        return UserAttributeMapper()
    }

    @Singleton
    @Provides
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }

    @Singleton
    @Provides
    fun provideImageService(mediaManager: MediaManager): ImageService {
        return ImageServiceImpl(mediaManager)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authService: AuthService,
        imageService: ImageService,
        userManager: UserManager,
        userAttributeMapper: UserAttributeMapper
    ): AuthRepository {
        return AuthRepository(authService, imageService, userManager, userAttributeMapper)
    }
}
