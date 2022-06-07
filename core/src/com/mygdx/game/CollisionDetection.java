package com.mygdx.game;

public class CollisionDetection {
    CollisionDetection(){

    }

    /***
     * this method is used for checking the intersection exist
     * @param ballStartX
     * @param ballStartY
     * @param ballEndX
     * @param ballEndY
     * @param wallLeftX
     * @param wallLeftY
     * @param wallRightX
     * @param wallRightY
     * @return true means here is an intersection between the ball trajectory line segment and the wall line segment
     */
    public static boolean check(double ballStartX, double ballStartY,
                         double ballEndX, double ballEndY,
                         double wallLeftX, double wallLeftY,
                         double wallRightX,double wallRightY){
        boolean isOverlapped;
        //Check that the projections of the two line segments on the X and Y axes do not overlap
        if(!checkCastOverlap(ballStartX,ballEndX,wallLeftX,wallRightX)||!checkCastOverlap(ballStartY,ballEndY,wallLeftY,wallRightY)){
            return false;
        }else{
            if(checkSpanEachOther(
                    ballStartX,ballStartY,
                    ballEndX,ballEndY,
                    wallLeftX,wallLeftY,
                    wallRightX,wallRightY)){
                return true;
            }
        }
        return false;
    }

    /***
     * Check whether the projections of two line segments overlap
     * @param x1
     * @param x2
     * @param x3
     * @param x4
     * @return true means the cast of the two line segments overlap each other
     */
    public static boolean checkCastOverlap(double x1, double x2, double x3, double x4){
        if((x1<=x3&&
           x2<=x3&&
           x1<=x4&&
           x2<=x4)
        &&
            (x1>=x3&&
             x2>=x3&&
             x1>=x4&&
             x2>=x4))

        {
            return false;
        }else{
            return true;
        }
    }

    /***
     *  Check if two line segments span each other
     * @param ballStartX
     * @param ballStartY
     * @param ballEndX
     * @param ballEndY
     * @param wallLeftX
     * @param wallLeftY
     * @param wallRightX
     * @param wallRightY
     * @return true means the two line segments span each other
     */
    public static boolean checkSpanEachOther(double ballStartX, double ballStartY,
                                             double ballEndX, double ballEndY,
                                             double wallLeftX, double wallLeftY,
                                             double wallRightX, double wallRightY){
        //vector1 = the vector that from wall left point to ball start point
        double vector1X = wallLeftX-ballStartX;
        double vector1Y = wallLeftY-ballStartY;
        //vector2 = the vector that from ball end point to ball start point
        double vector2X = ballEndX-ballStartX;
        double vector2Y = ballEndY-ballStartY;
        //vector3 = the vector that from wall right point to ball start point
        double vector3X = wallRightX-ballStartX;
        double vector3Y = wallRightY-ballStartY;

        //vector4 = the vector that from ball end point to wall left point
        double vector4X = ballEndX-wallLeftX;
        double vector4Y = ballEndY-wallLeftY;
        //vector5 = the vector that from wall right point to wall left point
        double vector5X = wallRightX-wallLeftX;
        double vector5Y = wallRightY-wallLeftY;
        //vector5 = the vector that from ball start point to wall left point
        double vector6X = ballStartX-wallLeftX;
        double vector6Y = ballStartY-wallLeftY;
        if(
                ((vector1X*vector2Y-vector1Y*vector2X)<=0)
                        &&((vector3X*vector2Y-vector3Y*vector2X)<=0)
                &&
                ((vector4X*vector5Y-vector4Y*vector5X)<=0)
                        &&((vector6X*vector5Y-vector6Y*vector5X)<=0)
        ){
                return true;
        }else{
            return false;
        }
    }
}
