[![Release](https://jitpack.io/v/jitpack/android-example.svg)](https://jitpack.io/#rommansabbir/StoreX)
# StoreX
✔️ Simplify Caching in Android

## Features
* Notify on Data Changes based on Subscription
* Lightweight
* AES Encryption
* Thread Safe

## How does it work?
Caching is just a simple key-value pair data saving procedure. StoreX follows the same approach. StoreX uses SharedPreference/Cache Directory (as File) as storage for caching data. Since we really can't just save the original data because of security issues. StoreX uses AES encryption & decryption behind the scene when you are caching data or fetching data from the cache. Also, you can observer cached data in real-time.

## Documentation

### Installation

---
Step 1. Add the JitPack repository to your build file 

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    implementation 'com.github.rommansabbir:StoreX:Tag'
}
```

---

### Version available

| Latest Releases
| ------------- |
| 2.1.0         |

---

### What's new in this version?
- Multiple instance of StoreX with differnet configuration & different storage type support (SharedPref/Cache Storage as File)
- Added support for custom Coroutine Scopes

## Initialize
````
// Create multiple identifers
object StoreXIdentifiers {
    val mainConfig : StoreXConfig = StoreXConfig(
        "Something_1", 
        "main_pref",
        writeOrGetAsFileUsingCacheDirectory= true)

    val anotherConfig : StoreXConfig = StoreXConfig(
        "Something_2",
        "secondary_pref",
        writeOrGetAsFileUsingCacheDirectory= false)
}
````

````
StoreXCore.init(this, mutableListOf(
    StoreXIdentifiers.mainConfig,
    StoreXIdentifiers.anotherConfig,
))
````

## How To Access
````
StoreXCore.instance(configs: StoreXConfig)
````
or else, use the extension function
````
fun storeXInstance(config: StoreXConfig)
````
which return an instance of `StoreX` [Note:  You must initalize `StoreX` properly before accessing or else it will throw `NotInitializedException`]

Also, you can set encryption key for your data accross the application by calling this method `StoreXCore.setEncryptionKey(key:String)` which throw `InvalidEncryptionKeyException` if you passed empty `String`

----

# Usages

## How to save an object to StoreX?

Create any data class that extends `StoreAbleObject`
Example:
`````
class MyClass:StoreAbleObject()
`````

````
//How to save the object (Main Thread) [Deprecated]
StoreX.put(key: String, value: StoreAbleObject)

//How to save the object (Backed by Coroutine)
StoreX.put(scope: CoroutineScope, key: String, value: StoreAbleObject)
````

or use Async
````
//How to save the object  with Callback [Deprecated]
StoreX.put<T : StoreAbleObject>(key: String, value: StoreAbleObject, callback: SaveCallback<T>)

//How to save the object with Callback (Backed by Coroutine)
StoreX.put<T : StoreAbleObject>(scope: CoroutineScope, key: String, value: StoreAbleObject, callback: SaveCallback<T>)
````


## How to get an object from StoreX?
Get an saved object (Main Thread) which may throw `RuntimeException`
````
StoreX.get<T : StoreAbleObject>(key: String, objectType: Class<T>): T
````
or use Async
````
//Deprecated
StoreX.<T : StoreAbleObject>get(key: String, objectType: Class<T>, callback: GetCallback<T>)

//Backed by Coroutine
StoreX.<T : StoreAbleObject>get(scope: CoroutineScope,key: String, objectType: Class<T>, callback: GetCallback<T>)
````

## How to get notified on data changes?
Simple, to get notified on any data changes according to a given key you first need to subscribe to StoreX by calling this method `StoreX.addSubscriber(subscriber: Subscriber)` else if you want to add a list subscriber use `StoreX.addSubscriber(subscribers: ArrayList<Subscriber>)`

Example:
First setup an callback for the data changes
````
    private val callback1 = object : EventCallback{
        override fun onDataChanges(subscriber: Subscriber, instance: StoreX) {
            // Callback return an instance of StoreX and the specific subscriber
        }
    }
````
Create a new subscriber.

Create a new subscriber by providing the `Key`, `Observer ID (Must be unique)` and the `Callback`

````
    private var subscriber1 = Subscriber(key, OBSERVER_ID_1, callback1)
````

Now register the subscriber to the `StoreX` by calling this method
````
StoreX.addSubscriber(subscriber1)
````

Also, you can remove your subscription any time by calling this method 
`StoreX.removeSubscriber(subscriber: Subscriber)` or list of subscriber `StoreX.removeSubscriber(subscribers: ArrayList<Subscriber>)`
````
storeXInstance().removeSubscriber(subscriber1)
````

## How to remove any saved data?
````
StoreX.remove(key: String)
````

## How to remove all data when you are crazy
````
StoreX.removeAll()
````

##### Need more information regarding the solid implementation? - Check out the sample application.

---


### How does StoreX work?

<img src='https://github.com/rommansabbir/StoreX/blob/master/art/how-storex-works.png'/>

----

### Contact me
[Portfolio](https://www.rommansabbir.com/) | [LinkedIn](https://www.linkedin.com/in/rommansabbir/) | [Twitter](https://www.twitter.com/itzrommansabbir/) | [Facebook](https://www.facebook.com/itzrommansabbir/)

### License

---
[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````
Copyright (C) 2021 Romman Sabbir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````


