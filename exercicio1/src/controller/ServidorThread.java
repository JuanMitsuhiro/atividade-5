package controller;

import java.util.concurrent.Semaphore;

public class ServidorThread extends Thread{
	private int id;
	private int tempoCalculo;
	private int tempoTransacao;
	private Semaphore semaforo;
	
	public ServidorThread(int id, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
	}
	
	@Override
	public void run() {
		switch (id % 3) {
			case 1:
				tempoCalculo = (int)(Math.random() * 801) + 200;
				tempoTransacao = 1000;
				break;
			case 2:
				tempoCalculo = (int)(Math.random() * 1001) + 500;
				tempoTransacao = 1500;
				break;
			case 0:
				tempoCalculo = (int)(Math.random() * 1001) + 1000;
				tempoTransacao = 1500;
				break;
			default:
				break;
		}

		
		for (int i = 0; i < 2; i++) {
			calculo(tempoCalculo);
			try {
				semaforo.acquire();
				transacao(tempoTransacao);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforo.release();
			}
		}
		System.out.println("#" + id + " - FINALIZADO");
	}
	
	private void calculo(int tempo) {
		try {
			System.out.println("#"+ id +" - Iniciando cálculo");
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("#"+ id +" - Calculo finalizado (" + (double)tempo/1000 + "s)");
	}
	
	private void transacao(int tempo) {
		try {
			System.out.println("#"+ id +" - Iniciando transação");
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("#"+ id +" - Transação finalizada (" + (double)tempo/1000 + "s)");
	}

}
