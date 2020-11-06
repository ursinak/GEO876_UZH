package parsing; // add this class to package
import java.util.*;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.data.XML;

/**
 * Parse earthquakes of RSS feed
 * @author Ursina C. Boos
 * @version 12.03.2019
 * */

public class Parsing{

    /**
     * Parses GeoRSS file, gets earthquakes (=xmlItem) and converts them to point features
     * @param myPApplet PApplet
     * @param file (data source: RSS feed)
     * @return features parsed from XML file
     */

    public static List <PointFeature> analyseEarthquake(PApplet myPApplet, String file){

        List<PointFeature> earthquakes = new ArrayList<>();

        XML geoRss = myPApplet.loadXML(file);

        // get all items saved in XML file
        XML [] xmlItem = geoRss.getChildren("entry");
        PointFeature pointFeature;

        for (XML xml : xmlItem) {

            // for each earthquake in XML file get location and create feature
            Location loc = getLocation(xml);

            // if feature has location, create new PointFeature and add to previously initialised list
            if (loc != null) {
                pointFeature = new PointFeature(loc);
                earthquakes.add(pointFeature);
            }

            else {
                // if no location go to next XML item
                continue;
            }

            String title = getStringValue(xml, "title");

            if (title != null) {
                // Sets title if existing in XML file
                pointFeature.putProperty("title", title);

                // Sets magnitude (inferred from title)
                pointFeature.putProperty("magnitude", Float.parseFloat(title.substring(2, 5)));
            }

            // Get depth value from xml file (depth in m)
            float depth = getFloatValue(xml);
            // Convert depth to km, take absolute value and set property
            pointFeature.putProperty("depth", Math.abs((depth /1000)));
        }
        return earthquakes;
    }


    /**
     * Gets location from <GeoRSS:point> </GeoRSS:point> tag
     * @param xmlItem item which has point (earthquake) as child
     * @return location of point
     */

    private static Location getLocation(XML xmlItem) {

        // set location = null if failed to find earthquake's location
        Location location = null;

        // format of tag: <georss:point>-44.2581 -76.15</georss:point>
        XML pointLatLong = xmlItem.getChild("georss:point");

        // sets location if found
        if (pointLatLong != null && pointLatLong.getContent() != null) {
            String pointLatLongStr = pointLatLong.getContent();
            String[] latLong = pointLatLongStr.split(" ");
            float lat = Float.valueOf(latLong[0]);
            float lon = Float.valueOf(latLong[1]);
            location = new Location(lat, lon);
        }
        return location;
    }


    /**
     * Get string values from child node
     * @param xmlItem entry in XML file
     * @param tagName name of the tag to be analysed
     * @return string content of tag
     */

    private static String getStringValue(XML xmlItem, String tagName) {
        String stringValue = null;
        XML xmlString = xmlItem.getChild(tagName); // get content from tag name

        // check if item exists and string content is available
        if (xmlString != null && xmlString.getContent() != null) {
            stringValue = xmlString.getContent();
        }
        return stringValue;
    }

    /**
     * Get float values for depth of event
     * @param xmlItem entry in XML file
     * @return (float) depth [m]
     */

    private static float getFloatValue(XML xmlItem) {
        return Float.parseFloat(getStringValue(xmlItem, "georss:elev"));
    }

}