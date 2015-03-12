package pt.c02classes.s01knowledge.s02app.app;

import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
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
        Scanner scanner = new Scanner(System.in);

        IEnquirer enq;
        IResponder resp;
        IStatistics stat;

        System.out.print("Jogar (A)nimals, (M)aze, ou (S)air? ");
        String jogo = scanner.nextLine();
        while(!jogo.equalsIgnoreCase("F")) {
            switch (jogo.toUpperCase()) {
                case "A":
                    IBaseConhecimento base = new BaseConhecimento();

                    base.setScenario("animals");
                    String listaAnimais[] = base.listaNomes();
                    for (String listaAnimai : listaAnimais) {
                        System.out.println("Enquirer com " + listaAnimai + "...");
                        stat = new Statistics();
                        resp = new ResponderAnimals(stat, listaAnimai);
                        enq = new EnquirerAnimals();
                        enq.connect(resp);
                        enq.discover();
                        System.out.println("----------------------------------------------------------------------------------------\n");
                    }
                    break;
                case "M":
                    System.out.println("Escolha o Labirinto: ");
                    System.out.print("--> ");
                    String labirinto = scanner.nextLine();
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
            jogo = scanner.nextLine();
        }
        scanner.close();

    }


}

