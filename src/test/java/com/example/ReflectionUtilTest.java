package com.example;

import com.example.dto.DTO;
import com.example.entity.SPBaseProduct;
import com.example.entity.SPInternet;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;


// getDeclaredFields()=todos  vs getField()= only public accessibility
public class ReflectionUtilTest {
    
    @Test
    public void test_GetInstance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object obj = ReflectionUtil.getInstanceByClassName("com.example.entity.SPBaseProduct");
        assertEquals("SPBaseProduct{integralSolution='null'}",obj.toString());
    }

    @Test
    public void test_GetValueFromField() throws IllegalAccessException, NoSuchFieldException {
        String value = (String) ReflectionUtil.getValueFromField(getMock(), "integralSolution");
        assertEquals("Fibra Verde",value);
    }

    @Test
    public void test_SetValueToField() throws IllegalAccessException, NoSuchFieldException {
        SPBaseProduct spBaseProduct = getEmptyMock();
        ReflectionUtil.setValueToField(spBaseProduct, "integralSolution","Fibra Roja");
        assertEquals("Fibra Roja", spBaseProduct.getIntegralSolution());
    }

    @Test
    public void test_GetValueFromFieldUsingGetter() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String value = (String) ReflectionUtil.getValueFromFieldUsingGetter(getMock(), "getIntegralSolution");
        assertEquals("Fibra Verde",value);
    }

    @Test
    public void test_SetValueToFieldUsingSetter() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SPBaseProduct spBaseProduct = getEmptyMock();
        ReflectionUtil.setValueToFieldUsingSetter(spBaseProduct, "setIntegralSolution", String.class, "Fibra Roja");
        assertEquals("Fibra Roja", spBaseProduct.getIntegralSolution());
    }

    @Test
    public void test_GetValueToAnnotatedField() {
        Object annotationValue = ReflectionUtil.getValueFromAnnotatedField(SPBaseProduct.class);
        assertEquals("Sales Engineer", annotationValue);
    }

    @Test
    public void test_SetValueToParentClassRecursive() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        DTO dto = new DTO();
        dto.setBandwidth("10 GBPS");
        dto.setIntegralSolution("Fibra verde");

        SPBaseProduct spBaseProduct = (SPBaseProduct) ReflectionUtil.setValueToParentClassRecursive(dto, SPInternet.class, "integralSolution");
        SPInternet spInternet = (SPInternet) ReflectionUtil.setValueToParentClassRecursive(dto, SPInternet.class, "bandwidth");
        assertEquals("SPBaseProduct{integralSolution='Fibra verde'}", spBaseProduct.toString());
        assertEquals("SPInternet{bandwidth='10 GBPS'}", spInternet.toString());
    }

    @Test
    public void test_SetAllValuesRecursive() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        DTO dto = new DTO();
        dto.setBandwidth("10 GBPS");
        dto.setIntegralSolution("Fibra verde");

        SPBaseProduct spBaseProduct = (SPBaseProduct) ReflectionUtil.setAllValuesRecursive(dto, SPBaseProduct.class);
        assertEquals("SPBaseProduct{integralSolution='Fibra verde'}", spBaseProduct.toString());

        SPInternet spInternet = (SPInternet) ReflectionUtil.setAllValuesRecursive(dto, SPInternet.class);
        assertEquals("SPInternet{bandwidth='10 GBPS'}", spInternet.toString());
    }


    private SPBaseProduct getMock() {
        SPBaseProduct sp = new SPBaseProduct();
        sp.setIntegralSolution("Fibra Verde");
        return sp;
    }

    private SPBaseProduct getEmptyMock() {
        return new SPBaseProduct();
    }
    
}