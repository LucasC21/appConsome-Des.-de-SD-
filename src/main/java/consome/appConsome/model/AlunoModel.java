package consome.appConsome.model;

public class AlunoModel {
	
	private Long id;
	private String nome;
	private int idade;
	private String sexo;
	private String curso;
	private String campus;
	
	
	public AlunoModel() {
		super();
	}
	
	public AlunoModel(String nome, int idade, String sexo, String curso, String campus) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.sexo = sexo;
		this.curso = curso;
		this.campus = campus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	@Override
	public String toString() {
		return "AlunoModel [nome=" + this.nome + ", idade=" + this.idade + ", sexo=" + this.sexo + ", curso=" + this.curso + ", campus="
				+ this.campus + "]";
	}
	
}
