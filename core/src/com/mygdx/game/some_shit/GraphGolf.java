package com.mygdx.game.some_shit;

import com.mygdx.game.Game;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.Vector;

import java.io.FileNotFoundException;
import java.util.*;

public class GraphGolf {

    public VertexGolf[][] matrix;

    public Vector start = new Vector(-3, 0, 0, null, 0, 0);
    public Vector hole = new Vector(4,1,0,null,0,0);

    public Game game = new Game();
    Vector[][] input = game.createField();

    public VertexGolf startVertex;
    public VertexGolf holeVertex;

    public GraphGolf() throws FileNotFoundException {
        this.matrix = convertMatrix(input);
    }

    //field converter to VertexGolf 2D array
    public VertexGolf[][] convertMatrix(Vector[][] input) {

        VertexGolf[][] output = new VertexGolf[input.length][input[0].length];

        for (int row = 0; row < output.length; row++) {
            for (int col = 0; col < output[row].length; col++) {

                int x = (int) Math.round(input[row][col].getX());
                int y = (int) Math.round(input[row][col].getY());
                VertexGolf vertex = new VertexGolf(x, y);


                if (input[row][col].getZ() == 9) {
                    vertex.type = 'H'; //hole
                    holeVertex = vertex;
                } else if ((input[row][col].getZ() > 5 && input[row][col].getZ() < 7) || input[row][col].getZ() < 0) {
                    vertex.type = 'X'; //its obstacle/water
                } else if (input[row][col].getZ() == 7.5) {
                    vertex.type = 'S';//start
                    startVertex = vertex;
                } else {
                    vertex.type = '-'; //grass
                }
                output[row][col] = vertex;
                //proly will have to change start S and hole H condition
            }
        }
        return output;
    }

    public VertexGolf getVertex(int x, int y){
        return matrix[x][y];
    }

    public char getVertexType(int x, int y){
        return matrix[x][y].type;
    }

