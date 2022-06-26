import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    Methods methods = new Methods();
    private String avatar = "src/main/resources/avatar1.png";
    private String email = methods.generateRandomHexString(5) + "@mail.ru";
    private String name = "hello";
    private String password = "Lokon";
    private String role = "user";
    private String loginEmail = "789fb@mail.ru";
    private String loginPassword = "Lokon";

    private String id = "9ac414a1-cbb2-4fd0-8464-4e785e673070";
    private String newId;
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZjhhYjBjZC03MmFjLTQ0YmQtODgzYy0zYTZmODIxYmM5NWYiLCJleHAiOjE2NTcxNTIwMDB9.wGJlDRkcgzXNCIKLX7li73fJ757KV7rI2ygjJJ87BDqywkNf76pO8Iut0nuYUj86bFUWEEeHbs7Ie4LorJbi1g";
    private String newToken;

    public Methods getMethods() {
        return methods;
    }

    public void setMethods(Methods methods) {
        this.methods = methods;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}

