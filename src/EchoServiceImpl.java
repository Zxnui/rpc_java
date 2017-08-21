/**
 * 服务端接口
 * @Auther ZhuXun
 * @Time 2017/8/18 17:09
 */
public class EchoServiceImpl implements EchoService{
    @Override
    public String echo(String ping) {
        return ping!=null?ping+"I am Ok" : "Error";
    }
}
