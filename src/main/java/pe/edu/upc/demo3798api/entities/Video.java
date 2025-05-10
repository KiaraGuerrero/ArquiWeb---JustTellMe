package pe.edu.upc.demo3798api.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "video")
public class Video implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idVideo;

        @Column(nullable = false)
        private String url;

        @Column(nullable = true)
        private String titulo;

        @Column(name = "descripcion", length = 1000)
        private String descripcion;

        @Column(name = "categoria", length = 100)
        private String categoria;

        public Integer getIdVideo() {
            return idVideo;
        }

        public void setIdVideo(Integer idVideo) {
            this.idVideo = idVideo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
