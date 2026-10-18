package A07_Sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Laufzeitmessung BubbleSort vs. QuickSort (A07) auf wachsenden Person-Arrays.
 * Zufaellige Nach- und Vornamen (fester Startwert, gleiche Daten fuer beide).
 * Jeder Lauf bekommt eine frische Kopie, damit nicht schon sortiert wird.
 * Starten: gruener Pfeil neben main oder Strg + Shift + F10.
 */
public class SortierMessung {

    static final int[] GROESSEN = {1_000, 5_000, 10_000, 20_000, 40_000};
    static final int WIEDERHOLUNGEN = 3;

    public static void main(String[] args) {
        // Aufwaermen
        for (int i = 0; i < 3; i++) {
            Person[] p = erzeugePersonen(3_000);
            new BubbleSort().sort(p.clone());
            new QuickSort().sort(p.clone());
        }

        System.out.println("Zeit in ms (Median aus " + WIEDERHOLUNGEN + " Laeufen)");
        System.out.println("n\t\tBubble zufaellig\tQuick zufaellig\tBubble sortiert\tQuick sortiert");
        for (int n : GROESSEN) {
            Person[] original = erzeugePersonen(n);
            Person[] sortiert = original.clone();
            new QuickSort().sort(sortiert);

            double bz = messe(new BubbleSort(), original);
            double qz = messe(new QuickSort(), original);
            double bs = messe(new BubbleSort(), sortiert);
            double qs = messe(new QuickSort(), sortiert);
            System.out.printf("%,d\t\t%.1f\t\t\t%.2f\t\t%.2f\t\t%.2f%n", n, bz, qz, bs, qs);
        }
    }

    /** Sortiert WIEDERHOLUNGEN-mal eine frische Kopie und liefert den Median in ms. */
    static double messe(PersonenSort sortierer, Person[] daten) {
        long[] zeiten = new long[WIEDERHOLUNGEN];
        for (int w = 0; w < WIEDERHOLUNGEN; w++) {
            Person[] kopie = daten.clone();
            long start = System.nanoTime();
            sortierer.sort(kopie);
            zeiten[w] = System.nanoTime() - start;
            pruefe(kopie);
        }
        Arrays.sort(zeiten);
        return zeiten[WIEDERHOLUNGEN / 2] / 1_000_000.0;
    }

    static void pruefe(Person[] p) {
        for (int i = 1; i < p.length; i++) {
            if (p[i - 1].compareTo(p[i]) > 0) {
                throw new IllegalStateException("Nicht richtig sortiert!");
            }
        }
    }

    static Person[] erzeugePersonen(int n) {
        String[] nach = {"Simpson", "Einstein", "Planck", "Hubble", "Feynman", "Curie", "Newton",
                "Darwin", "Turing", "Lovelace", "Bohr", "Kepler", "Galilei", "Hopper", "Gauss"};
        String[] vor = {"Homer", "Marge", "Lisa", "Bart", "Maggie", "Albert", "Max", "Edwin",
                "Richard", "Marie", "Isaac", "Charles", "Alan", "Ada", "Niels", "Grace"};
        Random zufall = new Random(42);
        Person[] p = new Person[n];
        for (int i = 0; i < n; i++) {
            String nn = nach[zufall.nextInt(nach.length)] + zufall.nextInt(1000);
            String vn = vor[zufall.nextInt(vor.length)];
            p[i] = new Person(vn, nn);
        }
        return p;
    }
}
