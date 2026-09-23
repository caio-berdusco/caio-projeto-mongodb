package caio.workshopmongo.domain.user;

import java.util.Locale;
import java.util.Set;

public class Name {
	
	private final String value;
	private static final Set<String> PREPOSITIONS  = Set.of("de", "da", "do", "dos", "das", "e");

	public Name(String name) {
		
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("The name must have at least one character.");
		
		this.value = formatName(name);
	}
	
	private static String formatName(String name) {
		String[] namePart = name.trim().split("\\s+");
		
		for (int i = 0; i < namePart.length; i++) {

		    if (isPreposition(namePart[i])) {
		        namePart[i] = uncapitalize(namePart[i]);
		    } else {
		        namePart[i] = capitalize(namePart[i]);
		    }
		}
		return String.join(" ", namePart);
	}
	
	// Getters
	public String getValue() {
		return value;
	}
	
	// Utils
	
	private static boolean isPreposition(String word) {
		return PREPOSITIONS.contains(word.toLowerCase(Locale.ROOT));
	}
	
	private static String capitalize(String word) {
	    return word.substring(0, 1).toUpperCase(Locale.ROOT)
	            + word.substring(1).toLowerCase(Locale.ROOT);
	}
	
	private static String uncapitalize(String word) {
		return word.toLowerCase(Locale.ROOT);
	}
}
