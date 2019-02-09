/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tangpj.recurve.ui.creator

import com.tangpj.recurve.ui.strategy.LoadingStrategy

class RecurveLoadingCreator: LoadingCreator{

    private val loadingMap: MutableMap<String, LoadingStrategy> = mutableMapOf()

    override fun addLoadingStrategy(pair: Pair<String, LoadingStrategy>) {
        loadingMap[pair.first] = pair.second
    }

    override fun getLoadingStrategy(key: String): LoadingStrategy? = loadingMap[key]

    override fun clear() {
        loadingMap.clear()
    }
}