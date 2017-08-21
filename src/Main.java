import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //异步 服务器端发布服务
                    RpcExporter.exporter("localhost",8080);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //客户服务器 代理类
        RpcImporter<EchoService> importer = new RpcImporter<>();

        EchoService echo = importer.importer(EchoServiceImpl.class,new InetSocketAddress("localhost",8080));

        System.out.println(echo.echo("Are you Ok ?"));
    }
}
