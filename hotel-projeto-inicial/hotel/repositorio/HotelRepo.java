package hotel.repositorio;

import hotel.negocio.Hotel;
import java.io.*;

public class HotelRepo {
    private static final String ARQUIVO = "hotel.dat";

    public static void salvar(Hotel hotel) throws IOException {
        throw new UnsupportedOperationException("Implementar salvar");
    }

    public static Hotel carregar() throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Implementar carregar");
    }

    public static void salvar(Hotel hotel, String caminho) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(hotel);
        }
    }

    public static Hotel carregar(String caminho) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho))) {
            return (Hotel) in.readObject();
        }
    }
}
