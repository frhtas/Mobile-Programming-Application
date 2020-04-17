package ce.yildiz.edu.tr.mobileprogrammingapp;

public class User {
    private String username;
    private String password;
    private int userImage;

    public User() {
    }

    public User(String username, String password, int userImage) {
        this.username = username;
        this.password = password;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserImage() {
        return userImage;
    }

}
