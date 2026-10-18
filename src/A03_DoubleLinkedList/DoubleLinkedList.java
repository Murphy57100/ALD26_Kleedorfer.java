package A03_DoubleLinkedList;

public class DoubleLinkedList<T>
{
    private Node<T> first;
    private Node<T> last;
    private Node<T> current;

    /**
     * Einf�gen einer neuen <T>
     * @param a <T>
     */
    public void add(T a) {
        Node<T> neu = new Node<>(a);
        if (last == null) {
            first = neu;
        } else {
            last.setNext(neu);
            neu.setPrevious(last);
        }
        last = neu;
    }

    /**
     * Internen Zeiger f�r next() zur�cksetzen
     */
    public void reset() {
        current = first;

    }

    /**
     * analog zur Funktion reset()
     */
    public void resetToLast() {
        current = last;

    }

    /**
     * Liefert erste Node der Liste retour oder null, wenn Liste leer
     * @return Node|null
     */
    public Node<T> getFirst() {
    	return first;
    }
    
    /**
     * Liefert letzte Node der Liste retour oder null, wenn Liste leer
     * @return Node|null
     */
    public Node<T> getLast() {
    	return last;
    }
    
    /**
     * Gibt aktuelle <T> zur�ck und setzt internen Zeiger weiter.
     * Falls current nicht gesetzt, wird null retourniert.
     * @return <T>|null
     */
    public T next() {
        if (current == null) {
            return null;
        }
        T wert = current.getData();
        current = current.getNext();
        return wert;
    }

    /**
     * analog zur Funktion next()
     * @return <T>|null
     */
    public T previous() {
        if (current == null) {
            return null;
        }
        T wert = current.getData();
        current = current.getPrevious();
        return wert;
    }
    
    /**
     * Current-Pointer auf nächste <T> setzen (aber nicht auslesen).
     * Ignoriert still, dass current nicht gesetzt ist.
     */
    public void moveNext() {
        if (current != null) {
            current = current.getNext();
        }
    }
    
    /**
     * Analog zur Funktion moveNext()
     */
    public void movePrevious() {
        if (current != null) {
            current = current.getPrevious();
        }
    }
   
    /**
     * Retourniert aktuelle (current) <T>, ohne Zeiger zu �ndern
     * @return <T>
     * @throws CurrentNotSetException
     */
    public T getCurrent() throws CurrentNotSetException {
        if (current == null) {
            throw new CurrentNotSetException();
        }
        return current.getData();
    }

    /**
     * Gibt <T> an bestimmter Position zur�ck
     * @param pos Position, Nummerierung startet mit 1
     * @return <T>|null
     */
    public T get(int pos) {
        Node<T> n = getNode(pos);
        if (n == null) {
            return null;
        }
        return n.getData();
    }

    /**
     * Entfernen des Elements an der angegebenen Position.
     * Falls das entfernte Element das aktuelle Element ist, wird current auf null gesetzt.
     * @param pos
     */
    public void remove(int pos) {
        Node<T> n = getNode(pos);
        if (n == null) {
            return;
        }
        if (n == current) {
            current = null;
        }
        unlink(n);
    }
    
    /**
     * Entfernt das aktuelle Element.
     * Als neues aktuelles Element wird der Nachfolger gesetzt oder
     * (falls kein Nachfolger) das vorhergehende Element 
     * @throws CurrentNotSetException
     */
    public void removeCurrent() throws CurrentNotSetException {
        if (current == null) {
            throw new CurrentNotSetException();
        }
        Node<T> weg = current;
        if (weg.getNext() != null) {
            current = weg.getNext();
        } else if (weg.getPrevious() != null) {
            current = weg.getPrevious();
        } else {
            current = null;
        }
        unlink(weg);
    }
    
    /**
     * Die Methode fügt die übergebene <T> nach der aktuellen (current) ein
     * und setzt dann die neu eingefügte <T> als aktuelle (current) <T>.
     * @throws CurrentNotSetException 
     */
    public void insertAfterCurrentAndMove(T a) throws CurrentNotSetException {
        if (current == null) {
            throw new CurrentNotSetException();
        }
        Node<T> neu = new Node<>(a);
        Node<T> danach = current.getNext();

        neu.setPrevious(current);
        neu.setNext(danach);

        if (danach == null) {
            last = neu;
        } else {
            danach.setPrevious(neu);
        }
        current.setNext(neu);

        current = neu;

    }

    private Node<T> getNode(int pos) {
        if (pos < 1) {
            return null;
        }
        Node<T> n = first;
        int nummer = 1;
        while (n != null && nummer < pos) {
            n = n.getNext();
            nummer++;
        }
        return n;
    }

    private void unlink(Node<T> n) {
        Node<T> davor = n.getPrevious();
        Node<T> danach = n.getNext();

        if (davor == null) {          // n war der erste Waggon
            first = danach;
        } else {
            davor.setNext(danach);
        }

        if (danach == null) {         // n war der letzte Waggon
            last = davor;
        } else {
            danach.setPrevious(davor);
        }

        n.setNext(null);
        n.setPrevious(null);
    }
}
