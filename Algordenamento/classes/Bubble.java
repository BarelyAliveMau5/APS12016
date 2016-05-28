package classes;

public class Bubble {
	public String[] bubble_srt(String values[]) {
        int n = values.length;
        String temp;
        for (int j = n; j >= 0; j--) {
            for (int i = 0; i < n - 1; i++) {
                if (values[i].compareTo(values[j]) < 0) {
                    temp = values[i];
                	values[i] = values[j];
                	values[j] = temp;
                }
            }
        }
        return values;
    }
}
