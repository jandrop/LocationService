Location Service Sample
===================================

## How to use

### 1.- Gradle Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```groovy
gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

### 2.- Configuring your Project Dependencies
Add the library dependency to your build.gradle file.

```groovy
dependencies {
    ...
    compile 'com.github.jandrop:LocationService:1.1'
}
```
### 3.- Example of use:
```groovy

private LocationManager manager;

...

manager = new LocationManager(this); // Create a instance of LocationManagers 
manager.registerLocationListener(this); // you must implement the interface LocationManager.LocationIF
manager.startLocationUpdates(); // Start the service
manager.stopLocationUpdates(); // Stop the service
manager.destroyReceiver(); // Destroy the receiver

...

@Override
    public void onLocationChange(Location location) {
        if(location!=null)
            locationText.setText(location.toString());
    }
```

See the sample project for more info.

## License

Copyright Alejandro Platas 2016

Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
