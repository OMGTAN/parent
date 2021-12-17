import javax.xml.stream.events.StartDocument;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    private Thread t;

    public Server(int port) {
        try {
            serverSocket= new ServerSocket(port);
            t = Thread.currentThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(""+t.getName()+"状态"+t.getState());
        System.out.println("server start!");
        Socket socket=null;
            try {
                new Thread(()->{
                    System.out.println(""+Thread.currentThread().getName()+"状态"+Thread.currentThread()  .getState());
                        while(true){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(""+t.getName()+"状态"+t.getState());
                        }
                }).start();
                System.out.print("等待连接。。。");
                socket = this.serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                StringBuilder sb = new StringBuilder();
                byte[] bytes = new byte[5];
                while( true){
                    int read = inputStream.read(bytes);
                    sb.append(new String(bytes,0,read));
                    if(read==-1||read<bytes.length){
                        break;
                    }
                }
                String s = sb.toString();
                System.out.println("receive message: " + s);
                if(s.equals("client")){
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("i am server!".getBytes());
                    System.out.println("send message: i am server!" );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void  close(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8087);

    }

}
