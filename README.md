# Exercise 4 – Animation

## Overview

In this exercise you will implement an OpenGL 3 D animation game that will consist of a

locomotive moving on a track.

The implementation of this exercise will be an extension of ex3.


## Usage

The same as ex3, plus the following:

- Pressing ‘c’ toggles the camera position (explained below).
- Pressing ‘l’ (Bonus) - turns on/off marking the light sources (little spheres will be

```
displayed at the location of the light sources, which will have only an emittance
```
```
attribute – i.e. not affected by the light source itself).
```
- Pressing the up and down arrows changes the locomotive's speed (note that the

```
speed can be negative – in which case the locomotive will move backwards).
```
- Typing a number (greater than zero) and then pressing Enter changes the track.
- Pressing esc closes the application.

## Requirements

1. General

```
1.1. A locomotive should move on a track.
```
```
1.2. The implementation of ex3 should be used as a starting point.
```
```
1.3. Lighting and materials should be used (instead of glColor) – this is a Bonus.
```
```
1.4. The track should be rendered above a textured plane (e.g. grass field).
```
```
1.5. The track itself should be textured.
```
```
1.6. The locomotive will face the correct direction.
```
2. View

```
2.1. The camera will be placed in one of two positions (toggled by the ‘c’ key):
```
```
2.1.1. As in ex3 – rotated/zoomed using the mouse.
```
```
2.1.2. Behind the locomotive, giving the perception of moving along with the
```
```
locomotive.
```
```
2.2. In both cases, as before, a perspective projection will be used.
```
3. Bonus – adding lighting effects (10 points).


## Modeling the track course

The track can be modeled by a parametric closed curve using cubic splines. You can design

your own, but in the supplied TrackPoints.java file there are three examples. The x and y

coordinates of each point should be between -1 and 1. The z coordinate of each point should

be non-negative.
