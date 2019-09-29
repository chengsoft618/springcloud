//package com.iyysoft.msdp.common.core.util.seriazle;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.ByteArrayInputStream;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//public class KryoUtil2 {
//
//    private static final int DEFAULT_CAPACITY = 4096;
//    private static final int DEFAULT_MAX_CAPACITY = -1;
//    private static final Logger log = LoggerFactory
//            .getLogger(KryoUtil.class);
//
//    private KryoUtil2(){}
//    private static volatile KryoUtil kryoInstant=null;
//
//    private  final Queue<Kryo> pool = new ConcurrentLinkedQueue<Kryo>();
//
//    public static KryoUtil getKryoUtilInstance(){
//        if(kryoInstant==null){
//            synchronized(KryoUtil.class){
//                if(kryoInstant==null){
//                    kryoInstant=new KryoUtil();
//                }
//            }
//        }
//        return kryoInstant;
//    }
//    private  Kryo createKryo() {
//        Kryo kryo = new Kryo();
//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(false);
//
//        return kryo;
//    }
//
//    private  void returnKryo(Kryo kryo) {
//        pool.offer(kryo);
//    }
//
////	private  static void close() {
////		pool.clear();
////	}
//
//    public  Kryo getKryo() {
//        Kryo kryo = pool.poll();
//        if (kryo == null) {
//            kryo = createKryo();
//        }
//        return kryo;
//    }
//
//    public  byte[] writeToByteArray(Object object) {
//        Output output = new Output(DEFAULT_CAPACITY, DEFAULT_MAX_CAPACITY);
//        Kryo kryo = getKryo();
//        try {
//            kryo.writeClassAndObject(output, object);
//            return output.toBytes();
//        } catch (Exception e) {
//            log.error("Kryo serialize error!", e);
//        } finally {
//            returnKryo(kryo);
//            output.close();
//            output = null;
//        }
//        return null;
//    }
//
//    public  Object readFromByteArray(byte[] bytes) {
//        if (bytes == null) {
//            return null;
//        }
//        Input input = new Input(new ByteArrayInputStream(bytes), bytes.length);
//        Kryo kryo = getKryo();
//        try {
//            return kryo.readClassAndObject(input);
//        } catch (Exception e) {
//            log.error("Kryo deserialize error!", e);
//        } finally {
//            returnKryo(kryo);
//            input.close();
//            input = null;
//        }
//        return null;
//    }
//
//}
