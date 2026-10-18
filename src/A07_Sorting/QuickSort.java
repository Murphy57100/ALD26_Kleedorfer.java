package A07_Sorting;


public class QuickSort implements PersonenSort {

	/**
	 * Sortier-Funktion
	 */
	public void sort(Person[] personen) {
		sort(personen, 0, personen.length - 1);
	}

	private void sort(Person[] personen, int left, int right) {
		// TODO: Implementation
        if (left < right) {
            int pivotPos = partition(personen, left, right);
            sort(personen, left, pivotPos - 1);
            sort(personen, pivotPos + 1, right);
        }
	}

	private int partition(Person[] personen, int left, int right) {
		// TODO: Implementation
        int mitte = (left + right) / 2;
        tausche(personen, mitte, right);
        Person pivot = personen[right];
        int grenze = left;
        for (int i = left; i < right; i++) {
            if (personen[i].compareTo(pivot) < 0) {
                tausche(personen, i, grenze);
                grenze++;
            }
        }
        tausche(personen, grenze, right);
        return grenze;
	}

    private void tausche(Person[] personen, int a, int b) {
        Person temp = personen[a];
        personen[a] = personen[b];
        personen[b] = temp;
    }
}