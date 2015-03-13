

public interface IBaseConhecimento
{
	public void setScenario(String scenario);
    public String[] listaNomes();
	public IObjetoConhecimento recuperaObjeto(String nome);
}