package br.ufrn.domain;

public class User {
    private String userName;

    private User(String userName) {
        this.userName = userName;
    }

    public static User createUser(String userName) throws IllegalArgumentException{
        if(userName == null || userName.isEmpty()){
            throw new IllegalArgumentException("The username is required.");
        }

        return new User(userName);
    }

    public String getUserName() {
        return userName;
    }

    public boolean hasUserName(String userName) {
        return userName.equals(userName);
    }
}
