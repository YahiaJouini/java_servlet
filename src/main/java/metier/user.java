package metier;

public class user {
	private String login ;
	private String motdepasse;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	
	public boolean verifier() {
	    if (login != null && motdepasse != null && login.equals("admin") && motdepasse.equals("admin")) {
	        return true;
	    }
	    return false;
	}

	public user(String login, String motdepasse) {
		super();
		this.login = login;
		this.motdepasse = motdepasse;
	}
	
	
}
