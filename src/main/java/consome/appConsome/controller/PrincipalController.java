package consome.appConsome.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import consome.appConsome.model.AlunoModel;
import consome.appConsome.util.Util;

@Controller
public class PrincipalController {
	static String webService = "http://localhost:8082/aluno";
	static int codigoSucesso = 200;

	@GetMapping("/")
	public String principal() {
		return "redirect:/index";
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("indexNovo");
		return modelAndView;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastroAluno() {
		ModelAndView modelAndView = new ModelAndView("cadastrarAlunos");
		modelAndView.addObject("aluno", new AlunoModel());
		return modelAndView;
	}

	@PostMapping("/alunooos")
	public String salvar(AlunoModel aluno) throws Exception {
		System.out.println("\n>>> SALVANDO ALUNO <<<\n");
		System.out.println(">>> " + aluno);
		ModelAndView modelAndView = new ModelAndView("listarAlunos");
		String conectaAPI = webService + "/salvar/" + aluno;
		try {
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			System.out.println(conexao);
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			String jsonEmString = Util.converteJsonEmString(resposta);
			System.out.println(jsonEmString);
			modelAndView.addObject(jsonEmString);

			return "redirect:/alunos";
		} catch (Exception e) {
			throw new Exception("Erro ao conectar na API: " + e);
		}
	}
	
//	@GetMapping("/listAlunos")
//	public ModelAndView listaAluno() {
//		ModelAndView modelAndView = new ModelAndView("listarAlunos");
////		modelAndView.addObject("aluno", new AlunoModel());
//		return modelAndView;
//	}
	
	@GetMapping("/listarAlunos")
	public ModelAndView listar() throws Exception {
		ModelAndView modelAndView = new ModelAndView("listarAlunos");
		System.out.println(">>> " + modelAndView);
		String conectaAPI = webService + "/todos";
		String jsonEmString = "";

		try {
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			jsonEmString = Util.converteJsonEmString(resposta);
			TypeToken tt = new TypeToken<List<AlunoModel>>() {};
		    Gson gson = new Gson();
		    List<AlunoModel> fromJson = gson.fromJson(jsonEmString, tt.getType());

			modelAndView.addObject("alunos", fromJson);
			System.out.println(fromJson);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

//	@GetMapping("/aluno/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		ModelAndView modelAndView = new ModelAndView("editarAlunos");
//		modelAndView.addObject("aluno", alunoService.buscarPorId(id));
//		return modelAndView;
//	}

	@GetMapping("/buscar")
	public ModelAndView buscar() {
		ModelAndView modelAndView = new ModelAndView("buscar");
		return modelAndView;
	}

	@GetMapping("/buscarId")
	public ModelAndView buscarPorId(Long id) throws Exception {
		Long idAluno = id;
		ModelAndView modelAndView = new ModelAndView("listarPorId");
		String conectaAPI = webService + "/unico/" + id;
		String jsonEmString = "";
		ObjectMapper mapper= new ObjectMapper();

		try {
			System.out.println("\n>>> ESTOU NO TRY <<<\n" + idAluno);
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			jsonEmString = Util.converteJsonEmString(resposta);
			AlunoModel aluno = mapper.readValue(jsonEmString, AlunoModel.class);

			modelAndView.addObject("alunos", aluno);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	@GetMapping("/buscarNome")
	public ModelAndView buscarNome() {
		System.out.println("\n>>> EM BUSCAR <<<\n");
		ModelAndView modelAndView = new ModelAndView("buscarNome");
		return modelAndView;
	}

	@GetMapping("/buscarPorNome")
	public ModelAndView buscarPorNome(String nome) throws Exception {
		System.out.println("\n>>> EM BUSCAR POR NOME <<<\n");
		String nomeAluno = nome;
		ModelAndView modelAndView = new ModelAndView("listarPorNome");
		String conectaAPI = webService + "/nome/" + nomeAluno;
		String jsonEmString = "";
		
		try {
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			jsonEmString = Util.converteJsonEmString(resposta);
			TypeToken tt = new TypeToken<List<AlunoModel>>() {};
		    Gson gson = new Gson();
		    List<AlunoModel> fromJson = gson.fromJson(jsonEmString, tt.getType());
		    
			modelAndView.addObject("alunos", fromJson);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	@GetMapping("/buscarCampus")
	public ModelAndView buscarCampus() {
		System.out.println("\n>>> EM BUSCAR <<<\n");
		ModelAndView modelAndView = new ModelAndView("buscarCampus");
		return modelAndView;
	}

	@GetMapping("/buscarPorCampus")
	public ModelAndView buscarPorCampus(String campus) throws Exception {
		System.out.println("\n>>> EM BUSCAR POR CAMPUS <<<\n");
		String nomeCampus = campus;
		ModelAndView modelAndView = new ModelAndView("listarPorCampus");
		String conectaAPI = webService + "/campus/" + nomeCampus;
		String jsonEmString = "";
		
		try {
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			jsonEmString = Util.converteJsonEmString(resposta);
			TypeToken tt = new TypeToken<List<AlunoModel>>() {};
		    Gson gson = new Gson();
		    List<AlunoModel> fromJson = gson.fromJson(jsonEmString, tt.getType());
		    
			modelAndView.addObject("alunos", fromJson);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	@GetMapping("/buscarCurso")
	public ModelAndView buscarCurso() {
		ModelAndView modelAndView = new ModelAndView("buscarCurso");
		return modelAndView;
	}

	@GetMapping("/buscarPorCurso")
	public ModelAndView buscarPorCurso(String curso) throws Exception {
		String nomeCurso = curso;
		ModelAndView modelAndView = new ModelAndView("listarPorCurso");
		String conectaAPI = webService + "/curso/" + nomeCurso;
		String jsonEmString = "";
		
		try {
			URL url = new URL(conectaAPI);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
			
			if (conexao.getResponseCode() != codigoSucesso) {
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
			}
			
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			jsonEmString = Util.converteJsonEmString(resposta);
			TypeToken tt = new TypeToken<List<AlunoModel>>() {};
		    Gson gson = new Gson();
		    List<AlunoModel> fromJson = gson.fromJson(jsonEmString, tt.getType());
		    
			modelAndView.addObject("alunos", fromJson);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}

	@GetMapping("/remover")
	public ModelAndView remover() {
		System.out.println("\n>>> EM REMOVER <<<\n");
		ModelAndView modelAndView = new ModelAndView("remover");
		return modelAndView;
	}

	@GetMapping("/deletar/{id}")
	public String deletar(Long id) {

		return "redirect:/alunos";
	}
//
//	public List<AlunoModel> buscarAlunos() {
//		List<AlunoModel> alunos = new ArrayList<AlunoModel>();
//		return alunos;
//	}

}