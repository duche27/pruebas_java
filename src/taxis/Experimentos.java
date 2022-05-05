package taxis;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Experimentos {
    
    
    // con distancia social por la izq ni dcha
    public static long getMaxAdditionalDinersCountWithSideSocialDistance(long N, long K, int M, long[] S) {
        
        long emptySpaces = 0, availableSeats = 0;
        boolean coincidence = false;
        
        Arrays.sort(S);
        
        for (long seat = 1; seat <= N; seat++) {
            for (long seatedDiner : S) {
                if (seatedDiner == seat) {
                    emptySpaces = 0;
                    coincidence = true;
                    break;
                }
            }
            if (!coincidence) {
                emptySpaces++;
                if (emptySpaces == K * 3) {
                    emptySpaces = 0;
                    availableSeats++;
                }
            } else
                coincidence = false;
        }
        return availableSeats;
    }
    
    // sin distancia social por la izq ni dcha
    public static long getMaxAdditionalDinersCountWithoutSideSocialDistance(long N, long K, int M, long[] S) {
        
        long emptySpaces = 0, availableSeats = 0;
        boolean coincidence = false;
        
        Arrays.sort(S);
        
        for (long seat = 1; seat <= N; seat++) {
            for (long seatedDiner : S) {
                if (seatedDiner == seat) {
                    emptySpaces = 0;
                    coincidence = true;
                    break;
                }
            }
            if (!coincidence) {
                emptySpaces++;
                if ((seat == K + 1) && (emptySpaces + 1 > K)) {
                    emptySpaces -= 1;
                    availableSeats++;
                } else if (emptySpaces == K * 3 || (seat == N && emptySpaces + 1 > K)) {
                    emptySpaces -= (K + 1);
                    availableSeats++;
                }
            } else
                coincidence = false;
        }
        return availableSeats;
    }
    
    public static long getMaxAdditionalDinersCount(long N, long K, int M, long[] S) {
        
        int emptySpaces = 0;
        boolean coincidence = false;
        List<Integer> gaps = new ArrayList<>();
        
        Arrays.sort(S);
        
        for (long seat = 1; seat <= N; seat++) {
            for (long diner : S) {
                if (diner == seat) {
                    gaps.add(emptySpaces);
                    emptySpaces = 0;
                    coincidence = true;
                    break;
                }
            }
            if (!coincidence) {
                emptySpaces++;
                if (seat == N) gaps.add(emptySpaces);
            }
            else coincidence = false;
            
        }
    
        // falta conseguir el floor de la division y ya
//        gaps.stream().map(
//                gap -> Math.floor(gap / (K + 1))
//        ).collect(Collectors.toList());
    
        return gaps.stream().map(
                gap -> gap / (K + 1)
        ).reduce(Long::sum).orElse(0L);
    }
}
