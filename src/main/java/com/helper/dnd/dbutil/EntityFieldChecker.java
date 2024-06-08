package com.helper.dnd.dbutil;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityFieldChecker {

  public static <T> Set<String> getSupportedFields(Class<T> entityClass) {
    Set<String> supportedFields = new HashSet<>();
    if (entityClass.isAnnotationPresent(Entity.class)) {
      Field[] fields = entityClass.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Id.class)) {
          supportedFields.add("id");
        } else {
          supportedFields.add(field.getName());
        }
      }
    }
    return supportedFields;
  }

  public static <T> boolean areQueryParamsSupported(
      Class<T> entityClass, Map<String, String> queryParams) {
    Set<String> supportedFields = getSupportedFields(entityClass);
    for (String key : queryParams.keySet()) {
      if (!supportedFields.contains(key)) {
        return false;
      }
    }
    return true;
  }
}
