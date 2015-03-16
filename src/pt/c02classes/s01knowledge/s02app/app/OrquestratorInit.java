package pt.c02classes.s01knowledge.s02app.app;

import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.*;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;
import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;

import java.util.Scanner;

public class OrquestratorInit {
	/*
	* Trabalho: Etapa 2
	*          Lucas Alves Racoci         | RA: 156331 | Turma B
	*                                     |            |
	*  Luiz Fernando Rodrigues da Fonseca | RA: 156475 | Turma A
	*/
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		IEnquirer enq;
		IResponder resp;
		IStatistics stat;

		/* Pergunta o jogo e o animal ou labirinto para adivinhar ou encontrar a saida */
		System.out.print("Jogar (A)nimals, (M)aze, ou (S)air? ");
		String jogo = teclado.nextLine();
		while(!jogo.equalsIgnoreCase("S")) {
			switch (jogo.toUpperCase()) {
				case "A":
					BaseConhecimento base = new BaseConhecimento();
					base.setScenario("animals");
					System.out.println("Escolha o animal: ");
					System.out.print("--> ");
					String animal = teclado.nextLine();
					System.out.println("Enquirer com " + animal + "...");
					stat = new Statistics();
					resp = new ResponderAnimals(stat, animal);
					enq = new EnquirerAnimals();
					enq.connect(resp);
					enq.discover();
					System.out.println("----------------------------------------------------------------------------------------\n");
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
