package com.mygdx.game.some_shit;

interface DFSVisitor<T> {
    //Called by the graph traversal methods when a vertex is first visited.
    public void visit(Graph<T> g, Vertex<T> v);
}
