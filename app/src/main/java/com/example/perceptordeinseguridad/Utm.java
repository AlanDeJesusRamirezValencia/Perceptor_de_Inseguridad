package com.example.perceptordeinseguridad;

import org.jetbrains.annotations.NotNull;

/**
 * Utm class
 * Universal Transverse Mercator (UTM) is a system for assigning coordinates to locations on the Earth's surface.
 * This system divides the earth into 60 zones and projects each one onto the plane as the basis for its coordinates.
 * A location is specified using X and Y coordinates, measured in Meters.
 * The hemisphere, north or south, is also specified.
 * @author AlanDeJesusRamirezValencia
 * @author AngelDanielLopezVazquez
 * @version 1.0
 */
public class Utm {

    private double[] coordinatesUMT;
    private final double zone;
    private final int hemisphere;

    /**
     * Major axis of ellipsoid model
     */
    final double sm_a = 6378137.0;

    /**
     * Minor axis of the ellipsoid model
     */
    final double sm_b = 6356752.314;

    /**
     * UTM factor scale
     */
    final double UTMScaleFactor = 0.9996;

    /**
     * Convert degrees to radians
     * @param degreeValue   Degree value
     * @return RadianValue
     */
    private double DegToRad (double degreeValue){
        return (degreeValue / 180.0 * Math.PI);
    }

    /**
     * Unique UTM class constructor
     * @param geographicalCoordinates  coordinates assigned from an array of double values,
     *                                 where the value [0] corresponds to the longitude and [1] to the latitude.
     */
    public Utm(@NotNull double[] geographicalCoordinates){
        coordinatesUMT = new double[2];
        this.zone = (int) Math.floor(LatLonToXY(DegToRad(geographicalCoordinates[1]),
                DegToRad(geographicalCoordinates[0]),
                Math.floor((geographicalCoordinates[0] + 180.0) / 6) + 1, this.coordinatesUMT));
        this.hemisphere = (geographicalCoordinates[1] < 0)? 0: 1;
    }

    /**
     * @return X    The longitude represented in meters
     */
    public double getX(){
        return this.coordinatesUMT[0];
    }

    /**
     * @return Y    The longitude represented in meters
     */
    public double getY(){
        return this.coordinatesUMT[1];
    }

    /**
     * @return hemisphere
     */
    public int getHemisphere(){
        return this.hemisphere;
    }

    /**
     * @return Zone
     */
    public double getZone(){
        return this.zone;
    }

    /**
     * Find the ellipsoidal distance from the equator to a point at a given latitude
     * @param phi , latitude of the point, in radians
     * @return The ellipsoidal distance of the point from the equator, in meters.
     */
    private double ArcLengthOfMeridian (double phi) {
        double alpha, beta, gamma, delta, epsilon, n;

        n = (sm_a - sm_b) / (sm_a + sm_b);
        alpha = ((sm_a + sm_b) / 2.0) * (1.0 + (Math.pow (n, 2.0) / 4.0) + (Math.pow (n, 4.0) / 64.0));
        beta = (-3.0 * n / 2.0) + (9.0 * Math.pow (n, 3.0) / 16.0) + (-3.0 * Math.pow (n, 5.0) / 32.0);
        gamma = (15.0 * Math.pow (n, 2.0) / 16.0) + (-15.0 * Math.pow (n, 4.0) / 32.0);
        delta = (-35.0 * Math.pow (n, 3.0) / 48.0) + (105.0 * Math.pow (n, 5.0) / 256.0);
        epsilon = (315.0 * Math.pow (n, 4.0) / 512.0);
        // calculates the sum of the series
        return alpha * (phi + (beta * Math.sin (2.0 * phi)) + (gamma * Math.sin (4.0 * phi)) +
                (delta * Math.sin (6.0 * phi)) + (epsilon * Math.sin (8.0 * phi)));
    }

