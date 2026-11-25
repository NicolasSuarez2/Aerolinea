import java.util.ArrayList;

public class Aerolinea {
    private String nombre;
    private ArrayList<Vuelo> vuelos;
    private ArrayList<Personal> personal;
    private ArrayList<Pasajero> pasajeros;

    public Aerolinea(String nombre) {
        this.nombre = nombre;
        this.vuelos = new ArrayList<Vuelo>();
        this.personal = new ArrayList<Personal>();
        this.pasajeros = new ArrayList<Pasajero>();

        vuelos.add(new Vuelo("AV101", "Medellin", 10));
        vuelos.add(new Vuelo("AV202", "Cartagena", 8));
        vuelos.add(new Vuelo("AV303", "Cali", 6));
    }

    public void agregarVuelo(String codigo, String destino, int capacidad) {
        Vuelo nuevo = new Vuelo(codigo, destino, capacidad);
        vuelos.add(nuevo);
        System.out.println("Vuelo '" + codigo + "' a " + destino + " creado con capacidad " + capacidad);
    }

    public void agregarPersonal(Personal p) {
        personal.add(p);
        System.out.println(p.getNombre() + " registrado en la aerolinea.");
    }

    public void registrarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
    }

    public Vuelo buscarVuelo(String codigo) {
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return vuelos.get(i);
            }
        }
        return null;
    }

    public Pasajero buscarPasajero(String pasaporte) throws PasajeroNoEncontradoException {
        for (int i = 0; i < pasajeros.size(); i++) {
            if (pasajeros.get(i).getPasaporte().equalsIgnoreCase(pasaporte)) {
                return pasajeros.get(i);
            }
        }
        for (int i = 0; i < vuelos.size(); i++) {
            try {
                return vuelos.get(i).buscarPasajero(pasaporte);
            } catch (PasajeroNoEncontradoException e) {
            }
        }
        throw new PasajeroNoEncontradoException(pasaporte);
    }

    public AgenteVentas buscarAgente(String id) throws PersonalNoEncontradoException {
        for (int i = 0; i < personal.size(); i++) {
            Personal p = personal.get(i);
            if (p instanceof AgenteVentas && p.getId().equalsIgnoreCase(id)) {
                return (AgenteVentas) p;
            }
        }
        throw new PersonalNoEncontradoException(id);
    }

    public void listarAgentes() {
        System.out.println("--- Agentes disponibles ---");
        for (int i = 0; i < personal.size(); i++) {
            Personal p = personal.get(i);
            if (p instanceof AgenteVentas) {
                System.out.println(p.getId() + " - " + p.getNombre() + " (" + p.getCargo() + ")");
            }
        }
    }

    public void generarReporteGeneral() {
        System.out.println("\n===== REPORTE GENERAL: " + nombre + " =====");
        System.out.println("--- Vuelos ---");
        for (int i = 0; i < vuelos.size(); i++) {
            Vuelo v = vuelos.get(i);
            System.out.println("Vuelo: " + v.getCodigo()
                    + " | Destino: " + v.getDestino()
                    + " | Ocupacion: " + v.getPasajeros().size() + "/" + v.getCapacidad());
        }

        System.out.println("\n--- Personal (" + personal.size() + ") ---");
        for (int i = 0; i < personal.size(); i++) {
            System.out.println(personal.get(i).generarReporte());
            System.out.println();
        }
    }

    public ArrayList<Vuelo> getVuelos() {
        return vuelos;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }
}
