package groupChat;


import io.netty.util.internal.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChatClient {

    SocketChannel socketChannel;
    Selector selector;
    Map<String, ByteBuffer> bufferMap = new HashMap<>();

    public ChatClient() throws IOException {

        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8007));
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {

        ChatClient chatClient = new ChatClient();
        new Thread(()->{
            try {
                chatClient.readInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String s = scanner.nextLine();
            chatClient.socketChannel.write(ByteBuffer.wrap(s.getBytes()));
        }
    }

    private void readInfo() throws IOException {
        while(!socketChannel.finishConnect()){
            continue;
        }
        while(true){
            if(selector.select(100)==0){
                continue;
            }
            ByteBuffer byteBuffer = bufferMap.getOrDefault(socketChannel.getRemoteAddress(),ByteBuffer.allocate(128));
            StringBuilder sb = new StringBuilder();
            int read;
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
            String message = sb.toString();
            System.out.println(message);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.clear();
        }
    }

}
