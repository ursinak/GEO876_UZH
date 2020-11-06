import java.util.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.providers.EsriProvider;
import parsing.Parsing;
import java.awt.Color;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;


/**
 * Display earthquake events of GeoRSS feed on map
 * @author Ursina C. Boos
 * @version 16.03.2019
 */


public class Map extends PApplet {

    /**
     * Class to map the latest earthquakes, parsed from USGS GeoRSS Feed. The resulting map
     * allows the user to click on the earthquake features to get more information about
     * the earthquake's properties.
     *
     * */


    // creates new unfolding map
    private UnfoldingMap map;

    // read in and parse earthquake RSS feed
    private String earthquakesRSS = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.atom";

    // returns list of earthquakes
    private List<PointFeature> earthquakes = Parsing.analyseEarthquake(this, earthquakesRSS);

    // initialise variable to check if mouse clicked on earthquake marker on map
    private boolean overMarker = false;


    // define colours for magnitude (Richter magnitude scale), based on damage
    private static Color micro = new Color(248, 193, 168, 90);
    private static Color vLight = new Color(239, 145, 152, 90);
    private static Color light = new Color(232, 96, 138, 90);
    private static Color medium = new Color(192, 69, 138, 90);
    private static Color strong = new Color(143, 49, 146, 90);
    private static Color great = new Color(99, 33, 143, 90);
    private static Color vGreat = new Color(75, 24, 108, 90);
    private static Color globalCatastrophe = new Color(51, 16, 74, 90);


    /**
     * Method to create a point marker for each earthquake event location
     * @param earthquakeLocation
     * @return point marker for each earthquake event
     */

    private SimplePointMarker getEarthquakeMarker(Location earthquakeLocation) {

        return new SimplePointMarker(earthquakeLocation);
    }


    /**
     * methods to set the colour of the stroke and filling of the marker
     * @param earthquakeLocationMarker
     */

    private static void setColourMarkerMicro(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(micro.hashCode());
    }

    private static void setStrokeColourMicro(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(micro.hashCode());
    }

    private static void setColourMarkerVLight(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(vLight.hashCode());
    }

    private static void setStrokeColourVLight(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(vLight.hashCode());
    }

    private static void setColourMarkerLight(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(light.hashCode());
    }

    private static void setStrokeColourLight(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(light.hashCode());
    }

    private static void setColourMarkerMedium(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(medium.hashCode());
    }

    private static void setStrokeColourMedium(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(medium.hashCode());
    }

    private static void setColourMarkerStrong(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(strong.hashCode());
    }

    private static void setStrokeColourStrong(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(strong.hashCode());
    }

    private static void setColourMarkerGreat(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(great.hashCode());
    }

    private static void setStrokeColourGreat(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(great.hashCode());
    }

    private static void setColourMarkerVGreat(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(vGreat.hashCode());
    }

    private static void setStrokeColourVGreat(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(vGreat.hashCode());
    }

    private static void setColourMarkerGlobalCatastrophe(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setColor(globalCatastrophe.hashCode());
    }

    private static void setStrokeColourGlobalCatastrophe(SimplePointMarker earthquakeLocationMarker) {
        earthquakeLocationMarker.setStrokeColor(globalCatastrophe.hashCode());
    }


    /**
     * method to calculate the radius of the point markers
     * @param depth
     * @return radius
     */

    private float getRadius(float depth) {

        // size of circle = 1/log(depth) * 50. Multiply by 50 to make circle visible
        return (float) (1/Math.log(depth))*50;
    }

    /**
     * Method to set the markers colours and sizes according to the magnitude/depth of the earthquake
     * @param pointFeature
     * @return markers
     * */

    private SimplePointMarker getPropertyMarker(PointFeature pointFeature) {

        SimplePointMarker earthquakeMarker = this.getEarthquakeMarker(pointFeature.location);

        // get magnitude and depth property from point feature and save values to array
        float magnitude = (float) pointFeature.getProperties().values().toArray()[1]; // property [1] = magnitude
        float depth = (float) pointFeature.getProperties().values().toArray()[0]; // property [0] = depth

        float m12 = (float) 3.0;
        float m3 = (float) 4.0;
        float m4 = (float) 5.0;
        float m5 = (float) 6.0;
        float m6 = (float) 7.0;
        float m7 = (float) 8.0;
        float m8 = (float) 9.0;

        // set colours according to the event's magnitude
        if (magnitude < m12) {
            setColourMarkerMicro(earthquakeMarker);
            setStrokeColourMicro(earthquakeMarker);
        }
        else if (magnitude < m3 && magnitude >= m12) {
            setColourMarkerVLight(earthquakeMarker);
            setStrokeColourVLight(earthquakeMarker);
        }
        else if (magnitude < m4 && magnitude >= m3) {
            setColourMarkerLight(earthquakeMarker);
            setStrokeColourLight(earthquakeMarker);
        }
        else if (magnitude < m5 && magnitude >= m4) {
            setColourMarkerMedium(earthquakeMarker);
            setStrokeColourMedium(earthquakeMarker);
        }
        else if (magnitude < m6 && magnitude >= m5) {
            setColourMarkerStrong(earthquakeMarker);
            setStrokeColourStrong(earthquakeMarker);
        }
        else if (magnitude < m7 && magnitude >= m6) {
            setColourMarkerGreat(earthquakeMarker);
            setStrokeColourGreat(earthquakeMarker);
        }
        else if (magnitude < m8 && magnitude >= m7) {
            setColourMarkerVGreat(earthquakeMarker);
            setStrokeColourVGreat(earthquakeMarker);
        }
        else {
            setColourMarkerGlobalCatastrophe(earthquakeMarker);
            setStrokeColourGlobalCatastrophe(earthquakeMarker);
        }

        // set size of point marker according to the event's depth
        earthquakeMarker.setRadius(getRadius(depth));

        return earthquakeMarker;
    }


