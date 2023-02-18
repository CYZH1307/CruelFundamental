用自己熟悉的语言创建一个线程池。
### 参考：《java多线程编程实战指南》p116-117 p133-134
```
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.concurrent.*;

public class SmsVerficationCodeSender {
    private  static final ExecutorService EXECUTOR =new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t=new Thread(r,"VerfCodeSender");
                    t.setDaemon(true);
                    return t;
                }
            },new ThreadPoolExecutor.DiscardPolicy());

    /**
     *生成并下发验证码短信到指定手机号码
     * 
     * @param msisdn 短信接收方号码
     */
    public void sendVerificationSms(final String msisdn){
        Runnable task=new Runnable() {
            @Override
            public void run() {
                //生成强随机数验证码
                int verificationCode =ThreadSpecificSecureRandom.INSTANCE.nextInt(9999999);
                DecimalFormat df = new DecimalFormat("000000");
                String txtVerCode =df.format(verificationCode);

                //发送验证码短信
                sendSms(msisdn,txtVerCode);
            }
        };
        EXECUTOR.submit(task);
    }
    private void sendSms(String msisdm,String verificationCode){
        System.out.println("Sending verification code "+verificationCode +" to " + msisdm);
    }

}
 class ThreadSpecificSecureRandom{
    //该类的唯一实例
    public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();
    /**
     * SECURE_RANDOM相当于模式角色：ThreadSpecificStorage.TSObjectProxy.
     * SecureRandom相当于模式角色：ThreadSpecificStorage.TSObject.
     */
    private static final ThreadLocal<SecureRandom> SECURITY_RANDOM = new ThreadLocal<SecureRandom>(){
      @Override
      protected SecureRandom initialValue(){
          SecureRandom srnd;
          try{
              srnd =SecureRandom.getInstance("SHA1PRNG");
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
              srnd = new SecureRandom();  
          }
          return srnd;
      }
    };
    //私有构造器
    private ThreadSpecificSecureRandom(){
        
    }
    public int nextInt(int upperBound){
        SecureRandom secureRnd = SECURITY_RANDOM.get();
        return secureRnd.nextInt();
    }
    public void setSeed(long seed){
        SecureRandom secureRnd = SECURITY_RANDOM.get();
        secureRnd.setSeed(seed);
    }
}
```
