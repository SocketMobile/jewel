# Jewel

A configurable log handler that allows you to see `java.util.logging` output in logcat.

## Usage

Add it as a dependency

```
dependencies {
    implementation 'com.socketmobile:jewel:+'
}
```

Install the handler early in your application's `onCreate` to ensure it is
installed before the library/module that uses j.u.l.Logger starts to run.

```
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Jewel.install()
    }
}
```

## License

Copyright 2018 Socket Mobile, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Acknowledgements

Thanks to [Christian Bauer](https://stackoverflow.com/users/377721/christian-bauer) who's [Stack Overflow answer](https://stackoverflow.com/a/9047282/44816) was most helpful in the creation of this library.
