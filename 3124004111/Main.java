import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static final int HASH_BITS = 64;

    public static void main(String[] args) {
        System.out.println("====程序启动成功====");
        try {
            String text1 = readFile("orig.txt");
            System.out.println("orig读取成功：" + text1);
            String text2 = readFile("ans.txt");
            System.out.println("ans读取成功：" + text2);

            long hash1 = simHash(text1);
            long hash2 = simHash(text2);
            System.out.println("hash1=" + hash1 + ",hash2=" + hash2);

            int distance = getHammingDistance(hash1, hash2);
            double similarity = 1.0 - (double) distance / HASH_BITS;
            double rate = similarity * 100;

            System.out.printf("海明距离：%d%n", distance);
            System.out.printf("文本相似度：%.2f %% %n", rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取文件
    public static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static long simHash(String text) {
        int[] weight = new int[HASH_BITS];
        String cleanText = text.replaceAll("[^\\p{L}\\p{N}]", "");
        int gramLen = 3;
        // 短文本保护：不足3个字直接返回0
        if (cleanText.length() < gramLen) {
            return 0;
        }
        for (int i = 0; i <= cleanText.length() - gramLen; i++) {
            String gram = cleanText.substring(i, i + gramLen);
            long hash = gram.hashCode();
            for (int j = 0; j < HASH_BITS; j++) {
                long bit = (hash >> j) & 1;
                if (bit == 1) {
                    weight[j]++;
                } else {
                    weight[j]--;
                }
            }
        }
        long simhash = 0;
        for (int i = 0; i < HASH_BITS; i++) {
            if (weight[i] > 0) {
                simhash |= (1L << i);
            }
        }
        return simhash;
    }

    public static int getHammingDistance(long a, long b) {
        long xor = a ^ b;
        return Long.bitCount(xor);
    }
}



//-Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 --enable-native-access=ALL-UNNAMED