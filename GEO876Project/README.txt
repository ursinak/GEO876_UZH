------------------------------------------------------------------------
Earthquake Mapping Project README File.
------------------------------------------------------------------------

PROJECT TITLE: Earthquake Mapping
PURPOSE OF PROJECT: GEO876
VERSION or DATE: 17.03.2019
AUTHORS: Ursina C. Boos (15-725-708)

------------------------------------------------------------------------
This project is a possible solution to map all the earthquakes registered worldwide using Java. The quakes are mapped using the two libraries, Unfolding and Processing. The earthquakes are parsed from USGS GeoRSS feed.

The project was programmed in Intellij IDEA.

Output of this code is a map displaying all the earthquakes. When pressing the mouse on an earthquake marker, the magnitude and depth of the earthquake can be viewed in a popup.

Classes used:

1. Main
2. Map
3. Parsing

------------------------------------------------------------------------
1. Main

Methods:
* main() containing the executable statement to call the PApplet and Map class.

2. Map

Methods:
* getEarthquakeMarker()
* setColourMarkerX() // one method for each colour used
* setStrokeColour() // one method for each colour used
* getRadius()
* getPropertyMarker()
* getEarthquakeMarkersList()
* getEarthquakeCount()
* getDepth()
* getMagnitude()
* setup()
* draw()
* popupMenu()
* addLegend()

3. Parsing

Methods:
* analyseEarthquake()
* getLocation()
* getStringValue()
* getFloatValue()

