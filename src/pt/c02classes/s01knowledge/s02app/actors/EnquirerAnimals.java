package pt.c02classes.s01knowledge.s02app.actors;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import java.util.Map;
import java.util.HashMap;

public class EnquirerAnimals implements IEnquirer {

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
        IBaseConhecimento bc = new BaseConhecimento();
        IObjetoConhecimento obj;
		
		bc.setScenario("animals");
		
		String[] animais;
        animais = bc.listaNomes();

        HashMap<String, String> resps_adq;
        resps_adq = new HashMap<String, String>();

        boolean acertei = false;
        /* Testar todos os animais até acertar */
		for (int i = 0; i < animais.length && !acertei; i++) {
            /* Recupera o arquivo txt do objeto */
            obj = bc.recuperaObjeto(animais[i]);

            /* Pega a pergunta */
            IDeclaracao decl = obj.primeira();

            boolean animalEsperado = true;

            /* Pega cada pergunta e a resposta de cada animal, checa se ela
             * ja foi perguntada anteriormente no hash e chama ou nao o
             * programa Responder; depois compara com a resposta esperada
             * e pula para o proximo animal caso nao for a esperada
             */
            while (decl != null && animalEsperado) {
                String pergunta = decl.getPropriedade();
                String respostaEsperada = decl.getValor();
                String resposta;
                if (resps_adq.containsKey(pergunta)) {
                    resposta = resps_adq.get(pergunta);
                } else {
                    resposta = responder.ask(pergunta);
                    resps_adq.put(pergunta, resposta);
                }
                if (resposta.equalsIgnoreCase(respostaEsperada))
                    decl = obj.proxima();
                else
                    animalEsperado = false;
            }
            /* Se acertou o animal chama a resposta final */
            if (animalEsperado) {
                acertei = responder.finalAnswer(animais[i]);
            }
        }
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;
	}

}
