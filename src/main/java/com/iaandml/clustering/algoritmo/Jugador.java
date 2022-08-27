
package com.iaandml.clustering.algoritmo;


public class Jugador {
    private double id;
    private String nombre;
    private double valor;
    private double partidosJugados;
    private double minutosJugados;
    private double goles;
    private double asistencias;
    private String posicion;
    private double distanciaMinCluster;
    private Jugador clusterAsignado;
    public Jugador(double id, String nombre, double valor, double partidosJugados, double minutosJugados, double goles, double asistencias, String posicion) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.partidosJugados = partidosJugados;
        this.minutosJugados = minutosJugados;
        this.goles = goles;
        this.asistencias = asistencias;
        this.posicion = posicion;
    }
    
    
    
    //operaciones

    @Override
    public String toString() {
        return "Jugador:\n"+"Id:"+this.id+"\n"
                +"Nombre:"+this.nombre+"\n"
                +"Valor % del maximo:"+this.valor+"\n"
                +"Partidos Jugados % del maximo:"+this.partidosJugados+"\n"
                +"Minutos Jugados * 10^3:"+this.minutosJugados+"\n"
                +"Goles:"+this.goles+"\n"
                +"Aistencias:"+this.asistencias+"\n"
                +"Posicion:"+this.posicion;
    }

    //Getters and setters
    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(double partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public double getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(double minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public double getGoles() {
        return goles;
    }

    public void setGoles(double goles) {
        this.goles = goles;
    }

    public double getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(double asistencias) {
        this.asistencias = asistencias;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public double getDistanciaMinCluster() {
        return distanciaMinCluster;
    }

    public void setDistanciaMinCluster(double distanciaMinCluster) {
        this.distanciaMinCluster = distanciaMinCluster;
    }

    public Jugador getClusterAsignado() {
        return clusterAsignado;
    }

    public void setClusterAsignado(Jugador clusterAsignado) {
        this.clusterAsignado = clusterAsignado;
    }
    
}
