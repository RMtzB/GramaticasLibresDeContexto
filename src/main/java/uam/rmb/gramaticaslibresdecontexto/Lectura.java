package uam.rmb.gramaticaslibresdecontexto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author RaulMb
 */
public class Lectura {

	public String leerReglas(String nombre) {
		FileReader file = null;
		BufferedReader br = null;
		String aux = "";
		String texto;

		try {
			file = new FileReader(nombre);
			br = new BufferedReader(file);

			texto = br.readLine();

			while (texto != null) {
				if(aux.equals("")) {
					aux += texto;
				} else {
					aux += "\n" + texto;
				}

				texto = br.readLine();
			}
			
			br.close();
			file.close();
			
			return aux;
		} catch (Exception e) {
			return null;
		}
	}
	
	public ArrayList<Regla> obtenerReglas(String reglas) {
		String[] aux = reglas.split("\n");
		ArrayList<Regla> rules = new ArrayList<>();
		int cont = 0;
		
		for(int i = 0; i < aux.length; i++) {
			String[] aux1 = aux[i].split(" ");
			String numero = aux1[0].replace(".", "");
			
			cont++;
			rules.add(new Regla(Integer.valueOf(numero), aux1[1], aux1[3]));
			
			if(rules.get(cont - 1).cuerpo.contains("|")) {
				int position = cont - 1;
				String cabeza = rules.get(position).cabeza;
				String[] aux2 = rules.get(position).cuerpo.split("|");
				
				rules.get(position).cuerpo = aux2[0] + "";
				
				for(int j = 1; j < aux2.length; j++) {
					cont++;
					rules.add(new Regla(rules.get(position).numRegla, cabeza, aux2[j]));
				}
			}
		}

		return rules;
	}
}
