import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Handler implements Runnable{
    private final Socket soket;

    public Handler(Socket soket) {
        this.soket = soket;
    }

    public Socket getSoket() {
        return soket;
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(soket.getInputStream()); DataOutputStream os = new DataOutputStream(soket.getOutputStream())){
            while (true){
                String s = is.readUTF();
                System.out.printf("Recived { %s }" + s);
                System.out.println();
                os.writeUTF(s);
                os.flush();

            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("connection lost");
        }
    }
}
