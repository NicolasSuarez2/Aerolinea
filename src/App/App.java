import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = sc.nextInt();
                sc.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un numero entero.");
                sc.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Aerolinea aerolinea = new Aerolinea("Aerolinea Andes Express");

        AgenteVentas a1 = new AgenteVentas("Sofia Herrera", "AG001", "Ventas aeropuerto");
        AgenteVentas a2 = new AgenteVentas("Mateo Vargas", "AG002", "Ventas en linea");
        Piloto p1 = new Piloto("Capitan Ricardo Leon", "P001", "Piloto comandante", 5200);

        aerolinea.agregarPersonal(a1);
        aerolinea.agregarPersonal(a2);
        aerolinea.agregarPersonal(p1);

        int opcion;

        do {
            System.out.println("\n===== MENU AEROLINEA =====");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Listar pasajeros registrados");
            System.out.println("3. Abordar pasajero a un vuelo");
            System.out.println("4. Vender tiquete");
            System.out.println("5. Ver pasajeros de un vuelo");
            System.out.println("6. Ver tiquetes de un agente");
            System.out.println("7. Desembarcar un pasajero");
            System.out.println("8. Buscar pasajero por pasaporte");
            System.out.println("9. Reporte general de la aerolinea");
            System.out.println("10. Crear nuevo vuelo");
            System.out.println("0. Salir");

            opcion = leerEntero(sc, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    int tipo = leerEntero(sc, "Tipo de pasajero (1=Economico, 2=Ejecutivo): ");
                    if (tipo != 1 && tipo != 2) {
                        System.out.println("Tipo invalido.");
                        break;
                    }
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Pasaporte: ");
                    String pasaporte = sc.nextLine();
                    int edad = leerEntero(sc, "Edad: ");

                    try {
                        if (tipo == 1) {
                            int equipaje = leerEntero(sc, "Equipaje en kg (0-23): ");
                            PasajeroEconomico pe = new PasajeroEconomico(nombre, pasaporte, edad, equipaje);
                            aerolinea.registrarPasajero(pe);
                            System.out.println("Pasajero economico registrado.");
                        } else {
                            int millas = leerEntero(sc, "Millas: ");
                            System.out.print("Tiene sala VIP? (s/n): ");
                            String respVip = sc.nextLine();
                            boolean vip = respVip.equalsIgnoreCase("s");
                            PasajeroEjecutivo pej = new PasajeroEjecutivo(nombre, pasaporte, edad, millas, vip);
                            aerolinea.registrarPasajero(pej);
                            System.out.println("Pasajero ejecutivo registrado.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    if (aerolinea.getPasajeros().isEmpty()) {
                        System.out.println("No hay pasajeros registrados.");
                    } else {
                        for (int i = 0; i < aerolinea.getPasajeros().size(); i++) {
                            System.out.println(aerolinea.getPasajeros().get(i).obtenerInfo());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Codigo del vuelo (ej: AV101): ");
                    String codVuelo = sc.nextLine();
                    Vuelo vuelo = aerolinea.buscarVuelo(codVuelo);
                    if (vuelo == null) {
                        System.out.println("Error: vuelo no encontrado.");
                        break;
                    }

                    System.out.print("Pasaporte del pasajero: ");
                    String pasAbordar = sc.nextLine();
                    try {
                        Pasajero encontrado = aerolinea.buscarPasajero(pasAbordar);
                        vuelo.abordar(encontrado);
                    } catch (PasajeroNoEncontradoException e) {
                        System.out.println("Error: pasajero no registrado.");
                    } catch (VueloLlenoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    aerolinea.listarAgentes();
                    System.out.print("ID del agente: ");
                    String idAgente = sc.nextLine();

                    try {
                        AgenteVentas agente = aerolinea.buscarAgente(idAgente);
                        System.out.print("Pasaporte del pasajero: ");
                        String pasTiquete = sc.nextLine();
                        Pasajero pasajero = aerolinea.buscarPasajero(pasTiquete);

                        System.out.print("Fecha del tiquete (ej: 2026-12-01): ");
                        String fecha = sc.nextLine();
                        System.out.print("Desea indicar la clase? (s/n): ");
                        String indicarClase = sc.nextLine();

                        if (indicarClase.equalsIgnoreCase("s")) {
                            System.out.print("Clase: ");
                            String clase = sc.nextLine();
                            agente.venderTiquete(fecha, clase, pasajero);
                        } else {
                            agente.venderTiquete(fecha, pasajero);
                        }
                    } catch (PersonalNoEncontradoException | PasajeroNoEncontradoException | TiqueteInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Codigo del vuelo: ");
                    String codVer = sc.nextLine();
                    Vuelo vVer = aerolinea.buscarVuelo(codVer);
                    if (vVer == null) {
                        System.out.println("Error: vuelo no encontrado.");
                    } else {
                        vVer.listarPasajeros();
                    }
                    break;

                case 6:
                    aerolinea.listarAgentes();
                    System.out.print("ID del agente: ");
                    String idVer = sc.nextLine();
                    try {
                        AgenteVentas ag = aerolinea.buscarAgente(idVer);
                        ArrayList<Tiquete> listaTiq = ag.getTiquetes();
                        if (listaTiq.isEmpty()) {
                            System.out.println("Este agente no ha vendido tiquetes.");
                        } else {
                            for (int i = 0; i < listaTiq.size(); i++) {
                                System.out.println(listaTiq.get(i).getInfo());
                            }
                        }
                    } catch (PersonalNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    System.out.print("Codigo del vuelo: ");
                    String codDes = sc.nextLine();
                    Vuelo vDes = aerolinea.buscarVuelo(codDes);
                    if (vDes == null) {
                        System.out.println("Error: vuelo no encontrado.");
                        break;
                    }
                    System.out.print("Pasaporte del pasajero a desembarcar: ");
                    String pasDes = sc.nextLine();
                    try {
                        vDes.desembarcar(pasDes);
                    } catch (PasajeroNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 8:
                    System.out.print("Pasaporte a buscar: ");
                    String pasBuscar = sc.nextLine();
                    try {
                        Pasajero pEncontrado = aerolinea.buscarPasajero(pasBuscar);
                        System.out.println(pEncontrado.obtenerInfo());
                    } catch (PasajeroNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 9:
                    aerolinea.generarReporteGeneral();
                    break;

                case 10:
                    System.out.print("Codigo del nuevo vuelo: ");
                    String nuevoCod = sc.nextLine();
                    System.out.print("Destino: ");
                    String nuevoDest = sc.nextLine();
                    int nuevaCap = leerEntero(sc, "Capacidad: ");
                    try {
                        aerolinea.agregarVuelo(nuevoCod, nuevoDest, nuevaCap);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        } while (opcion != 0);

        sc.close();
    }
}
