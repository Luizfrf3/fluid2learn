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
        Boolean saida = metodoInteligente();

        if (responder.finalAnswer("cheguei"))
            System.out.println("Você encontrou a saida!");
        else
            System.out.println("Fuém fuém fuém!");
		
		/*scanner.close();*/

        return saida;
    }

}