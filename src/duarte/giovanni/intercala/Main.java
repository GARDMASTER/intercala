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
		Integer qtdRegistros = 0;
		Scanner scanInt = new Scanner(System.in);
		Scanner scanTxt = new Scanner(System.in);
		
		try {
			f = buscaCepOrdenado();
			System.out.println("O arquivo de CEP's possui "+f.length()/300+" registros.");
			System.out.println("Entre com a quantidade de registros que deseja gravar separadamente em dois arquivos ordenados: ");
			qtdRegistros = scanInt.nextInt();
			gravarDoisArquivos(f, qtdRegistros);
			
			
			
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
	
	public static void gravarDoisArquivos(RandomAccessFile f, Integer qtdRegistros) throws IOException {
		
		OutputStream saida = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq1.dat");
		OutputStream saida2 = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq2.dat");
		DataOutputStream dout = null;
		byte[] endereco = new byte [300];
	 
	    for (int i = 1; i <= qtdRegistros; i++) {
			
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
	    	
		}
	    
	    dout.close();
		saida.close();
		saida2.close();
	}
}
