package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;
import java.util.Map;
import java.util.HashMap;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public Enquirer()
	{
	}
	
	/*
	* Trabalho: Etapa 1
	*          Lucas Alves Racoci         | RA: 156331 | Turma B
	*                                     |            |
	*  Luiz Fernando Rodrigues da Fonseca | RA: 156475 | Turma A
	*/
	@Override
	public void connect(IResponder responder)
	{
        IBaseConhecimento bc = new BaseConhecimento();

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

	}

}
