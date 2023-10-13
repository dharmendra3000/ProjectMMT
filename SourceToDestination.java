import java.util.*;
import java.lang.*;

class Pair<U,V>{
    U first;
    V second;

    Pair(U first, V second){
        this.first=first;
        this.second=second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public void setFirst(U first) {
        this.first=first;
    }

    public void setSecond(V second) {
        this.second=second;
    }

}

class Graph {
    public ArrayList<ArrayList<Pair<Float, Pair<Character, Integer>>>> adj;
    public HashMap<String, Integer> stateMappingNumber;
    public int countOfState;

    public Graph(int size) {
        adj = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            adj.add(new ArrayList<>());
        }
        stateMappingNumber = new HashMap<>();
        countOfState = 0;
    }

    public void addInGraph(int source, int destination, float price, float time, char mode) {
        adj.get(source).add(new Pair<>(price, new Pair<>(mode, destination)));
    }

    public void makeGraph(String source, String destination, float price, float time, char mode) {
        int sourceNum, destinationNum;

        if (stateMappingNumber.containsKey(source)) {
            sourceNum = stateMappingNumber.get(source);
        } else {
            sourceNum = countOfState;
            stateMappingNumber.put(source, countOfState);
            countOfState++;
        }

        if (stateMappingNumber.containsKey(destination)) {
            destinationNum = stateMappingNumber.get(destination);
        } else {
            destinationNum = countOfState;
            stateMappingNumber.put(destination, countOfState);
            countOfState++;
        }

        addInGraph(sourceNum, destinationNum, price, time, mode);
    }

    public int minimumTimeRoute(int source, int destination) {
        PriorityQueue<Pair<Integer, Pair<Integer, String>>> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.getFirst(), b.getFirst())
        );

        pq.add(new Pair<>(0, new Pair<>(source, "")));

        int[] vis = new int[countOfState];

        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, String>> top = pq.poll();

            int time = top.getFirst();
            int node = top.getSecond().getFirst();
            String path = top.getSecond().getSecond();

            vis[node] = 1;

            if (node == destination) {
                System.out.println(path);
                return time;
            }

            for (Pair<Float, Pair<Character, Integer>> it : adj.get(node)) {
                if (vis[it.getSecond().getSecond()] == 0) {
                    String temp = " ";
                    temp += it.getSecond().getSecond() + "_";
                    temp += it.getSecond().getFirst();
                    pq.add(new Pair<>(Math.round(time + it.getFirst()), new Pair<>(it.getSecond().getSecond(), path + temp)));
                }
            }
        }

        System.out.println("No route");
        return -1;
    }
}




class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(1000000);

        graph.makeGraph("Delhi", "Chandigarh", 5000.0f, 2.0f, 'F');
        graph.makeGraph("Delhi", "Chandigarh", 2000.0f, 4.0f, 'T');
        graph.makeGraph("Delhi", "Chandigarh", 1000.0f, 6.0f, 'B');



        graph.makeGraph("Delhi","Patiala",1800.0f,6.0f,'T');
        graph.makeGraph("Delhi","Patiala",800.0f,8.0f,'B');

        graph.makeGraph("Delhi","Pune",6000.0f,3.0f,'F');
        graph.makeGraph("Delhi","Pune",3500.0f,14.0f,'T');
        graph.makeGraph("Delhi","Pune",2000.0f,20.0f,'B');

        graph.makeGraph("Chandigarh","Delhi",4500.0f,2.0f,'F');
        graph.makeGraph("Chandigarh","Delhi",1000.0f,6.0f,'B');
        graph.makeGraph("Chandigarh","Delhi",2000.0f,4.0f,'T');


        graph.makeGraph("Chandigarh","Patiala",400.0f,1.0f,'T');
        graph.makeGraph("Chandigarh","Patiala",200.0f,2.0f,'B');

        graph.makeGraph("Chandigarh","Pune",8000.0f,4.0f,'F');
        graph.makeGraph("Chandigarh","Pune",4000.0f,18.0f,'T');
        graph.makeGraph("Chandigarh","Pune",3500.0f,24.0f,'B');

        graph.makeGraph("Patiala","Chandigarh",400.0f,1.0f,'T');
        graph.makeGraph("Patiala","Chandigarh",200.0f,2.0f,'B');

        graph.makeGraph("Patiala","Delhi",1800.0f,6.0f,'T');
        graph.makeGraph("Patiala","Delhi",800.0f,8.0f,'B');
        graph.makeGraph("Patiala","Pune",3800.0f,20.0f,'T');


        graph.makeGraph("Pune","Delhi",6500.0f,3.0f,'F');
        graph.makeGraph("Pune","Delhi",3500.0f,14.0f,'T');
        graph.makeGraph("Pune","Delhi",2500.0f,20.0f,'B');

        graph.makeGraph("Pune","Chandigarh",8200.0f,4.0f,'F');
        graph.makeGraph("Pune","Chandigarh",3800.0f,18.0f,'T');
        graph.makeGraph("Pune","Chandigarh",3200.0f,24.0f,'B');
        graph.makeGraph("Pune","Patiala",3800.0f,20.0f,'T');



        for (String state : graph.stateMappingNumber.keySet()) {
            System.out.println("state " + state + " mapping with " + graph.stateMappingNumber.get(state) + " state");
        }

        System.out.println();

        System.out.println(graph.minimumTimeRoute(graph.stateMappingNumber.get("Delhi"), graph.stateMappingNumber.get("Chandigarh")));
    }
}

