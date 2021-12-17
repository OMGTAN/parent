import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {

    public static void main(String[] args) throws  Exception {
        SocketChannel socketChannel = SocketChannel.open();
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8007));
        while(!socketChannel.finishConnect()){
            Thread.currentThread().sleep(1000);
        }
        String message = "你好！";
        socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));

    }
}
