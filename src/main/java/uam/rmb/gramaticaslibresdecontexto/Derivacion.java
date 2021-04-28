package uam.rmb.gramaticaslibresdecontexto;

import java.util.ArrayList;

/**
 *
 * @author RaulMb
 */
public class Derivacion {

	private int[] getPositionI(String cadena, ArrayList<Regla> reglas) {
		int[] positions = new int[2];
		int position = 1000000;
		int aux;

		for (int i = 0; i < reglas.size(); i++) {
			aux = cadena.indexOf(reglas.get(i).cabeza);

			if (aux < position && aux != -1) {
				position = aux;
				positions[0] = aux;
				positions[1] = i;
			}
		}

		if (position == 1000000) {
			return null;
		}

		return positions;
	}

	private  int[] getPositionD(String cadena, ArrayList<Regla> reglas) {
		int[] positions = new int[2];

		for (int i = cadena.length() - 1; -1 < i; i--) {
			for (int j = 0; j < reglas.size(); j++) {
				if (reglas.get(j).cabeza.charAt(reglas.get(j).cabeza.length() - 1) == cadena.charAt(i)) {
					boolean coincideCabeza = true;
					int position = i - 1;

					for (int k = reglas.get(j).cabeza.length() - 2; -1 < k; k--) {
						if ((position == -1) || reglas.get(j).cabeza.charAt(k) != cadena.charAt(position)) {
							coincideCabeza = false;
							break;
						}

						position--;
					}

					if (coincideCabeza) {
						positions[0] = position + 1;
						positions[1] = j;

						return positions;
					}
				}
			}
		}

		return null;
	}

	private String crearNuevaCadena(String cadena, String cuerpo, String cabeza, int[] position) {
		String aux = "";

		for (int i = 0; i < position[0]; i++) {
			aux += cadena.charAt(i);
		}

		aux += cuerpo;

		for (int i = position[0] + cabeza.length(); i < cadena.length(); i++) {
			aux += cadena.charAt(i);
		}

		return aux;
	}

	public void derivacionIzquierda(String cadena, String cadenaDerivar, String derivacion, ArrayList<String> soluciones, ArrayList<Regla> reglas, int tamMaxCabeza) {
		if (cadenaDerivar.equals(cadena)) {
			derivacion += " -> (" + cadena + ")";
			soluciones.add(derivacion);
		}

		if (cadena.length() <= cadenaDerivar.length() + tamMaxCabeza) {
			int[] position = getPositionI(cadena, reglas);

			if (position != null) {
				String cabeza = reglas.get(position[1]).cabeza;

				for (int i = 0; i < reglas.size(); i++) {
					if (cabeza.equals(reglas.get(i).cabeza)) {
						String aux = " -> (" + cadena + ", " + reglas.get(i).numRegla + ")";
						String nuevaCadena = crearNuevaCadena(cadena, reglas.get(i).cuerpo, cabeza, position);

						derivacionIzquierda(nuevaCadena, cadenaDerivar, derivacion + aux, soluciones, reglas, tamMaxCabeza);
					}
				}
			}
		}
	}

	public void derivacionDerecha(String cadena, String cadenaDerivar, String derivacion, ArrayList<String> soluciones, ArrayList<Regla> reglas, int tamMaxCabeza) {
		if (cadenaDerivar.equals(cadena)) {
			derivacion += " -> (" + cadena + ")";
			soluciones.add(derivacion);
		}

		if (cadena.length() <= cadenaDerivar.length() + tamMaxCabeza) {
			int[] position = getPositionD(cadena, reglas);

			if (position != null) {
				String cabeza = reglas.get(position[1]).cabeza;

				for (int i = 0; i < reglas.size(); i++) {
					if (cabeza.equals(reglas.get(i).cabeza)) {
						String aux = " -> (" + cadena + ", " + reglas.get(i).numRegla + ")";
						String nuevaCadena = crearNuevaCadena(cadena, reglas.get(i).cuerpo, cabeza, position);

						derivacionDerecha(nuevaCadena, cadenaDerivar, derivacion + aux, soluciones, reglas, tamMaxCabeza);
					}
				}
			}
		}
	}
}