    /**
     * @return markers with relevant values & colours assigned*
     * */

    private List<Marker> getEarthquakeMarkersList() {

        //create array list of markers
        List<Marker> markers = new ArrayList<>();

        //create point list to customize each earthquake point & iterate through point list to add as marker
        for (PointFeature pointFeature : earthquakes) {
            markers.add(getPropertyMarker(pointFeature));
        }

        return markers;
    }


    /**
     * Counts the number of earthquake markers displayed on the map
     * @return count of all displayed earthquakes
     */

    private int getEarthquakeCount(){
        int count = 0;
        for(Marker marker : getEarthquakeMarkersList()){
            count ++;
        }
        return count;
    }


    /**
     * gets the depth of the mapped earthquake at clicking-position of mouse
     * @return depth value of event
     */

    private float getDepth(){
        float depth = 0;

        for (Marker marker : getEarthquakeMarkersList()){
            Location markerID = marker.getLocation();

            // create point list to customize each earthquake point & iterate through point list to add as marker
            for (PointFeature pointFeature : earthquakes) {
                Location pID = pointFeature.getLocation();

                if (pID.equals(markerID) && marker.isInside(map, mouseX, mouseY)){
                    depth = (float) pointFeature.getProperty("depth");
                }
            }
        }
        return depth;
    }


    /**
     * ets the magnitude of the mapped earthquake at clicking-position of mouse
     * @return magnitude value of event
     */

    private float getMagnitude() {
        float magnitude = 0;
        for (Marker marker : getEarthquakeMarkersList()){
            Location markerID = marker.getLocation();

            // create point list to customize each earthquake point & iterate through point list to add as marker
            for (PointFeature pointFeature : earthquakes) {
                Location pID = pointFeature.getLocation();

                if (pID.equals(markerID) && marker.isInside(map, mouseX, mouseY)){
                    magnitude = (float) pointFeature.getProperty("magnitude");
                }
            }
        }
        return magnitude;
    }

    /**
     * All initial map setups included here are run when the programme starts. This function runs only once.
     * */

    public void setup() {
        this.size(800, 600); //size of applet window
        this.map = new UnfoldingMap(this, new EsriProvider.WorldGrayCanvas());
        this.map.addMarkers(getEarthquakeMarkersList());
        MapUtils.createDefaultEventDispatcher(this, map); // handles all events that happen on the map
        this.map.zoomToLevel(2); // zooms into map to given extent

    }

    /**
     * Draws the map and other elements such as markers and legend. This method runs continuously
     * and is called when executing the programme. Furthermore, advanced interaction instructions (e.g.
     * interaction when mouse is clicked are set here.
     * */

    public void draw() {

        this.map.draw(); // draws map on applet
        addLegend(); // adds legend to the left side of the map


        // set overMarker true, if mouse is pressed on marker
        for (Marker marker : getEarthquakeMarkersList()){
            Location markerLoc = marker.getLocation();
            overMarker = !(this.mouseX > markerLoc.x) || !(this.mouseX < markerLoc.x) ||
                    !(this.mouseY > markerLoc.y) || !(this.mouseY < markerLoc.y);
        }

        // advanced interaction: show popup when mouse is pressed
        if (this.mousePressed && overMarker) {
            popupMenu();
        }
    }


    /**
     * popupMenu
     * settings for the popup menu
     * This method uses processing's (library) graphics methods.
     * */

    private void popupMenu() {
        fill(255, 255, 255, 90);

        int xBase = mouseX;
        int yBase = mouseY;

        rect(xBase, yBase, 200, 100);

        fill(22);
        textAlign(LEFT, CENTER);
        textSize(15);
        text("Earthquake's Properties", xBase+17, yBase+15);

        textSize(12);
        fill(0);
        textAlign(LEFT, CENTER);
        text("Magnitude: " + getMagnitude(), xBase+17, yBase+45);
        text("Depth: " + getDepth() , xBase+17, yBase+75);
    }


    /**
     * addLegend
     * Settings for the legend. This method uses processing's graphics methods.
     * */
    private void addLegend() {

        // create legend box
        fill(255, 255, 255, 70);
        rect(25, 50, 150, 340);

        // legend title
        fill(0); //text colour
        textAlign(LEFT, CENTER);
        textSize(14);
        text("Legend", 50, 75);

        textSize(12);
        text("# Earthquakes: " + getEarthquakeCount(), 50, 115);

        // create points to represent markers on map in legend
        fill(micro.hashCode());
        ellipse(50, 145, 15, 15);
        fill(vLight.hashCode());
        ellipse(50, 175, 15, 15);
        fill(light.hashCode());
        ellipse(50, 205, 15, 15);
        fill(medium.hashCode());
        ellipse(50, 235, 15, 15);
        fill(strong.hashCode());
        ellipse(50, 265, 15, 15);
        fill(great.hashCode());
        ellipse(50, 295, 15, 15);
        fill(vGreat.hashCode());
        ellipse(50, 325, 15, 15);
        fill(globalCatastrophe.hashCode());
        ellipse(50, 355, 15, 15);

        // text to points in legend
        fill(0, 0, 0);
        text("<2 Magnitude", 75, 145);
        text("3+ Magnitude", 75, 175);
        text("4+ Magnitude", 75, 205);
        text("5+ Magnitude", 75, 235);
        text("6+ Magnitude", 75, 265);
        text("7+ Magnitude", 75, 295);
        text("8+ Magnitude", 75, 325);
        text("9+ Magnitude", 75, 355);
    }
}