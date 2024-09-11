package controller;

import java.util.concurrent.Semaphore;

public class PratosThread extends Thread{
	private int id;
	private String nomePrato;
	private int tempoPrato;
	private Semaphore semaforo;
	
	public PratosThread(Semaphore semaforo){
		this.semaforo = semaforo;
	}
	
	@Override
	public void run(){
		id = (int)threadId();
		if (id % 2 == 0){
	    	nomePrato = "Lasanha a bolonhesa";
	    	tempoPrato = (int)(Math.random() * 601)+ 600; //600ms - 1200ms
	    	System.out.println(tempoPrato);
		} else{
	        nomePrato = "Sopa de cebola";
	        tempoPrato = (int)(Math.random() * 301) + 500;//500ms - 800ms
	        System.out.println(tempoPrato);
	    }
		
		cozinharPrato();
		try{
			semaforo.acquire();
	        entregarPrato();
	    } catch (Exception e){
	    	e.printStackTrace();
	    }finally {
	        semaforo.release();
	    }
	}
	   
	private void cozinharPrato(){
		double tempoTotal = 0.0;
	    double porcentagemPrato = 0.0;
	    while (tempoTotal < tempoPrato){
	        System.out.println("(#" + id + ") " + nomePrato + " está cozinhando... ("+ (int)porcentagemPrato +"%)");
	        tempoTotal += 100;
	        porcentagemPrato = (tempoTotal/tempoPrato)*100;
	        try {
		    	sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    }
	    
	    System.out.println("(#" + id + ") " + nomePrato + " está PRONTA (100%)");
	}
	   
	private void entregarPrato(){
		try {
	    	sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("(#" + id + ") " + nomePrato + " foi SERVIDA.");
	}
}
