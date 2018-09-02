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
package tang.com.recurve.binding

import androidx.databinding.DataBindingComponent
import com.bumptech.glide.RequestManager

/**
 * Created by tang on 2018/3/2.
 * A Data Binding Component implementation for recurve.
 * RequestManager created by Glide.with()
 */
class RecurveDataBindingComponent(private val requestManager: RequestManager
                                  , private var placeholderRes: Int = 0
                                  , private var fallbackRes: Int = 0
                                  , private var errorRes: Int = 0): DataBindingComponent {

    override fun getImageBindingAdapters(): ImageBindingAdapters =
            ImageBindingAdapters(requestManager,placeholderRes,fallbackRes,errorRes)


}
