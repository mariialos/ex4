
import java.util.*;
import java.util.NoSuchElementException;

public class ListGraph<T> implements Graph<T> {

    private Map<T, Set<Edge<T>>> nodes;

    public ListGraph() {
        this.nodes = new HashMap<>();
    }

    public void add(T node) {
        if (!nodes.containsKey(node) && node != null) {
            nodes.put(node, new HashSet<>()); //varje nod har ett sett med kanter, (kopplingslista).
        }
    }

    public void remove(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException("Node does not exist"); //skapar ett exception-objekt så att metoden inte fastnar i ett null
        }
        nodes.remove(node);
        for (T key : nodes.keySet()) { //Söker över alla nycklar. För att ta bort de edges i kopplingslistan som hör till de anslutande noderna.
            Set<Edge<T>> edges = nodes.get(key); //för varje nyckel sparas värdet i edges.
            Set<Edge<T>> edgesToRemove = new HashSet<>();  //värden att ta bort.
            for (Edge<T> e : edges) { //kolla om den lokala nodens kanter pekar mot noden.
                if (e.getDestination().equals(node)) { //referens-jämförelse.
                    edgesToRemove.add(e);
                }
            }
            edges.removeAll(edgesToRemove); //tar bort sub-setet ur setet.
        }
    }

    public void connect(T node1, T node2, String name, int weight) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Node does not exist");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be < 0");
        }
        if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException("Edge already connects nodes.");
        }
        Set<Edge<T>> node1CurrentEdges = nodes.get(node1);
        Set<Edge<T>> node2CurrentEdges = nodes.get(node2);

        node1CurrentEdges.add(new Edge(node2, name, weight));
        node2CurrentEdges.add(new Edge(node1, name, weight));
    }

    public void disconnect(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Node does not exist");
        }
        if (getEdgeBetween(node1, node2) == null) {
            throw new IllegalStateException("Edge already connects nodes.");
        }//hämta värde från nyckeln och ta bort värdet ut setet.
        nodes.get(node1).remove(getEdgeBetween(node1, node2));
        nodes.get(node2).remove(getEdgeBetween(node2, node1));
    }

    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Node does not exist");
        }
        Set<Edge<T>> edges = nodes.get(node1);
        for (var e : edges) {
            if (e.getDestination().equals(node2)) {
                return e;
            }
        }
        return null;
    }

    public void setConnectionWeight(T node1, T node2, int weight) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException("Node does not exist");
        }
        if (getEdgeBetween(node1, node2) == null || getEdgeBetween(node2, node1) == null) {
            throw new NoSuchElementException("Edge does not exist");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be < 0");
        }
        getEdgeBetween(node1, node2).setWeight(weight);
        getEdgeBetween(node2, node1).setWeight(weight);
    }

    public Set<T> getNodes() {
        return nodes.keySet();
    }

    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException("Node not found!");
        }
        Set<Edge<T>> edges;
        edges = nodes.get(node);
        return Collections.unmodifiableCollection(edges);
    }

    public boolean pathExists(T from, T to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            return false;
        }
        return getPath(from, to) != null;
    }

    //använder bredden-först.
    public List<Edge<T>> getPath(T from, T to) {
        Map<T, T> connections = new HashMap<>();
        connections.put(from, null);
        LinkedList<T> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            T current = queue.pollFirst();
            for (Edge<T> edge : nodes.get(current)) {
                T next = edge.getDestination();
                if (!connections.containsKey(next)) {
                    connections.put(next, current);
                    queue.add(next);
                }
            }
        }
        LinkedList<Edge<T>> path = new LinkedList<>();
        T current = to;
        while (current != null && !current.equals(from)) {
            T next = connections.get(current);
            if (next == null) {
                break;
            }
            Edge<T> edge = getEdgeBetween(next, current);
            current = next;
            path.addFirst(edge);
        }
        if (path.isEmpty()) {
            return null;
        }
        return path;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("Nodes:\n");
        for (Map.Entry<T, Set<Edge<T>>> kv : nodes.entrySet()) {  //Avbildningens nyckel-värde-par. getValue hämtar alla kanter.
            str.append(kv.getKey().toString()).append(" :").append(kv.getValue().toString());
        }
        return str.toString();
    }
}

