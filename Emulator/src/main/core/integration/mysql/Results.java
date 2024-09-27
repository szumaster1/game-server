package core.integration.mysql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Results.
 */
public class Results {
    private ResultSet set;

    /**
     * Constructs a new Results.
     *
     * @param set the set
     */
    public Results(ResultSet set) {
        this.set = set;
    }

    /**
     * String string.
     *
     * @param column the column
     * @return the string
     */
    public String string(String column) {

        try {

            String result = set().getString(column);

            return result;

        } catch (Exception e) {

        }

        return null;

    }

    /**
     * Integer int.
     *
     * @param column the column
     * @return the int
     */
    public int integer(String column) {

        try {

            int result = set().getInt(column);

            return result;

        } catch (Exception e) {

        }

        return -1;

    }

    /**
     * Columns list.
     *
     * @return the list
     */
    public List<String> columns() {

        try {

            ResultSetMetaData meta = set().getMetaData();
            int count = meta.getColumnCount();

            List<String> columns = new ArrayList<String>(count);

            for (int i = 1; i <= count; i++)
                columns.add(meta.getColumnName(i));

            return columns;

        } catch (Exception e) {

        }

        return null;

    }

    /**
     * Empty boolean.
     *
     * @return the boolean
     */
    public boolean empty() {

        try {
            return !set().next();
        } catch (Exception e) {

        }

        return true;
    }

    /**
     * Integers int [ ].
     *
     * @param values the values
     * @return the int [ ]
     */
    public static int[] integers(String[] values) {

        int[] integers = new int[values.length];

        for (int i = 0; i < integers.length; i++)
            integers[i] = Integer.parseInt(values[i]);

        return integers;

    }

    /**
     * Doubles double [ ].
     *
     * @param values the values
     * @return the double [ ]
     */
    public static double[] doubles(String[] values) {

        double[] integers = new double[values.length];

        for (int i = 0; i < integers.length; i++)
            integers[i] = Double.parseDouble(values[i]);

        return integers;

    }

    /**
     * Set result set.
     *
     * @return the result set
     */
    public ResultSet set() {
        return set;
    }

}
