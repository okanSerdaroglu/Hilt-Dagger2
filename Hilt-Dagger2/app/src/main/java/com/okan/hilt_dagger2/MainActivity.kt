package com.okan.hilt_dagger2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing(): String {
        return "Look I did some other thing"
    }
}