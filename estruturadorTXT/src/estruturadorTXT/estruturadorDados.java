package estruturadorTXT;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class estruturadorDados {
	
	public static List<PMGinss> carregaPMGinss() {
		List<PMGinss> aposentados = new ArrayList<PMGinss>();
		
		try {
			System.out.println("Iniciando carga do arquivo...");
			List<String> lines = Files.readAllLines(Paths.get("apos_ativos_INSS_PMG.txt"));
			
			PMGinss aposentado = null;
	        for (String line : lines) {
	        	
	            if(line.startsWith("| Servidor :"))
	            {
	            	aposentado = new PMGinss();
	            	aposentados.add(aposentado);
	            	aposentado.setMatric(line.substring(13, 19)); 
	            	aposentado.nome = line.split(" Orgao")[0].split("-")[1];
	            	aposentado.orgao = line.split(" Orgao : ")[1].replace("|", "").trim();
	                //...add data (name)
	            }else if(line.startsWith("| Arquivo :")) {
	            	aposentado.arquivo = line.substring(12,24);
	            	aposentado.data = line.substring(32,42);
	            	aposentado.horario = line.substring(53,61);
	            	aposentado.usuario = line.substring(72).replace("|", "").trim();//.split("|")[0];
	            	
	            }else if(line.startsWith("| Assunto :")) {
	            	aposentado.assunto = line.substring(12).replace("|", "").trim();//.split("|")[0];
	            }
	            System.out.println("Fim da carga, dados agora são objetos mapeados.");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aposentados;
	}

	public static void gravaCSV(List<PMGinss> aposentados) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream("apoINSSPMG.csv"));
			
			out.println("matric; nome; orgao; arquivo; data; horario; usuario; assunto");
			for (PMGinss apo : aposentados) {
				out.println(apo.getMatric() + "; " + apo.nome + "; " + apo.orgao +"; " + apo.arquivo + "; " +
					apo.data + "; " + apo.horario + "; " + apo.usuario + "; " + apo.assunto);
			}		    
			out.close();
			System.out.println("Arquivo escrito com sucesso!");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("saida");
		gravaCSV(carregaPMGinss());
	}
}


