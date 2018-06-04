package com.github.paulerpen.mypush.user;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;
    private String username;
    private String password;

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, username='%s', password='%s']",
                id, username, password);
    }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
    

}