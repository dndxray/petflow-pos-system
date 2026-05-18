package model;

public abstract class User {
    private String id;
    private String name;
    private String password;
    private String role;

    public User(String id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public abstract String getPermissions();

    public String getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public String getRole() { 
        return role; 
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", id, name, role);
    }
}