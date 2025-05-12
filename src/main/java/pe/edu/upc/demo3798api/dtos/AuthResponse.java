package pe.edu.upc.demo3798api.dtos;

public class AuthResponse {
    private String token;
    private String tipo;   // p.ej. "Bearer"

    public AuthResponse() {}

    public AuthResponse(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
