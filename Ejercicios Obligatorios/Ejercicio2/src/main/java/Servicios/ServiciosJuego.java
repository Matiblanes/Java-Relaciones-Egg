package Servicios;

import Constantes.Constantes;
import Entidades.Juego;
import Entidades.Jugador;
import Entidades.Revolver;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiciosJuego {

    private final Scanner leer = new Scanner(System.in).useDelimiter("\n");
    private final ServiciosJugador ServJ = new ServiciosJugador();
    private final ServiciosRevolver ServR = new ServiciosRevolver();

    public void llenarJuego(ArrayList<Jugador> jugadores, Revolver r) {

        System.out.println("-----Que comience el juego-----");

        ServR.llenarRevolver(r);

        System.out.println("Desea ingresar una cantidad de jugadores (S/N)");

        String opc = leer.next();

        cargarJugadores(jugadores, opc);

    }

    public void cargarJugadores(ArrayList<Jugador> jugadores, String opc) {

        if (opc.equalsIgnoreCase("S")) {

            System.out.println("Ingrese la cantidad de jugadores");

            int cantidadJugadores = leer.nextInt();

            for (int i = 0; i < cantidadJugadores; i++) {

                System.out.println("Jugadores ingresados " + (i + 1) + " de " + cantidadJugadores);

                jugadores.add(ServJ.crearJugador());

            }

        } else {

            for (int i = 0; i < 6; i++) {

                System.out.println("Jugadores ingresados " + (i + 1) + " de 6");

                jugadores.add(ServJ.crearJugador());
            }
        }
    }

    public void mostrarResultados(ArrayList<Jugador> jugadores) {

        for (Jugador jugador : jugadores) {

            if (jugador.isMojado() == true) {

                System.out.println(jugador.getNombre() + " se ha mojado ");

            } else {

                System.out.println(jugador.getNombre() + " no se mojado ");

            }

        }
    }

    public boolean todosMojados(ArrayList<Jugador> jugadores) {

        for (Jugador jugador : jugadores) {

            if (!jugador.isMojado()) {

                return false;
            }
        }
        return true;
    }

    public void siguienteRonda(Jugador jugadorActual, Revolver revolver) {

        System.out.println("\nTurno del jugador: " + jugadorActual.getNombre());

        if (ServJ.disparo(revolver, jugadorActual)) {

            System.out.println(Constantes.JUGADOR_MOJADO);

            System.out.println(Constantes.RECARGAR);

            ServR.llenarRevolver(revolver);

        } else {

            System.out.println(Constantes.JUGADOR_SALVADO);

        }
    }

    public void juego() {

        Juego partidaActual = new Juego();

        int numeroJugador = 0;

        String opc = "Y";

        ArrayList<Jugador> listaJugadores = partidaActual.getListaJugadores();

        Revolver revolver = partidaActual.getRevolver();

        llenarJuego(listaJugadores, revolver);

        Jugador jugadorActual;

        do {

            jugadorActual = listaJugadores.get(numeroJugador);

            numeroJugador++;

            if (numeroJugador > 5) {

                numeroJugador = 0;
            }

            if (jugadorActual.isMojado()) {

                continue;
            }

            siguienteRonda(jugadorActual, revolver);

            System.out.print("\n" + Constantes.SEGUIR_JUGANDO);

            opc = leer.next().substring(0, 1).toUpperCase();

        } while (!todosMojados(listaJugadores) && !opc.equals("N"));

        System.out.println("-----Resultados-----");

        mostrarResultados(listaJugadores);
    }
}
