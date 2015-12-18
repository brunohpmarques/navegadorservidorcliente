package cliente;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;

public class Navegador {

	private JFrame frame;
	private String nomeNavegador = "Mini-Navegador";
	private String desenvolvedores = "DESENVOLVEDORES: BRUNO MARQUES E DANIELLY QUEIROZ";
	private ArrayList<String[]> pilhaHistorico;
	private JTextField barraEndereco;
	private JTextPane areaNavegacao;
	private JScrollPane scrollPane;
	private JButton btnVoltar, btnAtualizar, btnIr;
	private JLabel lblLoad, lblDesenvolvedores;
	private String enderecoAtual = "";
	
	public static void main(String[] args) {
		Navegador window = new Navegador();
		window.frame.setVisible(true);
	}

	public Navegador() {
		initialize();
	}

	private void initialize(){
		try{
			// Seta para o estilo do SO
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			System.out.println("Nao foi possivel carregar a interface do SO pelo motivo: "+ e.getMessage());
		}
		
		pilhaHistorico = new ArrayList<String[]>();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(235, 241, 244));
		frame.setBounds(centralizarTela(690, 450));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(nomeNavegador);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("\\img\\ico.png")));
		frame.getContentPane().setLayout(null);	
		
		lblLoad = new JLabel("<html><body><img src=\""+getClass().getResource("/img/load.gif")+"\"></body></html>");
		lblLoad.setBounds(594, 10, 16, 23);
		lblLoad.setVisible(false);
		frame.getContentPane().add(lblLoad);
		
		barraEndereco = new JTextField();
		barraEndereco.setBounds(130, 11, 484, 21);
		barraEndereco.setColumns(10);
		frame.getContentPane().add(barraEndereco);  
		
		/*barraEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
					System.out.println(barraEndereco.getText().length());
				}
			});*/
		
		areaNavegacao = new JTextPane();
		areaNavegacao.setFont(new Font("Calibri", Font.PLAIN, 11));
		areaNavegacao.setBounds(0, 40, 684, 370);
		areaNavegacao.setContentType("text/html");
		areaNavegacao.setEditable(false);
		frame.getContentPane().add(areaNavegacao);
		
		scrollPane = new JScrollPane(areaNavegacao);
		scrollPane.setBounds(0, 40, 684, 370);
		frame.getContentPane().add(scrollPane);
		
		btnVoltar = new JButton("<html><body><img src=\""+getClass().getResource("/img/btn_voltar.png")+"\"></body></html>");
		btnVoltar.setBounds(10, 10, 50, 23);
		btnVoltar.setEnabled(false);
		btnVoltar.setBorderPainted(false); 
		btnVoltar.setContentAreaFilled(false); 
		btnVoltar.setMnemonic('v'); 
		btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		frame.getContentPane().add(btnVoltar);
		
		btnVoltar.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			String[] historico = popPilhaHistorico();
			barraEndereco.setText(historico[0]);
			frame.setTitle(historico[1]);
			areaNavegacao.setText(historico[2]);
		}});
		
		btnAtualizar = new JButton("<html><body><img src=\""+getClass().getResource("/img/btn_atualizar.png")+"\"></body></html>");
		btnAtualizar.setBounds(70, 10, 50, 23);
		btnAtualizar.setEnabled(false);
		btnAtualizar.setBorderPainted(false); 
		btnAtualizar.setContentAreaFilled(false);
		btnAtualizar.setMnemonic('a'); 
		btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		frame.getContentPane().add(btnAtualizar);
		
		btnAtualizar.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			new Thread() { 
				 @Override
		         public void run() {
					barraEndereco.setText(enderecoAtual);
					lblLoad.setVisible(true);
					try {Thread.sleep(1000);}catch (Exception e){}	
					String response = htmParaHtml(Cliente.request(enderecoAtual));
					areaNavegacao.setText(response);
					lblLoad.setVisible(false);
		         }  
		       }.start();
		}});
		
		btnIr = new JButton("<html><body><img src=\""+getClass().getResource("/img/btn_ir.png")+"\"></body></html>");
		btnIr.setBounds(624, 10, 50, 23);
		btnIr.setMnemonic('i'); 
		btnIr.setBorderPainted(false); 
		btnIr.setContentAreaFilled(false);
		btnIr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		frame.getContentPane().add(btnIr);
		
		btnIr.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {			
			 new Thread() { 
				 @Override
		         public void run() {
					barraEndereco.setText(barraEndereco.getText().replace(" ", "%20"));
					btnIr.setEnabled(false);
					lblLoad.setVisible(true);
					try {Thread.sleep(1000);}catch (Exception e){}
					String response = htmParaHtml(Cliente.request(barraEndereco.getText()));
			 		areaNavegacao.setText(response);
			 		lblLoad.setVisible(false);
			 		btnIr.setEnabled(true);
			 		pushPilhaHistorico(barraEndereco.getText(), frame.getTitle(), response);
		         }  
		       }.start();  
		}});
		
		lblDesenvolvedores = new JLabel(desenvolvedores);
		lblDesenvolvedores.setForeground(Color.GRAY);
		lblDesenvolvedores.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesenvolvedores.setFont(new Font("Calibri", Font.PLAIN, 8));
		lblDesenvolvedores.setBounds(0, 409, 690, 15);
		frame.getContentPane().add(lblDesenvolvedores);
		
	}
	
	private String htmParaHtml(String htm){	
		htm = htm.replaceAll("<HTM>", "<html>").replaceAll("</HTM>", "</html>")
				.replaceAll("<HEA>", "<head>").replaceAll("</HEA>", "</head>")
				.replaceAll("<TIT>", "<title>").replaceAll("</TIT>", "</title>")
				.replaceAll("<BOD>", "<body>").replaceAll("</BOD>", "</body>")
				.replaceAll("<CEN>", "<center>").replaceAll("</CEN>", "</center>")
				.replaceAll("<PAR>", "<p>").replaceAll("</PAR>", "</p>")
				.replaceAll("<NEG>", "<b>").replaceAll("</NEG>", "</b>")
				.replaceAll("<ITA>", "<i>").replaceAll("</ITA>", "</i>")
				.replaceAll("<SUB>", "<u>").replaceAll("</SUB>", "</u>")
				.replaceAll("</TAM>", "</span>")
				.replaceAll("</COR>", "</font>");
				
		Pattern p = Pattern.compile("<head><title>(.+?)</title></head>");  
		Matcher m = p.matcher(htm);
		boolean achou = m.find();
		if(!achou){
			frame.setTitle("Sem título"+" - "+nomeNavegador);
		}else{
			frame.setTitle(m.group(1)+" - "+nomeNavegador);
		}
				
		p = Pattern.compile("<TAM ([\\d]+)>");  
		m = p.matcher(htm);
		while(m.find()) {
			htm = htm.replaceAll("<TAM "+m.group(1)+">", "<span style=\"font-size:"+m.group(1)+"px\">");
        }
		
		p = Pattern.compile("<COR ([\\w]+)>");  
		m = p.matcher(htm);
		while(m.find()) {
			htm = htm.replaceAll("<COR "+m.group(1)+">", "<font color=\""+traduzCor(m.group(1))+"\">");
        }
		
		p = Pattern.compile("<IMG (.+?)>");  
		m = p.matcher(htm);
		while(m.find()) {
			htm = htm.replaceAll("<IMG "+m.group(1)+">", "<img src=\""+m.group(1)+"\" />");
        }
		return htm;
	}

	private String traduzCor(String cor){
		switch (cor) {
		case "preto":
			return "black";
		case "verde":
			return "green";
		case "azul":
			return "blue";
		case "vermelho":
			return "red";
		case "amarelo":
			return "yellow";
		case "branco":
			return "white";
		case "laranja":
			return "orange";
		case "roxo":
			return "purple";
		default:
			return "black";
		}
	}
	
 	private void pushPilhaHistorico(String endereco, String titulo, String html){
 		enderecoAtual = endereco;
 		String[] novoHistorico = {endereco, titulo, html};
		pilhaHistorico.add(novoHistorico);
		btnAtualizar.setEnabled(true);
		if (pilhaHistorico.size() > 1) {
			btnVoltar.setEnabled(true);
		}
	}
	
	private String[] popPilhaHistorico(){
		String[] historico = pilhaHistorico.get(pilhaHistorico.size()-1);
		pilhaHistorico.remove(pilhaHistorico.size()-1);
		if(pilhaHistorico.size() == 0){
			btnVoltar.setEnabled(false);
		}
		enderecoAtual = historico[0];
		return historico;
	}
	
	private Rectangle centralizarTela(int largura, int altura){
		Rectangle d = new Rectangle();
		d.x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - largura / 2;
		d.y = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - altura / 2;
		d.width = largura;
		d.height = altura;
		return d;
	}
}
