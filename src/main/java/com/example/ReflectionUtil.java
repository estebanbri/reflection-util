package com.example;

import com.example.annotation.FieldFrom;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ReflectionUtil {

    /**
     * Class.forName: permite cargar una clase en memoria atraves de un call al class loader.
     * newInstance(): nos permite crear instancias (es decir objeto)
     * @param className
     * @return retorna una instancia (es decir objeto)
     */
    public static Object getInstanceByClassName(String className)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * field.get(objeto)  : permite obtener valores de fields de objetos
     * @param target El objeto a obtener el valor de su field
     * @param fieldName El nombre del field a obtener
     * @return retorna el valor del field
     */
    public static Object getValueFromField(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    /**
     * field.set(objeto, valor)  : permite setear valores a fields de objetos
     * @param target El objeto a setearle el valor a su field
     * @param fieldName El nombre del field a setear
     * @param value El valor del field que queremos que tenga
     * @return retorna el objeto con el field seteado
     */
    public static Object setValueToField(Object target, String fieldName, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
        return target;
    }

    /**
     * method.invoke(objeto): permite invocar uno de los metodos de un objeto
     * @param target El objeto a invocar su metodo
     * @param getterName El nombre del metodo getter
     * @return retorna el valor del field del objeto haciendo introspect de sobre el getter de la clase
     */
    public static Object getValueFromFieldUsingGetter(Object target, String getterName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = target.getClass().getDeclaredMethod(getterName);
        return method.invoke(target);
    }

    /**
     * method.invoke(objeto, value): permite invocar uno de los metodos de un objeto pasando n parametros segun los argumentos del metodo
     * @param target El objeto a invocar su metodo
     * @param setterName El nombre del metodo setter
     * @param paramType El tipo de dato del parametro del metodo
     * @param value El valor a setearle al field del objeto
     */
    public static void setValueToFieldUsingSetter(Object target, String setterName, Class<?> paramType, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = target.getClass().getDeclaredMethod(setterName, paramType);
        method.invoke(target, value);
    }

    /**
     * field.getAnnotation(annotationClazz): permite obtener la anotacion del field
     * @param clazz La clase que tiene la anotacion en el field
     * @return el valor de la anotation que se encuentra sobre el field
     */
    public static  Object getValueFromAnnotatedField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(FieldFrom.class))
                .map(field -> field.getAnnotation(FieldFrom.class))
                .map(fieldFrom -> fieldFrom.role())
                .findFirst().orElse(null);
    }

    public static  Object setValueToParentClassRecursive(Object source, Class<?> targetClazz, String fieldName) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field sourceField = source.getClass().getDeclaredField(fieldName);
        sourceField.setAccessible(true);
        Object value = sourceField.get(source);

        Field targetField = getFieldRecursive(targetClazz, fieldName);
        targetField.setAccessible(true);
        Object targetObject = targetField.getDeclaringClass().getDeclaredConstructor().newInstance();
        targetField.set(targetObject, value);
        return targetObject;
    }

    public static Object setAllValuesRecursive(Object source, Class<?> targetClazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] sourceField = source.getClass().getDeclaredFields();
        Object targetObject = targetClazz.getDeclaredConstructor().newInstance();
        Arrays.stream(sourceField).forEach(field -> {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                Field targetField = getFieldRecursive(targetClazz, field.getName());
                targetField.setAccessible(true);
                targetField.set(targetObject, value);
            } catch (Exception e) {}
        });
        return targetObject;
    }

    private static Field getFieldRecursive(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        while(field == null && clazz.getSuperclass() != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            }catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return field;
    }


}

