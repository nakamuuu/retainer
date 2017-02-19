# Retainer

[![Release](https://jitpack.io/v/nakamuuu/retainer.svg)](https://jitpack.io/#nakamuuu/retainer)

Retainer is an Android library that helps you retain objects in an Activity / Fragment during configuration changes.

It auto-generates container objects by using annotation processing, and this objects will be internally preserved in a Fragment.
`setRetainInstance(true)` is called for this fragment in its `onCreate()`, so that stateful objects are preserved during configuration changes.

If you need more information about the handling of configuration changes, check the following document.

https://developer.android.com/guide/topics/resources/runtime-changes.html

## How to use

Retainer also provides you static methods that bundle a procedure related to Fragment's lifecycle.
So you can simply retain objects by following the steps and examples below.

1. Add `@Retain` to a field you want to retain.
1. Call `Retainer.onCreate(this)` and `Retainer.onDestroy(this)` in your activity or fragment.

**IMPORTANT**: You should never add `@Retain` to an object that is tied to the activity context, such as a Drawable, an Adapter, or a View.
If you do, it will cause serious memory leaks.

### Activity

Supports only `android.support.v4.app.FragmentActivity` or its subclasses.

```java
import net.divlight.retainer.Retainer;
import net.divlight.retainer.annotation.Retain;

public class ExampleActivity extends AppCompatActivity {
    // This field will be automatically preserved by Retainer.
    @Retain
    int retainedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retainer.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Retainer.onDestroy(this);
    }
}
```

### Fragment

Supports only `android.support.v4.app.Fragment` or its subclasses.

```java
import net.divlight.retainer.Retainer;
import net.divlight.retainer.annotation.Retain;

public class ExampleFragment extends Fragment {
    // This field will be automatically preserved by Retainer.
    @Retain
    int retainedValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retainer.onCreate(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Retainer.onDestroy(this);
    }
}
```

## Download

### Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency in your module's build.gradle:

```groovy
compile 'com.github.nakamuuu.retainer:library:{{latest_version}}'
annotationProcessor 'com.github.nakamuuu.retainer:processor:{{latest_version}}'
```

## Requirements

Android 4.0.3+ (API Level: 15+)

## License

```
Copyright 2017 Keita Nakamura

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
