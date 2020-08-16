package com.revature.models;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Update {
	private static final Logger log = LogManager.getLogger(Update.class);
	private Timestamp updateTime;
	private int updater;
	
	public Update() {
		super();
		log.debug("Blank Update object created.");
	}
	
	public Update(int updater) {
		super();
	    this.updateTime = new Timestamp(System.currentTimeMillis());
		this.updater = updater;
	}
	
	public Update(Timestamp updateTime, int updater) {
		super();
		this.updateTime = updateTime;
		this.updater = updater;
		log.debug("Full Update object created.");
	}
	
	public Timestamp getUpdateTime() {
		log.debug("updateTime returned.");
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		log.debug("updateTime set.");
		this.updateTime = updateTime;
	}
	public int getUpdater() {
		log.debug("updater returned.");
		return updater;
	}
	public void setUpdater(int updater) {
		log.debug("updater set.");
		this.updater = updater;
	}
	@Override
	public String toString() {
		return "Update = [updateTime=" + updateTime.toString() + ", updater=" + updater + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + updater;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			log.debug("These are the same Updates.");
			return true;
		}
		if (obj == null) {
			log.debug("UpdateB is a null Update.");
			return false;
		}
		if (getClass() != obj.getClass()) {
			log.debug("One of these isn't an Update!");
			return false;
		}
		Update other = (Update) obj;
		if (updateTime == null) {
			log.debug("UpdateA has a null time.");
			if (other.updateTime != null) {
				log.debug("... but UpdateB doesn't.");
				return false;
			}
		} else if (!updateTime.equals(other.updateTime)) {
			log.debug("These Updates have different updateTimes.");
			return false;
		}
		if (updater != other.updater) {
			log.debug("These Updates have different updaters.");
			return false;
		}
		log.debug("These Updates are the same!");
		return true;
	}
}