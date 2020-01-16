# Liquid Swipe

[![GitHub license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://raw.githubusercontent.com/Cuberto/flashy-tabbar-android/master/LICENSE)

![Animation](https://raw.githubusercontent.com/Cuberto/liquid-swipe/master/Screenshots/animation.gif)

## Requirements

- Android 5.0+

## Example

To run the example project, clone the repo, and run `app`

### As library

#### GitHub Packages

Step 1 : Generate a Personal Access Token for GitHub
- Inside you GitHub account:
- Settings -> Developer Settings -> Personal Access Tokens -> Generate new token
- Make sure you select the following scopes (“ read:packages”) and Generate a token
- After Generating make sure to copy your new personal access token. You cannot see it again! The only option is to generate a new key.

Step 2: Store your GitHub — Personal Access Token details
- Create a github.properties file within your root Android project
- In case of a public repository make sure you add this file to .gitignore for keep the token private
- Add properties gpr.usr=GITHUB_USERID and gpr.key=PERSONAL_ACCESS_TOKEN
- Replace GITHUB_USERID with personal / organisation Github User ID and PERSONAL_ACCESS_TOKEN with the token generated in #Step 1

Step 3 : Update build.gradle inside the application module
- Add the following code to build.gradle inside the app module that will be using the library
```
    def githubProperties = new Properties()
    githubProperties.load(new FileInputStream(rootProject.file("github.properties")))
    repositories {
        maven {
            name = "GitHubPackages"

            url = uri("https://maven.pkg.github.com/Cuberto/liquid-swipe-android")
            credentials {
                /** Create github.properties in root project folder file with     
                ** gpr.usr=GITHUB_USER_ID & gpr.key=PERSONAL_ACCESS_TOKEN 
                ** Or set env variable GPR_USER & GPR_API_KEY if not adding a properties file**/
                username = githubProperties['gpr.usr'] ?: System.getenv("GPR_USER")
                password = githubProperties['gpr.key'] ?: System.getenv("GPR_API_KEY")
            }
        }
    }
```
- inside dependencies of the build.gradle of app module, use the following code
```
    dependencies {
        //consume library
        implementation 'com.cuberto:liquid-swipe:1.0.0'
        ....
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
