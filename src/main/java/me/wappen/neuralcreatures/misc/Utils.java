package me.wappen.neuralcreatures.misc;

import processing.core.PVector;

public final class Utils {
    public static boolean circleLineIntersection(PVector start, PVector end, PVector circle, float radius) {
        if (pointInBounds(circle.copy().sub(start), end.copy().sub(start))) { // Check line intersection
            PVector normalizedCircle = circle.copy().sub(start);
            PVector slope = end.copy().sub(start);

            return Math.abs(normalizedCircle.rotate(-slope.heading()).y) <= radius;
        }
        else { // Check endpoint intersection
            return circle.dist(start) <= radius || circle.dist(end) <= radius;
        }
    }

    public static boolean pointInBounds(PVector normalizedPoint, PVector bounds) {
        return normalizedPoint.x >= 0 && normalizedPoint.x <= bounds.x &&
                normalizedPoint.y >= 0 && normalizedPoint.y <= bounds.y;
    }
}
