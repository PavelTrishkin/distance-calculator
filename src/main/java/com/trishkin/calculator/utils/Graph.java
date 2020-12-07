package com.trishkin.calculator.utils;

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

//    public void display() {
//        for (int i = 0; i < getVertexSize(); i++) {
//            System.out.print(vertexList.get(i));
//            for (int j = 0; j < getVertexSize(); j++) {
//                if (adjMat[i][j] > 0) {
//                    System.out.print("- " + adjMat[i][j] + " -> " + vertexList.get(j));
//                }
//            }
//            System.out.println();
//        }
//    }

    private void resetVertexState() {
        for (Vertex vertex: vertexList) {
            vertex.setVisited(false);
        }
    }

    private void visitVertex(Vertex vertex, Queue<Vertex> vertexQueue) {
        System.out.println(vertex);
        vertexQueue.add(vertex);
        vertex.setVisited(true);
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

    public Map<Double, Stack<String>> findShortPathViaBfs(String startLabel, String finishLabel) {
        int startIndex = indexOf(startLabel);
        int finishIndex = indexOf(finishLabel);
        Map<Double, Stack<String>> routes = new HashMap<>();
        if (startIndex == -1) {
            throw new IllegalArgumentException("Invalid startLabel: " + startLabel);
        }
        if (finishIndex == -1) {
            throw new IllegalArgumentException("Invalid finishLabel: " + finishLabel);
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
                System.out.println("STACK PEEK " + previous);
                vertex.setPreviousVertex(previous);
                if (vertex.getLabel().equals(finishLabel)) {
                    routes.putAll(buildPath(vertex));
                    vertexStack.pop();

                }
            }

        }
        return routes;
    }

    private void visitVertex(Vertex vertex, Stack<Vertex> vertexStack) {
        System.out.println(vertex);
        vertexStack.push(vertex);
        vertex.setVisited(true);
    }


    private Map<Double, Stack<String>> buildPath(Vertex vertex) {
        Stack<String> stack = new Stack<>();
        Vertex current = vertex;
        double dist = 0;
        Map<Double, Stack<String>> routes = new HashMap<>();
        while (current != null) {
            stack.push(current.getLabel());
            dist += getDistance(current, current.getPreviousVertex());
            current = current.getPreviousVertex();
        }

        routes.put(dist, stack);
        return routes;
    }
}
