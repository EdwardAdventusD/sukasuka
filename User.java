class User {
    protected String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }
}
