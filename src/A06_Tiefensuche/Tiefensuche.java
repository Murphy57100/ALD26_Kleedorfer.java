package A06_Tiefensuche;

import java.util.ArrayList;
import java.util.List;

import A05_Breitensuche.BaseTree;
import A05_Breitensuche.Node;

public class Tiefensuche extends BaseTree<Film> {

	@Override
	/**
	 * Sortierkriterium im Baum: Länge des Films
	 */
	protected int compare(Film a, Film b) {
        return Double.compare(a.getLänge(), b.getLänge());
	}

	/**
	 * Retourniert die Titelliste der Film-Knoten des Teilbaums in symmetrischer Folge (engl. in-order, d.h. links-Knoten-rechts)
	 * @param node Wurzelknoten des Teilbaums
	 * @return Liste der Titel in symmetrischer Reihenfolge
	 */
	public List<String> getNodesInOrder(Node<Film> node) {
        List<String> ergebnis = new ArrayList<>();
        inOrder(node, ergebnis);
        return ergebnis;
	}
	
	/**
	 * Retourniert Titelliste jener Filme, deren L�nge zwischen min und max liegt, in Hauptreihenfolge (engl. pre-order, d.h. Knoten-links-rechts)
	 * @param min Minimale Länge des Spielfilms
	 * @param max Maximale Länge des Spielfilms
	 * @return Liste der Filmtitel in Hauptreihenfolge
	 */
	public List<String> getMinMaxPreOrder(double min, double max) {
        List<String> ergebnis = new ArrayList<>();
        minMaxPreOrder(root, min, max, ergebnis);
        return ergebnis;
	}


    private void inOrder(Node<Film> node, List<String> ergebnis) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft(), ergebnis);
        ergebnis.add(node.getValue().getTitel());
        inOrder(node.getRight(), ergebnis);
    }

    private void minMaxPreOrder(Node<Film> node, double min, double max, List<String> ergebnis) {
        if (node == null) {
            return;
        }
        double laenge = node.getValue().getLänge();
        if (laenge < min) {                                   // Fall 1: zu kurz
            minMaxPreOrder(node.getRight(), min, max, ergebnis);
        } else if (laenge > max) {                            // Fall 2: zu lang
            minMaxPreOrder(node.getLeft(), min, max, ergebnis);
        } else {                                              // Fall 3: passt
            ergebnis.add(node.getValue().getTitel());         //   Ich
            minMaxPreOrder(node.getLeft(), min, max, ergebnis);   //   links
            minMaxPreOrder(node.getRight(), min, max, ergebnis);  //   rechts
        }
    }
}
