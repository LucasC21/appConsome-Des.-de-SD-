package consome.appConsome.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import consome.appConsome.model.Endereco;
import consome.appConsome.util.*;

import com.google.gson.Gson;

@Controller
@RequestMapping("/cep")
public class CepController {
	static String webService = "http://viacep.com.br/ws/";
	static int codigoSucesso = 200;

	@GetMapping("/insereCEP")
	public ModelAndView informacep() {
		ModelAndView modelAndView = new ModelAndView("cep/insereCEP");
		return modelAndView;
	}

	@PostMapping("/consomeCEP")
	public ModelAndView buscarCep(String cep) throws Exception {
		ModelAndView modelAndView = new ModelAndView("cep/exibeCEP");
		String urlParaChamada = webService + cep + "/json";

		try {
			URL url = new URL(urlParaChamada);
			HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

			if (conexao.getResponseCode() != codigoSucesso)
				throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
			String jsonEmString = Util.converteJsonEmString(resposta);
			System.out.println(resposta);
			Gson gson = new Gson();
			Endereco endereco = gson.fromJson(jsonEmString, Endereco.class);
			System.out.println(endereco);
			modelAndView.addObject("endereco", endereco);
			return modelAndView;
		} catch (Exception e) {
			throw new Exception("ERRO: " + e);
		}
	}
}
