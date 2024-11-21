package services;

import android.os.StrictMode;
import android.widget.Toast;

import com.lifegreen.testepim.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Cliente;
import model.Endereco;

public class ClienteApiService {
    public static final String BASE_URL = "http://10.0.2.2:8080/api/clientes";
    public static final String URL_SENHA = "http://10.0.2.2:8080/api/recuperacao";


 public static Cliente listarCliente(String cpf)throws Exception {
     URL url = new URL(BASE_URL + "/listarCliente?cpf=" + cpf);
     HttpURLConnection con = (HttpURLConnection) url.openConnection();
     con.setRequestMethod("GET");
     con.setRequestProperty("Content - Type", "application/json");

     int responseCode = con.getResponseCode();
     if (responseCode == HttpURLConnection.HTTP_OK) {
         // Ler a resposta
         InputStream inputStream = con.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
         StringBuilder response = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
             response.append(line);
         }

         // Converter a resposta JSON em um objeto Cliente
         String jsonResponse = response.toString();
         Cliente cliente = parseClienteFromJson(jsonResponse);
         return cliente;
     }
     else {
         return null;
     }
 }

    public static String addCliente(Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/cadastrar"); // Cria a URL completa para a operação de adicionar.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("POST"); // Define o método da requisição como POST.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados do cliente.
        JSONObject jsonCliente = new JSONObject();
        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome ao objeto JSON.
        jsonCliente.put("cpf", cliente.getCpf());
        jsonCliente.put("telefone", cliente.getTelefone());
        jsonCliente.put("email", cliente.getEmail()); // Adiciona o email ao objeto JSON.
        jsonCliente.put("senha", cliente.getSenha());
        jsonCliente.put("cep", cliente.getCep());
        jsonCliente.put("logradouro", cliente.getLogradouro());
        jsonCliente.put("numero", cliente.getNumero());
        jsonCliente.put("complemento", cliente.getComplemento());
        jsonCliente.put("localidade", cliente.getLocalidade());
        jsonCliente.put("bairro", cliente.getBairro());
        jsonCliente.put("estado", cliente.getEstado());

        // Envia o JSON com os dados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }


    // Método para iniciar o processo de recuperação de senha (enviar código para o e-mail)
    public static String iniciarRecuperacaoSenha(String cpf) {

        String endpoint = URL_SENHA + "/iniciar";  // Endpoint para enviar o código
        String parametros = "cpf=" + cpf;  // Parâmetro para enviar o CPF

        return executarPost(endpoint, parametros);
    }

    // Método para validar o código de recuperação
    public static String validarCodigo(String cpf, String codigo) {
        String endpoint = URL_SENHA + "/validar";  // Endpoint para validar o código
        String parametros = "cpf=" + cpf + "&codigo=" + codigo;  // Parâmetros para CPF e Código

        return executarPost(endpoint, parametros);
    }

    // Método para alterar a senha
    public static String alterarSenha(String cpf, String codigo, String novaSenha) {
        String endpoint = URL_SENHA + "/alterar-senha";  // Endpoint para alterar a senha
        String parametros = "cpf=" + cpf + "&codigo=" + codigo + "&novaSenha=" + novaSenha;  // Parâmetros para CPF, Código e Nova Senha

        return executarPost(endpoint, parametros);
    }

    // Método genérico para enviar um POST
    private static String executarPost(String endpoint, String parametros) {
        try {
            // Permite realizar a conexão em threads principais (pode ser necessário ajustar dependendo da sua configuração)
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            // Conexão com a API
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // Envia os parâmetros para a API
            try (OutputStream os = conn.getOutputStream()) {
                os.write(parametros.getBytes());
                os.flush();
            }

            // Lê a resposta da API
            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao conectar com a API: " + e.getMessage();
        }
    }






    private static Cliente parseClienteFromJson(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            Cliente cliente = new Cliente();
            //Endereco endereco = new Endereco();
           // cliente.setEndereco(endereco);
            cliente.setId(jsonObject.getLong("id"));
            cliente.setNome(jsonObject.getString("nome"));
            cliente.setCpf(jsonObject.getString("cpf"));
            cliente.setTelefone(jsonObject.getString("telefone"));
            cliente.setEmail(jsonObject.getString("email"));
            cliente.setSenha(jsonObject.getString("senha"));
            cliente.getEndereco().setCep(jsonObject.getString("cep"));
            cliente.getEndereco().setLogradouro(jsonObject.getString("logradouro"));
            cliente.getEndereco().setNumero(jsonObject.getString("numero"));
            cliente.getEndereco().setComplemento(jsonObject.getString("complemento"));
            cliente.getEndereco().setLocalidade(jsonObject.getString("localidade"));
            cliente.getEndereco().setBairro(jsonObject.getString("bairro"));
            cliente.getEndereco().setEstado(jsonObject.getString("estado"));

            return cliente;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String updateCliente(String cpf, Cliente cliente) throws Exception {
        URL url = new URL(BASE_URL + "/atualizar/" + cpf); // Cria a URL completa para a operação de atualização.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("PUT"); // Define o método da requisição como PUT.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.
        conn.setDoOutput(true); // Permite enviar dados na requisição.

        // Cria um objeto JSON com os dados atualizados do cliente.
        JSONObject jsonCliente = new JSONObject();

        jsonCliente.put("nome", cliente.getNome()); // Adiciona o nome atualizado ao objeto JSON.
        jsonCliente.put("cpf", cliente.getCpf());
        jsonCliente.put("telefone", cliente.getTelefone()); // Adiciona o email atualizado ao objeto JSON.
        jsonCliente.put("email", cliente.getEmail());
        jsonCliente.put("senha", cliente.getSenha());
        jsonCliente.put("cep", cliente.getCep());
        jsonCliente.put("logradouro", cliente.getLogradouro());
        jsonCliente.put("numero", cliente.getNumero());
        jsonCliente.put("complemento", cliente.getComplemento());
        jsonCliente.put("localidade", cliente.getLocalidade());
        jsonCliente.put("bairro", cliente.getBairro());
        jsonCliente.put("estado", cliente.getEstado());
        // Envia o JSON com os dados atualizados do cliente.
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(jsonCliente.toString()); // Escreve o JSON no corpo da requisição.
        writer.flush();
        writer.close();

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }


    public static String deleteCliente(String cpf) throws Exception {
        URL url = new URL(BASE_URL + "/deletar/" + cpf); // Cria a URL completa para a operação de exclusão.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Abre uma conexão HTTP.
        conn.setRequestMethod("DELETE"); // Define o método da requisição como DELETE.
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo de conteúdo como JSON.

        // Lê a resposta da API.
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { // Lê cada linha da resposta.
            result.append(line);
        }
        reader.close();
        return result.toString(); // Retorna a resposta da API.
    }
}
