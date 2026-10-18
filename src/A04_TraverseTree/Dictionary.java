package A04_TraverseTree;

import java.util.Set;
import java.util.TreeSet;


public class Dictionary {

	/**
	 * Wurzel des Baums (Startknoten)
	 */
	private Word root;
	
	public Word getRoot() {
		return root;
	}

	/**
	 * Zählt alle Wörter des Teilbaums ab einem bestimmten Wort
	 * @param w Wort
	 * @return Zahl der Wörter (=Anzahl der Elemente)
	 */
	public int countWordsInSubTree(Word w) {
        if (w == null) {
            return 0;
        }
        return 1 + countWordsInSubTree(w.getLeft()) + countWordsInSubTree(w.getRight());
	}

	/**
	 * Liefert die Menge aller Wörter retour, die ein spezifisches Präfix haben.
	 * @param prefix Wörter müssen diesen Präfix haben
	 * @return Menge aller zutreffenden Wörter
	 */
	public Set<String> getWordsWithPrefix(String prefix) {
        Set<String> ergebnis = new TreeSet<>();
        sammeln(root, prefix, ergebnis);
        return ergebnis;
	}
	

	/**
	 * Neues Wort hinzufügen
	 * @param wort Hinzuzufügendes Wort
	 */
	public void add(String wort) {
		Word neu = new Word(wort);
		if (root == null) {			// Fall 1: Baum ist leer
			root = neu;
			return;
		}
		Word w = root;				// Fall 2: Baum ist nicht leer
		while (true) {
			int vgl = wort.compareTo(w.getWort());
			if (vgl < 0) {			// Neues Wort ist lexikographisch kleiner
				if (w.getLeft() == null) {
					w.setLeft(neu);
					neu.setParent(w);
					return;
				}
				w = w.getLeft();
			}
			else if (vgl > 0) {		// Neues Wort ist lexikographisch gr��er
				if (w.getRight() == null) {
					w.setRight(neu);
					neu.setParent(w);
					return;
				}
				w = w.getRight();
			}
			else {					// Neues Wort ist lexikographisch gleich
				return;
			}
		}
	}

	public Word find(String s) {
		return find(root, s);
	}
	
	private Word find(Word current, String s) {
		if (current == null) {
			return null;
		}
		int vgl = s.compareTo(current.getWort());
		if (vgl == 0) {		// Gefunden
			return current;
		}
		else if (vgl < 0) {	// Links
			return find(current.getLeft(), s);
		}
		else {				// Rechts
			return find(current.getRight(), s);
		}
	}

//    private void sammeln(Word w, String prefix, Set<String> ergebnis) {
//        if (w == null) {
//            return;
//        }
//        if (w.getWort().startsWith(prefix)) {
//            ergebnis.add(w.getWort());
//        }
//        sammeln(w.getLeft(), prefix, ergebnis);
//        sammeln(w.getRight(), prefix, ergebnis);
//    } Diese Lösung ist einfacher verständlich, aber weniger effizient.

    private void sammeln(Word w, String prefix, Set<String> ergebnis) {
        if (w == null) {
            return;
        }
        String wort = w.getWort();
        if (wort.startsWith(prefix)) {                // Fall 1: passt
            ergebnis.add(wort);
            sammeln(w.getLeft(), prefix, ergebnis);
            sammeln(w.getRight(), prefix, ergebnis);
        } else if (wort.compareTo(prefix) < 0) {      // Fall 2: kommt vorher
            sammeln(w.getRight(), prefix, ergebnis);
        } else {                                      // Fall 3: kommt nachher
            sammeln(w.getLeft(), prefix, ergebnis);
        }
    }
	
}
