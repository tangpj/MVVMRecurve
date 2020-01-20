## MVVM Android开发框架

这是一个基于MVVM架构的Android快速开发框架,主要作用是帮助开发者搭建一个合理的MVVM架构的应用。
这个架构支持RestFul风格的Api和GraphQL，你可以根据自身需求添加recurve-retrofit2-support库（RestFul）或recurve-apollo-support库（GraphQL）实现相应的支持。
该架构同时使用纯Kotlin开发，但是你也可以在Java中使用它。

## 功能模块

- recurve.core : 核心功能库
- recurve.retrofit2 :  Retrofit2支持库（RestFul）
- recurve.apollo :  Apollo支持库（GraphQL）
- recurve.dagger2 : Dagger2支持库
- recurve.module_adapter :  提供模块化RecyclerView Adapter
- recurve.paging :  jetpack Paging分页库支持库
- recurve.glide :  glide支持库
- recurve.viewpager2_navigation :  实现通过navigation快速生成ViewPager2
- recurve.bottom_navigation : 实现navigation快速支持BottomNavigation
- recurve.viewpager2_tablayout  :  ViewPager2 tablelayout快速支持
- recurve.navigation_dialog : 通过Navigation把fragment转换成dialog




## 将Recurve依赖到你的项目中
```groovy
//project build.gradle
buildscript {
  ext.recurve_version = '1.0.0'
}

//modules build.gradle
implementation "com.recurve:recurve.core:$recurve_version"
implementation "com.recurve:recurve-retrofit2-support:$recurve_version"
implementation "com.recurve:recurve-apollo-support:$recurve_version"
implementation "com.recurve:recurve-dagger2-support:$recurve_version"
implementation "com.recurve:recurve-module-adapter:$recurve_version"
implementation "com.recurve:recurve-module-paging-support:$recurve_version"
implementation "com.recurve:recurve-glide-support:$recurve_version"
implementation "com.recurve:viewpager2-navigation-ktx:$recurve_version"
implementation "com.recurve:bottom-navigation-ktx:$recurve_version"
implementation "com.recurve:viewpager2-tablayout-ktx:$recurve_version"
implementation "com.recurve:navigation-dialog:$recurve_version"

```



## 示例代码

一个最简单的MVVM项目应该包含：MVVM架构，网络，缓存功能。所以有两个库recureve库是必须要依赖的,分别是：recurve.core（依赖了recurve.module_adapter库）、recurve.retrofit2。然后在配合Jetpack相关的库就能快速开发一个MVVM架构的项目了。

### Repository

```kotlin
class RepoRepository constructor(
        private val db: GithubDb,
        private val githubService: GithubService) {

    val repoRateLimiter = RateLimiter<String>(15, TimeUnit.SECONDS)

    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>() {

            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                        query = query,
                        repoIds = repoIds,
                        totalCount = item.total,
                        next = item.nextPage
                )
                db.runInTransaction {
                    db.repoDao().insertRepos(item.items)
                    db.repoDao().insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>?) =
                    data == null && repoRateLimiter.shouldFetch(query)

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(db.repoDao().search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        db.repoDao().loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = githubService.searchRepos(query)

        }.asLiveData()
    }
}

```



### ViewModel

```kotlin
class SearchRepoViewModel : ViewModel(){

    var repoRepository: RepoRepository? = null

    private val _query = MutableLiveData<String>()
    val query : LiveData<String> = _query

    val results = Transformations
            .switchMap<String, Resource<List<Repo>>>(_query) { search ->
                if (search.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    repoRepository?.search(search)
                }
            }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
    }
}
```

### View

具体的Activit或者Fragment，view层只提供关键示例代码

```kotlin
//query
searchViewModel.setQuery(query)

//result callback
searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            result?.data?.let {
                creator.setDataList(it)
            }
        })
```



## 更多实战

sample能的例子比较简单，如果想体验实战系列的代码的话，欢迎关注[GithubRecurve]。该项目有较多的应用场景，该项目主要是用来演示MVVMRecurve的，还在不断的完善中。



## 引用的开源项目





## License

```
/*
 * Copyright (C) 2020 Tang
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
```


