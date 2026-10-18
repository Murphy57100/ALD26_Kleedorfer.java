package A04_TraverseTree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DictionaryCountWordsTest {

	private Dictionary wb;
	
	@Before
	public void setUp() throws Exception {
		wb = new Dictionary();
		wb.add("Homer");
		wb.add("Flanders");
		wb.add("Maggie");
		wb.add("Marge");
		wb.add("Lisa");
		wb.add("Burns");
		wb.add("Crusty");
		wb.add("Bart");		
	}

	@Test
	public void noWord() {
		assertEquals(0, wb.countWordsInSubTree(null));
	}
	
	@Test
	public void singleWord() {
		Word w = wb.find("Bart");
		assertEquals(1, wb.countWordsInSubTree(w));
	}
	
	@Test
	public void threeWords() {
		Word w = wb.find("Maggie");
		assertEquals(3, wb.countWordsInSubTree(w));
	}

	@Test
	public void wholeTree() {
		Word w = wb.getRoot();
		assertEquals(8, wb.countWordsInSubTree(w));
	}
}
