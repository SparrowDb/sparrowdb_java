package org.sparrow.cache;

import sun.misc.Unsafe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * Created by mauricio on 7/5/16.
 */
public class UnsafeEntry
{
    public static final UnsafeEntry instance = new UnsafeEntry();
    private Unsafe unsafe;
    private final int intSize = Integer.BYTES;

    private UnsafeEntry()
    {
        try
        {
            unsafe = getUnsafe();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Unsafe getUnsafe() throws Exception
    {
        Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
        unsafeConstructor.setAccessible(true);
        return unsafeConstructor.newInstance();
    }

    long putEntry(Object o) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        baos.flush();

        byte[] buff = baos.toByteArray();

        long address = unsafe.allocateMemory(buff.length + intSize);
        unsafe.putInt(address, buff.length);

        for (int i = 0; i < buff.length; i++)
        {
            unsafe.putByte(address + intSize + i, buff[i]);
        }

        return address;
    }

    Object getEntry(long address) throws Exception
    {
        int size = unsafe.getInt(address);

        byte[] buff = new byte[size];
        for (int i = 0; i < size; i++)
        {
            buff[i] = unsafe.getByte(address + intSize + i);
        }

        return new ObjectInputStream(new ByteArrayInputStream(buff)).readObject();
    }

    void free(long address)
    {
        unsafe.freeMemory(address);
    }
}
