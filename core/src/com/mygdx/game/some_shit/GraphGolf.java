package com.mygdx.game.some_shit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphGolf {
    private List<VertexGolf> vertices;
    private List<EdgeGolf> edges;
    private VertexGolf root;

    public GraphGolf(){
        vertices = new ArrayList<VertexGolf>();
        edges = new ArrayList<EdgeGolf>();
    }
    public boolean isEmpty(){
        return vertices.size() == 0;
    }
    public void addVertex(VertexGolf v){
        if (!vertices.contains(v)) {
            vertices.add(v);
        }
        else
            vertices.add(v);
    }
    public int getSize(){
        return vertices.size();
    }
    public VertexGolf getRoot(){
        return this.root;
    }
    public void setRoot(VertexGolf root){
        this.root = root;
        if(vertices.contains(root) == false)
            this.addVertex(root);
    }
    public VertexGolf getVertex(int n){
        return vertices.get(n);
    }
    public List<VertexGolf> getVertices(){
        return this.vertices;
    }
    public void addEdge(VertexGolf from, VertexGolf to, int cost) throws IllegalArgumentException {
        if (vertices.contains(from) == false)
            throw new IllegalArgumentException("from is not in graph");
        if (vertices.contains(to) == false)
            throw new IllegalArgumentException("to is not in graph");
        EdgeGolf e = new EdgeGolf(from, to, cost);
    }
    public List<EdgeGolf> getEdges(){
        return this.edges;
    }
    public boolean removeVertex(VertexGolf v) {
        if (!vertices.contains(v))
            return false;
        vertices.remove(v);
        if (v == root)
            root = null;
        // Remove the edges associated with v
        for (int n = 0; n < v.getOutgoingEdge().size(); n++) {
            EdgeGolf e = v.getOutgoingEdge().get(n);
            v.getOutgoingEdge().remove(e);
            VertexGolf to = e.getTo();
            to.getOutgoingEdge().remove(e);
            edges.remove(e);
        }
        v.setIncomingEdge(null);
        return true;
    }
    public boolean removeEdge(VertexGolf from, VertexGolf to) {
        EdgeGolf e = from.findEdge(to);
        if (e == null)
            return false;
        else {
            from.remove(e);
            to.remove(e);
            edges.remove(e);
            return true;
        }
    }

}
