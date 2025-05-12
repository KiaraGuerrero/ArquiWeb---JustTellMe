package pe.edu.upc.demo3798api.dtos;

public class MessageDTO {
    private String message;
    public MessageDTO() {}
    public MessageDTO(String message) { this.message = message; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
