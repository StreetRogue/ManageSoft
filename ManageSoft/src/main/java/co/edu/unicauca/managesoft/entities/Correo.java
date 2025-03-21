package co.edu.unicauca.managesoft.entities;

public class Correo {

    private String remitente;
    private String destinatario;
    private String asunto;
    private String mensaje;
    private int idProyecto;



    public Correo(String destinatario, String asunto, String mensaje) {

        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;

    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

}
