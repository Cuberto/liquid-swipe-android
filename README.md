# Liquid Swipe

[![GitHub license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://raw.githubusercontent.com/Cuberto/flashy-tabbar-android/master/LICENSE)

[![](https://jitpack.io/v/Udhayarajan/liquid-swipe-android.svg)](https://jitpack.io/#Udhayarajan/liquid-swipe-android)

![Animation](https://raw.githubusercontent.com/Cuberto/liquid-swipe/master/Screenshots/animation.gif)

## Requirements

- Android 5.0+

## Example

To run the example project, clone the repo, and run `sample`

### As library

#### GitHub Packages

Step 1 : Update root `build.gradle(Project: <project_name>)`
- In Android Studio -> Make sure that you have selected `Android`
 
![image](https://user-images.githubusercontent.com/77388817/120667372-0ce94d00-c4ab-11eb-8700-8676f7b15e84.png)
- Under Gradle Scripts 1st File is root build.gradle
 
![image](https://user-images.githubusercontent.com/77388817/120668108-c6e0b900-c4ab-11eb-9e5e-6bbeec2a2e36.png)
- Add following code in 1st file:
```
    allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
    }
```

Step 2: Update app `build.gradle(Module: <project_name>.app)`
- Now open second file
- inside dependencies of the build.gradle of app module, use the following code
```
    dependencies {
	    ...
	    implementation 'com.github.Udhayarajan:liquid-swipe-android:1.0.1'
    }
```


Sync project and now you can use flashytabbar library

## Usage

Add LiquidPager to your xml and use it like you would ViewPager

```

    <com.cuberto.liquid_swipe.LiquidPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
        
```

## iOS

Similar library [LiquidSwipe](https://github.com/Cuberto/liquid-swipe) by [Cuberto](https://github.com/Cuberto)

## Author

Cuberto Design, info@cuberto.com

## License

Liquid Swipe is available under the MIT license. See the LICENSE file for more info.
