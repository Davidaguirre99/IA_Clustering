/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iaandml.clustering.algoritmo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author DAVID
 */
public class ProcesoClustering {
    private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private static int numeroClusters;
    private static double variabilidad;
    private static double media;
    private static Jugador jugadoresClusters[];
    private static double mediaClustersGeneral[][];
    //Metodos
    public static void obtenerDatos(){
        try{
            File archivoDatosExcel = new File("C:\\Users\\david\\Documents\\DavidAguirre\\8vosemestre\\Inteligencia_Artificial\\2doBimestre\\tareas\\tareaClustering\\datasets\\datosjugadores.xlsx");
            InputStream inp = new FileInputStream(archivoDatosExcel);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet hoja = wb.getSheetAt(0);
            int indiceFila = 1;
            Row filaDato = hoja.getRow(indiceFila);
            while(filaDato!=null){
                Cell celdaId = filaDato.getCell(0);
                Cell celdaNombre = filaDato.getCell(1);
                Cell celdaPrecio = filaDato.getCell(2);
                Cell celdaJuegos = filaDato.getCell(3);
                Cell celdaMinutos = filaDato.getCell(4);
                Cell celdaGoles = filaDato.getCell(5);
                Cell celdaAsistencias = filaDato.getCell(6);
                Cell celdaPosicion = filaDato.getCell(7);
                double id = celdaId.getNumericCellValue();
                String jugador = celdaNombre.getStringCellValue();
                //precio % del maximo
                double precio = celdaPrecio.getNumericCellValue()/180000000;
                //juegos % del maximo
                double juegos = celdaJuegos.getNumericCellValue()/38;
                //minutos * 10^3
                double minutos = celdaMinutos.getNumericCellValue()/1000;
                double goles = celdaGoles.getNumericCellValue();
                double asistencias = celdaAsistencias.getNumericCellValue();
                String posicion = celdaPosicion.getStringCellValue();
                Jugador player = new Jugador(id, jugador, precio, juegos, minutos, goles, asistencias, posicion);
                jugadores.add(player);
                System.out.println(player.toString());
//                if(indiceFila==400){
//                    break;
//                }
                indiceFila++;
                filaDato = hoja.getRow(indiceFila);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    public static void clusterizar(){
        Random aleatorioClusters = new Random();
        //Asignar numero de clusters
//        numeroClusters = (int) Math.floor(Math.random()*(49-2+1)+2);
        numeroClusters=2;
        boolean variabilidadNoAceptable=true;
        double diferenciaVariabilidad = 0;
        //int contadorVecesWhile=0;
        while(variabilidadNoAceptable){
            
            Jugador clusterJugadores[] = new Jugador[numeroClusters];
            int posicionesClustersGeneradas[] = new int[numeroClusters];
            //Calificar en base al numero de clusters  a alguno de los datos como clusters
            //Asigno aleatoriamente a ciertos jugadores como clusters
            
                for (int i = 0; i < numeroClusters; i++) {
                    if (i == 0) {
                        int posicionCluster = (int) Math.floor(Math.random() * jugadores.size());
                        clusterJugadores[i] = jugadores.get(posicionCluster);
                        posicionesClustersGeneradas[i] = posicionCluster;
                        //System.out.println("Cluster 0: " + clusterJugadores[i].toString());
                    } else {
                        boolean seRepite = true;
                        while (seRepite) {
                            int posicionCluster = (int) Math.floor(Math.random() * jugadores.size());
                            for (int j = 0; j < i; j++) {
                                if (posicionesClustersGeneradas[j] != posicionCluster) {
                                    seRepite = false;
                                    clusterJugadores[i] = jugadores.get(posicionCluster);
                                    posicionesClustersGeneradas[i] = posicionCluster;
                                    //System.out.println("Cluster " + i + ": " + clusterJugadores[i].toString());
                                    break;
                                }
                            }

                        }

                    }
                }
                jugadoresClusters = clusterJugadores;
            
            //Medir distancias de datos hacia los clusters
            for (Jugador jugadorDato : jugadores) {
                //System.out.println("Jugador de dato para obtener diferencias a clusters->\n" + jugadorDato.toString());
                //double distanciasClusters[] = new double[numeroClusters];
                for (int i = 0; i < numeroClusters; i++) {

                    //System.out.println("Jugador cluster " + i + " seleccionado:" + clusterJugadores[i].toString());
                    double diferenciaValor = Math.abs(clusterJugadores[i].getValor() - jugadorDato.getValor());
                    double diferenciaPartidosJugados = Math.abs(clusterJugadores[i].getPartidosJugados() - jugadorDato.getPartidosJugados());
                    double diferenciaMinutos = Math.abs(clusterJugadores[i].getMinutosJugados() - jugadorDato.getMinutosJugados());
                    double diferenciaGoles = Math.abs(clusterJugadores[i].getGoles() - jugadorDato.getGoles());
                    double diferenciaAsistencias = Math.abs(clusterJugadores[i].getAsistencias() - jugadorDato.getAsistencias());
                    double sumaCuadradosDiferencias = Math.pow(diferenciaValor, 2) + Math.pow(diferenciaPartidosJugados, 2) + Math.pow(diferenciaMinutos, 2) + Math.pow(diferenciaGoles, 2) + Math.pow(diferenciaAsistencias, 2);
                    double distanciaAlDatoCluster = Math.sqrt(sumaCuadradosDiferencias);
                    //System.out.println("Distancia al cluster = " + distanciaAlDatoCluster);
                    // se asigna la distancia minima al cluster y se verifica para cada cluster
                    if (i == 0) {
                        jugadorDato.setDistanciaMinCluster(distanciaAlDatoCluster);
                        jugadorDato.setClusterAsignado(clusterJugadores[i]);
                    } else {
                        if (distanciaAlDatoCluster < jugadorDato.getDistanciaMinCluster()) {
                            jugadorDato.setDistanciaMinCluster(distanciaAlDatoCluster);
                            jugadorDato.setClusterAsignado(clusterJugadores[i]);
                        }
                    }
                    //distanciasClusters[i]=distanciaAlDatoCluster;
                }
                //double distMin = obtenerDistanciaMinima(distanciasClusters);
                //jugadorDato.setDistanciaMinCluster(distMin);
                //System.out.println("Distancia minima al cluster es:" + jugadorDato.getDistanciaMinCluster());
                //System.out.println("Cluster asignado : " + jugadorDato.getClusterAsignado().toString());
            }
            //Actualizar medias para asignación de clusters
            
             
            variabilidad = calcularVariabilidad();
            diferenciaVariabilidad=Math.abs(diferenciaVariabilidad-variabilidad);
            
            System.out.println("LA VARIABILIDAD CAMBIA A: "+variabilidad);
            if(diferenciaVariabilidad>0.1){
                //actualizamos medias
//                for(int j=0;j<numeroClusters;j++){
//                    clusterJugadores[j].setValor(mediaClustersGeneral[j][0]);
//                    clusterJugadores[j].setPartidosJugados(mediaClustersGeneral[j][1]);
//                    clusterJugadores[j].setMinutosJugados(mediaClustersGeneral[j][2]);
//                    clusterJugadores[j].setGoles(mediaClustersGeneral[j][3]);
//                    clusterJugadores[j].setAsistencias(mediaClustersGeneral[j][4]);
//                }
                numeroClusters++;
                System.out.println("Los clusters cambian A: "+numeroClusters);
            }else{
                variabilidadNoAceptable=false;
            }
            
        }
        for(int i=0;i<numeroClusters;i++){
            System.out.println("++++++++++++Cluster de jugador: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+  jugadoresClusters[i].toString()+"+++++++++");
            for(Jugador jugador:jugadores){
                if(jugador.getClusterAsignado().getId()==jugadoresClusters[i].getId()){
                    System.out.println(jugador.toString());
                }
            }
        }
        
    }
    public static void actualizarValoresClustersConMedias(Jugador[] jugadoresClusters){
        for(int i=0;i<jugadoresClusters.length;i++){
            for(int j=0;j<mediaClustersGeneral.length;j++){
                jugadoresClusters[i].setValor(mediaClustersGeneral[j][0]);
                jugadoresClusters[i].setPartidosJugados(mediaClustersGeneral[j][1]);
                jugadoresClusters[i].setMinutosJugados(mediaClustersGeneral[j][2]);
                jugadoresClusters[i].setGoles(mediaClustersGeneral[j][3]);
                jugadoresClusters[i].setAsistencias(mediaClustersGeneral[j][4]);
            }
        }
    }
    public static double calcularVariabilidad(){
        double variabilidadJugadores = 0;
        int numeroDatosAnalizar = 5;
        int contadorClusters[] = new int[numeroClusters];
        double mediaClusters[][] = new double[numeroClusters][numeroDatosAnalizar];
        
        for(int i=0;i<numeroClusters;i++){
            contadorClusters[i]=0;
            for(int j=0;j<numeroDatosAnalizar;j++){
                mediaClusters[i][j]=0;
                
            }
        }
        
        for(Jugador jugador : jugadores){
            for(int j=0;j<numeroClusters;j++){
                if(jugador.getClusterAsignado().getId()==jugadoresClusters[j].getId()){
                    contadorClusters[j]++;
                    mediaClusters[j][0]+=jugador.getValor();
                    mediaClusters[j][1]+=jugador.getPartidosJugados();
                    mediaClusters[j][2]+=jugador.getMinutosJugados();
                    mediaClusters[j][3]+=jugador.getGoles();
                    mediaClusters[j][4]+=jugador.getAsistencias();
                    break;
                }
            }
        }
        for(int i=0;i<numeroClusters;i++){
            variabilidadJugadores+=contadorClusters[i];
            mediaClusters[i][0]=mediaClusters[i][0]/numeroClusters;
            mediaClusters[i][1]=mediaClusters[i][1]/numeroClusters;
            mediaClusters[i][2]=mediaClusters[i][2]/numeroClusters;
            mediaClusters[i][3]=mediaClusters[i][3]/numeroClusters;
            mediaClusters[i][4]=mediaClusters[i][4]/numeroClusters;
        }
        variabilidadJugadores=variabilidadJugadores/numeroClusters;
        mediaClustersGeneral=mediaClusters;
        return variabilidadJugadores;
    }
    public static double obtenerDistanciaMinima(double distancias[]){
        double distanciaMinima=0;
        for(int i=0;i<distancias.length;i++){
            if(i==0){
                distanciaMinima=distancias[i];
            }else{
                if(distanciaMinima>distancias[i]){
                    distanciaMinima=distancias[i];
                }
            }
        }
        return distanciaMinima;
    }
    public static void main(String args[]){
        obtenerDatos();
        clusterizar();
    }
    
}
