package com.mygdx.game.Phase3;

import com.mygdx.game.Game;
import com.mygdx.game.HillClimbing;
import com.mygdx.game.Vector;

import java.io.FileNotFoundException;
import java.util.*;

public class GraphGolf {

    public VertexGolf[][] matrix;

    public Game game = new Game();
    Vector[][] input = game.createField();

    public VertexGolf startVertex;
    public VertexGolf holeVertex;

    //public static Display disp = new Display();

    public GraphGolf() throws FileNotFoundException {
        this.matrix = convertMatrix(input);
    }

    //field converter to VertexGolf 2D array
    public VertexGolf[][] convertMatrix(Vector[][] input) {

        VertexGolf[][] output = new VertexGolf[input.length][input[0].length];

        for (int row = 0; row < output.length; row++) {
            for (int col = 0; col < output[row].length; col++) {

                int x = (int) (input[row][col].getX());
                int y = (int) (input[row][col].getY());
                VertexGolf vertex = new VertexGolf(x, y);


                if (input[row][col].getZ() == 9) {
                    vertex.type = 'H'; //hole
                    System.out.println("H" +" row " + input[row][col].getX() + " col " + input[row][col].getY());
                    holeVertex = vertex;

                } else if ((input[row][col].getZ() > 5 && input[row][col].getZ() < 7) || input[row][col].getZ() < 0) {
                    vertex.type = 'X'; //its obstacle/water
                    //System.out.println("X" +" row " + input[row][col].getX() + " col " + input[row][col].getY());

                } else if (input[row][col].getZ() == 7.5) {
                    vertex.type = 'S';//start
                    System.out.println("S" +" row " + input[row][col].getX() + " col " + input[row][col].getY());
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

        //int counter = 0;
        for (VertexGolf[] q : matrix) {
            //counter++;
            //System.out.println(counter);
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
        //System.out.println("one vertex neigh" + " X " + matrix[5][15].neigh.get(6).xCord + " Y " + matrix[5][15].neigh.get(6).yCord);
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
            //System.out.println("While");
            VertexGolf min = queue.poll();
            for (int i = 0; i < min.neigh.size(); i++) {
                //System.out.println("i");
                int alt = min.pathCost + 1;
                if(alt<min.neigh.get(i).pathCost ){
                    //System.out.println("if");
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

//        System.out.println("/////////////////////");
//        System.out.println("vertex " + "x " + vertex.xCord + "y " + vertex.yCord);
//        System.out.println("Previous vertex " + "x " + vertex.previous.xCord + "y " + vertex.previous.yCord);
        System.out.println("START VERTEX " + "X " + startVertex.xCord + "Y " + startVertex.yCord);
        ArrayList<int[]> path = new ArrayList<>();
        int xLast = vertex.xCord;
        int yLast = vertex.yCord;
        int[] lastCord = {xLast, yLast};
        path.add(lastCord);
        while(vertex.xCord != startVertex.xCord && vertex.yCord !=startVertex.yCord){
            //vertex.previous != null
            //System.out.println("while");
//            System.out.println("vertex " + "x " + vertex.xCord + "y " + vertex.yCord);
//            System.out.println("Previous vertex " + "x " + vertex.previous.xCord + "y " + vertex.previous.yCord);
            int xPrevCord = vertex.previous.xCord;
            int yPrevCord = vertex.previous.yCord;
            int[] prevCord = {xPrevCord, yPrevCord};
            //System.out.println("Prev cord " + Arrays.toString(prevCord));
            path.add(prevCord);
            vertex = vertex.previous;
            //System.out.println("/////////////////////");
        }
        return path;
    }

    //method to store the path in vectors
    public ArrayList<Vector> convertInVector(ArrayList<int[]> path){
        ArrayList<Vector> vectorPath = new ArrayList<>();

        //dont touch for now
        int xFirst = startVertex.xCord;
        int yFirst = startVertex.yCord;
        Vector initialPos = new Vector(xFirst, yFirst,0,null,0,0);
        vectorPath.add(initialPos);

        for(int i = path.size()-1; i >= 0; i--){
            int[] cord = path.get(i);
            int xCord = cord[0];
            int yCord = cord[1];;
            Vector vector = new Vector(xCord, yCord, 0, null, 0, 0);
            vectorPath.add(vector);
        }
        return vectorPath;
    }

    //method to run the path with hc
    public ArrayList<Vector> runPathWithHc(ArrayList<Vector> path) throws FileNotFoundException {
        System.out.println("/////////////////////");
        HillClimbing hc = new HillClimbing();
        RuleBased ruleBot = new RuleBased();

        ArrayList<Vector> pathToFollow = new ArrayList<Vector>();

        Vector start = path.get(0);
        System.out.println("start " + start);

        Vector sPrev = start;

        for(int i = 1; i<path.size(); i++){
            Vector target = path.get(i);
            Vector s = ruleBot.constructVector(start, target);
            System.out.println("s " + s);
            System.out.println("target " + target);

            Vector result = hc.shoot(s, 0.1);
            System.out.println("SHOOT RESULT " + result);

            double dist = hc.simulate(s, 0.1, target);
            System.out.println("distance " + dist);

            if (dist > 0.35){  //never gets closer
                System.out.println("if");
                start = result;
                System.out.println("new start " + start);
                i--;
                pathToFollow.add(sPrev);
                System.out.println("/////////////////////");
            }
            sPrev = s;
        }
        return pathToFollow;
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
        for(int i=0; i<pathVector.size(); i++){
            System.out.println(pathVector.get(i));
        }
        System.out.println("5 VECTOR PATH DONE");
        ArrayList<Vector> pathToFolow= runPathWithHc(pathVector);
        for (int i = 0; i < pathToFolow.size(); i++) {
            System.out.println(pathToFolow.get(i));
        }
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
