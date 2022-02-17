//AUTHORS: TAYLOR / SOCHAJ
//Mini-Projet L3 Info
//DATE: 04/21

package myPackage;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class DictionaryTest {
	
	//Tous les tests ci-dessous sont expliqués dans le rapport
	
	@Test
	public void returnCorrectName() {
		Dictionary d1 = new Dictionary("Collins");
		assertEquals("Collins", d1.getName());
	}
	
	@Test
	public void addTranslation() {
		Dictionary d1 = new Dictionary("Collins");
		ArrayList<String> chapeauTranslation = new ArrayList<String>(1);
		ArrayList<String> chapeau = new ArrayList<String>(1);
		chapeauTranslation.add("hat");
		chapeau.add("chapeau");
		d1.addTranslation(chapeau, chapeauTranslation);
		assertEquals(1, d1.translationFR_EN.size());
	}
	
	@Test
	public void returnCorrectTranslation() {
		Dictionary d1 = new Dictionary("Collins");
		ArrayList<String> chapeauTranslation = new ArrayList<String>(1);
		ArrayList<String> chapeau = new ArrayList<String>(1);
		chapeauTranslation.add("hat");
		chapeau.add("chapeau");
		d1.addTranslation(chapeau, chapeauTranslation);
		assertEquals(chapeauTranslation, d1.getTranslation(chapeau));
	}
	
	@Test
	public void returnMultipleTranslations() {
		Dictionary d1 = new Dictionary("Collins");
		ArrayList<String> navireTranslations = new ArrayList<String>(2);
		ArrayList<String> navire = new ArrayList<String>(1);
		navireTranslations.add("ship");
		navireTranslations.add("boat");
		navire.add("navire");
		d1.addTranslation(navire, navireTranslations);
		assertEquals(navireTranslations, d1.getTranslation(navire));
	}
	
	@Test
	public void bidirectionalTranslations() {
		Dictionary d1 = new Dictionary("Collins");
		ArrayList<String> sensTranslations = new ArrayList<String>(2);
		ArrayList<String> sens = new ArrayList<String>(1);
		ArrayList<String> meaning = new ArrayList<String>(1);
		sensTranslations.add("direction");
		sensTranslations.add("meaning");
		sens.add("sens");
		meaning.add("meaning");
		d1.addTranslation(sens, sensTranslations);
		assertEquals(sensTranslations, d1.getTranslation(sens));
		assertEquals(sens, d1.getTranslation(sensTranslations));
	}
	
	Dictionary d2;
	
	// ***** MOCK DICTIONARY *****
	
	@Before
	public void setup() {
		d2 = mock(Dictionary.class);
		d2.setName("collins");
		ArrayList<String> chapeauTranslation = new ArrayList<String>(1);
		ArrayList<String> chapeau = new ArrayList<String>(1);
		chapeauTranslation.add("hat");
		chapeau.add("chapeau");
		d2.addTranslation(chapeau, chapeauTranslation);
		when(d2.getTranslation(chapeau)).thenReturn(chapeauTranslation);
	}
	
	@Test
	public void myMockTest() {
		ArrayList<String> chapeau = new ArrayList<String>(1);
		chapeau.add("chapeau");
		ArrayList<String> hat = new ArrayList<String>(1);
		hat.add("hat");
		ArrayList<String> chapeauTranslation = d2.getTranslation(chapeau);
		assertEquals(chapeauTranslation, hat);
	}
}
