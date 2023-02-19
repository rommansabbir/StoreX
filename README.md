![StoreX](https://github.com/rommansabbir/StoreX/blob/master/art/storex_logo.png)

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

 <p align="center">
    <a href="https://android-arsenal.com/details/1/8362"><img alt="Maintained" src="https://img.shields.io/badge/Android%20Arsenal-StoreX-green.svg?style=flat" height="20"/></a>
</p>

 <p align="center">
     <a href="https://github.com/rommansabbir/StoreX"><img alt="Maintained" src="https://img.shields.io/badge/Maintained_Actively%3F-Yes-green.svg" height="20"/></a>
</p>

 <p align="center">
     <a href="https://jitpack.io/#rommansabbir/StoreX"><img alt="JitPack" src="https://img.shields.io/badge/JitPack-Yes-green.svg?style=flat" height="20"/></a>
</p>

<h1 align="center"> ⚡ Latest Version: 3.0.0 | Change Logs 🔰</h1>

- Breaking changes compared to `v2.1.0`.
- Encryption is not coming with the library itself anymore. It's developer specific now.
- `SmartStoreX` is now completely storage (`CacheDir/FilesDir/Others`) based, no more `SharedPref` usages.
- New configuration `StoreXSmartConfig` to work with `SmartStoreX`.

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

<h1 align="center">Installation</h1>

## ➤ Step 1:

Add the JitPack repository to your build file .

```gradle
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

## ➤ Step 2:

Add the dependency.

```gradle
    dependencies {
            implementation 'com.github.rommansabbir:StoreX:3.0.0'
    }
```

## ➤ Step 3:
How to Access `SmartStoreX`.
- First, create an instance of `StoreXSmartConfig` :
```kotlin
    val testConfig = StoreXSmartConfig(
        WeakReference(this),
        "filename",
        StoreAbleObject(), //Object to be saved, must be a instance of StoreAbleObject.
        StoreXCachingStrategy.CacheDir, // Caching directory: CacheDir-FilesDir-Others
        overwriteExistingFile = true
    )
```

- To write an object :
 ````kotlin
    val isWriteDone = SmartStoreX.getInstance.write(testConfig)
    Log.d("Write Object", isWriteDone.toString())
````

- To get a written object :
```kotlin
    val storedObject: StoreAbleObject? =
        SmartStoreX.getInstance.read(testConfig, StoreAbleObject::class.java)
    Log.d("Written Object", "Object ID: " + (storedObject?.objectId ?: "null"))
```

- To delete a written object :
```kotlin
    val isDeleted = SmartStoreX.getInstance.delete(testConfig)
    Log.d("Is Delete Done", isDeleted.toString())
```

How to manage `Subscription` (`Write/Delete`).

- To register a subscriber : 
```kotlin
    SmartStoreX.registerSubscriber(
        testConfig.fileName,
        subscriber = object : StoreXSubscription {
            override fun <T : StoreAbleObject> onCallback(fileName: String, updatedObject: T) {
                Log.d("Write Callback", updatedObject.objectId)
            }

            override fun <T : StoreAbleObject> onDelete(fileName: String, deletedObject: T) {
                Log.d("Delete Callback", deletedObject.objectId)
            }
        })
```

- To remove a subscriber :
```kotlin
    SmartStoreX.removeSubscriber(testConfig.fileName)
```

- To remove all subscribers :
```kotlin
    SmartStoreX.removeAllSubscriber()
```

- `Objects` that are extended from `StoreAbleObject` :
```
StoreAbleString("Value")
StoreAbleInt(1)
StoreAbleDouble(1.0)
StoreAbleLong(1234567890879)
StoreAbleByte(Byte)
StoreAbleChar(Char)
StoreAbleShort(Short
StoreAbleArrayList(Arraylist)
StoreAbleMutableList(MutableList)
StoreAbleSet(Set)
StoreAbleMutableSet(MutableSet)
StoreAbleHashMap(HashMap)
StoreAbleHashSet(HashSet)
```

[Looking for old version documentation? Click here to see.](https://github.com/rommansabbir/StoreX/blob/master/README_OLD.md)

[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

## Contact me

✔ [LinkedIn](https://www.linkedin.com/in/rommansabbir/)

✔ [Website](https://rommansabbir.com)


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#getting-started-quick)

### License

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

````html
Copyright (C) 2023 Romman Sabbir

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
