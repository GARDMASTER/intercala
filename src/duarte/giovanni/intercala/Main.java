package duarte.giovanni.intercala;

import java.io.DataOutputStream;
import java.io.EOFException;
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
		RandomAccessFile[] arqs = null;
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
			
			arqs = gravarDoisArquivos(f, Long.parseLong(qtdRegistros));
			System.out.println("Arquivos gravados com sucesso.");
			
			System.out.println("O programa começará a intercalação dos arquivos.");
			System.out.println("Intercalando...");
			
			intercala(arqs[0], arqs[1]);

			System.out.println("FIM.");
			
			
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
	
	public static RandomAccessFile[] gravarDoisArquivos(RandomAccessFile f, Long qtdRegistros) throws IOException {
		
		
		OutputStream saida = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq1.dat");
		OutputStream saida2 = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arq2.dat");
		DataOutputStream dout = null;
		byte[] endereco = new byte [300];
		Endereco end = new Endereco();
	 
	    for (int i = 0; i < qtdRegistros; i++) {
			
	    	if(Math.random() > 0.5) {
				f.seek(REGISTRO*i);
				end.leEndereco(f);
				dout = new DataOutputStream(saida);
				end.escreveEndereco(dout);
			} else {
				f.seek(REGISTRO*i);
				end.leEndereco(f);
				dout = new DataOutputStream(saida2);
				end.escreveEndereco(dout);
			}
	    	
		}
	    
	    dout.close();
		saida.close();
		saida2.close();
		
		RandomAccessFile[] arqs = new RandomAccessFile[2];
		arqs[0] = new RandomAccessFile("C:\\\\Users\\\\Sala\\\\workspace\\\\eclipse\\\\intercala\\\\files\\\\arq1.dat", "r");
		arqs[1] = new RandomAccessFile("C:\\\\Users\\\\Sala\\\\workspace\\\\eclipse\\\\intercala\\\\files\\\\arq2.dat", "r");
		
		return arqs;
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
	
	public static String intercala(RandomAccessFile f1, RandomAccessFile f2) throws IOException {
		
		OutputStream saidaFinal = new FileOutputStream("C:\\Users\\Sala\\workspace\\eclipse\\intercala\\files\\arqFinal.dat\\");
		DataOutputStream doutFinal = new DataOutputStream(saidaFinal);
		
		Endereco end1 = new Endereco();
		Endereco end2 = new Endereco();

		end1.leEndereco(f1);
		end2.leEndereco(f2);
		
		while(f1.getFilePointer() < f1.length() && f2.getFilePointer() < f2.length()) {
			
			if(end1.getCep().compareTo(end2.getCep()) > 0) {
				end2.escreveEndereco(doutFinal);
				end2.leEndereco(f2);
			} else {
				end1.escreveEndereco(doutFinal);
				end1.leEndereco(f1);
			}
		}
		
		while(f1.getFilePointer() < f1.length()) {
			end1.leEndereco(f1);
			end1.escreveEndereco(doutFinal);
		}
		
		while(f2.getFilePointer() < f2.length()) {
			end2.escreveEndereco(doutFinal);
			end2.leEndereco(f2);
		}
		
		saidaFinal.close();
		doutFinal.close();
		return null;
	}
	
}
