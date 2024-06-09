package com.animusic.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.ilnitsk.animusic.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JsonMergePatchService {
    @Autowired
    private ObjectMapper mapper;

    public <T> T mergePatch(JsonNode patch, T targetBean, Class<T> beanClass) {
        try {
            JsonNode original = mapper.valueToTree(targetBean);
            JsonNode patched = JsonMergePatch.fromJson(patch).apply(original);
            return mapper.treeToValue(patched,beanClass);
        } catch (Exception e) {
            throw new BadRequestException("Invalid patch request",e);
        }
    }

}
