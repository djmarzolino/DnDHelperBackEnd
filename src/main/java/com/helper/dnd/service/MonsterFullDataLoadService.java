package com.helper.dnd.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helper.dnd.model.Monster;
import com.helper.dnd.repository.MonsterRepository;
import com.helper.dnd.util.StringUtils;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MonsterFullDataLoadService {

  @Autowired private MonsterRepository monsterRepository;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private static final Logger logger = LogManager.getLogger(MonsterFullDataLoadService.class);

  public MonsterFullDataLoadService(
      RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
    this.restTemplate = restTemplateBuilder.build();
    this.objectMapper = objectMapper;
  }

  public void fetchDataAndSave() {
    String url = "https://api.open5e.com/v1/monsters/";
    boolean finishedFetching = false;
    try {

      while (!finishedFetching) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
          String json = response.getBody();
          if (json != null) {
            JsonNode rootNode = objectMapper.readTree(json);
            if (rootNode != null) {
              JsonNode resultsNode = rootNode.get("results");
              JsonNode nextNode = rootNode.get("next");
              if (nextNode == null) {
                finishedFetching = true;
              } else {
                url = nextNode.asText();
              }
              if (resultsNode != null && resultsNode.isArray()) {
                Iterator<JsonNode> elements = resultsNode.elements();
                while (elements.hasNext()) {
                  JsonNode element = elements.next();
                  if (isValidSource(getStringField(element, "document__title"))) {
                    Monster monster = convertJsonToEntity(element);

                    if (monsterRepository.findByName(monster.getName()).isEmpty()) {
                      monsterRepository.save(monster);
                    }
                  }
                }
              }
            }
          } else {
            logger.error("Data returned from call was null");
          }
        }
      }
    } catch (RestClientException | JsonProcessingException e) {
      logger.error("Exception occurred while fetching data", e);
    } catch (Exception e) {
      logger.error("Unexpected error occurred", e);
    }
  }

  private Monster convertJsonToEntity(JsonNode element) {
    Monster monster = new Monster();
    monster.setName(getStringField(element, "name"));
    monster.setDescription(StringUtils.truncate(getStringField(element, "desc"), 255));
    monster.setSize(getStringField(element, "size"));
    monster.setType(getStringField(element, "type"));
    monster.setArmor_class(getIntegerField(element, "armor_class"));
    monster.setHit_points(getIntegerField(element, "hit_points"));
    monster.setStrength(getIntegerField(element, "strength"));
    monster.setDexterity(getIntegerField(element, "dexterity"));
    monster.setConstitution(getIntegerField(element, "constitution"));
    monster.setIntelligence(getIntegerField(element, "intelligence"));
    monster.setWisdom(getIntegerField(element, "wisdom"));
    monster.setCharisma(getIntegerField(element, "charisma"));
    monster.setChallenge_rating(getDoubleField(element, "cr"));
    monster.setRef_title(getStringField(element, "document__title"));
    return monster;
  }

  private String getStringField(JsonNode element, String fieldName) {
    if (element == null) {
      return null;
    }
    return element.get(fieldName).asText();
  }

  private Integer getIntegerField(JsonNode element, String fieldName) {
    if (element == null) {
      return null;
    }
    return element.get(fieldName).asInt();
  }

  private Double getDoubleField(JsonNode element, String fieldName) {
    if (element == null) {
      return null;
    }
    return element.get(fieldName).asDouble();
  }

  private boolean isValidSource(String title) {
    if ("Level Up Advanced 5e Monstrous Menagerie".equals(title)
        || "Creature Codex".equals(title)) {
      return false;
    }
    return true;
  }
}
