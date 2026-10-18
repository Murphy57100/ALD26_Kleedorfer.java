package A05_Breitensuche;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Breitensuche extends BaseTree<Integer> {

	@Override
	protected int compare(Integer a, Integer b) {
		return a.compareTo(b);
	}

	/**
	 * Liefert Knoten des Baums ausgehend von Start in Reihenfolge der Breitensuche zurück
	 * @param start Startknoten für Teilbaum
	 * @return Liste der Knoten in Breitenfolge
	 */
	public List<Integer> getBreadthFirstOrder(Node<Integer> start) {
        List<Integer> ergebnis = new ArrayList<>();
        if (start == null) {
            return ergebnis;
        }
        Queue<Node<Integer>> warteschlange = new LinkedList<>();
        warteschlange.add(start);
        while (!warteschlange.isEmpty()) {
            Node<Integer> aktuell = warteschlange.poll();
            ergebnis.add(aktuell.getValue());
            if (aktuell.getLeft() != null) {
                warteschlange.add(aktuell.getLeft());
            }
            if (aktuell.getRight() != null) {
                warteschlange.add(aktuell.getRight());
            }
        }
        return ergebnis;
	}

	/**
	 * Liefert Knoten des Baums ausgehend von Start in Reihenfolge der Breitensuche zurück,
	 * allerdings nur jene Knoten, die in der angegebenen Ebene liegen (Start hat Ebene=1)
	 * @param start Startknoten für Teilbaum
	 * @param level Nur Knoten dieser Ebene ausgeben
	 * @return Liste aller Knoten
	 */
	public List<Integer> getBreadthFirstOrderForLevel(Node<Integer> start, int level) {
        List<Integer> ergebnis = new ArrayList<>();
        if (start == null || level < 1) {
            return ergebnis;
        }
        Queue<NodeLevel> warteschlange = new LinkedList<>();
        warteschlange.add(new NodeLevel(start, 1));
        while (!warteschlange.isEmpty()) {
            NodeLevel aktuell = warteschlange.poll();
            Node<Integer> knoten = aktuell.getNode();
            int ebene = aktuell.getLevel();
            if (ebene == level) {
                ergebnis.add(knoten.getValue());
            } else {
                if (knoten.getLeft() != null) {
                    warteschlange.add(new NodeLevel(knoten.getLeft(), ebene + 1));
                }
                if (knoten.getRight() != null) {
                    warteschlange.add(new NodeLevel(knoten.getRight(), ebene + 1));
                }
            }
        }
        return ergebnis;
	}

    public class NodeLevel {

        public NodeLevel(Node<Integer> node, int level) {
            this.node = node;
            this.level = level;
        }

        private final Node<Integer> node;
        private final int level;

        public Node<Integer> getNode() {
            return node;
        }

        public int getLevel() {
            return level;
        }
    }

}
