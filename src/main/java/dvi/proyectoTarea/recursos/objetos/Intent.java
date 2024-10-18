/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dvi.proyectoTarea.recursos.objetos;

/**
 *
 * @author Alumne
 */
public class Intent {
    int id;
    int idUsuari;
    String nomUsuari;
    int idExercici;
    String nomExercici;
    String timestamp_Inici;
    String timestamp_Fi;
    String videofile;

    public Intent() {
    }

    public Intent(int id, int idUsuari, String nomUsuari, int idExercici, String nomExercici, String timestamp_Inici, String timestamp_Fi, String videofile) {
        this.id = id;
        this.idUsuari = idUsuari;
        this.nomUsuari = nomUsuari;
        this.idExercici = idExercici;
        this.nomExercici = nomExercici;
        this.timestamp_Inici = timestamp_Inici;
        this.timestamp_Fi = timestamp_Fi;
        this.videofile = videofile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public int getIdExercici() {
        return idExercici;
    }

    public void setIdExercici(int idExercici) {
        this.idExercici = idExercici;
    }

    public String getNomExercici() {
        return nomExercici;
    }

    public void setNomExercici(String nomExercici) {
        this.nomExercici = nomExercici;
    }

    public String getTimestamp_Inici() {
        return timestamp_Inici;
    }

    public void setTimestamp_Inici(String timestamp_Inici) {
        this.timestamp_Inici = timestamp_Inici;
    }

    public String getTimestamp_Fi() {
        return timestamp_Fi;
    }

    public void setTimestamp_Fi(String timestamp_Fi) {
        this.timestamp_Fi = timestamp_Fi;
    }

    public String getVideofile() {
        return videofile;
    }

    public void setVideofile(String videoFile) {
        this.videofile = videoFile;
    }
    
    
}
