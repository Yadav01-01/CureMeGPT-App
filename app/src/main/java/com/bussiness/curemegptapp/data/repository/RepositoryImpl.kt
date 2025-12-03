package com.bussiness.curemegptapp.data.repository

import com.bussiness.curemegptapp.apiservice.ApiListing
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiListing
) : Repository {
}