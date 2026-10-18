package A02_Heap;

import A02_Heap.Task;
import A02_Heap.TaskHeapArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Heap (A02) im Vergleich mit einer sortierten Liste.
 * Teil 1: swim und sink beobachten (wie viele Tausche pro Operation?)
 * Teil 2: Laufzeit Heap vs. sortierte Liste - ab wann lohnt sich der Heap?
 * Starten: gruener Pfeil neben main oder Strg + Shift + F10.
 */
public class HeapVergleich {

    static final int[] GROESSEN = {10, 100, 1_000, 10_000, 100_000};
    static final int[] GROESSEN_VERGLEICH = {10, 100, 1_000, 10_000, 20_000, 50_000, 100_000, 200_000};

    public static void main(String[] args) {
        teil1SwimSinkBeobachten();
        System.out.println();
        teil2Laufzeitvergleich();
    }

    // ------------------------------------------------------------------
    // Teil 1: swim / sink beobachten
    // ------------------------------------------------------------------

    /**
     * Zaehlt die Tausche in swim und sink. Dafuer wird hier dieselbe Logik wie in
     * TaskHeapArrayList nachgebaut, nur mit Zaehlern (TaskHeapArrayList selbst darf
     * laut Aufgabe keine zusaetzlichen Variablen bekommen).
     * Zum Vergleich: verschobene Elemente beim Einfuegen in eine sortierte Liste.
     */
    static void teil1SwimSinkBeobachten() {
        System.out.println("TEIL 1: swim und sink beobachten (Durchschnitt pro Operation)");
        System.out.println("n\t\tHoehe log2(n)\tswim-Tausche\tsink-Tausche\tsortierte Liste: verschoben");
        Random zufall = new Random(42);
        for (int n : GROESSEN) {
            int[] heap = new int[n + 1];
            int groesse = 0;
            long swimTausche = 0;
            long sinkTausche = 0;
            long verschoben = 0;
            ArrayList<Integer> sortiert = new ArrayList<>();
            int[] werte = new int[n];
            for (int i = 0; i < n; i++) {
                werte[i] = zufall.nextInt(1_000_000);
            }
            for (int w : werte) {
                groesse++;
                heap[groesse] = w;
                swimTausche += swim(heap, groesse);
                int pos = sortiertePosition(sortiert, w);
                verschoben += sortiert.size() - pos;
                sortiert.add(pos, w);
            }
            for (int i = 0; i < n; i++) {
                heap[1] = heap[groesse];
                groesse--;
                sinkTausche += sink(heap, groesse);
            }
            double hoehe = Math.log(n) / Math.log(2);
            System.out.printf("%,d\t\t%.1f\t\t%.2f\t\t%.2f\t\t%,.1f%n", n, hoehe,
                    swimTausche / (double) n, sinkTausche / (double) n, verschoben / (double) n);
        }
        System.out.println("swim: wie oft ein neues Element mit seinem Elternknoten tauscht (zufaellige Prioritaeten).");
        System.out.println("sink: wie oft das nach oben gesetzte letzte Element nach unten tauscht.");
        System.out.println("sortierte Liste: wie viele Elemente beim Einfuegen nach hinten rutschen muessen.");
    }

    /** swim wie in TaskHeapArrayList, liefert die Anzahl der Tausche. */
    static int swim(int[] h, int pos) {
        int tausche = 0;
        while (pos > 1 && h[pos] < h[pos / 2]) {
            int t = h[pos];
            h[pos] = h[pos / 2];
            h[pos / 2] = t;
            pos = pos / 2;
            tausche++;
        }
        return tausche;
    }

    /** sink wie in TaskHeapArrayList, liefert die Anzahl der Tausche. */
    static int sink(int[] h, int groesse) {
        int tausche = 0;
        int pos = 1;
        while (pos * 2 <= groesse) {
            int kind = pos * 2;
            if (kind + 1 <= groesse && h[kind + 1] < h[kind]) {
                kind++;
            }
            if (h[kind] >= h[pos]) {
                break;
            }
            int t = h[pos];
            h[pos] = h[kind];
            h[kind] = t;
            pos = kind;
            tausche++;
        }
        return tausche;
    }

