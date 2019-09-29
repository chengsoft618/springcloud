package com.iyysoft.msdp.common.core.util.seriazle;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;

public class KryoUtil3 {
    // 由于kryo不是线程安全的，所以每个线程都使用独立的kryo
    final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.register(ct, new BeanSerializer<>(kryo, ct));
            return kryo;
        }
    };
    final ThreadLocal<Output> outputLocal = new ThreadLocal<Output>();
    final ThreadLocal<Input> inputLocal = new ThreadLocal<Input>();
    private Class<?> ct = null;

    public KryoUtil3(Class<?> ct) {
        this.ct = ct;
    }

    public Class<?> getCt() {
        return ct;
    }

    public void setCt(Class<?> ct) {
        this.ct = ct;
    }


    public void serialize(Object obj, byte[] bytes) {
        Kryo kryo = getKryo();
        Output output = getOutput(bytes);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

    public void serialize(Object obj, byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Output output = getOutput(bytes, offset, count);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

    /**
     * 获取kryo
     *
     * @return
     */
    private Kryo getKryo() {
        return kryoLocal.get();
    }

    /**
     * 获取Output并设置初始数组
     *
     * @param bytes
     * @return
     */
    private Output getOutput(byte[] bytes) {
        Output output = null;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.setBuffer(bytes);
        }
        return output;
    }

    /**
     * 获取Output
     *
     * @param bytes
     * @return
     */
    private Output getOutput(byte[] bytes, int offset, int count) {
        Output output = null;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.writeBytes(bytes, offset, count);
        }
        return output;
    }

    /**
     * 获取Input
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    private Input getInput(byte[] bytes, int offset, int count) {
        Input input = null;
        if ((input = inputLocal.get()) == null) {
            input = new Input();
            inputLocal.set(input);
        }
        if (bytes != null) {
            input.setBuffer(bytes, offset, count);
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Input input = getInput(bytes, offset, count);
        return (T) kryo.readObjectOrNull(input, ct);
    }

    public <T> T deserialize(byte[] bytes) {
        return deserialize(bytes, 0, bytes.length);
    }

    /**
     * 序列化和反序列化
     * psvm
     *
     * @param args
     */
    public static void main(String[] args) {
//        KryoUtil3 ser = new KryoUtil3(Msg.class);
//        for (int i = 0; i < 10; i++) {
//
//            Msg msg = new Msg();
//
//            msg.setVersion_flag(new byte[] { 1, 2, 3 });
//            msg.setCrc_code((short) 1);
//            msg.setMsg_body(new byte[] { 123, 123, 123, 43, 42, 1, 12, 45, 57, 98 });
//            byte[] bytes = new byte[300];
//            long start = System.nanoTime();
//            ser.serialize(msg, bytes);
//            System.err.println("序列化耗时：" + (System.nanoTime() - start));
//            System.out.println(msg);
//            System.out.println(Arrays.toString(bytes));
//
//            Msg newmsg = null;
//            start = System.nanoTime();
//            newmsg = ser.deserialize(bytes);
//            System.err.println("反序列化耗时：" + (System.nanoTime() - start));
//            System.out.println(newmsg);

    }
}
