package A01a_Queue;

public class Queue<T>
{
    private Node<T> first;
    private Node<T> last;
    private int count;

    /**
     * Das vorderste (=erste) Element aus der Queue entfernen und zurückliefern.
     * Existiert kein Element, wird eine Exception ausgelöst.
     * @throws QueueEmptyException 
     */
    public T dequeue() throws QueueEmptyException {
        if (first == null) {
            throw new QueueEmptyException();
        }
        T wert = first.getData();;
        first = first.getNext();
        if (first == null) {
            last = null;
        }
        count--;
        return wert;
    }
    
    /**
     * übergebenen Integer am Ende der Queue anhängen.
     * @param i Zahl
     */
    public void enqueue(T i) {
        Node<T> neu = new Node<>(i);
        if (last == null) {
            first = neu;
        } else {
            last.setNext(neu);
        }
        last = neu;
        count++;


    }
    
    /**
     * Liefert die Anzahl der Elemente im Stack
     * @return
     */
    public int getCount() {
    	return count;
    }

    public boolean contains(T wert) {
        Node<T> aktuell = first;
        while (aktuell != null) {
            if (java.util.Objects.equals(aktuell.getData(), wert)) {
                return true;
            }
            aktuell = aktuell.getNext();
        }
        return false;
    }
}
