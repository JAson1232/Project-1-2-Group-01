package com.mygdx.game.some_shit;

import com.mygdx.game.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BIGclass {

    private static Vector start = new Vector(-3,0,0,null,0,0);
    private static Vector hole = new Vector(4,1,0,null,0,0);

    HillClimbing hc = new HillClimbing();

    HeightFunction fhc  = new HeightFunction();
    PartialDerivative px = new PartialDerivative(fhc);
    MathFunctions matFun = new MathFunctions();

    private static Queue<VertexGolf> path = new LinkedList<>();
    private static GraphGolf graph = new GraphGolf();

    //////main method//////////
    public void runPath (Vector start, Vector hole) throws FileNotFoundException {
        path = findPath(start, hole);
        while(path.size() != 0){
            double newVx = path.poll().getXCord();
            double newVy = path.poll().getYCord();
            Vector state = new Vector(start.getX(), start.getY(), 0, null, newVx, newVy);

            Game game = new Game();
            Ball ball = new Ball(state);

            ball = game.moveBall(ball);
            state = ball.state;
//
//            Vector nextVector = new Vector(state.getX(), state.getY(), 0, null, )
//            return
        }
    }

    public Queue<VertexGolf> findPath(Vector start, Vector hole) throws FileNotFoundException {
        VertexGolf root = new VertexGolf(start.getVx(), start.getVy(), false,false);
        System.out.println("root: " + "Vx " +root.getXCord() + " Vy " + root.getYCord());

        //the root has starting x nd y cord
        graph.setRoot(root);

        //create 5 children of the start vec
        System.out.println("start " + start);
        Vector[] children = createChildren(start, 2.5);
        System.out.println("children " + Arrays.toString(children));

        //find best children
        Vector bestChi = returnBest(children);
        System.out.println("best " + bestChi);

        //delete after
        Vector bestChildrenAfterShoot = simulate(bestChi, 0.1);
        double distatnceOfBestToHole = bestChildrenAfterShoot.distanceTo(hole);
        System.out.println("bestChildrenAfterShoot " + bestChildrenAfterShoot);
        System.out.println("distatnceOfBestToHole " + distatnceOfBestToHole);
        ///

        double distToHole = calcDistatnce(bestChi, 0.1);
        System.out.println("dist to hole " + distToHole);//fitness

        //create a new vertex
        System.out.println("######################################################################");
        VertexGolf next = new VertexGolf(bestChi.getVx(), bestChi.getVy(), false, false);
        EdgeGolf cost = new EdgeGolf(root, next, distToHole);
        path.add(next); //adding to path

        //recursion comes
        if(distToHole < 1.0) {
//            System.out.println("path =====");
//            System.out.println(path.toString());
//            return path;
            System.out.println("start climb");
            Vector lastShoot = climbbb(bestChi, hole, 0.15);
            System.out.println("lastShoot " + lastShoot);
            VertexGolf last = new VertexGolf(lastShoot.getVx(), lastShoot.getVy(), false, false);
            path.add(last);
            return path;
        }
        else{
         return findPath(bestChi, hole);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Vector start = new Vector(-3,0,0,null,0,0);
        Vector hole = new Vector(4,1,0,null,0,0);
        BIGclass ccc = new BIGclass();
        ccc.findPath(start, hole);
        System.out.println("--------------------------------------------------------------------");
        System.out.println("path queue " + path.toString());
    }


    //////help methods/////////

    public Vector climbbb(Vector state, Vector hole, double n) throws FileNotFoundException {

        System.out.println("VECTOR BEFORE: " + state);
        Vector stateCopy = state;

        while (HillClimbing.hasNotStopped(stateCopy, px)) {
            if (Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy()) > 5) {
                double v = Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy());
                stateCopy.setVx(5 * stateCopy.getVx() / v);
                stateCopy.setVy(5 * stateCopy.getVy() / v);
            }
            stateCopy = matFun.RK4(stateCopy, 0.1);
            //stateCopy = matFun.euler(stateCopy, 0.1);
            // System.out.println(stateCopy.distanceTo(hole));
        }
        System.out.println("VECTOR AFTER: " + stateCopy);
        System.out.println("DISTANCE " + stateCopy.distanceTo(hole));
        if(stateCopy.distanceTo(hole) <= 0.1){
            System.out.println("is in hole 1");
            return state;
        }
//        else if (stateCopy.getX() == hole.getX() && stateCopy.getY() == hole.getY() || Math.abs(stateCopy.getX() - hole.getX()) < 0.17 && Math.abs(stateCopy.getY() - hole.getY()) < 0.17) {
//            System.out.println("is in hole 2");
//            return state;
//        }

        double[] neighVx = new double[2];
        neighVx[0] = state.getVx() + n;
        neighVx[1] = state.getVx() - n;

        double[] neighVy = new double[2];
        neighVy[0] = state.getVy() + n;
        neighVy[1] = state.getVy() - n;

        Vector state00 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[0], neighVy[0]);
        //System.out.println("state00 " + state00);
        Vector state00Copy = state00;

        Vector state01 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[0], neighVy[1]);
        //System.out.println("state01 " + state01);
        Vector state01Copy = state01;

        Vector state10 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[1], neighVy[0]);
        //System.out.println("state10 " + state10);
        Vector state10Copy = state10;

        Vector state11 = new Vector(state.getX(), state.getY(), state.getZ(), null, neighVx[1], neighVy[1]);
        //System.out.println("state11 " + state11);
        Vector state11Copy = state11;

        double dist00 = hc.simulate(state00Copy, 0.1, hole);
        //System.out.println("dist00 is " + dist00);
        double dist01 = hc.simulate(state01Copy, 0.1, hole);
        //System.out.println("dist01 is " + dist01);
        double dist10 = hc.simulate(state10Copy, 0.1, hole);
        //System.out.println("dist10 is " + dist10);
        double dist11 = hc.simulate(state11Copy, 0.1, hole);
        //System.out.println("dist11 is " + dist11);

        if (dist00 < dist01 && dist00 < dist10 && dist00 < dist11) {
            //System.out.println("dist00 is smallest");
            return climbbb(state00, hole, n);
        }
        if (dist01 < dist00 && dist01 < dist10 && dist01 < dist11) {
            //System.out.println("dist01 is smallest");
            return climbbb(state01, hole, n);
        }
        if (dist10 < dist00 && dist10 < dist01 && dist10 < dist11) {
            //System.out.println("dist10 is smallest");
            return climbbb(state10, hole, n);
        }
        if (dist11 < dist00 && dist11 < dist01 && dist11 < dist10) {
            //System.out.println("dist11 is smallest");
            return climbbb(state11, hole, n);
        }
        return state;
    }


    public Vector returnBest(Vector[] arr) throws FileNotFoundException {
        //all distances
        double fir = calcDistatnce(arr[0], 0.1);
        double sec = calcDistatnce(arr[1], 0.1);
        double the = calcDistatnce(arr[2], 0.1);
        double fou = calcDistatnce(arr[3], 0.1);
        double fif = calcDistatnce(arr[4], 0.1);
        //check
        if(fir<sec && fir<the && fir<fou && fir<fif) {
            Vector zeroAfter = simulate(arr[0], 0.1);
            Vector zero = new Vector(zeroAfter.getX(), zeroAfter.getY(), 0, null, arr[0].getVx(), arr[0].getVy());
            return zero;
            //return simulate(arr[0], 0.1);
        }
        if(sec<fir && sec<the && sec<fou && sec<fif) {
            Vector oneAfter = simulate(arr[1], 0.1);
            Vector one = new Vector(oneAfter.getX(), oneAfter.getY(), 0, null, arr[1].getVx(), arr[1].getVy());
            return one;
            //return simulate(arr[1], 0.1);
        }
        if(the<sec && the<fir && the<fou && the<fif) {
            Vector twoAfter = simulate(arr[2], 0.1);
            Vector two = new Vector(twoAfter.getX(), twoAfter.getY(), 0, null, arr[2].getVx(), arr[2].getVy());
            return two;
            //return simulate(arr[2], 0.1);
        }
        if(fou<sec && fou<the && fou<fir && fou<fif) {
            Vector threeAfter = simulate(arr[3], 0.1);
            Vector three = new Vector(threeAfter.getX(), threeAfter.getY(), 0, null, arr[3].getVx(), arr[3].getVy());
            return three;
            //return simulate(arr[3], 0.1);
        }
        if(fif<sec && fif<the && fif<fou && fif<fir) {
            Vector fourAfter = simulate(arr[4], 0.1);
            Vector four = new Vector(fourAfter.getX(), fourAfter.getY(), 0, null, arr[4].getVx(), arr[4].getVy());
            return four;
            //return simulate(arr[4], 0.1);
        }
        return null;
    }

    public Vector simulate(Vector state, double h) throws FileNotFoundException {
        while (hasNotStopped(state, px, 0.1)) {
            if (Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy()) > 5) {
                double v = Math.sqrt(state.getVx() * state.getVx() + state.getVy() * state.getVy());
                state.setVx(5 * state.getVx() / v);
                state.setVy(5 * state.getVy() / v);
            }
            state = matFun.RK4(state, h);
        }
        return state;
    }

    public double calcDistatnce(Vector state, double h) throws FileNotFoundException {
        Vector stateCopy = state;
        while (hasNotStopped(stateCopy, px, 0.1)) {
            if (Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy()) > 5) {
                double v = Math.sqrt(stateCopy.getVx() * stateCopy.getVx() + stateCopy.getVy() * stateCopy.getVy());
                stateCopy.setVx(5 * stateCopy.getVx() / v);
                stateCopy.setVy(5 * stateCopy.getVy() / v);
            }
            stateCopy = matFun.RK4(stateCopy, h);
        }
        //System.out.println("stateStop " + stateCopy);
        double dist = stateCopy.distanceTo(hole);
        return dist;
    }

    /**
     *
     * @param vX current vx of the ball
     * @param vY current vy of the ball
     * @param angle the angle u want to plus or minus
     * @return an array with element 0 is new vx and element 1 is new vy
     */
    public double[] changeAngle(double vX,double vY, double angle){
        double vxX = 0;
        double vyY = 0;

        double cosBig = vX/(Math.sqrt(vX*vX+vY*vY));
        double sinBig = vY/(Math.sqrt(vX*vX+vY*vY));
        double pi_Big = Math.acos(cosBig);
        double big = Math.toDegrees(pi_Big);

        double newAngle = big-angle;
        double newRadian = Math.toRadians(newAngle);

        vxX = 5 * Math.cos(newRadian);
        vyY = 5 * Math.sin(newRadian);


        double [] output;
        return output = new double[]{vxX, vyY};
    }
    
    public Vector[] createChildren(Vector currentPos, double n){
        Vector[] children = new Vector[5];
        double holeX = hole.getX();
        double holeY = hole.getY();
        double startX = currentPos.getX();
        double startY = currentPos.getY();
        // 1st - shoot in hole direction
        Vector toHole  = shootInHoleDirTwo(holeX, holeY, startX, startY);
        children[0] = toHole;
        // 2nd - Vx-n
        double[] ang45 = changeAngle(toHole.getVx(), toHole.getVy(), 45);
        Vector two = new Vector(toHole.getX(), toHole.getY(), 0, null, ang45[0], ang45[1]);
        children[1] = two;
        // 3rd - Vx-2n
        double[] angMinus45 = changeAngle(toHole.getVx(), toHole.getVy(), -45);
        Vector three = new Vector(toHole.getX(), toHole.getY(), 0, null, angMinus45[0], angMinus45[1]);
        children[2] = three;
        // 4th - Vy-n
        double[] ang90 = changeAngle(toHole.getVx(), toHole.getVy(), 90);
        Vector four = new Vector(toHole.getX(), toHole.getY(), 0, null, ang90[0], ang90[1]);
        children[3] = four;
        // 5th - Vy-2n
        double[] angMinus90 = changeAngle(toHole.getVx(), toHole.getVy(), -90);
        Vector five = new Vector(toHole.getX(), toHole.getY(), 0, null, angMinus90[0], angMinus90[1]);
        children[4] = five;

        return children;
    }

    public static Vector shootInHoleDir(double holeX, double holeY, double x, double y){
        Vector toHole = new Vector(x, y, 0, null, holeX-x, holeY-y);
        return toHole;
    }

    public static Vector shootInHoleDirTwo(double holeX, double holeY, double x, double y){
        double absX = Math.abs(holeX - x);//dx
        double absY = Math.abs(holeY - y);//dy

        double cosO = (holeX-x)/Math.sqrt(absX*absX + absY*absY);
        double sinO = (holeY-y)/Math.sqrt(absX*absX + absY*absY);

        double xStart = x;
        double yStart = y;

        double Vx = 5 * cosO;
        double Vy = 5 * sinO;

        Vector toHole = new Vector(xStart, yStart, 0, null, Vx, Vy);
        return toHole;
    }

    // stopping condition
    public static boolean hasNotStopped(Vector state, PartialDerivative px, double stepSize) throws FileNotFoundException {
        if (!((state.getVx() < stepSize * 5 && state.getVx() > stepSize * -5) && ((state.getVy() < stepSize * 5 && state.getVy() > stepSize * -5)))) {
            return true;
        }
        double x = px.getX(state.getX(), state.getY(), state.getVx(), state.getVy());
        double y = px.getY(state.getX(), state.getY(), state.getVx(), state.getVy());
        double value = x * x + y * y;
        return Field.frictionStatic <= Math.sqrt(value);
    }
}
