package utils;

public class StringUtils {

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
        for (E elem : enumClass.getEnumConstants()) {
            if (elem.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
