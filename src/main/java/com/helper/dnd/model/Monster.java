package com.helper.dnd.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "monster",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Monster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  private String description;
  private String size;
  private String type;
  private Integer armor_class;
  private Integer hit_points;
  private Integer strength;
  private Integer dexterity;
  private Integer constitution;
  private Integer intelligence;
  private Integer wisdom;
  private Integer charisma;
  private Double challenge_rating;
  private String ref_title;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSize() {
    return this.size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getArmor_class() {
    return this.armor_class;
  }

  public void setArmor_class(Integer armor_class) {
    this.armor_class = armor_class;
  }

  public Integer getHit_points() {
    return this.hit_points;
  }

  public void setHit_points(Integer hit_points) {
    this.hit_points = hit_points;
  }

  public Integer getStrength() {
    return this.strength;
  }

  public void setStrength(Integer strength) {
    this.strength = strength;
  }

  public Integer getDexterity() {
    return this.dexterity;
  }

  public void setDexterity(Integer dexterity) {
    this.dexterity = dexterity;
  }

  public Integer getConstitution() {
    return this.constitution;
  }

  public void setConstitution(Integer constitution) {
    this.constitution = constitution;
  }

  public Integer getIntelligence() {
    return this.intelligence;
  }

  public void setIntelligence(Integer intelligence) {
    this.intelligence = intelligence;
  }

  public Integer getWisdom() {
    return this.wisdom;
  }

  public void setWisdom(Integer wisdom) {
    this.wisdom = wisdom;
  }

  public Integer getCharisma() {
    return this.charisma;
  }

  public void setCharisma(Integer charisma) {
    this.charisma = charisma;
  }

  public Double getChallenge_rating() {
    return this.challenge_rating;
  }

  public void setChallenge_rating(Double challenge_rating) {
    this.challenge_rating = challenge_rating;
  }

  public String getRef_title() {
    return this.ref_title;
  }

  public void setRef_title(String ref_title) {
    this.ref_title = ref_title;
  }
}
