package A04_TraverseTree;

public class Word {

	/**
	 * Linkes Kind
	 */
	private Word left;
	
	/**
	 * Rechtes Kind
	 */
	private Word right;
	
	/**
	 * Elternelement
	 */
	private Word parent;
	
	/**
	 * Wert des Knotens, hier: String, der Wort enthält
	 */
	private final String wort;
	

	public Word(String s) {
		wort = s;
	}

	public Word getLeft() {
		return left;
	}

	public void setLeft(Word left) {
		this.left = left;
	}

	public Word getRight() {
		return right;
	}

	public void setRight(Word right) {
		this.right = right;
	}

	public Word getParent() {
		return parent;
	}

	public void setParent(Word parent) {
		this.parent = parent;
	}

	public String getWort() {
		return wort;
	}
}
