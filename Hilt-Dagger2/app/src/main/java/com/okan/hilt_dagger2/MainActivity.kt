package com.okan.hilt_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


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
    private val someInterface: SomeInterface
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
constructor() : SomeInterface {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing"
    }

    override fun getAThing(): String {
        return "A thing"
    }
}

interface SomeInterface {
    fun getAThing(): String
}


