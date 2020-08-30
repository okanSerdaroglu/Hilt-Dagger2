package com.okan.hilt_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton


@AndroidEntryPoint // be able to have dependencies inject in
class MainActivity : AppCompatActivity() {

    // field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing())
    }
}

//@Singleton // lives application alive
//@FragmentScoped // when it is FragmentScoped you can not inject Activity
@ActivityScoped
class SomeClass
@Inject
constructor(
    private val someInterface: SomeInterface,
    private val gson: Gson
) {
    fun doAThing(): String {
        return "Look I got: ${someInterface.getAThing()}"
    }
}

class SomeDependency
@Inject
constructor() {
    fun getAThing(): String {
        return "A Thing"
    }
}

/** dagger will check things at compile time
 *  If there is an error you see it on runtime
 *  For this reason we prefer dagger over Coin
 */
@AndroidEntryPoint
class MyFragment : Fragment() {

    @Inject
    lateinit var someClass: SomeClass

}


class SomeInterfaceImpl
@Inject
constructor(
    private val someDependency: String
) : SomeInterface {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing"
    }

    override fun getAThing(): String {
        return "A thing,${someDependency}"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

@InstallIn(ApplicationComponent::class) // lives as long as Application lives
@Module
class MyModule {

    @Singleton
    @Provides
    fun provideSomeString(): String {
        return "some string"
    }

    @Singleton
    @Provides
    fun provideSomeInterface(
        someString: String
    ): SomeInterface {
        return SomeInterfaceImpl(someString)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}


