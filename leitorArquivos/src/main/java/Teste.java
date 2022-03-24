import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		List<Integer> anosVencedores = new ArrayList<Integer>();
		
		anosVencedores.add(1987);
//		anosVencedores.add(1998);
//		anosVencedores.add(1999);
//		anosVencedores.add(2010);
//		anosVencedores.add(2011);
//		anosVencedores.add(2015);
//		anosVencedores.add(2016);
		
		int[] maiorIntervalo = maiorPrimeiroIntervalo(anosVencedores);
		int[] menorIntervalo = menorPrimeiroIntervalo(anosVencedores);
		
		
		System.out.println(Arrays.toString(maiorIntervalo));
		System.out.println(Arrays.toString(menorIntervalo));
		

	}

	private static int[] maiorPrimeiroIntervalo(List<Integer> anosVencedores) {
		int intervalo = 0;
		int intervaloTemp = 0;
		int[] retorno = new int[3];
		
		int anoAnterior = 0;
		for (Integer anoAtual : anosVencedores) {
			if (anoAnterior == 0) {
				anoAnterior = anoAtual;
				continue;
			}
			intervaloTemp = anoAtual - anoAnterior;
			
			if (intervaloTemp > intervalo) {
				intervalo = intervaloTemp;
				retorno[0] = anoAnterior;
				retorno[1] = anoAtual;
				retorno[2] = intervalo;
			}
			anoAnterior = anoAtual;
		}
		return retorno;
	}
	
	private static int[] menorPrimeiroIntervalo(List<Integer> anosVencedores) {
		int intervalo = 999999999;
		int intervaloTemp = 0;
		int[] retorno = new int[3];
		
		int anoAnterior = 0;
		for (Integer anoAtual : anosVencedores) {
			if (anoAnterior == 0) {
				anoAnterior = anoAtual;
				continue;
			}
			intervaloTemp = anoAtual - anoAnterior;
			
			if (intervaloTemp < intervalo) {
				intervalo = intervaloTemp;
				retorno[0] = anoAnterior;
				retorno[1] = anoAtual;
				retorno[2] = intervalo;
			}
			anoAnterior = anoAtual;
		}
		return retorno;
	}
	
//	private static Map<Integer, List<int[]>> maioresIntervalos(List<Integer> anosVencedores) {
//		int intervalo = 0;
//		int intervaloTemp = 0;
//		Map<Integer, List<int[]>> retorno = new HashMap<Integer, List<int[]>>();
//		
//		int anoAnterior = 0;
//		for (Integer anoAtual : anosVencedores) {
//			if (anoAnterior == 0) {
//				anoAnterior = anoAtual;
//				continue;
//			}
//			intervaloTemp = anoAtual - anoAnterior;
//			
//			if (intervaloTemp > intervalo) {
//				intervalo = intervaloTemp;
//				List<int[]> anosIntervalo = retorno.get(intervalo);
//				if (anosIntervalo == null) {
//					anosIntervalo = new ArrayList<int[]>();
//				}
//				anosIntervalo.add();
//				anosIntervalo[0] = anoAnterior;
//				anosIntervalo[1] = anoAtual;
//			}
//			anoAnterior = anoAtual;
//		}
//		return retorno;
//	}

}
