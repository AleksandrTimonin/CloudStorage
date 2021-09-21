import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Handler implements Runnable{
    private final Socket soket;

    public Handler(Socket soket) {
        this.soket = soket;
    }

    public Socket getSoket() {
        return soket;
    }
    private final String SINCRONIZED_DIR = "server/root/syncronyzed/";
    private byte[] buffer = new byte[1024];

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(soket.getInputStream()); DataOutputStream os = new DataOutputStream(soket.getOutputStream())){
            while (true){
                String s = is.readUTF();
                if(s.startsWith("{F_N}")){
                    System.out.println("Recived { "+s+" }"  );
                    s = s.substring(5,s.length());
                    createOrUpdateFiles(s,is);
                }


                os.writeUTF(s + " сохранён в облаке ");
                os.flush();

            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("connection lost");
        }
    }
    public void createOrUpdateFiles(String filename, DataInputStream is){
            File dir = new File(SINCRONIZED_DIR );
            if(!dir.exists()) dir.mkdir();
            File file = new File( SINCRONIZED_DIR + filename);
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            String readed = is.readUTF();

                fileOutputStream.write(readed.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.flush();

            System.out.println("файл сохранён");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
