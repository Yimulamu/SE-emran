import org.junit.Test;
import static org.junit.Assert.*;

public class SimHashTest {

    //测试1：完全相同文本，海明距离=0
    @Test
    public void testSameText() {
        String s = "今天天气很好，适合出门散步";
        long h1 = Main.simHash(s);
        long h2 = Main.simHash(s);
        int dis = Main.getHammingDistance(h1, h2);
        assertEquals(0, dis);
    }

    //测试2：空字符串
    @Test
    public void testEmptyString() {
        long h = Main.simHash("");
        assertEquals(0, h);
    }

    //测试3：短文本
    @Test
    public void testShortText() {
        long h = Main.simHash("你好");
        assertEquals(0, h);
    }

    //测试4：完全不同文本
    @Test
    public void testDifferentText() {
        String s1 = "人工智能是未来方向";
        String s2 = "篮球比赛今晚开赛";
        long h1 = Main.simHash(s1);
        long h2 = Main.simHash(s2);
        int dis = Main.getHammingDistance(h1, h2);
        assertTrue(dis > 10);
    }

    //测试5：少量修改的文本
    @Test
    public void testSlightlyModify() {
        String s1 = "春天公园里开满鲜花";
        String s2 = "春天的公园里开满鲜花";
        long h1 = Main.simHash(s1);
        long h2 = Main.simHash(s2);
        int dis = Main.getHammingDistance(h1, h2);
        assertTrue(dis <= 10);
    }

    //测试6：数字文本
    @Test
    public void testNumberText() {
        long h = Main.simHash("1234567890 1122334455");
        assertNotEquals(0, h);
    }

    //测试7：带标点
    @Test
    public void testPunctuation() {
        String s1 = "你好！世界。";
        String s2 = "你好世界";
        long h1 = Main.simHash(s1);
        long h2 = Main.simHash(s2);
        assertEquals(h1, h2);
    }

    //测试8：英文文本
    @Test
    public void testEnglishText() {
        long h = Main.simHash("I love computer science");
        assertNotEquals(0, h);
    }

    //测试9：长中文
    @Test
    public void testLongChinese() {
        long h = Main.simHash("计算机科学是研究信息计算的学科，包含算法、操作系统等。");
        assertNotEquals(0, h);
    }

    //测试10：海明距离基础
    @Test
    public void testHammingBase() {
        assertEquals(0, Main.getHammingDistance(100,100));
    }
}