    /**
     * Determine the central meridian for the given UTM zone.
     * Zone: an integer value that designates the UTM zone, range [1.60].
     * The central meridian for the given UTM zone, in radians or zero.
     * If the UTM zone parameter is outside the range [1.60].
     * The range of the central meridian is the radian equivalent of [-177, + 177]
     *
     * @param zone  UTM Zone
     */
    private double UTMCentralMeridian (double zone) {
        return DegToRad (-183.0 + (zone * 6.0));
    }

    /**
     * Method that converts latitude and longitude to x, y coordinates into a transverse mercator projection
     * @param phi     , The Latitude of the point, in radians
     * @param lambda  , The Longitude of the point, in radians
     * @param lambda0 , The longitude of the central meridian to be used in radians
     * @param xy      , 2-element array containing x, y coordinates
     */
    private void MapLatLonToXY (double phi, double lambda, double lambda0, @NotNull double[] xy){
        double N, nu2, ep2, t, t2, l;
        double l3Coefficient, l4Coefficient, l5Coefficient, l6Coefficient, l7Coefficient, l8Coefficient;

        ep2 = (Math.pow (sm_a, 2.0) - Math.pow (sm_b, 2.0)) / Math.pow (sm_b, 2.0);
        nu2 = ep2 * Math.pow (Math.cos (phi), 2.0);
        N = Math.pow (sm_a, 2.0) / (sm_b * Math.sqrt (1 + nu2));
        t = Math.tan (phi);
        t2 = t * t;
        l = lambda - lambda0;
        l3Coefficient = 1.0 - t2 + nu2;
        l4Coefficient = 5.0 - t2 + 9 * nu2 + 4.0 * (nu2 * nu2);
        l5Coefficient = 5.0 - 18.0 * t2 + (t2 * t2) + 14.0 * nu2 - 58.0 * t2 * nu2;
        l6Coefficient = 61.0 - 58.0 * t2 + (t2 * t2) + 270.0 * nu2 - 330.0 * t2 * nu2;
        l7Coefficient = 61.0 - 479.0 * t2 + 179.0 * (t2 * t2) - (t2 * t2 * t2);
        l8Coefficient = 1385.0 - 3111.0 * t2 + 543.0 * (t2 * t2) - (t2 * t2 * t2);
        xy[0] = N * Math.cos (phi) * l + (N / 6.0 * Math.pow (Math.cos (phi), 3.0) * l3Coefficient * Math.pow (l, 3.0)) + (N / 120.0 * Math.pow (Math.cos (phi), 5.0) * l5Coefficient * Math.pow (l, 5.0)) + (N / 5040.0 * Math.pow (Math.cos (phi), 7.0) * l7Coefficient * Math.pow (l, 7.0));
        xy[1] = ArcLengthOfMeridian (phi) + (t / 2.0 * N * Math.pow (Math.cos (phi), 2.0) * Math.pow (l, 2.0)) + (t / 24.0 * N * Math.pow (Math.cos (phi), 4.0) * l4Coefficient * Math.pow (l, 4.0)) + (t / 720.0 * N * Math.pow (Math.cos (phi), 6.0) * l6Coefficient * Math.pow (l, 6.0)) + (t / 40320.0 * N * Math.pow (Math.cos (phi), 8.0) * l8Coefficient * Math.pow (l, 8.0));
    }

    /**
     * Method that converts decimal coordinates (longitude, latitude) to UTM (x, y)
     * This method is used by the constructor of the class.
     * @param lat  , latitude
     * @param lon  , longitude
     * @param zone , zone
     * @param xy   , x,y coordinates
     */
    private double LatLonToXY (double lat, double lon, double zone, double[] xy){
        MapLatLonToXY (lat, lon, UTMCentralMeridian (zone), xy);

        xy[0] = xy[0] * UTMScaleFactor + 500000.0;
        xy[1] = xy[1] * UTMScaleFactor;
        if (xy[1] < 0.0){
            xy[1] = xy[1] + 10000000.0;
        }
        coordinatesUMT = xy;
        return zone;
    }
}