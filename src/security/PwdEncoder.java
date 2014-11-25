package security;

public interface PwdEncoder {

	public String encodePassword(String rawPass);

	public String encodePassword(String rawPass, String salt);

	public boolean isPasswordValid(String encPass, String rawPass);

	public boolean isPasswordValid(String encPass, String rawPass, String salt);
}
