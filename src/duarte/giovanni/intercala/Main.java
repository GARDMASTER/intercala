package duarte.giovanni.intercala;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Main {

	public static final int REGISTRO = 300;
	
	public static void main(String[] args) {
		
		RandomAccessFile f = null;
		String qtdRegistros = null;
		Scanner scanInt = new Scanner(System.in);
		Scanner scanTxt = new Scanner(System.in);
		boolean valido = false;
		
		try {
			f = buscaCepOrdenado();
			System.out.println("O arquivo de CEP's possui "+(f.length()/300)+" registros.");
			do {
				System.out.println("Entre com a quantidade de registros que deseja gravar separadamente em dois arquivos ordenados: ");
				qtdRegistros = scanInt.nextLine();
				
				if(!isOnlyNumbers(qtdRegistros)) {
					System.out.println("Somente dígitos são válidos");
					valido = false;
				} else if (Long.parseLong(qtdRegistros) > (f.length()/300)) {
					System.out.println("O número digitado é maior que o número de linhas do arquivo.");
				} else {
					valido = true;
				}
				
			} while (!valido);
			gravarDoisArquivos(f, Long.parseLong(qtdRegistros));
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		
		
		
		
		
		
		scanInt.close();
		scanTxt.close();
	}

	public static RandomAccessFile buscaCepOrdenado() throws FileNotFoundException{
		
		RandomAccessFile f = new RandomAccessFile("C:\\Users\\Sala\\workspace\\eclipse\\busca-binaria\\files\\cep_ordenado.dat", "r");
		
		return f;
	}
	
	public static void gravarDoisArquivos(RandomAccessFile f, Long qtdRegistros) throws IOException {
		
		OutputStream saida = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq1.dat");
		OutputStream saida2 = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq2.dat");
		DataOutputStream dout = null;
		byte[] endereco = new byte [300];
	 
		System.out.println(f.length());
		
	    for (int i = 0; i < qtdRegistros; i++) {
			
	    	if(Math.random() > 0.5) {
				f.seek(REGISTRO*i);
				f.readFully(endereco);
				dout = new DataOutputStream(saida);
				dout.write(endereco);
			} else {
				f.seek(REGISTRO*i);
				f.readFully(endereco);
				dout = new DataOutputStream(saida2);
				dout.write(endereco);
			}
	    	
	    	System.out.println("REGISTRO*i: "+REGISTRO*i);
		}
	    
	    dout.close();
		saida.close();
		saida2.close();
	}
	
	public static boolean isOnlyNumbers(String x) {
		boolean isOnlyNumbers = false;
		
		try {
			Long.parseLong(x);
			isOnlyNumbers =  true;
		} catch (NumberFormatException e) {
			isOnlyNumbers =  false;
		}
		
		return isOnlyNumbers;
	}
}
