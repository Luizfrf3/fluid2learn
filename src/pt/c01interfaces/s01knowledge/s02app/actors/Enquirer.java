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
	
	
	@Override
	public void connect(IResponder responder)
	{
        IBaseConhecimento bc = new BaseConhecimento();

        String[] animais;
        animais = bc.listaNomes();

        HashMap<String, String> resps_adq;
        resps_adq = new HashMap<String, String>();

        boolean acertei = false;

        /*for(int i = 0; i < animais.length; i++){
            System.out.println(animais[i]);
        }*/
       /* Testar todos os animais */
		for(int i = 0; i < animais.length && !acertei; i++) {
            /* Recupera o arquivo txt do objeto */
            obj = bc.recuperaObjeto(animais[i]);

            /* Pega a pergunta */
            IDeclaracao decl = obj.primeira();

            boolean animalEsperado = true;
            while (decl != null && animalEsperado) {
                String pergunta = decl.getPropriedade();
                String respostaEsperada = decl.getValor();

                String resposta = responder.ask(pergunta);
                if (resposta.equalsIgnoreCase(respostaEsperada))
                    decl = obj.proxima();
                else
                    animalEsperado = false;
            }
           if(animalEsperado) {
               acertei = responder.finalAnswer(animais[i]);
           }
        }
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
