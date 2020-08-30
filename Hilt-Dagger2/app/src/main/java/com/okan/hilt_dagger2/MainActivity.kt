package com.okan.hilt_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton


@AndroidEntryPoint // be able to have dependencies inject in
class MainActivity : AppCompatActivity() {

    // field injection
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println(someClass.doAThing1())
        println(someClass.doAThing2())
    }
}

//@Singleton // lives application alive
//@FragmentScoped // when it is FragmentScoped you can not inject Activity
@ActivityScoped
class SomeClass
@Inject
constructor(
    @Impl1 private val someInterface1: SomeInterface,
    @Impl2 private val someInterface2: SomeInterface
) {
    fun doAThing1(): String {
        return "Look I got: ${someInterface1.getAThing()}"
    }

    fun doAThing2(): String {
        return "Look I got: ${someInterface2.getAThing()}"
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


class SomeInterfaceImpl1
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "A thing2"
    }
}

interface SomeInterface {
    fun getAThing(): String
}

@InstallIn(ApplicationComponent::class) // lives as long as Application lives
@Module
class MyModule {

    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1(): SomeInterface {
        return SomeInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

