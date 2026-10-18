package A05_Breitensuche;

import java.util.ArrayList;
import java.util.List;

/**
 * Vergleich Breitensuche (BFS) und Tiefensuche (DFS) am selben Baum.
 * BFS kommt aus meiner Klasse Breitensuche (A05),
 * die drei DFS-Reihenfolgen sind hier mit derselben Idee wie in A06 nachgebaut.
 * Starten: gruener Pfeil neben main oder Strg + Shift + F10.
 */
public class Traversierungvergleich {

    public static void main(String[] args) {
        System.out.println("=== Baum 1: Testbaum aus A05 (Einfuegereihenfolge 6, 3, 10, 5, 2, 1, 12, 11, 15) ===");
        vergleiche(new int[]{6, 3, 10, 5, 2, 1, 12, 11, 15});

        System.out.println();
        System.out.println("=== Baum 2: dieselben Zahlen, aber sortiert eingefuegt (1, 2, 3, 5, 6, 10, 11, 12, 15) ===");
        vergleiche(new int[]{1, 2, 3, 5, 6, 10, 11, 12, 15});
    }

    static void vergleiche(int[] werte) {
        Breitensuche baum = new Breitensuche();
        for (int w : werte) {
            baum.add(w);
        }
        Node<Integer> wurzel = baum.getRoot();

        System.out.println("Baum (L = links, R = rechts):");
        baum.printTree();
        System.out.println();

        System.out.println("BFS  (Ebene fuer Ebene):         " + baum.getBreadthFirstOrder(wurzel));
        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> post = new ArrayList<>();
        preOrder(wurzel, pre);
        inOrder(wurzel, in);
        postOrder(wurzel, post);
        System.out.println("DFS Pre-order  (Ich-links-rechts): " + pre);
        System.out.println("DFS In-order   (links-Ich-rechts): " + in);
        System.out.println("DFS Post-order (links-rechts-Ich): " + post);

        int ebene = 1;
        while (true) {
            List<Integer> e = baum.getBreadthFirstOrderForLevel(wurzel, ebene);
            if (e.isEmpty()) {
                break;
            }
            System.out.println("  Ebene " + ebene + ": " + e);
            ebene++;
        }
        System.out.println("Hoehe des Baums: " + (ebene - 1) + " Ebenen");
    }

    static void preOrder(Node<Integer> n, List<Integer> l) {
        if (n == null) {
            return;
        }
        l.add(n.getValue());
        preOrder(n.getLeft(), l);
        preOrder(n.getRight(), l);
    }

    static void inOrder(Node<Integer> n, List<Integer> l) {
        if (n == null) {
            return;
        }
        inOrder(n.getLeft(), l);
        l.add(n.getValue());
        inOrder(n.getRight(), l);
    }

    static void postOrder(Node<Integer> n, List<Integer> l) {
        if (n == null) {
            return;
        }
        postOrder(n.getLeft(), l);
        postOrder(n.getRight(), l);
        l.add(n.getValue());
    }
}
