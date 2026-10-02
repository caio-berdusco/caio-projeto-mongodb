package caio.workshopmongo.domain.user;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Name(String full, List<String> parts) {

	private static final Set<String> PARTICLES = Set.of("de", "da", "do", "dos", "das", "e");
	private static final String VALID_CHARS_REGEX = "^[a-zA-ZÀ-ÿ\\s'\\-]+$";

	public Name {

		if (full == null || full.isBlank())
			throw new IllegalArgumentException("Name cannot be empty");
		if (parts == null || parts.size() < 2)
			throw new IllegalArgumentException("Name needs first and last name");

		parts = valitaed

	}

	public static Name of(String raw) {
		if (raw == null || raw.isBlank()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}

		String cleaned = raw.trim().replaceAll("\\s+", " ");

		if (!cleaned.matches(VALID_CHARS_REGEX)) {
			throw new IllegalArgumentException("Invalid characters: " + raw);
		}

		List<String> parts = Arrays.stream(cleaned.split(" ")).filter(p -> !p.isBlank()).toList();

		if (parts.size() < 2) {
			throw new IllegalArgumentException("Give first and surname");
		}

		return new Name(cleaned, parts);
	}

	public String first() {
		return parts.get(0);
	}

	public String last() {
		return parts.get(parts.size() - 1);
	}

	public String initials() {
		return parts.stream().map(p -> Character.toUpperCase(p.charAt(0)) + ".").collect(Collectors.joining());
	}

	public String formatted() {
		return parts.stream().map(p -> PARTICLES.contains(p.toLowerCase()) ? p.toLowerCase() : capitalize(p))
				.collect(Collectors.joining(" "));
	}

	public boolean equalsIgnoreAccent(Name other) {
		return normalize(this.full).equalsIgnoreCase(normalize(other.full));
	}

	// Helpers

	private static String capitalize(String p) {
		if (p.contains("-") || p.contains("'")) {
			return Arrays.stream(p.split("(?=[-'])|(?<=[-'])")).map(Name::capitalizeSimple)
					.collect(Collectors.joining());
		}
		return capitalizeSimple(p);
	}

	private static String capitalizeSimple(String s) {
		if (s.isBlank())
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	private static String normalize(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
	}
	
	private List<String> validateParticles(List<String> particles) {
		for (String p : particles) {
			if (!PARTICLES.contains(p))
				throw new IllegalArgumentException("Invalid particles");
		}
		return List.copyOf(particles);
	}
}
