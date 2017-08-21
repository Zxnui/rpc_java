import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * RPC客户端，调用服务端发布的服务
 * @Auther ZhuXun
 * @Time 2017/8/18 16:50
 */
public class RpcImporter<S> {
    public S importer(final Class<?> serviceClass,final InetSocketAddress add){

        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(),new Class<?>[] {serviceClass.getInterfaces()[0]}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;

                ObjectOutputStream out = null;
                ObjectInputStream input = null;

                try {
                    socket = new Socket();
                    socket.connect(add);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeUTF(serviceClass.getName());
                    out.writeUTF(method.getName());
                    out.writeObject(method.getParameterTypes());
                    out.writeObject(args);
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                } finally {
                    if (socket!=null){
                        socket.close();
                    }
                    if (out!=null){
                        out.close();
                    }
                    if (input!=null){
                        input.close();
                    }
                }
            }

        });
    }
}
