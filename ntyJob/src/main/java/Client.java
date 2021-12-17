import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.BreakIterator;

public class Client extends Socket {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect(new InetSocketAddress(8087));
            OutputStream outputStream = client.getOutputStream();
            outputStream.write("client".getBytes());
            outputStream.flush();
            System.out.println("send message: client!");

            InputStream inputStream = client.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[255];
            while( true){
                int read = inputStream.read(bytes);
                sb.append(new String(bytes,0,read));
                if(read==-1||read<bytes.length){
                    break;
                }
            }
            String s = sb.toString();
            System.out.println("receive message: "+s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
