package caio.workshopmongo.domain.user;

public class User {
	private String id;
	private Name name;
	private Email email;
	
	public User(Name name, Email email) {
		this.name = name;
		this.email = email;
	}
	
	public User() {}
	
	
}
