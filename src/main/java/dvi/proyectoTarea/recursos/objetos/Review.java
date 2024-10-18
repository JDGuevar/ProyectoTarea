/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dvi.proyectoTarea.recursos.objetos;

/**
 *
 * @author Alumne
 */
public class Review {
    int id;
    int idIntent;
    int idReviewer;
    int valoracio;
    String comentari;

    public Review() {
    }

    public Review(int id, int idIntent, int idReviewer, int valoracio, String comentari) {
        this.id = id;
        this.idIntent = idIntent;
        this.idReviewer = idReviewer;
        this.valoracio = valoracio;
        this.comentari = comentari;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIntent() {
        return idIntent;
    }

    public void setIdIntent(int idIntent) {
        this.idIntent = idIntent;
    }

    public int getIdReviewer() {
        return idReviewer;
    }

    public void setIdReviewer(int idReviewer) {
        this.idReviewer = idReviewer;
    }

    public int getValoracio() {
        return valoracio;
    }

    public void setValoracio(int valoracio) {
        this.valoracio = valoracio;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    
}
