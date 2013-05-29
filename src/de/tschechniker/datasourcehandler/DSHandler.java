package de.tschechniker.datasourcehandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import de.tschechniker.datasourcehandler.exceptions.IdNotInizializedException;
import de.tschechniker.datasourcehandler.exceptions.NoConnectionException;

import org.azeckoski.reflectutils.*;

public abstract class DSHandler {

	public abstract boolean testConnection() throws NoConnectionException;

	public abstract void connect() throws NoConnectionException;

	public abstract void disconnect();

	protected abstract void prefill() throws IdNotInizializedException;

	protected String getIDName() throws IllegalArgumentException,
			IllegalAccessException {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field
					.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.Id.class)) {
				return field.getName();
			}
		}
		return null;
	}

	protected String getMappedField(String key)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field
					.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.MappedBy.class)) {
				if (field
						.getAnnotation(
								de.tschechniker.datasourcehandler.annotations.MappedBy.class)
						.value().equals(key)) {
					return field.getName();
				}
			}
		}
		return null;
	}

	protected void setFieldValue(String fieldName, Object value) {
		ReflectUtils.getInstance().setFieldValue(this, fieldName, value);
	}

	protected Object getFieldValue(String fieldName) {
		return ReflectUtils.getInstance().getFieldValue(this, fieldName);
	}

	protected String getMappedBy(String fieldName) {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				if (field
						.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.MappedBy.class)) {
					return field
							.getAnnotation(
									de.tschechniker.datasourcehandler.annotations.MappedBy.class)
							.value();
				} else {
					return null;
				}
			}
		}
		return null;

	}

	protected String getClassMethod() {
		if (this.getClass().isAnnotationPresent(
				de.tschechniker.datasourcehandler.annotations.Method.class)) {
			return this
					.getClass()
					.getAnnotation(
							de.tschechniker.datasourcehandler.annotations.Method.class)
					.value();
		} else {
			return null;
		}
	}

	protected String getClass(Object obj) {
		if (obj.getClass().isAnnotationPresent(
				de.tschechniker.datasourcehandler.annotations.Class.class)) {
			return obj
					.getClass()
					.getAnnotation(
							de.tschechniker.datasourcehandler.annotations.Class.class)
					.value();
		} else {
			return null;
		}
	}

	protected void prefill(Map<String, Object> data)
			throws IllegalArgumentException, IllegalAccessException {
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			if (this.getMappedField(entry.getKey()) != null) {
				this.setFieldValue(this.getMappedField(entry.getKey()),
						entry.getValue());
			}
		}
	}

	/*
	 * Sets the defined id
	 */
	public void setID(Object ID) {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field
					.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.Id.class)) {
				this.setFieldValue(field.getName(), ID);
			}
		}
	}

	/*
	 * Gets the defined id
	 */
	public Object getID() {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field
					.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.Id.class)) {
				return this.getFieldValue(field.getName());
			}
		}
		return null;
	}
	
	public Map<String, Object> getAllCreate(){
		Map<String, Object> fields = new HashMap<String, Object>();
		for (Field field : this.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(de.tschechniker.datasourcehandler.annotations.Create.class)) {
					fields.put(field.getAnnotation(de.tschechniker.datasourcehandler.annotations.Create.class).value(), this.getFieldValue(field.getName()));
				}
		}
		return fields;
	}
}
