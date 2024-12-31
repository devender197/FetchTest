package com.dev.fetchtest.network.model.request

import com.dev.fetchtest.network.base.RequestModelInterface

class DataRequest: RequestModelInterface {
    override fun path(): String ="/hiring.json"
}