package org.sparrow.common.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.BitSet;

/**
 * Created by mauricio on 20/01/2016.
 */
public class BloomFilter
{
    private BitSet bitset;
    private int bitSetSize;
    private int hashCount;

    public BloomFilter() {}

    public BloomFilter(int numElements, double falsePositiveProbability) {
        bitSetSize = calculateBitSetSize(numElements, falsePositiveProbability);
        hashCount = calculateHashCount(bitSetSize, numElements);
        bitset = new BitSet(bitSetSize);
    }

    public BloomFilter(BitSet bitset, int bitSetSize, int hashCount) {
        this.bitset = bitset;
        this.bitSetSize = bitSetSize;
        this.hashCount = hashCount;
    }

    public void add(String key) {
        for (int idx : getHashes(key)) {
            bitset.set(idx);
        }
    }

    public boolean contains(String key) {
        for (int idx : getHashes(key)) {
            if (!bitset.get(idx)) {
                return false;
            }
        }
        return true;
    }

    public BitSet getBitset() {
        return bitset;
    }

    public int getBitSetSize() {
        return bitSetSize;
    }

    public int getHashCount() {
        return hashCount;
    }

    public void setBitset(BitSet bitset) {
        this.bitset = bitset;
    }

    public void setBitSetSize(int bitSetSize) {
        this.bitSetSize = bitSetSize;
    }

    public void setHashCount(int hashCount) {
        this.hashCount = hashCount;
    }

    public int[] getHashes(String key) {
        int[] result = new int[hashCount];
        int hash1 = MurmurHash.hash32(ByteBuffer.wrap(key.getBytes()), 0, key.length(), 0);
        int hash2 = MurmurHash.hash32(ByteBuffer.wrap(key.getBytes()), 0, key.length(), hash1);
        for (int i = 0; i < hashCount; i++) {
            result[i] = Math.abs((hash1 + i * hash2) % bitSetSize);
        }
        return result;
    }

    public int calculateBitSetSize(int expectedNumElement, double falsePositiveProbability) {
        return (int) -Math.ceil((expectedNumElement * Math.log(falsePositiveProbability)) / (Math.pow(Math.log(2), 2)));
    }

    public int calculateHashCount(int bitSetSize, int expectedNumElement) {
        return (int) Math.round((bitSetSize / expectedNumElement) * Math.log(2));
    }

    public static class BloomFilterSerializer {
        public static byte[] serializer(BloomFilter bf) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream bos = new DataOutputStream(baos);
            byte[] bytes;
            try {
                bytes = bf.getBitset().toByteArray();
                bos.writeInt(bytes.length+8);
                bos.writeInt(bf.getBitSetSize());
                bos.writeInt(bf.getHashCount());
                bos.write(bytes);
            } catch (IOException ex) {
            } finally {
                bytes = null;
                try {

                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException ex) {
                }
            }
            return baos.toByteArray();
        }

        public static BloomFilter deserialize(byte[] in) {
            ByteArrayInputStream bais = new ByteArrayInputStream(in);
            DataInputStream dis = new DataInputStream(bais);
            BloomFilter bf = null;
            byte[] bytes = null;
            int size = 0;
            try {
                bf = new BloomFilter();
                size = dis.readInt();
                bf.setBitSetSize(dis.readInt());
                bf.setHashCount(dis.readInt());
                bytes = new byte[size-8];
                dis.read(bytes);
                bf.setBitset(BitSet.valueOf(bytes));
            } catch (IOException e) {
            } finally {
                try {
                    dis.close();
                } catch (IOException e) {
                }
            }
            return bf;
        }
    }
}