    /** Position fuer w in einer aufsteigend sortierten Liste (binaere Suche). */
    static int sortiertePosition(ArrayList<Integer> liste, int w) {
        int links = 0;
        int rechts = liste.size();
        while (links < rechts) {
            int mitte = (links + rechts) / 2;
            if (liste.get(mitte) < w) {
                links = mitte + 1;
            } else {
                rechts = mitte;
            }
        }
        return links;
    }

    // ------------------------------------------------------------------
    // Teil 2: Laufzeit Heap vs. sortierte Liste
    // ------------------------------------------------------------------

    /**
     * Sortierte Liste als Gegenstueck zum Heap: absteigend sortiert, damit der Task mit
     * der kleinsten Prioritaet immer am Ende steht und remove ohne Verschieben geht.
     * Einfuegen: Position per binaerer Suche finden, dann einfuegen (alles dahinter rutscht).
     */
    static class SortierteListe {
        private final ArrayList<Task> liste = new ArrayList<>();

        void insert(Task t) {
            int links = 0;
            int rechts = liste.size();
            while (links < rechts) {
                int mitte = (links + rechts) / 2;
                if (liste.get(mitte).getPriority() > t.getPriority()) {
                    links = mitte + 1;
                } else {
                    rechts = mitte;
                }
            }
            liste.add(links, t);
        }

        Task remove() {
            if (liste.isEmpty()) {
                return null;
            }
            return liste.remove(liste.size() - 1);
        }
    }

    static void teil2Laufzeitvergleich() {
        System.out.println("TEIL 2: Laufzeit Heap vs. sortierte Liste");
        System.out.println("n Tasks einfuegen, dann alle n wieder entfernen. ns pro Task (insert + remove).");
        // Aufwaermen
        for (int i = 0; i < 5; i++) {
            messeHeap(erzeugeTasks(10_000));
            messeSortiert(erzeugeTasks(10_000));
        }
        System.out.println("n\t\tHeap ns\t\tsortierte Liste ns\tschneller");
        Integer lohntAb = null;
        for (int n : GROESSEN_VERGLEICH) {
            Task[] tasks = erzeugeTasks(n);
            int laeufe = n <= 1_000 ? 2_000_000 / n : (n <= 20_000 ? 30 : 5);
            long[] heap = new long[laeufe];
            long[] sort = new long[laeufe];
            for (int w = 0; w < laeufe; w++) {
                heap[w] = messeHeap(tasks);
                sort[w] = messeSortiert(tasks);
            }
            double h = median(heap) / (double) n;
            double s = median(sort) / (double) n;
            String sieger = h < s ? "Heap" : "sortierte Liste";
            if (h < s && lohntAb == null) {
                lohntAb = n;
            }
            System.out.printf("%,d\t\t%.1f\t\t%.1f\t\t\t%s (Faktor %.1f)%n", n, h, s, sieger, Math.max(h, s) / Math.min(h, s));
        }
        System.out.println(lohntAb == null
                ? "In diesem Lauf war der Heap bei keiner Groesse schneller."
                : "Der Heap war in diesem Lauf ab n = " + String.format("%,d", lohntAb) + " schneller.");
    }

    static Task[] erzeugeTasks(int n) {
        Random zufall = new Random(7);
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(i, zufall.nextInt(1_000_000));
        }
        return tasks;
    }

    static long messeHeap(Task[] tasks) {
        TaskHeapArrayList heap = new TaskHeapArrayList();
        long start = System.nanoTime();
        for (Task t : tasks) {
            heap.insert(t);
        }
        int letzte = Integer.MIN_VALUE;
        for (int i = 0; i < tasks.length; i++) {
            Task t = heap.remove();
            if (t.getPriority() < letzte) {
                throw new IllegalStateException("Heap liefert falsche Reihenfolge");
            }
            letzte = t.getPriority();
        }
        return System.nanoTime() - start;
    }

    static long messeSortiert(Task[] tasks) {
        SortierteListe liste = new SortierteListe();
        long start = System.nanoTime();
        for (Task t : tasks) {
            liste.insert(t);
        }
        int letzte = Integer.MIN_VALUE;
        for (int i = 0; i < tasks.length; i++) {
            Task t = liste.remove();
            if (t.getPriority() < letzte) {
                throw new IllegalStateException("Liste liefert falsche Reihenfolge");
            }
            letzte = t.getPriority();
        }
        return System.nanoTime() - start;
    }

    static long median(long[] a) {
        long[] k = a.clone();
        Arrays.sort(k);
        return k[k.length / 2];
    }
}