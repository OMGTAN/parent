import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel socketServerChannel = ServerSocketChannel.open();
        socketServerChannel.configureBlocking(false);
        socketServerChannel.bind(new InetSocketAddress(8007));
        Selector selector = Selector.open();
        socketServerChannel.register(selector,SelectionKey.OP_ACCEPT);
        while (true){
            int select = selector.select(1000);
            if(select==0){
//                System.out.println("等待连接！");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey =  iterator.next();
                if(selectionKey.isAcceptable()){
                    SocketChannel accept = socketServerChannel.accept();
                    System.out.println("accept！");
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    SelectableChannel channel = selectionKey.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    ((SocketChannel)channel).read(allocate);
                    System.out.println("from client: "+new String(allocate.array(), StandardCharsets.UTF_8));
                }
                iterator.remove();
            }
        }
    }
}
