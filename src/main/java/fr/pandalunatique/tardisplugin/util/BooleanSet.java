package fr.pandalunatique.tardisplugin.util;

import fr.pandalunatique.tardisplugin.tardis.TardisAppearance;
import org.w3c.dom.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class BooleanSet<T> {

    private Set<T> values;

    public BooleanSet() {
        this.values = new HashSet<T>();
    }

    public boolean add(T value) {
        return this.values.add(value);
    }

    public boolean remove(T value) {
        return this.values.remove(value);
    }

    public void clear() {
        this.values.clear();
    }

    public boolean contains(T value) {
        return this.values.contains(value);
    }

    public Set<T> getValues() {
        return this.values;
    }

    public String print() {

        String a = "";
        for(T v : this.values) {
            a += v.toString() + " ";
        }

        return a;

    }

    //FIXME: Find a better way to do this
    public int toInteger() {

        int value = 0;

        for (T t : this.values) {
            try {
                value += Math.pow(2, (int) (short) t.getClass().getMethod("getBit").invoke(t));
            } catch(NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return value;

    }

    //FIXME: Find a better way to do this
    public static <E> BooleanSet<E> fromInteger(int value, Class<E> clazz) {

        BooleanSet<E> set = new BooleanSet<>();
        String binary = new StringBuilder(Integer.toBinaryString(value)).reverse().toString();

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                try {
                    set.add((E) clazz.getMethod("fromBit", int.class).invoke(null, i));
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return set;
    }

}
