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



## 快速实现一个MVVM项目

一个最简单的MVVM项目应该包含：MVVM架构，网络，缓存功能。所以有两个库recureve库是必须要依赖的,分别是：recurve.core（依赖了recurve.module_adapter库）、recurve.retrofit2。然后在配合Jetpack相关的库就能快速开发一个MVVM架构的项目了。





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


