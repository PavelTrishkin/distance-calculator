package com.trishkin.calculator.utils;

import com.trishkin.calculator.exceptions.CityNotFoundException;

import java.util.*;

public class Graph {
    private final List<Vertex> vertexList;
    private final double[][] adjMat;

    public Graph(int maxVertexCount) {
        this.vertexList = new ArrayList<>(maxVertexCount);
        this.adjMat = new double[maxVertexCount][maxVertexCount];
    }

    public void addVertex(String label){
        vertexList.add(new Vertex(label));
    }

    public void addEdges(String startLabel, String endLabel, double val){
        int startIndex = indexOf(startLabel);
        int endIndex = indexOf(endLabel);

        if (startIndex == -1 || endIndex == -1){
            throw new IllegalArgumentException("Invalid label for vertex");
        }

        adjMat[startIndex][endIndex] = val;
        adjMat[endIndex][startIndex] = val;

    }

    private int indexOf(String vertexLabel) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexLabel.equals(vertexList.get(i).getLabel())){
                return i;
            }
        }
        return  -1;
    }

    public int getVertexSize(){
        return vertexList.size();
    }

    private void resetVertexState() {
        for (Vertex vertex: vertexList) {
            vertex.setVisited(false);
        }
    }


    public double getDistance(Vertex from, Vertex to) {
        if(from != null && to != null){
            int indexForFrom = vertexList.indexOf(from);
            int indexForTo = vertexList.indexOf(to);

            return adjMat[indexForFrom][indexForTo];
        }
        return 0;
    }

    private Vertex getUnvisitedVertex(Vertex peek) {
        int peekIndex = vertexList.indexOf(peek);

        for (int i = 0; i < getVertexSize(); i++) {
            if(adjMat[peekIndex][i] > 0 && !vertexList.get(i).isVisited()){
                return vertexList.get(i);
            }
        }

        return null;
    }

    public Map<Float, Stack<String>> findShortPathViaBfs(String startLabel, String finishLabel) {
        int startIndex = indexOf(startLabel);
        int finishIndex = indexOf(finishLabel);
        Map<Float, Stack<String>> routes = new HashMap<>();
        if (startIndex == -1) {
            throw new CityNotFoundException("Can't found city with name = " + startLabel + " in Distance matrix");
        }
        if (finishIndex == -1) {
            throw new CityNotFoundException("Can't found city with name = " + finishLabel + " in Distance matrix");
        }

        Stack<Vertex> vertexStack = new Stack<>();

        Vertex vertex = vertexList.get(startIndex);
        visitVertex(vertex, vertexStack);
        while (!vertexStack.empty()){
            Vertex previous = vertex;
            vertex = getUnvisitedVertex(vertexStack.peek());
            if(previous == null){
                previous = vertexList.get(startIndex);
            }
            if (vertex == null){
                vertexStack.pop();
                vertexList.get(finishIndex).setVisited(false);
            }
            else {
                visitVertex(vertex, vertexStack);
                vertex.setPreviousVertex(previous);
                if (vertex.getLabel().equals(finishLabel)) {
                    routes.putAll(buildPath(vertex));
                    vertexStack.pop();

                }
            }

        }
        resetVertexState();
        return routes;
    }

    private void visitVertex(Vertex vertex, Stack<Vertex> vertexStack) {
        vertexStack.push(vertex);
        vertex.setVisited(true);
    }


    private Map<Float, Stack<String>> buildPath(Vertex vertex) {
        Stack<String> stack = new Stack<>();
        Vertex current = vertex;
        float dist = 0;
        Map<Float, Stack<String>> routes = new HashMap<>();
        while (current != null) {
            stack.push(current.getLabel());
            dist += getDistance(current, current.getPreviousVertex());
            current = current.getPreviousVertex();
        }

        routes.put(dist, reversStack(stack));
        return routes;
    }

    private Stack<String> reversStack(Stack<String> stack){
        Stack<String> revers = new Stack<>();

        while (!stack.empty()){
            revers.push(stack.pop());
        }
        return revers;
    }
}
