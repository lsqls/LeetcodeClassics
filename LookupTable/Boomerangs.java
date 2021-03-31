package LookupTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

//447 149
class Boomerangs{
    public int numberOfBoomerangs (int[][] points) {
        List<HashMap<Integer,Integer>>  list=new ArrayList<>();
        for(int[] point1:points){
            HashMap<Integer,Integer> iMap=new HashMap<>();
            for(int[] point2:points){
                int dis=(point1[0]-point2[0])*(point1[0]-point2[0])+(point1[1]-point2[1])*(point1[1]-point2[1]);
                iMap.put(dis, iMap.getOrDefault(dis, 0)+1);
            }
            list.add(iMap);
        }

        int cnt=0;
        for(HashMap<Integer,Integer> map:list){
            for(int val:map.values()){
                if(val>1)
                    cnt+=(val)*(val-1);
            }
        }
        return cnt;

    }

    public int maxPoints(int[][] points) {
        HashMap<Line,Integer> iMap=new HashMap<>();
        int max=0;
        for(int[] point1:points){
            for(int[] point2:points){
                
                Line line=new Line(point1,point2);
                if(iMap.containsKey(line))
                    continue;
                for(int[] point:points){
                    if(line.existPoint(point))
                        iMap.put(line, iMap.getOrDefault(line, 0)+1);
                }
                max=Math.max(max, iMap.getOrDefault(line,0));
            }
        }
        for(Entry<Line,Integer> entry:iMap.entrySet()){
            System.out.println(entry.getKey().toString()+" : "+entry.getValue());
        }
        return max;
    }
    final double MINDIFF=0.0001;
    class Line{
        double k;
        double b;
        boolean isVertical=false;
        double vertical;
        Line(int []point1,int []point2){
            double x1=point1[0],y1=point1[1],x2=point2[0],y2=point2[1];
            if(x2-x1==0){
                vertical=x1;
                isVertical=true;
            }
            else{   
                k=(y2-y1)/(x2-x1);
                b=y1-k*x1;
            }
                  
        }
        @Override
        public int hashCode() {
            if(isVertical)
                return Double.valueOf(vertical).hashCode();
            else
                return (int)(Double.valueOf(k).hashCode()>>1)+(int)(Double.valueOf(b).hashCode()>>2);
        }
        @Override
        public boolean equals(Object obj) {
            Line l=(Line)obj;
            if(l.isVertical&&isVertical)
                return Math.abs(l.vertical-vertical)<MINDIFF;
            else if (l.isVertical||isVertical)
                return false;
            else
                return Math.abs(k-l.k)<MINDIFF&&Math.abs(b-l.b)<MINDIFF;
        }

        boolean existPoint(int[] point){
            
            double x=point[0],y=point[1];
            if(isVertical)
                return Math.abs(x-vertical)<MINDIFF;
            else
                return Math.abs(k*x+b-y)<MINDIFF;
        }
        @Override
        public String toString() {
            if(isVertical)
                return "x="+vertical;
            else
                return "y="+k+"x+"+b;
        }
    }


    
    public static void main(String[] args) {
        Boomerangs boomerangs=new Boomerangs();
        int [][] points={{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
        boomerangs.maxPoints(points);

        
    }
}