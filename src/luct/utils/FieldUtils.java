package luct.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Id;

import org.apache.commons.lang3.ArrayUtils;

public class FieldUtils {

	/**
	 * returns all fields inside fields variable including fields from the super class
	 * @param type
	 * @param fields
	 */
	private static <T> List<Field> getFields(Class<T> type) {
		List<Field> fields = new ArrayList<>();
		for(Class<?> c = type; c != null; c = c.getSuperclass()){
			fields.addAll(0, Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}

	/**
	 * Returns a {@link Field} with a specified fieldName
	 * @param type
	 * @param fieldName
	 * @return
	 */
	public static <T> Field getDeclaredField(Class<T> type, String fieldName) {
		Field result = null;
		for(Field field: getFields(type)){
			if(field.getName().equals(fieldName)){
				result = field;
			}
		}
		return result;
	}

	/**
	 * @param type
	 * @param string
	 * @return
	 */
	public static Field getIdField(Class<?> type) {
		Field idField = null;
		for(Field field: getFields(type)){
			if(field.getAnnotation(Id.class) != null){
				idField = field;
			}
		}
		return idField;
	}

	public static int getFieldIndex(Class<?> type, String fieldName) {
		List<Field> fields = getFields(type);
		for(int i = 0; i < fields.size(); i++){
			if(fields.get(i).getName().equals(fieldName)){
				return i;
			}
		}
		throw new IllegalArgumentException("Filed '"+fieldName+"' is not found in "+ type);
	}
}
