package A07_Sorting;


public class BubbleSort implements PersonenSort {

	/**
	 * Sortier-Funktion
	 */
	public void sort(Person[] personen) {
        int ende = personen.length - 1;
        boolean getauscht = true;
        while (getauscht) {
            getauscht = false;
            for (int i = 0; i < ende; i++) {
                if (personen[i].compareTo(personen[i + 1]) > 0) {
                    Person temp = personen[i];
                    personen[i] = personen[i + 1];
                    personen[i + 1] = temp;
                    getauscht = true;
                }
            }
            ende--;
        }

	}
	
}
