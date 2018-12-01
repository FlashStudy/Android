package br.com.flashstudy.api.model;

//Classe com o assunto e mensagem do usuário em caso de dúvida/crítica/sugestão
public class Duvida {

	private String assunto;
	private String mensagem;

	public Duvida() {
		super();
	}

	public Duvida(String assunto, String mensagem) {
		super();
		this.assunto = assunto;
		this.mensagem = mensagem;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "Duvida [assunto=" + assunto + ", mensagem=" + mensagem + "]";
	}

}
