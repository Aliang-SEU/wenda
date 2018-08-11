package com.aliang.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Aliang
 * @Date 2018/8/10 10:42
 * @Version 1.0
 **/
public class EventModel {

    private EventType type;
    private int actorId;
    private int entityType;
    //某一个实体的ID 依赖于entityType
    private int entityId;
    private int entityOwnerId;

    //扩展变量
    private Map<String, String> exts = new HashMap<>();

    public EventModel() {

    }

    public EventModel(EventType type) {
        this.type = type;
    }

    //改写get/set
    public EventModel setExts(String key, String value){
        this.exts.put(key, value);
        return this;
    }

    public String getExts(String key){
        return this.exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
