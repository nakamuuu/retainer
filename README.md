# Retainer

Retainer is an Android library that helps you retain objects in an Activity / Fragment during configuration changes.

It auto-generates a Fragment internally by using annotation processing.
A generated Fragment is called `setRetainInstance(true);` in its `onCreate()` to preserve stateful objects.

If you need more information about the handling of configuration changes, please check the following document.

https://developer.android.com/guide/topics/resources/runtime-changes.html

## How to use

### Activity

```java
public class ExampleActivity extends Activity {
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

At the moment, Retainer supports only `android.support.v4.app.Fragment` or its subclasses.

```java
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

**IMPORTANT**: You should never add `@Retain` to an object that is tied to the activity context, such as a Drawable, an Adapter, or a View.
If you do, it will cause serious memory leaks.

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
