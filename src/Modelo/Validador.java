public class Validador {

    private Validador() {
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isDigit(nombre.charAt(i))) {
                throw new IllegalArgumentException("El nombre no puede contener numeros: " + nombre);
            }
        }
    }

    public static void validarPasaporte(String pasaporte) {
        if (pasaporte == null || pasaporte.length() < 6 || pasaporte.length() > 9) {
            throw new IllegalArgumentException("El pasaporte debe tener entre 6 y 9 caracteres.");
        }
        for (int i = 0; i < pasaporte.length(); i++) {
            if (!Character.isLetterOrDigit(pasaporte.charAt(i))) {
                throw new IllegalArgumentException("El pasaporte solo puede tener letras y numeros.");
            }
        }
    }
}
