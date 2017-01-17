/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.mongodb.WriteResult;
import java.util.List;

/**
 *
 * @author MumbaiZone
 */
public interface Repository<T> {

	public List<T> getAllObjects();

	public void saveObject(T object);

	public T getObject(String id);

	public WriteResult updateObject(String id, String name);

	public void deleteObject(String id);

	public void createCollection();

	public void dropCollection();
}
