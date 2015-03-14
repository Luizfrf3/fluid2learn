package pt.c02classes.s01knowledge.s02app.actors;

import java.util.*;

import pt.c02classes.s01knowledge.s01base.impl.Direction;
import pt.c02classes.s01knowledge.s01base.impl.Posicao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

    IResponder responder;
    /* Refazer para que ele descubra a saida automaticamente,
     * ele pergunta para o responder e movera o responder
     */
    public void connect(IResponder responder) {
        this.responder = responder;
    }

    private void goHorse(){
        String[] dir = {"norte", "sul", "leste", "oeste"};
        Random generator = new Random();
        while(!responder.ask("aqui").equals("saida")){
            responder.move(dir[generator.nextInt(4)]);
        }
    }
    
    private void metodoMenos() {
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
    
    private boolean metodoInteligente(){
        HashMap<String, Integer> map = new HashMap<>();
        Stack<Posicao> caminho = new Stack<>();
        Stack<Direction> direcao = new Stack<>();
        Direction direc = Direction.NORTE;
        Posicao  atual = new Posicao(0,0);
        int passos = 0;

        /* Adiciona os primeiros pontos na pilha caminho e
         * as direcoes que levaram a esses pontos na pilha direcoes */
        map.put(String.format("(%d, %d)", atual.x, atual.y), passos);
        /* Se houver uma passagem na direcao atual */
        for (Direction dir : Direction.values()) {
            if (responder.ask(dir.getStr()).equalsIgnoreCase("passagem")) {
                caminho.push(atual.seAndasse(dir));
                direcao.push(dir);
            }
        }

        while(!responder.ask("aqui").equals("saida")) {
            if (caminho.isEmpty())
                return false;
            /*Faz um movimento*/
            atual = caminho.pop();
            direc = direcao.pop();
            responder.move(direc.getStr());
            Direction veio_de = direc.revert();
            passos++;

            /* Se ja passou pela posicao ... */
            if (map.containsKey(String.format("(%d, %d)", atual.x, atual.y))) {
                /* ... pegue o numero de passos da posição */
                passos = map.get(String.format("(%d, %d)", atual.x, atual.y));

            /* Se ainda não passou... */
            } else {
                /* ...passe */
                map.put(String.format("(%d, %d)", atual.x, atual.y), passos);
            }

            Boolean mudouTamanho = false;
            /* Empilha todas as possibilidades de movimento */
            for(Direction dir = veio_de.anterior();
                    !dir.equals(veio_de);
                    dir = dir.anterior())
            {
                /* Pergunta se há passagem nessa direção */
                /* getStr converte Direction em String */
                if(responder.ask(dir.getStr()).equalsIgnoreCase("passagem")) {
                    /* Cria uma tentativa de movimento */
                    Posicao teste = atual.seAndasse(dir);
                    /* Se a posicao ainda nao foi tentada ou, pelo menos não da posicao
                     * atual e também se o caminho não for de volta */
                    if(!map.containsKey(String.format("(%d, %d)", teste.x, teste.y)) ||
                            map.get(String.format("(%d, %d)", teste.x, teste.y)) != passos + 1)
                    {
                        /* ...tente */
                        caminho.push(atual.seAndasse(dir));
                        direcao.push(dir);
                        mudouTamanho = true;
                    }
                }
            }
            /* Se nada foi acrescentado a pilha(a pilha n mudou de tamnho),
             * volta-se pra posicao anterior*/
            if(!mudouTamanho){
                responder.move(veio_de.getStr());
            }

        }

        return true;
    }

    public boolean discover() {
        /*Boolean saida = */metodoMenos();

        if (responder.finalAnswer("cheguei"))
            System.out.println("Você encontrou a saida!");
        else
            System.out.println("Fuém fuém fuém!");
		
		/*scanner.close();*/

        return /*saida*/true;
    }

}