package groupChat;


import com.sun.deploy.panel.ITreeNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChatServer {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8007));
        Selector selector = Selector.open();
        Map<String, ByteBuffer> bufferMap = new HashMap<>();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            if(selector.select(1000)==0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey =  iterator.next();
                if(selectionKey.isAcceptable()){
                    //注册连接事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress()+" 上线了！在线总人数 = "+(selector.keys().size()-1));
                }else if (selectionKey.isReadable()){
                    //注册读事件
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer byteBuffer = bufferMap.getOrDefault(socketChannel.getRemoteAddress(),ByteBuffer.allocate(128));
                    StringBuilder sb = new StringBuilder();
                    int read;
                    try{
                        while((read =socketChannel.read(byteBuffer))>0){
                            if(read<byteBuffer.limit()){
                                byte[] array = byteBuffer.array();
                                byte[] bytes = Arrays.copyOf(array, byteBuffer.position());
                                sb.append(new String(bytes, StandardCharsets.UTF_8));
                            }else{
                                sb.append(new String(byteBuffer.array(), StandardCharsets.UTF_8));
                            }
                            byteBuffer.flip();
                        }
                    }catch (IOException e){
                        iterator.remove();
                        SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                        socketChannel.close();
                        System.out.println(remoteAddress+" 下线了！在线总人数 = "+(selector.keys().size()-1));
                        continue;
                    }
                    String message = sb.toString();
                    message=socketChannel.getRemoteAddress()+": "+message;
                    System.out.println(socketChannel.getRemoteAddress()+"说："+ message);
                    Set<SelectionKey> keys = selector.keys();
                    for (SelectionKey sk :
                            keys) {
                        if(sk.channel() instanceof SocketChannel){
                            SocketChannel sc = (SocketChannel)sk.channel();
                            if(sc==socketChannel){
                                continue;
                            }
                            sc.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                }
                iterator.remove();
            }
        }
    }
}
