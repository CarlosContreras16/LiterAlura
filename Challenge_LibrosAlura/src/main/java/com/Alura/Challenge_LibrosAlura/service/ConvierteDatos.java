package com.Alura.Challenge_LibrosAlura.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ConvierteDatos implements IConvierteDatos{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            //buscamos el array de results en el Json
            JsonNode resultArray = jsonNode.get("results");
            JsonNode resultfirst = resultArray.get(0);
            String resultado = resultfirst.toString();
            return objectMapper.readValue(resultado,clase);
            //si existe el libro en la api que lo devuelva
//            if (resultArray != null && resultArray.size()>0)
//            {
//                //solo toma el primer resultado y lo envia a los records
//                JsonNode resultfirst = resultArray.get(0);
//                return objectMapper.treeToValue(resultfirst,clase);
//            }
//            else {
//                System.out.println("no existe este libro");
////                throw new RuntimeException("No existe ese libro");
//            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public <T> List<T> obtenerDatosArray(String json, Class<T> clase){
    try {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode resultarray = rootNode.get("results");
        if (resultarray != null && resultarray.size() > 0) {
            List<T> resultList = new ArrayList<>();
            for (JsonNode node : resultarray){
                T result = objectMapper.treeToValue(node, clase);
                resultList.add(result);
//                return objectMapper.treeToValue((TreeNode) resultList, clase);
            }
            return resultList;
        }else {
            throw new RuntimeException("No se pudo encontrar ningun resultado");
        }
    }catch (JsonProcessingException e){
        throw new RuntimeException(e.getMessage());
    }
    }
}
