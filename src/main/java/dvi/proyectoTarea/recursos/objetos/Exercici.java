/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dvi.proyectoTarea.recursos.objetos;

/**
 *
 * @author Alumne
 */
public class Exercici {
    private int id;
    private String nomExercici;
    private String descripció;

    // Empty constructor
    public Exercici() {
    }

    // Full constructor
    public Exercici(int id, String nomExercici, String descripció) {
        this.id = id;
        this.nomExercici = nomExercici;
        this.descripció = descripció;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomExercici() {
        return nomExercici;
    }

    public void setNomExercici(String nomExercici) {
        this.nomExercici = nomExercici;
    }

    public String getDescripció() {
        return descripció;
    }

    public void setDescripció(String descripció) {
        this.descripció = descripció;
    }
}
