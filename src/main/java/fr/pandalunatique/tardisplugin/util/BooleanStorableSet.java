package fr.pandalunatique.tardisplugin.util;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BooleanStorableSet<T> {

    private Set<T> set;

    public BooleanStorableSet() {
        this.set = new HashSet<>();
    }

    public BooleanStorableSet(Set<T> set) {

        this();
        this.set.addAll(set);

    }

    public void add(T element) {
        this.set.add(element);
    }

    public void remove(T element) {
        this.set.remove(element);
    }

    public boolean contains(T element) {
        return this.set.contains(element);
    }

    public Set<T> getSet() {
        return this.set;
    }

    public void setSet(Set<T> set) {
        this.set = set;
    }

    public void clear() {
        this.set.clear();
    }

    public int size() {
        return this.set.size();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public int toInteger() {

        if(this.set.isEmpty()) return 0;

        int result = 0;

        try {

            Method ordinal = this.set.iterator().next().getClass().getMethod("ordinal");

            for(T element : this.set) {

                int bit = (int) ordinal.invoke(element);
                result += Math.pow(2, bit);

            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    public static int maxBit(int value) {

        int max = 0;
        while(value > 0) {

            value /= 2;
            max++;

        }

        return max;

    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <V> BooleanStorableSet<V> fromInteger(int integer, Class<V> clazz) {

        try {

            Set<V> set = new HashSet<>();

            Method values = clazz.getMethod("values");
            V[] enumValues = (V[]) values.invoke(null);

            int currentBit = BooleanStorableSet.maxBit(integer) - 1;
            int currentValue = integer;

            while(currentBit >= 0 && currentValue > 0) {

                if(currentValue % 2 == 1) {

                    V element = enumValues[currentBit];
                    set.add(element);

                }
                currentValue /= 2;
                currentBit--;
            }

            return new BooleanStorableSet<V>(set);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

}
