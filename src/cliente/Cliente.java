package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente{
	
  private static String response = "";
  private static final int ok = 200;  
  private static final int badRequest = 400;
  private static final int forbidden = 403;
  private static final int notFound = 404;

  public static String request(String endereco) {
	  	// Monta formato de requisição
	  	if(endereco == null){
	  		endereco = "";
	  	}
		String formato = "GET /"+endereco+" HTTP/1.0";
		System.out.println("Requisição: "+formato);
		
		// Inicia conexão com o servidor
		Socket s = null;
		try{
	      System.out.println("Conectando...");
	      s = new Socket("172.16.24.251", 6789); // mudar o endereço!
	      s.setSoTimeout(15000);
	      DataInputStream in = new DataInputStream(s.getInputStream());
	      DataOutputStream out = new DataOutputStream(s.getOutputStream());

	      out.writeUTF(formato);
	      out.flush();

	      // Faz o tratamento da resposta
	      response = in.readUTF();
	      String[] array = response.split("\n");
	      if(array.length > 1){
	    	 response = array[1]; 
	      }
	      System.out.println("Resposta: "+array[0]);
	      array = array[0].split(" ");
	      
	      int codigo = Integer.parseInt(array[1].trim());
	      if(codigo == notFound){
	    	  response = "<HTM><HEA><TIT>"+codigo+" "+array[2]+" "+array[3]+"</TIT></HEA><BOD><CEN><PAR><TAM 70><COR azul>"+codigo+"</COR></TAM></PAR><COR preto><PAR><TAM 14>"+array[2]+" "+array[3]+"</TAM></PAR><PAR><TAM 18>Não conseguimos encontrar a página solicitada.</TAM></PAR></COR></CEN></BOD></HTM>";
	      }else if(codigo == badRequest){
	    	  response = "<HTM><HEA><TIT>"+codigo+" "+array[2]+" "+array[3]+"</TIT></HEA><BOD><CEN><PAR><TAM 70><COR azul>"+codigo+"</COR></TAM></PAR><COR preto><PAR><TAM 14>"+array[2]+" "+array[3]+"</TAM></PAR><PAR><TAM 18>Requisição com formato inválido.</TAM></PAR></COR></CEN></BOD></HTM>";
	      }
	    }catch(Exception e){
	      // Monta HTM do erro em caso de exceção
		  System.out.println("Erro: "+e.getMessage());
	      response = "<HTM><HEA><TIT>Erro</TIT></HEA><BOD><CEN><TAM 22><COR vermelho><PAR>"+formato+"</PAR><PAR>"+e.getMessage()+"</PAR></COR></TAM></CEN></BOD></HTM>";
	    }finally{
	        try {if(s!=null) s.close();} catch(Exception e2){}
	    }
	    System.out.println("Conexão encerrada");
	    //try {System.in.read();} catch(Exception e3){} REMOVI POR QUE TAVA TRAVANDO O CLIENTE
	    return response;
	}
}
