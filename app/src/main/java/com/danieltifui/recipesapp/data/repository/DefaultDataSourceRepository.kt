package com.danieltifui.recipesapp.data.repository

import com.danieltifui.recipesapp.data.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DefaultDataSourceRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    private val remote = remoteDataSource
    fun getRemoteDataSource() = remote
}