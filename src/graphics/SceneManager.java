package graphics;

import java.util.ArrayList;

import java.util.List;
import scenes.MeshNode;
import scenes.Node;

public class SceneManager {
    private Node rootNode;

    public SceneManager() {

    }

    public Node getRootNode() {
        return rootNode;
    }

    public void addRootNode(Node node) {
        rootNode = node;
    }

    public void start() {
        rootNode.start();
    }

    public void update(double deltaTime) {
        rootNode.update(deltaTime);
    }

    public void render(Renderer renderer) {
        rootNode.render(renderer);
    }

    public List<Node> getNodesByName(String name) {
        List<Node> matchingNodes = new ArrayList<>();
        findNodesByName(rootNode, name, matchingNodes);
        return matchingNodes;
    }

    private void findNodesByName(Node currentNode, String name, List<Node> matchingNodes) {
        if (currentNode.getName().equals(name)) {
            matchingNodes.add(currentNode);
        }

        for (Node child : currentNode.getChildren()) {
            findNodesByName(child, name, matchingNodes);
        }
    }

    public Node getNodeByPath(String path) {
        if (rootNode == null) {
            return null;
        }

        String[] parts = path.split("/");

        Node current = rootNode;
        if (!current.getName().equals(parts[0])) {
            return null; // The root node doesn't match the first part of the path
        }

        for (int i = 1; i < parts.length; i++) {
            current = current.getChildByName(parts[i]);
            if (current == null) {
                return null; // No such child exists
            }
        }

        return current;
    }

    public void cleanup() {

        if (rootNode instanceof MeshNode) {
            ((MeshNode) rootNode).cleanup();
        }

    }
}
