package servidor;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servidor {
	private final static String statusOk = "HTTP/1.0 200 OK\n";
	private final static String statusBad = "HTTP/1.0 400 Bad Request\n";
	private final static String statusNot = "HTTP/1.0 404 Not Found\n";
	private final static String statusForbidden = "HTTP/1.0 403 Forbidden\n";

	private final static String ola= "<HTM><HEA><TIT>Ol� mundo!</TIT></HEA>"
			+"<BOD><CEN>"
			+"<PAR><TAM 24><COR verde><NEG>Ol� <ITA>mundo</ITA>!</NEG></COR></TAM></PAR>"
			+"<PAR><SUB><TAM 10>este � um <NEG>teste</NEG> de <ITA>HTM.</ITA></TAM></SUB></PAR>"
			+"<PAR><IMG http://www.tecnicontrol.pt/multimedia/images/comunica%C3%A7%C3%B5es/Cabos%20rede%20estruturada.jpg></PAR>"
			+"</CEN></BOD></HTM>";
	private final static String adm = "<HTM><HEA><TIT> Administrador</TIT></HEA>" 
			+"<BOD><CEN>"
			+"<PAR><TAM 30><COR azul><NEG>N�O AUTORIZADO</NEG></COR></TAM></PAR>"
			+"<PAR><IMG http://www.dicasdeviagensbaratas.com.br/wp-content/uploads/2014/04/Cadeado-para-mala-300x300.jpg></PAR>" 
			+"</CEN></BOD></HTM>";
	private final static String redes= "<HTM><HEA><TIT>Rede de Computadores </TIT></HEA> "
			+"<BOD><CEN>"
			+"<PAR><IMG http://www.bitabi.pt/ficheiros/bitabi_pt_2012/NETWORK.jpg></PAR>"
			+"<PAR><TAM 26><COR roxo><ITA>Tudo Sobre<NEG> REDES</NEG></ITA></COR></TAM></PAR>"
			+"<PAR><TAM 18><COR preto><NEG>Defini��o:</NEG> Uma rede de computadores � um conjunto de dois ou mais dispositivos (tamb�m chamados de n�s) que usam um conjunto de regras (protocolo) em comum para compartilhar recursos.<br/><NEG>Fonte: </NEG>(<SUB>pt.wikibooks.org</SUB>)</COR></TAM></PAR>"
			+"<PAR><IMG http://3.bp.blogspot.com/-CHRhMx5fSQg/TaYKzCnTM4I/AAAAAAAAAoA/QVdmjmti19g/s1600/HEADHUNTING.jpg></PAR>"
			+"</CEN></BOD></HTM>";
	private final static String index= "<HTM><HEA><TIT>In�cio</TIT></HEA>" 
			+"<BOD><CEN>"
			+"<PAR><TAM 30><COR preto><ITA>Ol� bem vindo a aula de </preto><vermelho>redes</vermelho>! </NEG></COR></TAM></PAR>"
			+"<PAR><IMG http://laquefrancisco.com/wp-content/uploads/2015/02/Comunica%C3%A7%C3%A3o-O-11-400x200.jpg></PAR>"
			+"</CEN></BOD></HTM>";
	private final static String sobrehttp = "<HTM><HEA><TIT>Mundo HTTP</TIT></HEA>"
			+"<BOD><CEN>"
			+"<PAR><TAM 25><COR laranja><NEG> HyperText Transfer Protocol </NEG></COR></TAM></PAR>"
			+"<PAR><IMG http://www.tecmundo.com.br/imagens/materias/iStock_000000809413XSmall.jpg></PAR>"
			+"<PAR><TAM 15><COR preto><ITA>O HTTP funciona como um protocolo de requisi��o-resposta no modelo computacional cliente-servidor. Um navegador web, por exemplo, pode ser o cliente e uma aplica��o em um computador que hospeda um s�tio da web pode ser o servidor. O cliente submete uma mensagem de requisi��o HTTP para o servidor. O servidor, que fornece os recursos, como arquivos HTML e outros conte�dos, ou realiza outras fun��es de interesse do cliente, retorna uma mensagem resposta para o cliente. A resposta cont�m informa��es de estado completas sobre a requisi��o e pode tamb�m conter o conte�do solicitado no corpo de sua mensagem.</ITA><NEG> Fonte: </NEG>(<SUB>pt.wikipedia.org</SUB>)</COR></TAM></PAR>"
			+"</CEN></BOD></HTM>";
	private final static String sobreclienteservidor= "<HTM><HEA><TIT>Cliente-Servidor</TIT></HEA>"
			+"<BOD><CEN>"
			+"<PAR><IMG https://upload.wikimedia.org/wikipedia/commons/1/1c/Cliente-Servidor.png></PAR>"
			+"<PAR><TAM 30><COR amarelo>CLIENTE-SERVIDOR</COR></TAM></PAR>"
			+"<PAR><TAM 15><COR preto>O modelo cliente-servidor � uma estrutura de aplica��o distribu�da que distribui as tarefas e cargas de trabalho entre os fornecedores de um recurso ou servi�o, designados como servidores, e os requerentes dos servi�os, designados como clientes. Geralmente os clientes e servidores comunicam atrav�s de uma rede de computadores em computadores distintos, mas tanto o cliente quanto o servidor podem residir no mesmo computador.Um servidor � um host que est� executando um ou mais servi�os ou programas que compartilham recursos com os clientes. Um cliente n�o compartilha qualquer de seus recursos, mas solicita um conte�do ou fun��o do servidor. Os clientes iniciam sess�es de comunica��o com os servidores que aguardam requisi��es de entrada.<NEG> Fonte: </NEG>(<SUB>pt.wikipedia.org</SUB>)</COR></TAM></PAR>"
			+"<PAR><IMG http://img.ibxk.com.br/materias/cliet-servidorartigo.jpg></PAR>"
			+"</CEN></BOD></HTM>";

	private final static String getPagina(String pagina) throws IOException {
		switch (pagina) {
		case "/t-a.htm":
			return statusOk + index;
		case "/index.htm":
			return statusOk + index;
		case "/ola.htm":
			return statusOk + ola;
		case "/redes.htm":
			return statusOk + redes;
		case "/adm.htm":
			return statusForbidden + adm;
		case "/sobrehttp.htm":
			return statusOk + sobrehttp;
		case"/sobreclienteservidor.htm":
			return statusOk + sobreclienteservidor;
		default:
			return statusNot;
		}
	}

	public static void main(String[] arg) {
		ServerSocket s;
		try {
			int p = 6789;
			s = new ServerSocket(p);
			System.out.println("Servidor iniciado na porta " + 6789);
			while (true) {
				Socket cliente = s.accept();

				System.out.println("Conex�o estabelecida " + "(" + cliente+ ")");
				DataInputStream in = new DataInputStream(cliente.getInputStream());
				DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

				String Recebe = in.readUTF();

				String array[] = Recebe.split(" ");
				array[0] = array[0].trim();
				array[1] = array[1].trim();
				array[2] = array[2].trim();
				
				if (array[0].equals("GET")) {
					if (array[1].equals("/")) {
						out.writeUTF(getPagina(array[1]));
					} else {
						Pattern p1 = Pattern.compile("/([\\w]+).htm$");
						Matcher m = p1.matcher(array[1]);
						boolean achou = m.find();
						if (achou) {
							out.writeUTF(getPagina(array[1]));
						}else {
							out.writeUTF(statusBad);
						}
					}
				} else {
					out.writeUTF(statusBad);
				}
				out.flush();
				cliente.close();
				System.out.println("Conex�o encerrada.");
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
