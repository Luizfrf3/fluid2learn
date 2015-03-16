package pt.c02classes.s01knowledge.s02app.actors;

import java.util.*;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

    IResponder responder;
    
    public void connect(IResponder responder) {
        this.responder = responder;
    }
    
    private void metodoInteligente() {
    	Hashtable<String, Integer> map = new Hashtable<>();
        Stack<String> caminho = new Stack<String>();
        Posicao atual = new Posicao(0,0);
        
        /* Coloca a primeira posicao em uma tabela Hash e em uma pilha */
        map.put(String.format("(%d, %d)", atual.x, atual.y), 1);
        caminho.push(String.format("%d,%d", atual.x, atual.y));
        
        /* Pergunta uma direcao de cada vez: se tiver passagem e se a proxima
         * posicao ainda nao tiver sido visitada, ou se for a saida; ele move,
         * atualiza a posicao, coloca na pilha e no hash
         */
        while (!responder.ask("aqui").equalsIgnoreCase("saida")) {
        	/*System.out.println(caminho);*/
        	if ((responder.ask("norte").equalsIgnoreCase("passagem") &&
        		!map.containsKey(String.format("(%d, %d)", atual.x, atual.y + 1))) ||
        		responder.ask("norte").equalsIgnoreCase("saida")) {
        		responder.move("norte");
        		atual.y++;
        		caminho.push(String.format("%d,%d", atual.x, atual.y));
        		/*System.out.println("N " + atual.x + " " + atual.y);*/
        		map.put(String.format("(%d, %d)", atual.x, atual.y), 1);
        	} else if ((responder.ask("leste").equalsIgnoreCase("passagem") &&
        			   !map.containsKey(String.format("(%d, %d)", atual.x + 1, atual.y))) ||
        			   responder.ask("leste").equalsIgnoreCase("saida")) {
            	responder.move("leste");
            	atual.x++;
            	caminho.push(String.format("%d,%d", atual.x, atual.y));
            	/*System.out.println("L " + atual.x + " " + atual.y);*/
            	map.put(String.format("(%d, %d)", atual.x, atual.y), 1);
            } else if ((responder.ask("sul").equalsIgnoreCase("passagem") &&
            		   !map.containsKey(String.format("(%d, %d)", atual.x, atual.y - 1))) ||
            		   responder.ask("sul").equalsIgnoreCase("saida")) {
            	responder.move("sul");
            	atual.y--;
            	caminho.push(String.format("%d,%d", atual.x, atual.y));
            	/*System.out.println("S " + atual.x + " " + atual.y);*/
            	map.put(String.format("(%d, %d)", atual.x, atual.y), 1);
            } else if ((responder.ask("oeste").equalsIgnoreCase("passagem") &&
            		   !map.containsKey(String.format("(%d, %d)", atual.x - 1, atual.y))) ||
            		   responder.ask("oeste").equalsIgnoreCase("saida")) {
               	responder.move("oeste");
               	atual.x--;
               	caminho.push(String.format("%d,%d", atual.x, atual.y));
               	/*System.out.println("O " + atual.x + " " + atual.y);*/
               	map.put(String.format("(%d, %d)", atual.x, atual.y), 1);
            } else {
            	/* Caso nao va para nenhuma posicao, retorna para a posicao
            	 * anterior que estava na pilha para continuar procurando
            	 */
            	String[] pos;
            	caminho.pop();
            	pos = caminho.peek().split(",");
            	int x = Integer.parseInt(pos[0]);
            	int y = Integer.parseInt(pos[1]);
            	if (atual.x > x) {
            		responder.move("oeste");
            	} else if (atual.x < x) {
            		responder.move("leste");
            	} else if (atual.y > y) {
            		responder.move("sul");
            	} else if (atual.y < y) {
            		responder.move("norte");
            	}
            	atual.set(x, y);
            }
        	
        }
        
    }

    public boolean discover() {
        metodoInteligente();

        if (responder.finalAnswer("cheguei"))
            System.out.println("Você encontrou a saida!");
        else
            System.out.println("Fuém fuém fuém!");

        return true;
    }
    
    /* Classe para guardar a posicao no tabuleiro, supondo que ela comeca em (0,0) */
    private class Posicao {
        public int x = 0;
        public int y = 0;
        
        public Posicao(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public void set(int x, int y){
            this.x = x;
            this.y = y;
        }
        
    }

}