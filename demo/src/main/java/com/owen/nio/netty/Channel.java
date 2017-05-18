package com.owen.nio.netty;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Channel {

    public static void main(String[] args) throws Exception {
        Channel.channel();
    }

    /**
     * 测试channel OK,I'm understand
     * 
     * @throws Exception
     */
    public static void channel() throws Exception {

        RandomAccessFile aFile = new RandomAccessFile("/Users/owen/Documents/workspace/owen-jar/src/main/resources/nio/netty/nio-channel-data.txt",
                "rw");
        FileChannel inChannel = aFile.getChannel();

        // 缓存的buffer大小，
        ByteBuffer buf = ByteBuffer.allocate(12);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            // 注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据。下一节会深入讲解Buffer的更多细节。
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

}
