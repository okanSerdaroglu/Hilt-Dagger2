package com.okan.hilt_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
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
        println(someClass.doSomething())
        println(someClass.doSomeOtherThing())
    }
}

//@Singleton // lives application alive
//@FragmentScoped // when it is FragmentScoped you can not inject Activity
@ActivityScoped
class SomeClass
@Inject
constructor(
    private val someOtherClass: SomeOtherClass
) {
    fun doSomething(): String {
        return "Look I did a thing"
    }

    fun doSomeOtherThing(): String {
        return someOtherClass.doSomeOtherThing()
    }
}

/** dagger will check things at compile time
 *  If there is an error you see it on runtime
 *  For this reason we prefer dagger over Coin
 */
@AndroidEntryPoint
class MyFragment : Fragment() {

    @Inject
    lateinit var someOtherClass: SomeOtherClass

}


class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing"
    }
}