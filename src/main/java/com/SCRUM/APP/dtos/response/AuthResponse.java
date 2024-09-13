package com.SCRUM.APP.dtos.response;


import com.SCRUM.APP.model.ERole;

public class AuthResponse {
    private ERole role;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public AuthResponse(String token,ERole role) {
        this.token = token;
        this.role = role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private ERole role;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, role);
        }
    }
}
