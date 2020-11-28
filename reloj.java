/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temporizador;



import java.io.BufferedInputStream;

import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


/**
 *
 * @author Javi G0
 */
public class reloj {
   
    int segundosIniciales;
    boolean detenido;
    boolean finalizado;
    
    
    public reloj() {
    }

    public reloj(int segundosIniciales) {
        this.segundosIniciales = segundosIniciales;
    }

    public int getSegundosIniciales() {
        return segundosIniciales;
    }

    public void setSegundosIniciales(int segundosIniciales) {
        this.segundosIniciales = segundosIniciales;
    }

    public boolean isDetenido() {
        return detenido;
    }

    public void setDetenido(boolean detenido) {
        this.detenido = detenido;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
    
    
    
    
    public String cronometro(int segundos) {
        //int segundos=segundosIniciales;
        //for(int i =segundos;i>0;i--){
            int dias = (int)Math.floor(segundos/(60*60*24));
            int horas= (int)Math.floor((segundos%(60*60*24))/(60*60));
            int minutos = (int)Math.floor((segundos%(60*60))/60);
            int segundoss = (int)Math.floor(segundos%60);
            
            //ventana.marcador.setText(" -->"+Integer.toString(dias)+" dias - "+Integer.toString(horas)+ " horas - "+Integer.toString(minutos)+" minutos - "+Integer.toString(segundoss)+" segundos");
            //System.out.println("Hasta aqui");
            System.out.println("Tiempo para terminar "+ventana.tarea.getText()+" -->"+Integer.toString(dias)+" dias - "+Integer.toString(horas)+ " horas - "+Integer.toString(minutos)+" minutos - "+Integer.toString(segundoss)+" segundos");
            ventana.marcador.setText(ventana.tarea.getText()+" - FALTA -->"+Integer.toString(dias)+" dias - "+Integer.toString(horas)+ " horas - "+Integer.toString(minutos)+" minutos - "+Integer.toString(segundoss)+" segundos");
            return " -->"+Integer.toString(dias)+" dias - "+Integer.toString(horas)+ " horas - "+Integer.toString(minutos)+" minutos - "+Integer.toString(segundoss)+" segundos";
            //ventana.marcador.setText("");
        //}
        //ventana.marcador.setText("El tiempo ha acabado");
        
    }
    
    
    public void contar(){
		//Se lanza un nuevo hilo para que el hilo principal pueda mostrar
		//los cambios en el cuadro de texto del bean mientras se encuentra
		//realizando las operaciones en el bucle.
		//El hilo tiene el nombre "Hilo Contador" y se inicia con la
		//instrucción start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
					try {
						//Espera un segundo, siempre y cuando no esté detenido
						for(int i = getSegundosIniciales(); i>=0 && !detenido; i--){
							Thread.sleep(1000);
                                                        cronometro(i);
                                                        setSegundosIniciales(--segundosIniciales);
                                                        if(i==0){
                                                            ventana.marcador.setText("El tiempo para la tarea "+ventana.tarea.getText() +" ha finalizado");
                                                            setFinalizado(true);
                                                            hablar();
                                                        }
                                                }
					} catch (InterruptedException e) {} catch (FileNotFoundException ex) {
                                Logger.getLogger(reloj.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (JavaLayerException ex) {
                                Logger.getLogger(reloj.class.getName()).log(Level.SEVERE, null, ex);
                            }
					
                                        
                   
			}
			
		},"Hilo Contador").start();		
	}
    
    public void hablar() throws FileNotFoundException, JavaLayerException{
        Player player;
        try {
        InputStream stream = reloj.class.getClassLoader().getResourceAsStream("imagenes/fin.mp3");
        BufferedInputStream bis = new BufferedInputStream(stream);
        player = new Player(bis);
        

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
        
        } catch (Exception e) {
            System.out.println("Problem playing file " );
            System.out.println(e);
        }
        
        
        
        
        
       
        
        
        
        /*
            String path = getClass().getResourceAsStream("imagenes/fin.mp3").toString();
            Player apl = new Player(new FileInputStream(path));
            apl.play();
        */
        
    }
    
}