    //method to assign neighbors
    public void assignNeighbors(VertexGolf[][] matrix){

        for (VertexGolf[] q : matrix) {
            for (VertexGolf qq : q) {
                if (qq == null) {
                    System.out.println("null!!!!!");
                    System.exit(0);
                }
                System.out.print(qq.type);
            }
            System.out.println();
        }
//        if(this.matrix == null){
//            convertMatrix(input);
//        }
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                VertexGolf vertex = matrix[row][col];
                //1st neigh
                if (row - 1 >= 0 && col - 1 >= 0) {
                    VertexGolf one = matrix[row - 1][col - 1];
                    if(one.type != 'X') {
                        vertex.neigh.add(one);
                    }
                }
                //2nd neigh
                if (row - 1 >= 0)  {
                    VertexGolf two = matrix[row - 1][col];
                    if(two.type != 'X') {
                        vertex.neigh.add(two);
                    }
                }
                //3rd neigh
                    if (row - 1 >= 0 && col + 1 < matrix[0].length) {
                    VertexGolf three = matrix[row - 1][col + 1];
                    if(three.type != 'X') {
                        vertex.neigh.add(three);
                    }
                }
                //4th neigh
                if (col - 1 >= 0) {
                    VertexGolf four = matrix[row][col - 1];
                    if(four.type != 'X') {
                        vertex.neigh.add(four);
                    }
                }
                //5th neigh
                if (col + 1 < matrix[0].length) {
                    VertexGolf five = matrix[row][col + 1];
                    if(five.type != 'X') {
                        vertex.neigh.add(five);
                    }
                }
                //6th neigh
                if (col - 1 >= 0 && row+1 < matrix.length) {
                    VertexGolf six = matrix[row + 1][col - 1];
                    if(six.type != 'X') {
                        vertex.neigh.add(six);
                    }
                }
                //7th neigh
                if(row+1 < matrix.length) {
                    VertexGolf seven = matrix[row + 1][col];
                    if(seven.type != 'X') {
                        vertex.neigh.add(seven);
                    }
                }
                //8th neigh
                if(row+1 < matrix.length && col+1< matrix[0].length) {
                    VertexGolf eight = matrix[row + 1][col + 1];
                    if(eight.type != 'X') {
                        vertex.neigh.add(eight);

                    }
                }
            }
        }
        System.out.println("one vertex neigh" + " X " + matrix[5][15].neigh.get(6).xCord + " Y " + matrix[5][15].neigh.get(6).yCord);
    }

    // path finding algo
    public VertexGolf Dijkstra(VertexGolf[][] matrix, VertexGolf start){

        start.pathCost = 0;
        Queue queue = new Queue();

        System.out.println("DJKS STARTVERTEX " + " X " + start.xCord + " Y " + start.yCord);

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if(matrix[row][col] != start) {
                    matrix[row][col].pathCost = Integer.MAX_VALUE;
                    matrix[row][col].previous = null;
                }
                queue.add(matrix[row][col]);
            }
        }
        while(queue.size() != 0){
            System.out.println("While");
            VertexGolf min = queue.poll();
            for (int i = 0; i < min.neigh.size(); i++) {
                System.out.println("i");
                int alt = min.pathCost + 1;
                if(alt<min.neigh.get(i).pathCost ){
                    System.out.println("if");
                    min.neigh.get(i).pathCost = alt;
                    min.neigh.get(i).previous = min;
                }
                if(min.neigh.get(i).type == 'H'){
                    System.out.println("DJKS FOUND-VERTEX " + " X " + min.neigh.get(i).xCord + " Y " + min.neigh.get(i).yCord);
                    return min.neigh.get(i);
                }
            }
        }
        return null;
    }

    //method to store the path in x and y cord
    public ArrayList<int[]> storePath(VertexGolf vertex){

        System.out.println("/////////////////////");
        System.out.println("vertex " + "x " + vertex.xCord + "y " + vertex.yCord);
        System.out.println("Previous vertex " + "x " + vertex.previous.xCord + "y " + vertex.previous.yCord);

        ArrayList<int[]> path = new ArrayList<>();
        int xLast = vertex.xCord;
        int yLast = vertex.yCord;
        int[] lastCord = {xLast, yLast};
        path.add(lastCord);
        while(vertex.previous != null){
            System.out.println("while");
            System.out.println("/////////////////////");
            int xPrevCord = vertex.previous.xCord;
            int yPrevCord = vertex.previous.yCord;
            int[] prevCord = {xPrevCord, yPrevCord};
            path.add(prevCord);
            vertex = vertex.previous;
        }
        return path;
    }

    //method to store the path in vectors
    public ArrayList<Vector> convertInVector(ArrayList<int[]> path){
        ArrayList<Vector> vectorPath = new ArrayList<>();
        for(int i = path.size()-1; i >= 0; i--){
            int[] cord = path.get(i);
            int xCord = cord[0];
            int yCord = cord[1];
            Vector vector = new Vector(xCord, yCord, 0, null, 0, 0);
            vectorPath.add(vector);
        }
        return vectorPath;
    }

    //method to run the path with hc
    public void runPathWithHc(ArrayList<Vector> path) throws FileNotFoundException {
        HillClimbing hc = new HillClimbing();
        Vector start = path.get(0);

        for(int i = 1; i<path.size()-1; i++){
            Vector target = path.get(i);
            Vector s = shootInHoleDir(target.getX(), target.getY(), start.getX(), start.getY());
            Vector result = hc.climb(s, target, 0.1);
            double dist = hc.simulate(result, 0.1, target);
            if(dist > 0.15){
                Vector newState = hc.shoot(start, 0.1, path.get(i-1));
                start = newState;
                i--;
            }
        }
    }

    //shoot in target direction
    public static Vector shootInHoleDir(double holeX, double holeY, double x, double y){
        double absX = Math.abs(holeX - x);
        double absY = Math.abs(holeY - y);

        double cosO = (holeX-x)/Math.sqrt(absX*absX + absY*absY);
        double sinO = (holeY-y)/Math.sqrt(absX*absX + absY*absY);

        double xStart = x;
        double yStart = y;

        double Vx = 5 * cosO;
        double Vy = 5 * sinO;

        Vector toHole = new Vector(xStart, yStart, 0, null, Vx, Vy);
        return toHole;
    }

    //make everything work together
    public void run() throws FileNotFoundException {

        GraphGolf graph = new GraphGolf();
        System.out.println("1 GRAPH DONE");
        graph.assignNeighbors(graph.matrix);
        System.out.println("2 ASSIGN DONE");
        VertexGolf target = Dijkstra(graph.matrix, startVertex);
        System.out.println("3 DIJKSTRA DONE");
        System.out.println("djks result " + " X " + target.xCord + " Y " + target.yCord);
        ArrayList<int[]> path = storePath(target);
        System.out.println("4 PATH DONE");
        System.out.println("Path size " + path.size());
        ArrayList<Vector> pathVector = convertInVector(path);
        System.out.println("5 VECTOR PATH DONE");
        runPathWithHc(pathVector);
        System.out.println("6 RUN DONE");
    }

    public static void main(String[] args) throws FileNotFoundException {
        new GraphGolf().run();
        System.out.println("MAIN DONE");
    }

    //hard coded priority queue
    static class Queue {
        ArrayList<VertexGolf> q = new ArrayList<>();

        public VertexGolf poll() {
            if (q.size() == 0)
                return null;
            VertexGolf min = q.get(0);
            for (VertexGolf vertexGolf : q) {
                if (vertexGolf.pathCost < min.pathCost) {
                    min = vertexGolf;
                }
            }
            q.remove(min);
            return min;
        }
        public int size(){
            return q.size();
        }

        public void add(VertexGolf v) {
            q.add(v);
        }
    }
}
