package A01_Stack;

import A01a_Queue.Queue;
import A01a_Queue.QueueEmptyException;

import java.util.Arrays;

/**
 * Laufzeitmessung Stack (A01) und Queue (A01a):
 * Einfuegen (push / enqueue) und Suchen (contains) bei wachsender Groesse.
 * Starten: gruener Pfeil neben main oder Strg + Shift + F10.
 */
public class StackQueueMessung {

    static final int[] GROESSEN = {1_000, 10_000, 100_000, 1_000_000};
    static final int WIEDERHOLUNGEN = 7;
    static final int SUCH_AUFRUFE = 50;

    public static void main(String[] args) throws Exception {
        // Aufwaermen
        for (int i = 0; i < 5; i++) {
            messeStack(100_000);
            messeQueue(100_000);
        }

        System.out.println("Einfuegen: ns pro Element | Suchen: us pro Aufruf (gesuchter Wert fehlt, also ganze Liste)");
        System.out.println("Median aus " + WIEDERHOLUNGEN + " Laeufen");
        System.out.println();
        System.out.println("n\t\tStack push\tQueue enqueue\tStack Suche\tQueue Suche\tFaktor Suche");
        double vorher = -1;
        for (int n : GROESSEN) {
            long[][] s = new long[2][WIEDERHOLUNGEN];
            long[][] q = new long[2][WIEDERHOLUNGEN];
            for (int w = 0; w < WIEDERHOLUNGEN; w++) {
                long[] ms = messeStack(n);
                long[] mq = messeQueue(n);
                s[0][w] = ms[0];
                s[1][w] = ms[1];
                q[0][w] = mq[0];
                q[1][w] = mq[1];
            }
            double stackPush = median(s[0]) / (double) n;
            double queueEnq = median(q[0]) / (double) n;
            double stackSuche = median(s[1]) / (double) SUCH_AUFRUFE / 1000.0;
            double queueSuche = median(q[1]) / (double) SUCH_AUFRUFE / 1000.0;
            String faktor = vorher < 0 ? "-" : String.format("x %.1f", stackSuche / vorher);
            System.out.printf("%,d\t\t%.2f\t\t%.2f\t\t%.2f\t\t%.2f\t\t%s%n",
                    n, stackPush, queueEnq, stackSuche, queueSuche, faktor);
            vorher = stackSuche;
        }
        System.out.println();
        System.out.println("Faktor Suche = Stack-Suchzeit geteilt durch die Zeile davor (10-mal mehr Elemente).");
    }

    /** {Zeit fuer n push, Zeit fuer SUCH_AUFRUFE erfolglose Suchen} in ns. Prueft danach LIFO. */
    static long[] messeStack(int n) throws StackEmptyException {
        Stack<Integer> s = new Stack<>();
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            s.push(i);
        }
        long einfuegen = System.nanoTime() - start;

        start = System.nanoTime();
        boolean gefunden = false;
        for (int k = 0; k < SUCH_AUFRUFE; k++) {
            gefunden |= s.contains(-1);
        }
        long suche = System.nanoTime() - start;
        if (gefunden) {
            throw new IllegalStateException("-1 darf nicht gefunden werden");
        }

        for (int i = n - 1; i >= 0; i--) {
            if (s.pop() != i) {
                throw new IllegalStateException("Stack liefert falsche Reihenfolge");
            }
        }
        return new long[]{einfuegen, suche};
    }

    /** {Zeit fuer n enqueue, Zeit fuer SUCH_AUFRUFE erfolglose Suchen} in ns. Prueft danach FIFO. */
    static long[] messeQueue(int n) throws QueueEmptyException {
        Queue<Integer> q = new Queue<>();
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        long einfuegen = System.nanoTime() - start;

        start = System.nanoTime();
        boolean gefunden = false;
        for (int k = 0; k < SUCH_AUFRUFE; k++) {
            gefunden |= q.contains(-1);
        }
        long suche = System.nanoTime() - start;
        if (gefunden) {
            throw new IllegalStateException("-1 darf nicht gefunden werden");
        }

        for (int i = 0; i < n; i++) {
            if (q.dequeue() != i) {
                throw new IllegalStateException("Queue liefert falsche Reihenfolge");
            }
        }
        return new long[]{einfuegen, suche};
    }

    static long median(long[] a) {
        long[] k = a.clone();
        Arrays.sort(k);
        return k[k.length / 2];
    }
}
