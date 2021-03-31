package LookupTable;
/*大部分情况下，两者的实现不具备有序性
set常见方法
add
contains
remove
clear
map常用方法
put
get
remove
keySet
values
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;




// 349 242 202 290 205 451 49
public class Anagrams {
    //349 数组交集 两个set
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> ret=new HashSet<>();
        HashSet<Integer> iset=new HashSet<>();
        for(int val:nums1){
            iset.add(val);
        }
        for(int val:nums2){
            if(iset.contains(val)){
                ret.add(val);
            }
        }
        int[] retArray=new int[ret.size()];
        int i=0;
        for(int val:ret){
            retArray[i++]=val;
        }
        return retArray;
    }
    //242 字母异位词 事实上，根据题目条件限制，使用数组效率更高
    public boolean isAnagram(String s, String t) {
        HashMap<Character,Integer> sfre=new HashMap<>(),tfre=new HashMap<>();
        for(int i=0;i<s.length();i++){
            char key=s.charAt(i);
            sfre.put(key, sfre.containsKey(key)?sfre.get(key)+1:1);
        }
        for(int i=0;i<t.length();i++){
            char key=t.charAt(i);
            tfre.put(t.charAt(i), tfre.containsKey(key)?tfre.get(t.charAt(i))+1:1);
        }
        return tfre.equals(sfre);
        

    }
    // 202 反复调用 getDigitSquareSum(n) 得到的链是一个隐式的链表，可以用hashset检测死循环（链表环）
    public boolean isHappy(int n) {
        HashSet<Integer> iSet=new HashSet<>();
        int sum=n;
        iSet.add(sum);
        while(sum!=1){
            sum=getDigitSquareSum(sum);
            if(iSet.contains(sum))
                return false;
            iSet.add(sum);
            
        }
        return true;
    }
    int getDigitSquareSum(int n){
        int sum=0;
        while(n!=0){
            sum+=(n%10)*(n%10);
            n=n/10;
        }
        return sum;
    }
    //290 使用hashmap记录映射冠关系是最合适不过的了,双向关系使用两个hashmap实现（还有一种解法是记录位置而不是双射关系）
    public boolean wordPattern(String pattern, String str) {
        HashMap<String, Character> str2ch = new HashMap<String, Character>();
        HashMap<Character, String> ch2str = new HashMap<Character, String>();
        int m = str.length();
        int i = 0;
        for (int p = 0; p < pattern.length(); ++p) {
            char ch = pattern.charAt(p);
            if (i >= m) {
                return false;
            }
            int j = i;
            while (j < m && str.charAt(j) != ' ') {
                j++;
            }
            String tmp = str.substring(i, j);
            if (str2ch.containsKey(tmp) && str2ch.get(tmp) != ch) {
                return false;
            }
            if (ch2str.containsKey(ch) && !tmp.equals(ch2str.get(ch))) {
                return false;
            }
            str2ch.put(tmp, ch);
            ch2str.put(ch, tmp);
            i = j + 1;
        }
        return i >= m;
    }


    
    //205 使用hashmap记录映射冠关系是最合适不过的了,双向关系使用两个hashmap实现（还有一种解法是记录位置而不是双射关系）
    public boolean isIsomorphic(String s, String t) {

        HashMap <Character,Character> smap=new HashMap<>();
        HashMap <Character,Character> tmap=new HashMap<>();
        for(int i=0;i<s.length();i++){
            char sch=s.charAt(i),tsh=t.charAt(i);
            if(smap.containsKey(sch)&&!smap.get(sch).equals(tsh))
                    return false;
            if(tmap.containsKey(tsh)&&!tmap.get(tsh).equals(sch))
                    return false;
            smap.put(sch, tsh);
            tmap.put(tsh, sch);
                
          
                
            
        }
        return true;
    }
    //451 map根据值排序
    //https://www.cnblogs.com/ltb6w/p/7862251.html
    public String frequencySort(String s) {
        Map<Character,Integer> cMap=new HashMap<>();
        for(int i=0;i<s.length();i++)
            cMap.put(s.charAt(i), cMap.getOrDefault(s.charAt(i), 0)+1);
        //这里将map.entrySet()转换成list
        List<Entry<Character,Integer>> list = new ArrayList<>(cMap.entrySet());

        //然后通过比较器来实现排序
        Collections.sort(list,(a,b) -> {
            //降序排序
            return b.getValue().compareTo(a.getValue());
        });
        StringBuilder sb=new StringBuilder();
        for(Entry<Character,Integer> entry:list){  
            for(int i=0;i<entry.getValue();i++)
                sb.append(entry.getKey()); 
        } 
        return sb.toString();

    }
    // 49 双重hashmap，词频用hashmap表示，指向词频相同的字符串组 , 另一种方法是每个word排序【这个做法经常出现】后作为hashmap索引值
    /*
    或者手写hash
    算术基本定理
    任何一个大于1的自然数N，如果N不为质数，那么N可以唯一分解成有限个质数的乘积
    [a, z]Unicode编码 - 97=[0, 25] 对应26个质数。每字母代表质数的乘积表示字符串
    乘法交换律忽略字母顺序。算术基本定理保证不同质数 或 相同质数不同个数，乘积唯一
    prime = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101]
    【这个hash方法可以使用，可以针对多维】
    */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] counts = new int[26];
            int length = str.length();
            for (int i = 0; i < length; i++) {
                counts[str.charAt(i) - 'a']++;
            }
            // 将每个出现次数大于 0 的字母和出现次数按顺序拼接成字符串，作为哈希表的键
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }

    public static void main(String[] args) {
        Anagrams ag=new Anagrams();
        String[] strs={"eat", "tea", "tan", "ate", "nat", "bat"};
        ag.groupAnagrams(strs);

    }
}
