import java.util.ArrayList;

public class Vuelo {
    private String codigo;
    private String destino;
    private int capacidad;
    private ArrayList<Pasajero> pasajeros;

    public Vuelo(String codigo, String destino, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0.");
        }
        this.codigo = codigo;
        this.destino = destino;
        this.capacidad = capacidad;
        this.pasajeros = new ArrayList<Pasajero>();
    }

    public void abordar(Pasajero p) throws VueloLlenoException {
        if (pasajeros.size() >= capacidad) {
            throw new VueloLlenoException("Vuelo " + codigo + " sin asientos disponibles.");
        }
        pasajeros.add(p);
        System.out.println("Pasajero " + p.getNombre() + " abordo el vuelo " + codigo);
    }

    public void desembarcar(String pasaporte) throws PasajeroNoEncontradoException {
        for (int i = 0; i < pasajeros.size(); i++) {
            if (pasajeros.get(i).getPasaporte().equalsIgnoreCase(pasaporte)) {
                pasajeros.remove(i);
                System.out.println("Pasajero desembarcado.");
                return;
            }
        }
        throw new PasajeroNoEncontradoException(pasaporte);
    }

    public Pasajero buscarPasajero(String pasaporte) throws PasajeroNoEncontradoException {
        for (int i = 0; i < pasajeros.size(); i++) {
            if (pasajeros.get(i).getPasaporte().equalsIgnoreCase(pasaporte)) {
                return pasajeros.get(i);
            }
        }
        throw new PasajeroNoEncontradoException(pasaporte);
    }

    public void listarPasajeros() {
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros en este vuelo.");
        } else {
            for (int i = 0; i < pasajeros.size(); i++) {
                System.out.println(pasajeros.get(i).obtenerInfo());
            }
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestino() {
        return destino;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }
}
