![alt text](https://github.com/MindorksOpenSource/RadialProgressBar/blob/master/images/logo.png)

# Radial Progress Bar

Radial ProgressBar inspired  by Apple Watch OS. It is highly Customisable <br/>
### Preview of Radial Progress Bar
<img  height="200" src="https://github.com/MindorksOpenSource/RadialProgressBar/blob/master/images/1.jpg"> <img  width="200" src="https://github.com/MindorksOpenSource/RadialProgressBar/blob/master/images/2.jpg">


## Getting Started

##### Step 1. Add the JitPack repository to your Add it in your root `build.gradle` at the end of repositories:


```
allprojects {
    repositories {
   	   maven { url 'https://jitpack.io' }
    }
}
```

##### Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.MindorksOpenSource:RadialProgressBar:v1.0'
	}
```

##### Step3. To use this in XML File, use

```
  <com.mindorks.RadialProgressBar
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:id="@+id/progress_view"
            app:useRoundedCorner="true"
            app:innerMaxProgress="100"
            app:outerMaxProgress="100"
            app:centerMaxProgress="100"
            app:isAnimationOn="true"
            app:innerProgress="50"
            app:centerProgress="50"
   />

```

##### Step 4. Link the XML in Activity File using,

```
val radialProgressBar = findViewById(R.id.progress_view)

or

RadialProgressBar radialProgressBar = findViewById(R.id.progress_view) //if using Java
```

thats it and how you can use the RadialProgressView in your project.

#### To Customise it according to your need you can use,

| Feature                                   | XML                              | Activity (Java/Koltin)        |Default Value|
| :-------------                         |:-------------                           | :-----            |:----
|Set Start Angle CenterView|app:centerProgressStartAngle="0/90/180/270"|radialProgressBar.setStartAngleCenterView(0/90/180/270)|270|
|Set Start Angle OuterView|app:outerProgressStartAngle="0/90/180/270"|radialProgressBar.setStartAngleOuterView(0/90/180/270)|270|
|Set Start Angle InnerView|app:innerProgressStartAngle="0/90/180/270"|radialProgressBar.setStartAngleInnerView(0/90/180/270)|270|
