package pt.c02classes.s01knowledge.s02app.app;

import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.*;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;
import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;

import java.util.Scanner;

public class OrchestratorGeral {
	/* Implementar um programa para funcionar com os dois jogos;
	 * ele pede por terminal o nome do jogo e o nome do animal
	 * ou do labirinto para realizar o jogo
	 */
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		IEnquirer enq;
		IResponder resp;
		IStatistics stat;

		System.out.print("Jogar (A)nimals, (M)aze, ou (S)air? ");
		String jogo = teclado.nextLine();
		while(!jogo.equalsIgnoreCase("S")) {
			switch (jogo.toUpperCase()) {
				case "A":
					BaseConhecimento base = new BaseConhecimento();

					base.setScenario("animals");
					String listaAnimais[] = base.listaNomes();
					for (String listaAnimal : listaAnimais) {
						System.out.println("Enquirer com " + listaAnimal + "...");
						stat = new Statistics();
						resp = new ResponderAnimals(stat, listaAnimal);
						enq = new EnquirerAnimals();
						enq.connect(resp);
						enq.discover();
						System.out.println("----------------------------------------------------------------------------------------\n");
					}
					break;
				case "M":
					System.out.println("Escolha o Labirinto: ");
					System.out.print("--> ");
					String labirinto = teclado.nextLine();
					System.out.printf("Enquirer com %s...%n", labirinto);
					labirinto = labirinto.toLowerCase();
					stat = new Statistics();
					resp = new ResponderMaze(stat, labirinto);
					enq = new EnquirerMaze();
					enq.connect(resp);
					enq.discover();
					System.out.println("----------------------------------------------------------------------------------------\n");
					break;

			}
			System.out.print("Jogar (A)nimals (M)aze, ou (S)air? ");
			jogo = teclado.nextLine();
		}
		teclado.close();

	}


}
