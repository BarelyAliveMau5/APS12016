package classes;

public class Insetion
{
    public String[] insertion_sort(String values[])
    {
        int i, j;
        String ivalue;
        for (i = 1; i < values.length; i++)
        {
            j = i;
            ivalue = values[i];
            while ((j > 0) && (values[j - 1].compareTo(ivalue) > 0))
            {
                values[j] = values[j - 1];
                j--;
            }
            values[j] = ivalue;
        }
        return values;
    }
}
