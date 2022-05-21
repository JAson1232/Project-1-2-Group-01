package PSO;

public class Particle {
    private Velocity velocity;
    private Position location;
    private double fitness;

    public double getFitness(Position toZero) {
        calculateFitness(toZero);
        return fitness;
    }
    // !!! should take vx/vy as parameters for getFitness in golf universe
    public void  calculateFitness(Position zero ){
        double x = this.location.getX();
        double y = this.location.getY();
        //Euclidean distance from zero(x,y) to location(x,y).location in golf universe is vx/vy of a ball
        //probably wrong!!! vx/vy of a ball corresponds to x/y of a Swarm, vx/vy is position in a search Space
        //in Golf and x,y are coords to calculate fitness/euclidean distance to Hole
        //so, the fitness function is distance from ball.state where location is Vx/Vy in a state vector
        fitness = Math.sqrt(Math.pow(x-zero.getX(),2)+Math.pow(y-zero.getY(),2));
    }

    public Position getLocation(){
        return location;
    }
    public void setLocation(Position location){
        this.location = location;
    }

    public Velocity getVelocity(){
        return this.velocity;
    }

    public void setVelocity(Velocity v){
        this.velocity=v;
    }
}
