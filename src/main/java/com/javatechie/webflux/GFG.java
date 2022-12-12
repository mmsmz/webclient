package com.javatechie.webflux;

// JAVA Code for Check whether a given point
// lies inside a triangle or not
import java.util.*;

class GFG {

    /* A utility function to calculate area of triangle
    formed by (x1, y1) (x2, y2) and (x3, y3) */
    static double area(int x1, int y1, int x2, int y2,
                       int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }

    /* A function to check whether point P(x, y) lies
    inside the triangle formed by A(x1, y1),
    B(x2, y2) and C(x3, y3) */
    static boolean isInside(int x1, int y1, int x2,
                            int y2, int x3, int y3, int x, int y)
    {
        /* Calculate area of triangle ABC */
        double A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        double A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        double A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        double A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }

    // Method prints possible triangle when array values are
    // taken as sides
    static boolean isPossibleTriangle(double [] arr, int N)
    {
        // If number of elements are less than 3, then no
        // triangle is possible
        if (N < 3)
            return false;
        // first sort the array
        Arrays.sort(arr);
        // loop for all 3 consecutive triplets
        for (int i = 0; i < N - 2; i++)
            // If triplet satisfies triangle condition, break
            if (arr[i] + arr[i + 1] > arr[i + 2])
                return true;
        return false;
    }

    public static double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static int pointsBelong(int x1, int y1, int x2, int y2, int x3, int y3, int xp, int yp, int xq, int yq) {
        double ab=calculateDistanceBetweenPoints(x1,y1,x2,y2);
        double ac=calculateDistanceBetweenPoints(x1,y1,x3,y3);
        double bc=calculateDistanceBetweenPoints(x2,y2,x3,y3);

        if(isPossibleTriangle(new double[]{ab,ac,bc},3)){
            boolean pInside=isInside(x1,y1,x2,y2,x3,y3,xp,yp);
            boolean qInside=isInside(x1,y1,x2,y2,x3,y3,xq,yq);

            if(pInside && qInside){
                return 3;
            }
            else if(!pInside && !qInside){
                return 4;
            }
            else if(pInside){
                return 1;
            }
            else if(qInside){
                return 2;
            }
        }
        return 0;
    }

    /* Driver program to test above function */
    public static void main(String[] args)
    {

		int check=pointsBelong(3, 1,7, 1,5, 5,3, 1,0,0);
        System.out.println();

    }
}

// This code is contributed by Arnav Kr. Mandal.

