package com.SCRUM.APP.dtos.resquest;

import com.SCRUM.APP.model.ERole;

public class RegisterRequest {

    String username;
    String email;
    String password;
    ERole role;

    public RegisterRequest(String password, String email, String username, ERole role) {
        this.password = password;
        this.email = email;
        this.username = username;
        this.role = role;
    }
    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public RegisterRequest build() {
        return new RegisterRequest(username, password, email, role);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;
        private String email;
        private ERole role;


        public Builder username(String username) {
            this.username = username;
            return this;
        }


        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }


    }
}
