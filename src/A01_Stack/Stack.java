package A01_Stack;


@SuppressWarnings("ALL")
public class Stack<T>
{
    private Node<T> first;
    private int count;

    /**
     * Oberstes Element entfernen und zurückliefern.
     * Existiert kein Element, wird eine Exception ausgelöst.
     * @throws StackEmptyException 
     */
    public T pop() throws StackEmptyException {
        if (first == null) {
            throw new StackEmptyException();
        }
        T wert = first.getData();
        first = first.getNext();
        count--;
        return wert;
    }
    
    /**
     * übergebenen T auf Stack (als oberstes Element) speichern.
     * @param i data
     */
    public void push(T i) {
        Node<T> neu = new Node<>(i);
        neu.setNext(first);
        first = neu;
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
